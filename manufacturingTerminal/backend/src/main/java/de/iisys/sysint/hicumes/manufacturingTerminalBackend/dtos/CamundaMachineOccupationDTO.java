package de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.iisys.sysint.hicumes.core.entities.Note;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.serializer.CamundaMachineOccupationDTOSerializer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonSerialize(using = CamundaMachineOccupationDTOSerializer.class)
public class CamundaMachineOccupationDTO {

    private Long cmoId; //CMO Id
    private Long moId; //MO Id
    private String productionOrdnerName; //BANr. machineOccupation.productionOrder.name
    private String machineOccupationName; //PosNr. machineOccupation.name
    private String orderType; //Art machineOccupation.productionOrder.orderType
    private String usage; //Verwendung machineOccupation.productionOrder.customerOrder.name
    private String prodStepName; //Arbeitsgang machineOccupation.activeProductionStep.name
    private String productName; //Artikel machineOccupation.productionOrder.product.name
    private String productDetail; //Artikel machineOccupation.productionOrder.product.elemname
    private Double amount; //Menge machineOccupation.productionOrder.measurement.amount
    private Double bamount; //Menge machineOccupation.productionOrder.measurement.amount
    private String unit; //Einheit machineOccupation.productionOrder.measurement.unitString
    private String machineExtId; //Machine machineOccupation.machine.externalId
    private Long machineId; //Machine machineOccupation.machine.externalId
    private String machineName; //Machinenname machineOccupation.machine.name
    private String machineType; //Machinenname machineOccupation.machine.name
    private LocalDateTime startDateTime; //Startzeit machineOccupation.plannedStartDateTime
    private LocalDateTime endDateTime; //Endzeit machineOccupation.plannedEndDateTime
    private LocalDateTime actualStartDateTime; //Endzeit machineOccupation.plannedEndDateTime
    private LocalDateTime lastBookingStartDateTime; // machineOccupation.lastSubProductionStep.subProductionStep.timeRecords.startDateTime
    private LocalDateTime lastBookingEndDateTime; // machineOccupation.lastSubProductionStep.subProductionStep.timeRecords.endDateTime
    private String lastBookingUser; // machineOccupation.lastSubProductionStep.timeRecords.responseUser.userName
    private Integer sumStep; // machineOccupation.productionOrder.sumStep
    private EMachineOccupationStatus status; // machineOccupation.status
    private List<NoteDTO> notes; //machineOccupation.productionOrder.notes
    private List<CamundaSubProductionStepDTO> cSubSteps; //machineOccupation.productionOrder.notes
    private List<CamundaMachineOccupationDTO> subMachineOccupations; //machineOccupation.productionOrder.notes
    private Double totalAcceptedAmount; //Menge machineOccupation.totalProductionNumbers.acceptedMeasurement.amount


    public CamundaMachineOccupationDTO(Long cmoId, Long moId, String productionOrdnerName, String machineOccupationName, String orderType, String usage, String prodStepName, String productName, String productDetail, Double amount, Double bamount, String unit, LocalDateTime startDateTime, LocalDateTime endDateTime, Long machineId, String machineExtId, String machineName, EMachineOccupationStatus status, Integer sumStep, List<NoteDTO> notes, String machineType, List<CamundaSubProductionStepDTO> cSubSteps, List<CamundaMachineOccupationDTO> subMachineOccupations, Double totalAcceptedAmount, LocalDateTime actualStartDateTime) {
        this.cmoId = cmoId;
        this.moId = moId;
        this.productionOrdnerName = productionOrdnerName;
        this.machineOccupationName = machineOccupationName;
        this.orderType = orderType;
        this.usage = usage;
        this.prodStepName = prodStepName;
        this.productName = productName;
        this.productDetail = productDetail;
        this.amount = amount;
        this.unit = unit;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.actualStartDateTime = actualStartDateTime;
        this.machineId = machineId;
        this.machineExtId = machineExtId;
        this.machineName = machineName;
        this.status = status;
        this.notes = notes;
        this.machineType = machineType;
        this.cSubSteps = cSubSteps;
        this.sumStep = sumStep;
        this.subMachineOccupations = subMachineOccupations;
        this.bamount = bamount;
        this.totalAcceptedAmount = totalAcceptedAmount;
    }
}
