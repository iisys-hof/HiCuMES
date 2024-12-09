package de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager;

import de.iisys.sysint.hicumes.core.entities.*;
import de.iisys.sysint.hicumes.core.entities.enums.EBookingState;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.CamundaMachineOccupationDTO;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.CamundaSubProductionStepDTO;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.NoteDTO;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.TimeRecordDTO;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import org.hibernate.Hibernate;
import org.hibernate.annotations.QueryHints;

import javax.ejb.*;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
@AccessTimeout(value=60000)
public class PersistService {

    @EJB
    PersistenceManager persistenceManager;

    final List<EMachineOccupationStatus> ALL_STATES = Arrays.asList(EMachineOccupationStatus.values());
    final List<EMachineOccupationStatus> NECESSARY_STATES = Arrays.asList(EMachineOccupationStatus.RUNNING, EMachineOccupationStatus.PAUSED, EMachineOccupationStatus.READY_TO_START, EMachineOccupationStatus.FINISHED);
    final List<EMachineOccupationStatus> BOOKING_STATES = Arrays.asList(EMachineOccupationStatus.RUNNING, EMachineOccupationStatus.PAUSED, EMachineOccupationStatus.FINISHED, EMachineOccupationStatus.ARCHIVED);
    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupation> getAllCamundaMachineOccupations(boolean loadAllStates) {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }
        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery(
                "SELECT DISTINCT c FROM CamundaMachineOccupation c " +
                        "JOIN FETCH c.machineOccupation m " +
                        "LEFT JOIN FETCH m.parentMachineOccupation " +
                        "LEFT JOIN FETCH m.productionOrder " +
                        "LEFT JOIN FETCH m.tool " +
                        "LEFT JOIN FETCH m.machine " +
                        "LEFT JOIN FETCH m.productionSteps " +
                        "WHERE c.machineOccupation.status IN :states")
                .setParameter("states", states).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).getResultList();
        return fetchCamundaMachineOccupationSubLists(em, results);
    }


    /*public CamundaMachineOccupationDTO(String productionOrdnerName, String machineOccupationName, String orderType, String usage, String machineId, String machineName, String status) {
        this.productionOrdnerName = productionOrdnerName;
        this.machineOccupationName = machineOccupationName;
        this.orderType = orderType;
        this.machineId = machineId;
        this.machineName = machineName;
        this.status = status;
    }*/

    final String dtoSelect =
            "SELECT " +
                    "c.id, p.name, m.name, p.id, co.name, " + //0-4
                    "aps.name, prod.name, prod.id, meas.amount, meas.unitString, " + //5-9
                    "m.plannedStartDateTime, m.plannedEndDateTime, ma.externalId, ma.name, m.status, " + //10-14
                    "n.noteString, n.userName, n.creationDateTime, matype.externalId, csubStep.id, " + //15-19
                    "csubStep.formKey, csubStep.name, csubStep.filledFormField, csubStep.formField, subStep.id, " + //20-24
                    "m.id, p.id, tr.startDateTime, tr.endDateTime, u.userName, " + //25-29
                    "ma.id, n.id, p.id, ameas.amount, m.actualStartDateTime " + //30 - 34
                    "FROM CamundaMachineOccupation c JOIN c.machineOccupation m " +
                    "LEFT JOIN m.productionOrder p " +
                    "LEFT JOIN p.measurement meas " +
                    "LEFT JOIN p.product prod " +
                    "LEFT JOIN p.notes n " +
                    "LEFT JOIN c.activeProductionStep aps " +
                    "LEFT JOIN aps.machineType matype " +
                    "LEFT JOIN p.customerOrder co " +
                    "LEFT JOIN m.tool t " +
                    "LEFT JOIN m.machine ma " +
                    "LEFT JOIN m.department dept " +
                    "LEFT JOIN c.camundaSubProductionSteps csubStep " +
                    "LEFT JOIN csubStep.subProductionStep subStep " +
                    "LEFT JOIN subStep.timeRecords tr " +
                    "LEFT JOIN tr.responseUser u " +
                    "LEFT JOIN m.totalProductionNumbers tpn " +
                    "LEFT JOIN tpn.acceptedMeasurement ameas ";

    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupationDTO> getAllCamundaMachineOccupationsDTO(boolean loadAllStates) {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }
        var em = persistenceManager.getEntityManager();
        List<Object[]> resultList = em.createQuery(
                         dtoSelect +
                                "WHERE m.status IN :states " +
                                "AND m.parentMachineOccupation IS NULL " +
                                "ORDER BY csubStep.id", Object[].class)
                .setParameter("states", states)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        return mapDTOResultList(resultList, em);
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupationDTO> getAllCamundaMachineOccupationsDTODept(String deptId, boolean loadAllStates) {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }
        var em = persistenceManager.getEntityManager();
        List<Object[]> resultList = em.createQuery(
                         dtoSelect +
                                "WHERE m.status IN :states " +
                                "AND m.parentMachineOccupation IS NULL " +
                                "AND dept.externalId = :deptId " +
                                "ORDER BY csubStep.id", Object[].class)
                .setParameter("states", states)
                .setParameter("deptId", deptId)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        return mapDTOResultList(resultList, em);
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupationDTO> getAllCamundaMachineOccupationsDTODepartments(List<String> departments, boolean loadAllStates) {
        if(departments == null || departments.isEmpty())
        {
            return getAllCamundaMachineOccupationsDTO(loadAllStates);
        }
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }
        var em = persistenceManager.getEntityManager();
        List<Object[]> resultList = em.createQuery(
                         dtoSelect +
                                "WHERE m.status IN :states " +
                                "AND m.parentMachineOccupation IS NULL " +
                                "AND dept.externalId in :departments " +
                                "ORDER BY csubStep.id", Object[].class)
                .setParameter("states", states)
                .setParameter("departments", departments)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        return mapDTOResultList(resultList, em);
    }


    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupationDTO> getCamundaMachineOccupationsDTOByMachineTypes(List<String> machineTypes, boolean loadAllStates) throws DatabaseQueryException {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }
        var em = persistenceManager.getEntityManager();
        List<Object[]> resultList = em.createQuery(
                         dtoSelect +
                                "WHERE m.status IN :states " +
                                "AND matype.externalId IN :machineTypes " +
                                "AND m.parentMachineOccupation IS NULL " +
                                "ORDER BY csubStep.id", Object[].class)
                .setParameter("states", states)
                .setParameter("machineTypes", machineTypes)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        return mapDTOResultList(resultList, em);
    }


    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupationDTO> getCamundaMachineOccupationsDTOByMachineTypeDept(String deptId, List<String> machineTypes, boolean loadAllStates) throws DatabaseQueryException {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }
        var em = persistenceManager.getEntityManager();
        List<Object[]> resultList = em.createQuery(
                         dtoSelect +
                                "WHERE m.status IN :states " +
                                "AND matype.externalId IN :machineTypes " +
                                "AND m.parentMachineOccupation IS NULL " +
                                "AND dept.externalId = :deptId " +
                                "ORDER BY csubStep.id", Object[].class)
                .setParameter("states", states)
                .setParameter("machineTypes", machineTypes)
                .setParameter("deptId", deptId)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        return mapDTOResultList(resultList, em);
    }
    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupationDTO> getCamundaMachineOccupationsDTOByMachineTypeDepartments(List<String> departments, List<String> machineTypes, boolean loadAllStates) throws DatabaseQueryException {
        if(departments == null || departments.isEmpty())
        {
            return getCamundaMachineOccupationsDTOByMachineTypes(machineTypes, loadAllStates);
        }
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }
        var em = persistenceManager.getEntityManager();
        List<Object[]> resultList = em.createQuery(
                                dtoSelect +
                                "WHERE m.status IN :states " +
                                "AND matype.externalId IN :machineTypes " +
                                "AND m.parentMachineOccupation IS NULL " +
                                "AND dept.externalId in :departments " +
                                "ORDER BY csubStep.id", Object[].class)
                .setParameter("states", states)
                .setParameter("machineTypes", machineTypes)
                .setParameter("departments", departments)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        return mapDTOResultList(resultList, em);
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupationDTO> getAllOpenCamundaMachineOccupationsDTOForUser(String userName) {

        var em = persistenceManager.getEntityManager();
        List<Object[]> resultList = em.createQuery(
                         dtoSelect +
                                "WHERE m.status = :state " +
                                "AND u.userName = :userName " +
                                "AND tr.endDateTime IS NULL " +
                                "AND m.parentMachineOccupation IS NULL " +
                                "ORDER BY csubStep.id", Object[].class)
                .setParameter("state", EMachineOccupationStatus.RUNNING)
                .setParameter("userName", userName)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        return mapDTOResultList(resultList, em);
    }

    private Stream<CamundaMachineOccupationDTO> mapDTOResultList(List<Object[]> resultList, EntityManager em)
    {
        List<CamundaMachineOccupationDTO> dtos = resultList.stream()
                .collect(Collectors.groupingBy(row -> (Long) row[0])) // Group by CamundaMAchineOccupation ID
                .values()
                .stream()
                .map(rows -> {
                    Object[] cMachineOcc = rows.get(0); //Rows beinhalten die ganzen CSubProdSteps
                    long cmoId = (long) cMachineOcc[0];
                    long moId = (long) cMachineOcc[25];
                    String productionOrdnerName = (String) cMachineOcc[1];
                    String machineOccupationName = (String) cMachineOcc[2];
                    String orderType = (String) "";
                    String usage = (String) cMachineOcc[4];
                    String prodStepName = (String) cMachineOcc[5];
                    String productName = (String) cMachineOcc[6];
                    String productDetail = (String) "";
                    Double amount = (Double) cMachineOcc[8];
                    String unit = (String) cMachineOcc[9];
                    LocalDateTime startDateTime = (LocalDateTime) cMachineOcc[10];
                    LocalDateTime endDateTime = (LocalDateTime) cMachineOcc[11];
                    LocalDateTime actualStartDateTime = (LocalDateTime) cMachineOcc[34];
                    String machineExtId = (String) cMachineOcc[12];
                    Long machineId = (Long) cMachineOcc[30];
                    String machineName = (String) cMachineOcc[13];
                    EMachineOccupationStatus status = (EMachineOccupationStatus) cMachineOcc[14];
                    String machineType = (String) cMachineOcc[18];
                    Double bamount = (Double) cMachineOcc[33];
                    Integer sumStep = (Integer) 0;
                    Double totalProductionNumbersAccepted = (Double) cMachineOcc[33];

                    List<NoteDTO> noteDTOs = null;
                    if(cMachineOcc[1] != null && cMachineOcc[31] != null) {
                        // Create a list of NoteDTOs
                        noteDTOs = rows.stream().collect(Collectors.groupingBy(row -> row[31])) // Group by NoteId
                                .values()
                                .stream()
                                .map(innerRows -> {
                                    Object[] notes = innerRows.get(0);
                                    if (notes[15] != null && notes[17] != null) {
                                        return new NoteDTO((String) notes[15], (String) notes[16], (LocalDateTime) notes[17]);
                                    }
                                    return null;
                                })
                                .collect(Collectors.toList());
                    }
                    List<CamundaSubProductionStepDTO> cSubStepDTOs = null;
                    if(cMachineOcc[19] != null && cMachineOcc[24] != null) {
                        cSubStepDTOs = rows.stream().collect(Collectors.groupingBy(row -> row[19])) // Group by cSubProdStep
                                .values()
                                .stream()  //iterate over all camundaMachineOccupations
                                .map(innerRows -> {
                                    /*var cSubProdStep = innerRows.get(0);
                                    List<Object[]> subProdSteps = em.createQuery(
                                                    "SELECT " +
                                                            "tr.startDateTime, tr.endDateTime, tr.responseUser.userName " + //0-4
                                                            "FROM SubProductionStep subStep " +
                                                            "LEFT JOIN subStep.timeRecords tr " +
                                                            "WHERE subStep.id = :subStepId " +
                                                            "ORDER BY tr.id", Object[].class)
                                            .setParameter("subStepId", cSubProdStep[24])
                                            .setHint("org.hibernate.cacheable", true)
                                            .getResultList();

                                    if(subProdSteps != null && !subProdSteps.isEmpty()) {
                                        List<TimeRecordDTO> timeRecordDTOS = subProdSteps.stream().collect(Collectors.groupingBy(row -> row[0])) // Group by CamundaMachineOccupation
                                                .values()
                                                .stream()
                                                .map(trRow ->
                                                {

                                                    Object[] timeRecords = trRow.get(0);
                                                    return new TimeRecordDTO((LocalDateTime) timeRecords[0], (LocalDateTime) timeRecords[1], (String) timeRecords[2]);
                                                })
                                                .collect(Collectors.toList());
                                        return new CamundaSubProductionStepDTO((Long) cSubProdStep[19], (String) cSubProdStep[20], (String) cSubProdStep[21], (String) cSubProdStep[22], (String) cSubProdStep[23], timeRecordDTOS);
                                    }
                                    else {
                                        return null;
                                    }*/

                                    var cSubProdStep = innerRows.get(0);

                                    List<TimeRecordDTO> timeRecordDTOS = innerRows.stream().collect(Collectors.groupingBy(row -> row[24])) // Group by CamundaMachineOccupation
                                            .values()
                                            .stream()
                                            .map(trRow ->
                                            {

                                                Object[] timeRecords = trRow.get(0);
                                                return new TimeRecordDTO((LocalDateTime) timeRecords[27], (LocalDateTime) timeRecords[28], (String) timeRecords[29]);
                                            })
                                            .collect(Collectors.toList());
                                    return new CamundaSubProductionStepDTO((Long) cSubProdStep[19], (String) cSubProdStep[20], (String) cSubProdStep[21], (String) cSubProdStep[22], (String) cSubProdStep[23], timeRecordDTOS);

                                })
                                .collect(Collectors.toList());
                    }


                    List<CamundaMachineOccupationDTO> subMOs = null;
                    //Wahrscheinlich ein Sammelauftrag
                    if(cMachineOcc[1] == null) {

                        List<Object[]> subResultList = em.createQuery(
                                        "SELECT " +
                                                "subm.id, subp.name, subm.name, subp.id, subco.name, " + //0-4
                                                "subprod.name, subprod.id, submeas.amount, submeas.unitString, subm.plannedStartDateTime, " + //5-9
                                                "subm.plannedEndDateTime, subma.externalId, subma.name, subm.status, subn.noteString, " + //10-14
                                                "subn.userName, subn.creationDateTime, subp.id, subp.id, subameas.amount,  " + //15-19
                                                "subm.actualStartDateTime  " + //20
                                                "FROM MachineOccupation m " +
                                                "LEFT JOIN m.subMachineOccupations subm " +
                                                "LEFT JOIN subm.productionOrder subp " +
                                                "LEFT JOIN subp.measurement submeas " +
                                                "LEFT JOIN subp.product subprod " +
                                                "LEFT JOIN subp.notes subn " +
                                                "LEFT JOIN subp.customerOrder subco " +
                                                "LEFT JOIN subm.tool subt " +
                                                "LEFT JOIN subm.machine subma " +
                                                "LEFT JOIN subm.totalProductionNumbers subtpn " +
                                                "LEFT JOIN subtpn.acceptedMeasurement subameas " +
                                                "WHERE m.id = :mId", Object[].class)
                                .setParameter("mId", cMachineOcc[25])
                                .setHint("org.hibernate.cacheable", true)
                                .getResultList();

                        subMOs = subResultList.stream()
                                .collect(Collectors.groupingBy(row -> (Long) row[0])) // Group by CamundaMAchineOccupation ID
                                .values()
                                .stream()
                                .map(subm -> {
                                    Object[] subMachineOcc = subm.get(0); //Rows beinhalten die ganzen CSubProdSteps
                                    long   subcmoId = 0;
                                    String subproductionOrdnerName = (String) subMachineOcc[1];
                                    String submachineOccupationName = (String) subMachineOcc[2];
                                    String suborderType = (String) "";
                                    String subusage = (String) subMachineOcc[4];
                                    String subprodStepName = null;
                                    String subproductName = (String) subMachineOcc[5];
                                    String subproductDetail = (String) "";
                                    Double subamount = (Double) subMachineOcc[7];
                                    String subunit = (String) subMachineOcc[8];
                                    LocalDateTime substartDateTime = (LocalDateTime) subMachineOcc[9];
                                    LocalDateTime subendDateTime = (LocalDateTime) subMachineOcc[10];
                                    LocalDateTime subactualStartDateTime = (LocalDateTime) subMachineOcc[20];
                                    String submachineExtId = (String) subMachineOcc[11];
                                    String submachineName = (String) subMachineOcc[12];
                                    EMachineOccupationStatus substatus = (EMachineOccupationStatus) subMachineOcc[13];
                                    String submachineType = null;
                                    Double subbamount = (Double) subMachineOcc[19];
                                    Integer subsumStep = (Integer) 0;
                                    Double subtotalProductionNumbersAccepted = (Double) subMachineOcc[19];


                                    return new CamundaMachineOccupationDTO(
                                            subcmoId,null, subproductionOrdnerName, submachineOccupationName, suborderType, subusage, subprodStepName, subproductName, subproductDetail,
                                            subamount, subbamount, subunit, substartDateTime, subendDateTime, null,submachineExtId, submachineName, substatus, subsumStep, null, submachineType, null, null, subtotalProductionNumbersAccepted, subactualStartDateTime
                                    );
                                })
                                .collect(Collectors.toList());
                    }

                    if(noteDTOs != null) {
                        noteDTOs.sort((o1, o2) ->
                        {
                            if (o1.getCreationDateTime().isEqual(o2.getCreationDateTime())) {
                                return 0;
                            } else if (o1.getCreationDateTime().isBefore(o2.getCreationDateTime())) {
                                return -1;
                            } else {
                                return 1;
                            }
                        });
                    }
                    // Create CamundaMachineOccupationDTO
                    return new CamundaMachineOccupationDTO(
                            cmoId, moId, productionOrdnerName, machineOccupationName, orderType, usage, prodStepName, productName, productDetail,
                            amount, bamount, unit, startDateTime, endDateTime, machineId, machineExtId, machineName, status, sumStep, noteDTOs, machineType, cSubStepDTOs, subMOs, totalProductionNumbersAccepted, actualStartDateTime
                    );
                })
                .collect(Collectors.toList());

        return dtos.stream();
    }


    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupation> getAllCamundaMachineOccupationsByTimeRange(boolean loadAllStates, LocalDateTime startTime, LocalDateTime endTime) {

        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery(
                "SELECT DISTINCT cam FROM CamundaMachineOccupation cam JOIN FETCH cam.machineOccupation moc LEFT JOIN FETCH moc.parentMachineOccupation LEFT JOIN FETCH moc.productionOrder LEFT JOIN FETCH moc.tool LEFT JOIN FETCH moc.machine LEFT JOIN FETCH moc.productionSteps JOIN moc.subProductionSteps step JOIN step.timeRecords tr WHERE cam.machineOccupation.status IN :states AND tr.timeRecordType IS NOT NULL AND tr.endDateTime BETWEEN :startDateTime AND :endDateTime ORDER BY tr.endDateTime")
                .setParameter("startDateTime", startTime).setParameter("endDateTime", endTime).setParameter("states", BOOKING_STATES).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).getResultList();
        return fetchCamundaMachineOccupationSubLists(em, results);
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<BookingEntry> getAllBookingEntriesByTimeRange(boolean loadAllStates, LocalDateTime startTime, LocalDateTime endTime) {

        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery(
                "SELECT DISTINCT b FROM BookingEntry b " +
                        "JOIN b.machineOccupation moc " +
                        "JOIN b.subProductionStep step " +
                        "JOIN step.timeRecords tr " +
                        //"WHERE b.machineOccupation.status IN :states " +
                        "WHERE tr.timeRecordType IS NOT NULL AND tr.endDateTime BETWEEN :startDateTime AND :endDateTime", BookingEntry.class)
                .setParameter("startDateTime", startTime).setParameter("endDateTime", endTime)./*setParameter("states", BOOKING_STATES).*/setHint("org.hibernate.cacheable", true).getResultList();
        /*var ohcResults = em.createQuery(
                        "SELECT DISTINCT b FROM BookingEntry b " +
                                "JOIN b.overheadCost ohc " +
                                "JOIN ohc.timeRecord tr " +
                                //"WHERE b.machineOccupation.status IN :states " +
                                "WHERE tr.endDateTime BETWEEN :startDateTime AND :endDateTime", BookingEntry.class)
                .setParameter("startDateTime", startTime).setParameter("endDateTime", endTime)./*setParameter("states", BOOKING_STATES).setHint("org.hibernate.cacheable", true).getResultList();*/
        /*return results.stream()
                .map(OverheadCost::getOverheadCostCenter)
                .distinct().limit(5);

         */
        /*for(BookingEntry entry : results)
        {
            initSubLists(entry.getMachineOccupation());
        }*/
        //results.addAll(ohcResults);
        return results.stream();
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<BookingEntry> getAllBookingEntriesByTimeRangeAndUser(boolean loadAllStates, LocalDateTime startTime, LocalDateTime endTime, String userName) {

        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery(
                "SELECT DISTINCT b FROM BookingEntry b " +
                        "JOIN b.machineOccupation moc " +
                        "JOIN b.subProductionStep step " +
                        "JOIN step.timeRecords tr " +
                        //"WHERE b.machineOccupation.status IN :states " +
                        "WHERE tr.timeRecordType IS NOT NULL AND tr.endDateTime BETWEEN :startDateTime AND :endDateTime and b.user.userName = :userName", BookingEntry.class)
                .setParameter("startDateTime", startTime).setParameter("endDateTime", endTime).setParameter("userName", userName)./*setParameter("states", BOOKING_STATES).*/setHint("org.hibernate.cacheable", true).getResultList();
        /*var ohcResults = em.createQuery(
                        "SELECT DISTINCT b FROM BookingEntry b " +
                                "JOIN b.overheadCost ohc " +
                                "JOIN ohc.timeRecord tr " +
                                //"WHERE b.machineOccupation.status IN :states " +
                                "WHERE tr.endDateTime BETWEEN :startDateTime AND :endDateTime", BookingEntry.class)
                .setParameter("startDateTime", startTime).setParameter("endDateTime", endTime)./*setParameter("states", BOOKING_STATES).setHint("org.hibernate.cacheable", true).getResultList();*/
        /*return results.stream()
                .map(OverheadCost::getOverheadCostCenter)
                .distinct().limit(5);

         */
        /*for(BookingEntry entry : results)
        {
            initSubLists(entry.getMachineOccupation());
        }*/
        //results.addAll(ohcResults);
        return results.stream();
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<BookingEntry> getAllBookingEntriesOverheadCostsByTimeRange(boolean loadAllStates, LocalDateTime startTime, LocalDateTime endTime) {

        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery(
                        "SELECT DISTINCT b FROM BookingEntry b " +
                                "JOIN b.overheadCost ohc " +
                                "JOIN ohc.timeRecord tr " +
                                //"WHERE b.machineOccupation.status IN :states " +
                                "WHERE tr.endDateTime BETWEEN :startDateTime AND :endDateTime", BookingEntry.class)
                .setParameter("startDateTime", startTime).setParameter("endDateTime", endTime)./*setParameter("states", BOOKING_STATES).*/setHint("org.hibernate.cacheable", true).getResultList();
        /*return results.stream()
                .map(OverheadCost::getOverheadCostCenter)
                .distinct().limit(5);

         */
        /*for(BookingEntry entry : results)
        {
            initSubLists(entry.getMachineOccupation());
        }*/
        //results.addAll(ohcResults);
        return results.stream();
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<BookingEntry> getAllBookingEntriesOverheadCostsByTimeRangeAndUser(boolean loadAllStates, LocalDateTime startTime, LocalDateTime endTime, String userName) {

        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery(
                        "SELECT DISTINCT b FROM BookingEntry b " +
                                "JOIN b.overheadCost ohc " +
                                "JOIN ohc.timeRecord tr " +
                                //"WHERE b.machineOccupation.status IN :states " +
                                "WHERE tr.endDateTime BETWEEN :startDateTime AND :endDateTime and b.user.userName = :userName", BookingEntry.class)
                .setParameter("startDateTime", startTime).setParameter("endDateTime", endTime).setParameter("userName", userName)./*setParameter("states", BOOKING_STATES).*/setHint("org.hibernate.cacheable", true).getResultList();
        /*return results.stream()
                .map(OverheadCost::getOverheadCostCenter)
                .distinct().limit(5);

         */
        /*for(BookingEntry entry : results)
        {
            initSubLists(entry.getMachineOccupation());
        }*/
        //results.addAll(ohcResults);
        return results.stream();
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupation> getCamundaMachineOccupationByMachineType(String machineTypeExternalId, boolean loadAllStates) throws DatabaseQueryException {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }
        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE c.activeProductionStep.machineType.externalId = :machineType AND c.machineOccupation.status IN :states")
                .setParameter("states", states).setParameter("machineType", machineTypeExternalId).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).getResultList();
        return fetchCamundaMachineOccupationSubLists(em, results);
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<CamundaMachineOccupation> getCamundaMachineOccupationByProductionOrder(String productionOrderExternalId, boolean loadAllStates) throws DatabaseQueryException {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }

        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE c.machineOccupation.productionOrder.externalId = :productionOrder AND c.machineOccupation.status IN :states")
                .setParameter("states", states).setParameter("productionOrder", productionOrderExternalId).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).getResultList();
        return fetchCamundaMachineOccupationSubLists(em, results);
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<MachineOccupation> getMachineOccupationByProductionOrder(String productionOrderExternalId, boolean loadAllStates) throws DatabaseQueryException {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }

        var em = persistenceManager.getEntityManager();
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE m.productionOrder.externalId = :productionOrder AND m.status IN :states")
                .setParameter("states", states).setParameter("productionOrder", productionOrderExternalId).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).getResultList();
        if (results.size() > 0) {
            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subMachineOccupations WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();

            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subProductionSteps WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.activeToolSettings WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
        }
        return results.stream();
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<MachineOccupation> getMachineOccupationByProductionOrderWithGraph(String productionOrderExternalId, boolean loadAllStates) throws DatabaseQueryException {
        List<EMachineOccupationStatus> states;
        if (loadAllStates) {
            states = ALL_STATES;
        } else {
            states = NECESSARY_STATES;
        }

        var em = persistenceManager.getEntityManager();

        EntityGraph graph = em.getEntityGraph("Graph.MachineOccupation.ProductionSteps");
        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
        //   .setHint("org.hibernate.cacheable", true);
        var results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE m.productionOrder.externalId = :productionOrder AND m.status IN :states")
                .setParameter("states", states).setParameter("productionOrder", productionOrderExternalId).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).setHint("javax.persistence.fetchgraph", graph).getResultList();
        if (results.size() > 0) {
            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subMachineOccupations WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();

            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subProductionSteps WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.activeToolSettings WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
        }
        return results.stream();
    }

    @Lock(LockType.READ)
    @Transactional
    public Stream<MachineOccupation> getMachineOccupationsFinishedBeforeDate(LocalDateTime date) throws DatabaseQueryException {

        var em = persistenceManager.getEntityManager();
        var results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE m.status = 'FINISHED' AND ((m.actualEndDateTime IS NOT NULL AND m.actualEndDateTime < :beforeDate) OR (m.actualEndDateTime IS NULL AND m.updateDateTime < :beforeDate))")
                .setParameter("beforeDate", date).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).getResultList();
        if (results.size() > 0) {
            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subMachineOccupations WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();

            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subProductionSteps WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
            results = em.createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.activeToolSettings WHERE m in :machineOccupations")
                    .setParameter("machineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
        }
        return results.stream();
    }

    @Lock(LockType.READ)
    private Stream<CamundaMachineOccupation> fetchCamundaMachineOccupationSubLists(EntityManager em, List results) {
        if (results.size() > 0) {
            results = em.createQuery(
                    "SELECT DISTINCT c FROM CamundaMachineOccupation c " +
                            "JOIN FETCH c.machineOccupation m " +
                            "LEFT JOIN FETCH m.subMachineOccupations " +
                            "WHERE c in :camundaMachineOccupations")
                    .setParameter("camundaMachineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();

            results = em.createQuery(
                    "SELECT DISTINCT c FROM CamundaMachineOccupation c " +
                            "JOIN FETCH c.machineOccupation m " +
                            "LEFT JOIN FETCH m.subProductionSteps " +
                            "WHERE c in :camundaMachineOccupations")
                    .setParameter("camundaMachineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
            results = em.createQuery(
                    "SELECT DISTINCT c FROM CamundaMachineOccupation c " +
                            "JOIN FETCH c.machineOccupation m " +
                            "LEFT JOIN FETCH m.activeToolSettings " +
                            "WHERE c in :camundaMachineOccupations")
                    .setParameter("camundaMachineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
        }
        return results.stream();
    }


    @Lock(LockType.READ)
    public CamundaMachineOccupation getCamundaMachineOccupationById(long id) throws DatabaseQueryException {
        //var c = persistenceManager.getById(CamundaMachineOccupation.class, id);
        //initSubLists(c);

        var em = persistenceManager.getEntityManager();
        var results = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE c.id = :id")
                .setParameter("id", id).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).getResultList();
        if (results.size() > 0) {
            results = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.subMachineOccupations WHERE c in :camundaMachineOccupations")
                    .setParameter("camundaMachineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();

            results = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.subProductionSteps WHERE c in :camundaMachineOccupations")
                    .setParameter("camundaMachineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
            results = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.activeToolSettings WHERE c in :camundaMachineOccupations")
                    .setParameter("camundaMachineOccupations", results)
                    .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
        }

        return (CamundaMachineOccupation) results.get(0);

        //return c;
    }

    @Lock(LockType.READ)
    public CamundaMachineOccupation getCamundaMachineOccupationByMachineOccupationId(long id) throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery("SELECT c FROM CamundaMachineOccupation c WHERE c.machineOccupation.id = :id", CamundaMachineOccupation.class);
        typedQuery.setHint("org.hibernate.cacheable", true);
        typedQuery.setParameter("id", id);
        try {
            var result = typedQuery.getSingleResult();
            initSubLists(result);
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    @Lock(LockType.READ)
    public CamundaMachineOccupation getCamundaMachineOccupationByBusinessKey(String businessKey) throws DatabaseQueryException {
        //var em = persistenceManager.getEntityManager();
//        //TypedQuery<CamundaMachineOccupation> query = em.createNamedQuery("CamundaMachineOccupation.findAll", CamundaMachineOccupation.class)
//        //   .setHint("org.hibernate.cacheable", true);
//        var result = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE c.businessKey = :businessKey")
//                .setParameter("businessKey", businessKey).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true).getResultList();
//        result = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.subMachineOccupations WHERE c in :camundaMachineOccupations")
//                .setParameter("camundaMachineOccupations", result)
//                .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
//
//        result = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.subProductionSteps WHERE c in :camundaMachineOccupations")
//                .setParameter("camundaMachineOccupations", result)
//                .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
//        result = em.createQuery("SELECT DISTINCT c FROM CamundaMachineOccupation c JOIN FETCH c.machineOccupation m LEFT JOIN FETCH m.activeToolSettings WHERE c in :camundaMachineOccupations")
//                .setParameter("camundaMachineOccupations", result)
//                .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
//         (CamundaMachineOccupation) result.get(0);


        var c = persistenceManager.getByField(CamundaMachineOccupation.class, businessKey, "businessKey");
        initSubLists(c);

        return c;
    }

    public void initSubLists(CamundaMachineOccupation c)
    {
        //Hibernate throws an LazyInitializationException if the sublists are not fetched explicitly
        Hibernate.initialize(c.getMachineOccupation().getProductionSteps());
        Hibernate.initialize(c.getMachineOccupation().getSubMachineOccupations());
        Hibernate.initialize(c.getMachineOccupation().getActiveToolSettings());
        Hibernate.initialize(c.getMachineOccupation().getSubProductionSteps());
        Hibernate.initialize(c.getMachineOccupation().getAvailableTools());
        for (var t: c.getMachineOccupation().getAvailableTools()) {
            Hibernate.initialize(t.getToolSettings());
        }
        if(c.getMachineOccupation().getTool() != null)
        {
            Hibernate.initialize(c.getMachineOccupation().getTool().getToolSettings());
        }
        if(c.getMachineOccupation().getUserOccupation() != null)
        {
            initSubListUserOccupation(c.getMachineOccupation().getUserOccupation());
        }
        for (var m: c.getMachineOccupation().getSubMachineOccupations()) {
            Hibernate.initialize(m.getProductionSteps());
            Hibernate.initialize(m.getSubMachineOccupations());
            Hibernate.initialize(m.getActiveToolSettings());
            Hibernate.initialize(m.getSubProductionSteps());
            Hibernate.initialize(m.getAvailableTools());
            for (var t: m.getAvailableTools()) {
                Hibernate.initialize(t.getToolSettings());
            }
            if(m.getTool() != null)
            {
                Hibernate.initialize(m.getTool().getToolSettings());
            }
            if(m.getUserOccupation() != null)
            {
                initSubListUserOccupation(m.getUserOccupation());
            }
        }
    }

    public void initSubLists(MachineOccupation m)
    {
        //Hibernate throws an LazyInitializationException if the sublists are not fetched explicitly
        Hibernate.initialize(m.getProductionSteps());
        Hibernate.initialize(m.getSubMachineOccupations());
        Hibernate.initialize(m.getActiveToolSettings());
        Hibernate.initialize(m.getSubProductionSteps());
        for (var mo: m.getSubMachineOccupations()) {
            Hibernate.initialize(mo.getProductionSteps());
            Hibernate.initialize(mo.getSubMachineOccupations());
            Hibernate.initialize(mo.getActiveToolSettings());
            Hibernate.initialize(mo.getSubProductionSteps());
        }
    }

    public void initSubListUserOccupation(UserOccupation u)
    {
        //Hibernate throws an LazyInitializationException if the sublists are not fetched explicitly
        Hibernate.initialize(u.getActiveUsers());
        Hibernate.initialize(u.getInactiveUsers());
        Hibernate.initialize(u.getUserTimeRecords());
    }

    @Lock(LockType.READ)
    public UserOccupation getUserOccupationByMachineOccupationId(long id) throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery("SELECT u FROM UserOccupation u WHERE u.machineOccupation.id = :id", UserOccupation.class);
        typedQuery.setHint("org.hibernate.cacheable", true);
        typedQuery.setParameter("id", id);
        try {
            var result = typedQuery.getSingleResult();
            initSubListUserOccupation(result);
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    @Lock(LockType.READ)
    public UserOccupation getUserOccupationByCamundaMachineOccupationId(long id) throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery("SELECT u FROM UserOccupation u LEFT JOIN CamundaMachineOccupation cmo on cmo.machineOccupation.id = u.machineOccupation.id WHERE cmo.id = :id", UserOccupation.class);
        typedQuery.setHint("org.hibernate.cacheable", true);
        typedQuery.setParameter("id", id);
        try {
            var result = typedQuery.getSingleResult();
            initSubListUserOccupation(result);
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    @Lock(LockType.READ)
    public MachineOccupation getMachineOccupationById(long id) throws DatabaseQueryException {
        return persistenceManager.getById(MachineOccupation.class, id);
    }

    @Lock(LockType.READ)
    public ProductionOrder getProductionOrderById(long id) throws DatabaseQueryException {
        return persistenceManager.getById(ProductionOrder.class, id);
    }

    @Lock(LockType.READ)
    public Stream<ProductionOrder> getAllProductionOrders() {
        return persistenceManager.getAllByClass(ProductionOrder.class);
    }

    @Lock(LockType.READ)
    public CD_Product getProductById(long id) throws DatabaseQueryException {
        return persistenceManager.getById(CD_Product.class, id);
    }

    @Lock(LockType.READ)
    public ToolSetting getToolSettingById(long id) throws DatabaseQueryException {
        return persistenceManager.getById(ToolSetting.class, id);
    }

    @Lock(LockType.READ)
    public Stream<CD_Product> getAllProducts() {
        return persistenceManager.getAllByClass(CD_Product.class);
    }

    @Lock(LockType.READ)
    public Stream<CD_ProductRelationship> getProductRelationshipsByProductId(long id) throws DatabaseQueryException {
        return getProductById(id).getProductRelationships().stream();
    }

    @Lock(LockType.READ)
    public Stream<CD_Machine> getAllMachines() {
        return persistenceManager.getAllByClass(CD_Machine.class);
    }

    @Lock(LockType.READ)
    public CamundaMachineOccupation getCamundaMachineOccupationByProcessInstanceId(String camundaProcessInstanceId) throws DatabaseQueryException {
        return persistenceManager.getByField(CamundaMachineOccupation.class, camundaProcessInstanceId, "camundaProcessInstanceId");
    }

    @Lock(LockType.READ)
    public Stream<CD_MachineType> getAllMachineTypes() {
        return persistenceManager.getAllByClass(CD_MachineType.class);
    }

    @Lock(LockType.READ)
    public Stream<CD_Department> getAllDepartments() {
        return persistenceManager.getAllByClass(CD_Department.class);
    }

    @Lock(LockType.READ)
    public CD_Machine getMachinById(int machineId) throws DatabaseQueryException {
        return persistenceManager.getById(CD_Machine.class, machineId);
    }

    @Lock(LockType.READ)
    public void removeCamundaMachineOccupation(CamundaMachineOccupation camundaMachineOccupation) {
        this.persistenceManager.remove(camundaMachineOccupation);
    }

    @Lock(LockType.READ)
    public List<CD_ProductionStep> getProductionStepByCamundaProcessName(String activeProcess) throws DatabaseQueryException {
        return persistenceManager.getAllByField(CD_ProductionStep.class, activeProcess, "camundaProcessName");
    }

    @Lock(LockType.READ)
    public List<CD_ProductionStep> getProductionStepByMachineOccupationId(long id) throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery("SELECT ps FROM MachineOccupation mo JOIN mo.productionSteps ps WHERE mo.id = :machineOccupationId", CD_ProductionStep.class);
        typedQuery.setHint("org.hibernate.cacheable", true);
        typedQuery.setParameter("machineOccupationId", id);
        try {
            var result = typedQuery.getResultList();
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    @Lock(LockType.READ)
    public Stream<CD_ProductRelationship> getProductRelationshipsByProductIdFilterByProductionOrder(long id, String extIdProductionOrder) throws DatabaseQueryException {
        return getProductById(id).getProductRelationships().stream().filter(cd_productRelationship -> {
            //If no production order is defined, we want to get all product relationships
            if(cd_productRelationship.getExtIdProductionOrder() == null)
            {
                return true;
            }
            //if it is defined, we only want those, that match the give id
            else
            {
                return extIdProductionOrder.equals(cd_productRelationship.getExtIdProductionOrder());
            }
        });
    }


    public List<BookingEntry> getAllNewAndResendBookingEntries() throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery("SELECT b FROM BookingEntry b WHERE b.bookingState = :bookingStateNew or b.bookingState = :bookingStateResend or b.bookingState = :bookingStateTimeout", BookingEntry.class);
        typedQuery.setParameter("bookingStateNew", EBookingState.NEW);
        typedQuery.setParameter("bookingStateResend", EBookingState.RESEND);
        typedQuery.setParameter("bookingStateTimeout", EBookingState.TIMEOUT);
        typedQuery.setHint("org.hibernate.cacheable", true);
        try {
            var result = typedQuery.getResultList();
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    public Object getAllOverheadCostsForUser(String userName, LocalDateTime startTime, LocalDateTime endTime) throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery("SELECT c FROM OverheadCost c JOIN c.timeRecord tr WHERE c.user.userName = :userName AND ((tr.endDateTime BETWEEN :startDateTime AND :endDateTime) OR (tr.endDateTime IS NULL)) ORDER BY -c.timeRecord.endDateTime", OverheadCost.class);
        typedQuery.setParameter("userName", userName).setParameter("startDateTime", startTime).setParameter("endDateTime", endTime);
        typedQuery.setHint("org.hibernate.cacheable", true);
        try {
            var result = typedQuery.getResultList();
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    public Object getAllOpenOverheadCostsForUser(String userName) throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery("SELECT c FROM OverheadCost c JOIN c.timeRecord tr WHERE c.user.userName = :userName AND tr.endDateTime IS NULL ORDER BY -tr.endDateTime", OverheadCost.class);
        typedQuery.setParameter("userName", userName);
        typedQuery.setHint("org.hibernate.cacheable", true);
        try {
            var result = typedQuery.getResultList();
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    public Stream<CD_OverheadCostCenter> getTopOverheadCostCenters(String userName) throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery(
                "SELECT oc FROM OverheadCost oc " +
                        " JOIN oc.timeRecord tr " +
                        " WHERE oc.user.userName = :userName " +
                        " ORDER BY -tr.endDateTime", OverheadCost.class);
        typedQuery.setParameter("userName", userName);
        typedQuery.setHint("org.hibernate.cacheable", true);
        try {
            var result = typedQuery.getResultList().stream()
                    .map(OverheadCost::getOverheadCostCenter)
                    .distinct().limit(5);
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    public User getUserByName(String userName) throws DatabaseQueryException {
        var em = persistenceManager.getEntityManager();
        var typedQuery = em.createQuery("SELECT u FROM User u WHERE u.userName = :userName", User.class);
        typedQuery.setParameter("userName", userName);
        typedQuery.setHint("org.hibernate.cacheable", true);
        try {
            var result = typedQuery.getSingleResult();
            //em.refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }
}
