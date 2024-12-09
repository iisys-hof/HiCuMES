package de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.entities.CD_ProductionStep;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.json.JsonTransformerMachineOccupation;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingOutput;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name="CamundaMachineOccupation.findAll", query="SELECT c FROM CamundaMachineOccupation c")
@NamedEntityGraph(
        name = "Graph.CamundaMachineOccupation",
        attributeNodes = {
                @NamedAttributeNode("camundaSubProductionSteps"),
                @NamedAttributeNode(value = "machineOccupation", subgraph = "machineOccupation-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "machineOccupation-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("subMachineOccupations"),
                                @NamedAttributeNode("productionSteps"),
                                @NamedAttributeNode("availableTools"),
                                @NamedAttributeNode("subProductionSteps"),
                                @NamedAttributeNode("activeToolSettings"),
                                @NamedAttributeNode("timeDurations")
                        }
                )
        }
)
public class CamundaMachineOccupation extends EntitySuperClass {

    String camundaProcessInstanceId;
    String businessKey;

    @OneToOne(cascade = CascadeType.MERGE)
    MachineOccupation machineOccupation;

    private boolean isSubMachineOccupation = false;

    @OneToMany(mappedBy = "camundaMachineOccupation",cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CamundaSubProductionStep> camundaSubProductionSteps = new ArrayList<>();

    @OneToOne
    private CD_ProductionStep activeProductionStep;

    public static final JsonTransformer JSON_TRANSFORMER = new JsonTransformer(JsonViews.ViewMachineOccupation.class);
    public static final JsonTransformerMachineOccupation jsonTransformerMachineOccupation = new JsonTransformerMachineOccupation();

    public CamundaMachineOccupation(MachineOccupation machineOccupation, String businessKey) {
        this.machineOccupation = machineOccupation;
        this.businessKey = businessKey;
        if(machineOccupation.getProductionSteps().size() > 0) {
            this.activeProductionStep = machineOccupation.getProductionSteps().get(0);
        }
    }

    @JsonIgnore
    public JsonNode toCamundaStartProcess(String userName) throws JsonParsingUtilsException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        var jsonMachineOccupation = (ObjectNode) JSON_TRANSFORMER.transformObjectToJson(machineOccupation);


        ObjectNode eventContent = mapper.createObjectNode()
                .put("key", machineOccupation.getCamundaProcessName())
                .put("businessKey", businessKey)
                .put("userName", userName);
        var variables = eventContent.putObject("variables");
        variables.putObject("machineOccupation").setAll(jsonMachineOccupation);
        variables.put("businessKey", businessKey);
        variables.put("key", machineOccupation.getCamundaProcessName());
        variables.put("userName", userName);
        return eventContent;
    }

