package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.*;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.entities.enums.EProductionStatus;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.entities.unit.Measurement;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCreateCollectionOrder;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;
import org.apache.james.mime4j.dom.datetime.DateTime;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class HandleCreateCollectionOrder implements ISubscribeEvent {

    @Inject
    private IEmitEvent events;


    private JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupation.class);
    ObjectMapper mapper = new ObjectMapper();
    SSESessionManager sessionManager = SSESessionManager.getInstance();

    @Subscribe
    public void handle(EventCreateCollectionOrder eventCreateCollectionOrder) throws DatabasePersistException {

        var collectionOrder = eventCreateCollectionOrder.getCollectionOrder();
        List<MachineOccupation> subMachineOccupations = null;
        try {
            subMachineOccupations = getNewSubMachineOccupations(eventCreateCollectionOrder.getSubMachineOccupationAmount(), eventCreateCollectionOrder);
        } catch (DatabaseQueryException e) {
            e.printStackTrace();
        }

        //eventCreateCollectionOrder.getCamundaMachineOccupations().stream().map(CamundaMachineOccupation::getMachineOccupation).collect(Collectors.toList());
        MachineOccupation machineOccupationParent = null;
        if(collectionOrder != null)
        {
            machineOccupationParent = collectionOrder.getMachineOccupation();
            machineOccupationParent.getSubMachineOccupations().addAll(subMachineOccupations);

            for (var subMachineOccupation: subMachineOccupations) {

                subMachineOccupation.updateMachine(machineOccupationParent.getMachine());
                //        machineOccupation.getMachineOccupation().setActiveToolSettings(toolSettings);

                //Compatibility, if machineOccupation was already created with old hicumes version, there is no totalproductionnumbers defined
                if(subMachineOccupation.getTotalProductionNumbers() == null)
                {
                    subMachineOccupation.setTotalProductionNumbers(new ProductionNumbers());
                    var unitString = subMachineOccupation.getProductionOrder().getMeasurement().getUnitString();
                    subMachineOccupation.getTotalProductionNumbers().getAcceptedMeasurement().setUnitString(unitString);
                    subMachineOccupation.getTotalProductionNumbers().getRejectedMeasurement().setUnitString(unitString);
                    persistService.getPersistenceManager().persist(subMachineOccupation.getTotalProductionNumbers());
                }
                persistService.getPersistenceManager().merge(subMachineOccupation);
            }

        }
        else
        {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
            DateTimeFormatter formatterShort = DateTimeFormatter.ofPattern("yyMMddHHmm");
            var datetimeNow = LocalDateTime.now().format(formatter);
            var datetimeNowShort = LocalDateTime.now().format(formatterShort);
            //TODO define naming scheme
            var id = Math.abs(new Random().nextInt(100000));
            machineOccupationParent = new MachineOccupation(subMachineOccupations.get(0).getProductionSteps(), null, null, subMachineOccupations.get(0).getCamundaProcessName());
            machineOccupationParent.setExternalId("Sammelauftrag_" + datetimeNow );
            machineOccupationParent.setName("Sammelauf. " + datetimeNowShort );
            machineOccupationParent.setSubMachineOccupations(subMachineOccupations);
            machineOccupationParent.setStatus(EMachineOccupationStatus.READY_TO_START);
            machineOccupationParent.getActiveToolSettings().addAll(subMachineOccupations.get(0).getActiveToolSettings());
        }

        /*for (CamundaMachineOccupation camundaMachineOccupation: eventCreateCollectionOrder.getCamundaMachineOccupations()) {
            //persistService.removeCamundaMachineOccupation(camundaMachineOccupation);
            camundaMachineOccupation.setSubMachineOccupation(true);
            persistService.getPersistenceManager().persist(camundaMachineOccupation);
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCreateCollectionOrder, camundaMachineOccupation));
        }*/

        MachineOccupation finalMachineOccupationParent = machineOccupationParent;
        machineOccupationParent.getSubMachineOccupations().forEach(subMachineOccupation -> {
            subMachineOccupation.setParentMachineOccupation(finalMachineOccupationParent);
        });

        persistService.getPersistenceManager().persist(machineOccupationParent);

        sessionManager.broadcast(new EventBackgroundProcessingDone(eventCreateCollectionOrder, collectionOrder));
        if(collectionOrder == null) {
            events.emit(new EventNewMachineOccupation(machineOccupationParent));
        }
    }

    private List<MachineOccupation> getNewSubMachineOccupations(Map<CamundaMachineOccupation, Double> subMachineOccupationAmount,EventCreateCollectionOrder eventCreateCollectionOrder) throws DatabasePersistException, DatabaseQueryException {
        List<MachineOccupation> newSubMachineOccupations = new ArrayList<>();
        for (var subCamundaMachineOccupation : subMachineOccupationAmount.keySet()) {
            var split = subMachineOccupationAmount.get(subCamundaMachineOccupation);
            //Use the same machineOccupation, if no split amount was set or the new amount is smaller or equal to 0 after the split is removed
            if(split == 0 || split.isNaN() || subCamundaMachineOccupation.getMachineOccupation().getProductionOrder().getMeasurement().getAmount() - split <= 0)
            {
                newSubMachineOccupations.add(subCamundaMachineOccupation.getMachineOccupation());
                subCamundaMachineOccupation.setSubMachineOccupation(true);

                sessionManager.broadcast(new EventBackgroundProcessingDone(eventCreateCollectionOrder, subCamundaMachineOccupation));
                //events.emit(new EventNewMachineOccupation(subCamundaMachineOccupation, true));
            }
            else
            {
                var mainMachineOccupation = subCamundaMachineOccupation.getMachineOccupation();
                var mainProductionOrder = mainMachineOccupation.getProductionOrder();
                //var splitMachineOccupations = new ArrayList<MachineOccupation>();

                //Save original production order with reduced amount
                mainProductionOrder.getMeasurement().setAmount(mainProductionOrder.getMeasurement().getAmount() - split);
                persistService.getPersistenceManager().merge(mainProductionOrder);
                persistService.getPersistenceManager().merge(mainMachineOccupation);
                sessionManager.broadcast(new EventBackgroundProcessingDone(eventCreateCollectionOrder, subCamundaMachineOccupation));

                //var splitProductionOrder = new ProductionOrder(new Measurement(split, mainProductionOrder.getMeasurement().getUnitString()), mainProductionOrder.getCustomerOrder(), mainProductionOrder.getProduct(), mainProductionOrder.getPriority(), mainProductionOrder.getDeadline(), EProductionStatus.PLANNED);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                var datetimeNow = LocalDateTime.now().format(formatter);

                //copy (just by reference) production order, save set new names and remove id
                var splitProductionOrder = mainProductionOrder;
                persistService.getPersistenceManager().getEntityManager().detach(splitProductionOrder);

                splitProductionOrder.setExternalId(mainProductionOrder.getExternalId() + "_split_" + datetimeNow);
                splitProductionOrder.setName(mainProductionOrder.getName() + " Teilauftrag");
                splitProductionOrder.setId(0);
                splitProductionOrder.getMeasurement().setAmount(split);
                splitProductionOrder.setKeyValueMap(new HashMap<>(mainProductionOrder.getKeyValueMap()));
                splitProductionOrder.setMachineOccupations(new ArrayList<>());
                splitProductionOrder.setNotes(new ArrayList<>(mainProductionOrder.getNotes()));
                //splitProductionOrder.setCustomerOrder();
                persistService.getPersistenceManager().persist(splitProductionOrder);

                //Do the same for machineOccupation
                var splitMachineOccupation = mainMachineOccupation;

                persistService.getPersistenceManager().getEntityManager().detach(splitMachineOccupation);

                splitMachineOccupation.setExternalId(mainMachineOccupation.getExternalId() + "_split_" +  datetimeNow);
                splitMachineOccupation.setName(mainMachineOccupation.getName() + " Teilauftrag");
                splitMachineOccupation.setId(0);
                splitMachineOccupation.setProductionOrder(splitProductionOrder);
                //Reset production numbers
                splitMachineOccupation.setTotalProductionNumbers(new ProductionNumbers());
                //Create new collections to avoid shared reference exception
                splitMachineOccupation.setActiveToolSettings(new ArrayList<>(mainMachineOccupation.getActiveToolSettings()));
                splitMachineOccupation.setProductionSteps(new ArrayList<>(mainMachineOccupation.getProductionSteps()));
                splitMachineOccupation.setSubMachineOccupations(new ArrayList<>(mainMachineOccupation.getSubMachineOccupations()));
                splitMachineOccupation.setSubProductionSteps(new ArrayList<>(mainMachineOccupation.getSubProductionSteps()));
                splitMachineOccupation.setTimeDurations(new HashMap<>(mainMachineOccupation.getTimeDurations()));
                persistService.getPersistenceManager().persist(splitMachineOccupation);

                var splitCamundaMachineOccupation = subCamundaMachineOccupation;
                persistService.getPersistenceManager().getEntityManager().detach(splitCamundaMachineOccupation);

                splitCamundaMachineOccupation.setId(0);
                splitCamundaMachineOccupation.setBusinessKey(splitCamundaMachineOccupation.getBusinessKey() + "_split_" +  datetimeNow);
                splitCamundaMachineOccupation.setSubMachineOccupation(true);
                splitCamundaMachineOccupation.setMachineOccupation(splitMachineOccupation);
                splitCamundaMachineOccupation.setCamundaSubProductionSteps(new ArrayList<>());

                persistService.getPersistenceManager().persist(splitCamundaMachineOccupation);

                newSubMachineOccupations.add(splitMachineOccupation);
                //events.emit(new EventNewEntity(splitMachineOccupation));

            }
        }
        return newSubMachineOccupations;
    }

    private void emitNewEntityEvent(EntitySuperClass entity, boolean isCamundaSubMachineOccupation)
    {

    }

}
