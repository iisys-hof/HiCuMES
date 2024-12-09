package de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities;

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.ProductionNumbers;
import de.iisys.sysint.hicumes.core.entities.SubProductionStep;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.entities.enums.ESubmitType;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingOutput;
import lombok.*;

import javax.json.JsonObject;
import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;

@Table(name = "CamundaSubProductionStep")
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class CamundaSubProductionStep extends EntitySuperClass {


    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private CamundaMachineOccupation camundaMachineOccupation;

    @OneToOne(cascade = CascadeType.ALL)
    SubProductionStep subProductionStep;

    String taskId;

    String name;

    String formKey;
    
    String taskDefinitionKey;

    @Column(columnDefinition = "TEXT")
    String formField;

    @Column(columnDefinition = "TEXT")
    String camundaProcessVariables;

    @Column(columnDefinition = "TEXT")
    String filledFormField;
    //TODO think about if this is a good solution
    public static final JsonTransformer JSON_TRANSFORMER = new JsonTransformer(JsonViews.ViewMachineOccupation.class);

    public CamundaSubProductionStep(String taskId, String formField) {
        this.taskId = taskId;
        this.formField = formField;
    }

    public CamundaSubProductionStep(CamundaMachineOccupation camundaMachineOccupation, String taskId, String name, String formField) {
        this.camundaMachineOccupation = camundaMachineOccupation;
        this.taskId = taskId;
        this.name = name;
        this.formField = formField;
    }
    public CamundaSubProductionStep(CamundaMachineOccupation camundaMachineOccupation, String taskId, String name, String formKey, String formField, String taskDefinitionKey) {
        this.camundaMachineOccupation = camundaMachineOccupation;
        this.taskId = taskId;
        this.name = name;
        this.formKey = formKey;
        this.formField = formField;
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public CamundaSubProductionStep(CamundaMachineOccupation camundaMachineOccupation, String taskId, String name, String formKey, String formField, String camundaProcessVariables, String taskDefinitionKey) {
        this.camundaMachineOccupation = camundaMachineOccupation;
        this.taskId = taskId;
        this.name = name;
        this.formKey = formKey;
        this.formField = formField;
        this.camundaProcessVariables = camundaProcessVariables;
        this.taskDefinitionKey = taskDefinitionKey;
    }
    @SneakyThrows
    public JsonNode getFormField() {
        return JSON_TRANSFORMER.transformStringToJson(formField);
    }

    public String getFormFieldRaw() {
        return this.formField;
    }

    @SneakyThrows
    public void finishSubProductionStep(User user, ObjectNode formFields, MappingOutput mappingOutput, String suspensionType) {
        this.finishSubProductionStep(user, formFields, mappingOutput, true, suspensionType);
    }

    @SneakyThrows
    public void finishSubProductionStep(User user, ObjectNode formFields, MappingOutput mappingOutput, boolean overWriteWithEmpty, String suspensionType) {
        //No fields wére saved before
        if(this.filledFormField == null)
        {
            this.filledFormField = JSON_TRANSFORMER.writeValueAsString(formFields);
        }
        else
        {
            var fields = JSON_TRANSFORMER.readValue(this.filledFormField, ObjectNode.class);
            fields.setAll(formFields);
            this.filledFormField = JSON_TRANSFORMER.writeValueAsString(fields);
        }

        var filledFields = JSON_TRANSFORMER.readValue(this.filledFormField, JsonNode.class);
        //Check if a customDuration was set in the formFields and use it to manually set the duration
        Duration customDuration = null;
        if(filledFields.get("HICUMES_CUSTOMDURATION") != null && filledFields.get("HICUMES_CUSTOMDURATION").asLong() > 0)
        {
            customDuration = Duration.ofSeconds(filledFields.get("HICUMES_CUSTOMDURATION").asLong());
        }

        System.out.println(String.join(", ", mappingOutput.getResult().keySet()));
        SubProductionStep mappingOutputSubProductionStep;
        if(((ArrayList<SubProductionStep>) mappingOutput.getResult().get("subProductionStep")).size() > 0) {
            mappingOutputSubProductionStep = ((ArrayList<SubProductionStep>) mappingOutput.getResult().get("subProductionStep")).get(0);
        }
        else
        {
            mappingOutputSubProductionStep = new SubProductionStep();
        }
        //Make sure, no subProductionStep was changed before
        if(this.subProductionStep == null) {
            this.subProductionStep = mappingOutputSubProductionStep;
            //Compatibility with old version
            if(this.subProductionStep.getExternalId() == null)
            {
                this.subProductionStep.setMachineOccupation(this.camundaMachineOccupation.machineOccupation);
                this.subProductionStep.setExternalId(this.camundaMachineOccupation.getCurrentSubProductionStep().taskDefinitionKey + "_" + this.camundaMachineOccupation.getCurrentSubProductionStep().getTaskId() + "_" + this.camundaMachineOccupation.getMachineOccupation().getExternalId());
            }
        }
        else
        {
            this.subProductionStep.updateFromMappingOutput(mappingOutputSubProductionStep, overWriteWithEmpty);
            //Compatibility with old version
            if(this.subProductionStep.getExternalId() == null)
            {
                this.subProductionStep.setMachineOccupation(this.camundaMachineOccupation.machineOccupation);
                this.subProductionStep.setExternalId(this.camundaMachineOccupation.getCurrentSubProductionStep().taskDefinitionKey + "_" + this.camundaMachineOccupation.getCurrentSubProductionStep().getTaskId() + "_" + this.camundaMachineOccupation.getMachineOccupation().getExternalId());
            }
            var durationCorrection = this.camundaMachineOccupation.getMachineOccupation().getTimeDurations().get("correct");
            if(durationCorrection != null && !durationCorrection.isZero())
            {
                Duration remainder = this.subProductionStep.stopTimeRecord(user, durationCorrection.toSeconds() * -1);
                this.camundaMachineOccupation.getMachineOccupation().getTimeDurations().replace("correct", remainder);
            }
            else if(customDuration != null) {
                this.subProductionStep.stopTimeRecord(user, customDuration);
            }
            else {
                this.subProductionStep.stopTimeRecord(user);
            }
        }
        //Update counter for total produce/rejected amounts in MachineOccupation
        if(this.subProductionStep.getProductionNumbers().size() > 0)
        {
            for (ProductionNumbers prodNumber: this.subProductionStep.getProductionNumbers()) {
                this.camundaMachineOccupation.getMachineOccupation().getTotalProductionNumbers().addProductionNumbers(prodNumber);
            }

        }

        if(suspensionType != null)
        {
            this.subProductionStep.setSubmitType(ESubmitType.SUBMIT_WITH_BREAK);
        }
        else
        {
            this.subProductionStep.setSubmitType(ESubmitType.SUBMIT);
        }
    }
    @Override
    public boolean equals( Object o )    {
        if ( o == null )
            return false;
        if ( o == this )
            return true;
        if (!(o instanceof CamundaSubProductionStep))
            return false;

        CamundaSubProductionStep that = (CamundaSubProductionStep) o;
        return   this.taskId.equals(that.taskId);
    }

    public void stopTimerOnEnd() {
        if(this.subProductionStep != null) {
            this.subProductionStep.stopTimeRecord(null);
        }
    }

    public void stopTimerOnEnd(long timeOffsetSeconds) {
        if(this.subProductionStep != null) {
            this.subProductionStep.stopTimeRecord(null, timeOffsetSeconds);
        }
    }
}
