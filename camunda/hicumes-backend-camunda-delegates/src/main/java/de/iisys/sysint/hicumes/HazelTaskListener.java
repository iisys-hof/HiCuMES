package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hazelcast.config.Config;
//import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.core.ITopic;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.instance.Participant;
import org.camunda.bpm.model.bpmn.instance.SubProcess;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HazelTaskListener implements TaskListener {

    private static HazelcastInstance hzInstance;

    public HazelTaskListener() {

       /* if (getHzInstance() == null) {
            Config config = new Config();
            setGroupConfig(config);
            setMcConfig(config);
            addTopicConfig(config, "taskStarted");
            setHzInstance(Hazelcast.newHazelcastInstance(config));
        }*/
    }
/*
    private void setGroupConfig(Config config){
        GroupConfig grpConfig = config.getGroupConfig();
        grpConfig.setName("dev");
        grpConfig.setPassword("test");
    }

    private void setMcConfig(Config config){
        ManagementCenterConfig mcc = config.getManagementCenterConfig();
        mcc.setEnabled(true);
        mcc.setUrl("http://localhost:8080/hazelcast-mancenter");
        config.setManagementCenterConfig(mcc);
    }

    private void addTopicConfig(Config config, String topicName){
        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setName(topicName);
        topicConfig.setStatisticsEnabled(true);
        config.addTopicConfig(topicConfig);
    }

    public static HazelcastInstance getHzInstance() {
        return hzInstance;
    }

    public static void setHzInstance(HazelcastInstance hzInstance) {
        HazelTaskListener.hzInstance = hzInstance;
    }

    public static ITopic<String> getTopic(String topic) {
        return hzInstance.getTopic(topic);
    }
*/

    @Override
    public void notify(DelegateTask task) {
        ObjectMapper objMapper = new ObjectMapper();
        ObjectNode rootNode = objMapper.createObjectNode();
        //set the header that is required on HiCuMES side
        ObjectNode jsonHeader = objMapper.createObjectNode();
        FormService formService = task.getProcessEngine().getFormService();
        TaskService taskService = task.getProcessEngine().getTaskService();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        String processInstanceId = task.getProcessInstanceId();
        System.out.println("#################  Hazelcast New Task Listener  ######################");
        //System.out.println("Parent Activity Id: " + task.getExecution().getParentActivityInstanceId());
        //System.out.println("Parent Id: " + task.getExecution().getParentId());
        ModelElementInstance parentElement = task.getBpmnModelElementInstance().getParentElement();
        if(SubProcess.class.isInstance(parentElement)) {
            SubProcess subProcess = (SubProcess)parentElement;
            System.out.println("Subprocess: " + subProcess.getName() + " (" + subProcess.getId() + ")");
            jsonHeader.put("subprocessId", subProcess.getId());
            jsonHeader.put("subprocessName", subProcess.getName());
        }
        System.out.println("Process Definition Id: " + task.getProcessDefinitionId());
        ProcessDefinition processDef = task.getProcessEngineServices().getRepositoryService().getProcessDefinition(task.getProcessDefinitionId());
        System.out.println("Current Process: " + processDef.getKey() +  " - " + processDef.getName());
        RepositoryService repositoryService = task.getProcessEngineServices().getRepositoryService();
        String executionId = task.getExecution().getId();
        System.out.println("Execution Id: " + task.getExecution().getId());
        System.out.println("Execution ParentId: " + task.getExecution().getParentId());
        System.out.println("BPMN model element instance: " + task.getExecution().getBpmnModelElementInstance().getName());
        Collection<Participant> participants = task.getExecution().getBpmnModelInstance().getModelElementsByType(Participant.class);
        Participant currentMachine = null;
        for(Participant participant : participants) {
            System.out.println("Participant: " + participant.getId() + " - " + participant.getName()
                               + " - " + participant.getProcess().getId());
            if(participant.getProcess().getId().equals(processDef.getKey()) ) {
                currentMachine = participant;
                System.out.println("Found corresponding machine: " + currentMachine.getId());
            }else {
                System.out.println("Machine not matching: " + participant.getProcess().getId()
                                    + " - " + processDef.getKey());
            }
        }
        if(currentMachine != null) {
            System.out.println("Put corresponding machine: " + currentMachine.getId());
            jsonHeader.put("machineId", currentMachine.getId());
            jsonHeader.put("machineName", currentMachine.getName());
        }
        System.out.println("TaskListener for Task: " + task.getName());
        System.out.println("Task Id: " + task.getId());
        System.out.println("Task definition key: " + task.getTaskDefinitionKey());
        //old method of getting task type. New one is using the formKey
        //System.out.println("Task type: " + getTaskType(task.getTaskDefinitionKey()));
        System.out.println("Task type: " + taskFormData.getFormKey());
        System.out.println("Business key of process: " + task.getExecution().getProcessBusinessKey());
        System.out.println("Num form fields: " + formFields.size());

        jsonHeader.put("taskId", task.getId());
        jsonHeader.put("taskName", task.getName());
        //jsonHeader.put("taskType", getTaskType(task.getTaskDefinitionKey()));
        jsonHeader.put("executionId", executionId);
        jsonHeader.put("processId", processInstanceId);
        jsonHeader.put("taskType", taskFormData.getFormKey());
        jsonHeader.put("taskId", task.getId());
        jsonHeader.put("businessKey", task.getExecution().getProcessBusinessKey());
        rootNode.set("taskForm", jsonHeader);
        rootNode.put("topic", HazelController.taskStarted);
        Set<String> varNames =  task.getExecution().getVariableNames();
        ObjectNode variableNode = objMapper.createObjectNode();
        for(String varName : varNames){
            System.out.println("variable " + varName + " : " + task.getVariable(varName).toString());
            variableNode.put(varName, task.getVariable(varName).toString());
        }
        rootNode.set("variables", variableNode);
        ArrayNode fieldsArray = encodeFields(formFields, objMapper);
        rootNode.set("fields", fieldsArray);
        System.out.println("message to be sent: " + rootNode.toString());
        HazelController.getTopic(HazelController.taskStarted).publish(rootNode.toString());
        System.out.println("message sent successfully ");
    }

    private ArrayNode encodeFields(List<FormField> formFields, ObjectMapper objMapper) {
        ArrayNode fieldsArray = objMapper.createArrayNode();
        String jsonFields ="";
        for(int i=0; i< formFields.size(); i++){
            try {
                FormField f1 = formFields.get(i);
                jsonFields += objMapper.writeValueAsString(f1);
                ObjectNode objNode = objMapper.createObjectNode();
                objNode.put("key", f1.getId());
                switch (f1.getTypeName()){
                    case "enum":
                        objNode.put("controlType", "dropdown");
                        break;
                    case "boolean":
                        objNode.put("controlType", "checkbox");
                        break;
                    case "date":
                        objNode.put("controlType", "datepicker");
                        break;
                    default:
                        objNode.put("controlType", "textbox");
                        break;
                }
                if(f1.getTypeName().equals("long"))
                    objNode.put("type", "number");
                else
                    objNode.put("type", f1.getType().getName());
                objNode.put("label", f1.getLabel());
                if(f1.getValue().getValue() != null)
                    objNode.put("value", f1.getValue().getValue().toString());
                if(f1.getValidationConstraints().size()>0) {
                    FormFieldValidationConstraint validConst = f1.getValidationConstraints().get(0);
                    objNode.put(validConst.getName(), validConst.getConfiguration().toString());
                }
                objNode.put("order", i);
                if(f1.getTypeName().equals("enum")){
                    try{
                    ArrayNode optionsArray = objMapper.createArrayNode();
                    EnumFormType enumFormType = (EnumFormType)f1.getType();
                    Map<String, String> props = enumFormType.getValues();
                    System.out.println("Num options: " + props.size());
                    props.forEach((k,v) -> {
                        System.out.println("key: " + k + " - value: " + v);
                        ObjectNode optNode = objMapper.createObjectNode();
                        optNode.put("key", k);
                        optNode.put("value", v);
                        optionsArray.add(optNode);
                    });
                    objNode.set("options", optionsArray);
                    }catch (Exception e2){
                        System.err.println("Error with options: " + e2.getMessage());
                        e2.printStackTrace();
                    }
                }
                fieldsArray.add(objNode);
                System.out.println("Form field " + i + " = " + f1.getId() + "(" + f1.getTypeName() + ") "
                        + f1.getLabel() + " : " + f1.getValue().getValue());
            }catch (Exception e1){
                System.err.println("Error serializing form field to json: " + e1.getMessage());
                e1.printStackTrace();
            }
        }
        return fieldsArray;
    }


    String getTaskType(String taskId){
        int i = taskId.indexOf("_");
        if(i>0)
            return taskId.substring(0, i);
        else
            return taskId;
    }
}
