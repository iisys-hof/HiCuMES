package de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CamundaSubProductionStepDTO {
    Long id; //camundaSubProdStep.formKey
    String formKey; //camundaSubProdStep.formKey
    String name; //camundaSubProdStep.name
    LocalDateTime startDateTime; //camundaSubProdStep.subProdStep.timeRecords[0].startDateTime
    LocalDateTime endDateTime; //camundaSubProdStep.subProdStep.timeRecords[last()].endDateTime
    String userName; //camundaSubProdStep.subProdStep.timeRecords[last()].responseUser.userName
    String filledFormField; //camundaSubProdStep.filledFormField
    String formField;
    private List<TimeRecordDTO> timeRecords; //camundaSubProdStep.subProdStep.timeRecords

    public CamundaSubProductionStepDTO(Long id, String formKey, String name, String filledFormField, String formField, List<TimeRecordDTO> timeRecords) {
        this.formKey = formKey;
        this.name = name;
        this.filledFormField = filledFormField;
        this.formField = formField;
        this.timeRecords = timeRecords;
    }

    public CamundaSubProductionStepDTO(String formKey) {
        this.formKey = formKey;
    }
}