    @JsonIgnore
    public JsonNode toCamundaManualEndProcess() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectNode eventContent = mapper.createObjectNode()
                .put("processInstanceId", this.getCamundaProcessInstanceId());
        eventContent.put("businessKey", businessKey);
        return eventContent;
    }

    @JsonIgnore
    public JsonNode toRequestActiveTask(String userName, String suspensionType) throws JsonParsingUtilsException {


        ObjectNode jsonMachineOccupation = (ObjectNode) jsonTransformerMachineOccupation.transformObjectToJson(machineOccupation);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectNode eventContent = mapper.createObjectNode()
                .put("processInstanceId", camundaProcessInstanceId)
                .put("businessKey", businessKey)
                .put("userName", userName);
        var variables = eventContent.putObject("variables");
        variables.putObject("machineOccupation").setAll(jsonMachineOccupation);
        if(suspensionType != null)
        {
            eventContent.put("suspensionType", suspensionType);
        }

        return eventContent;
    }

    @JsonIgnore
    public boolean isAllowedToStartMachineOccupation() {
        return machineOccupation.getStatus().equals(EMachineOccupationStatus.READY_TO_START);
    }

    @JsonIgnore
    public void startMachineOccupation(String processInstanceId) throws BusinessException {
        if (!isAllowedToStartMachineOccupation()) {
            throw new BusinessException("Not allowed to start this Camunda Production Order\n\tBusinessKey: " + businessKey);
        }
        this.camundaProcessInstanceId = processInstanceId;
        this.machineOccupation.setStatus(EMachineOccupationStatus.RUNNING);
        this.machineOccupation.setActualStartDateTime(LocalDateTime.now());
        for(MachineOccupation subMO : this.machineOccupation.getSubMachineOccupations())
        {
            subMO.setStatus(EMachineOccupationStatus.RUNNING);
            subMO.setActualStartDateTime(LocalDateTime.now());
        }
    }

    public void startSubProductionStep(CamundaSubProductionStep camundaSubProductionStep) {

        this.camundaSubProductionSteps.add(camundaSubProductionStep);
    }

    @JsonIgnore
    public CamundaSubProductionStep getCurrentSubProductionStep() {
        if(camundaSubProductionSteps.size() > 0)
        {
            return camundaSubProductionSteps.get(camundaSubProductionSteps.size() - 1);
        }
        else
        {
            return null;
        }
    }


    public JsonNode finishSubProductionStep(String taskId, ObjectNode formFields, String suspensionType, User user, MappingOutput mappingOutput) {
        return this.finishSubProductionStep(taskId, formFields, suspensionType, user, mappingOutput,true);
    }

    public JsonNode finishSubProductionStep(String taskId, ObjectNode formFields, String suspensionType, User user, MappingOutput mappingOutput, boolean overWriteWithEmpty) {
        //this.machineOccupation.setStatus(EMachineOccupationStatus.FINISHED);
        //Check if the current SubProductionStep matches the SubProductionStep that the frontend response belongs to

        if(!this.getCurrentSubProductionStep().getTaskId().equals(taskId))
        {
            System.err.println("Response to task does not match current task - current: " + this.getCurrentSubProductionStep().getTaskId() + " vs from response: " + taskId);
            return null;
        }

        var fieldList = getFieldNames((ArrayNode) this.getCurrentSubProductionStep().getFormField());
        var fieldListWithoutReadonly = getFieldNamesWithoutReadonly((ArrayNode) this.getCurrentSubProductionStep().getFormField());
        //Only keep the formfields that camunda expects and send them
        ObjectNode savedFormFields = formFields.retain(fieldList);

        this.getCurrentSubProductionStep().finishSubProductionStep(user, savedFormFields, mappingOutput, overWriteWithEmpty, suspensionType);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectNode eventContent = mapper.createObjectNode()
                .put("processInstanceId", camundaProcessInstanceId)
                .put("taskId", getCurrentSubProductionStep().getTaskId())
                .put("userName", user.getUserName());

        if(suspensionType != null)
        {
            eventContent.put("suspensionType", suspensionType);
        }

        ObjectNode formfieldsWithoutReadonly = formFields.retain(fieldListWithoutReadonly);
        eventContent.putObject("formField").setAll(formfieldsWithoutReadonly);
        try {
            ObjectNode jsonMachineOccupation = (ObjectNode) jsonTransformerMachineOccupation.transformObjectToJson(machineOccupation);
            var variables = eventContent.putObject("variables");
            variables.putObject("machineOccupation").setAll(jsonMachineOccupation);
        } catch (JsonParsingUtilsException e) {
            System.out.println("Faild to parse machineOccupation");
        }

        return eventContent;
    }


    public JsonNode persistSubProductionStep(CamundaMachineOccupation parentMachineOccupation, ObjectNode formFields, User user, MappingOutput mappingOutput) {
        var parentSubProductionStep = parentMachineOccupation.getCurrentSubProductionStep();
        if(this.getCurrentSubProductionStep() == null)
        {
            var camundaSubProductionStep = new CamundaSubProductionStep(this, parentSubProductionStep.getTaskId(), parentSubProductionStep.getName(), parentSubProductionStep.getFormKey(), parentSubProductionStep.getFormFieldRaw(), parentSubProductionStep.getTaskDefinitionKey());
            this.camundaSubProductionSteps.add(camundaSubProductionStep);
        }
        return finishSubProductionStep(this.getCurrentSubProductionStep().getTaskId(), formFields, null, user, mappingOutput);
    }

    private List<String> getFieldNames(ArrayNode formFields) {
        var fieldList = new ArrayList<String>();
        for (var formField : formFields) {
            fieldList.add(formField.findValue("key").asText());
        }
        return fieldList;
    }


    private List<String> getFieldNamesWithoutReadonly(ArrayNode formFields) {
        var fieldList = new ArrayList<String>();
        for (var formField : formFields) {
            //System.out.println(formField);
            var validationConstraints = formField.findValue("validationConstraints");
            if(validationConstraints != null && validationConstraints.asText().contains("readonly"))
            {
                continue;
            }
            fieldList.add(formField.findValue("key").asText());
        }
        return fieldList;
    }

    public void endProcess(boolean aborted) {
        EMachineOccupationStatus status;
        if(aborted && (
                this.machineOccupation.getStatus().equals(EMachineOccupationStatus.PLANNED) ||
                        this.machineOccupation.getStatus().equals(EMachineOccupationStatus.PAUSED) ||
                        this.machineOccupation.getStatus().equals(EMachineOccupationStatus.READY_TO_START) ||
                        this.machineOccupation.getStatus().equals(EMachineOccupationStatus.ABORTED) ||
                        this.machineOccupation.getStatus().equals(EMachineOccupationStatus.RUNNING)))
        {
            status = EMachineOccupationStatus.ABORTED;
        }
        else if(this.machineOccupation.getStatus().equals(EMachineOccupationStatus.ARCHIVED))
        {
            status = EMachineOccupationStatus.ARCHIVED;
        }
        else
        {
            status = EMachineOccupationStatus.FINISHED;
        }
        this.machineOccupation.setStatus(status);
        this.machineOccupation.setActualEndDateTime(LocalDateTime.now());

        if(this.getCurrentSubProductionStep() != null)
        {
            this.getCurrentSubProductionStep().stopTimerOnEnd();
        }
        for(MachineOccupation subMO : this.machineOccupation.getSubMachineOccupations())
        {
            subMO.setStatus(status);
            subMO.setActualEndDateTime(LocalDateTime.now());
        }
    }

}
