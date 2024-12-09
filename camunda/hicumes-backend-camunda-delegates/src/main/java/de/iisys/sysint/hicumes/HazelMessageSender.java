package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.impl.instance.EndEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.IntermediateThrowEventImpl;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.SubProcess;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HazelMessageSender implements JavaDelegate {

    public HazelMessageSender(){
    }

    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("################# Camunda Hazel Event Message Sender ###############");
        Object obj = execution.getBpmnModelElementInstance();
        // fill all the information from the event
        ObjectMapper objMapper = new ObjectMapper();
        ObjectNode rootNode = objMapper.createObjectNode();
        ObjectNode headerNode = objMapper.createObjectNode();
        headerNode.put("businessKey", execution.getProcessBusinessKey());
        headerNode.put("processInstanceId", execution.getProcessInstanceId());
        headerNode.put("processDefinitionId", execution.getProcessDefinitionId());
        System.out.println("Process definition Id: " + execution.getProcessDefinitionId());
        String nextProcess = "";        
        RepositoryService repoService = execution.getProcessEngineServices().getRepositoryService();
        ProcessDefinition processDef = repoService.getProcessDefinition(
                execution.getProcessDefinitionId());
        String lastProcess = processDef.getKey();
        System.out.println("Last process was " + lastProcess);
        //ToDo: has to be made generic later on. For testing purposes only
        Map<String, Object> variables = execution.getVariables();
        ObjectNode variablesNode = objMapper.createObjectNode();
        for (Map.Entry<String, Object> entry : variables.entrySet() ) {
            variablesNode.put(entry.getKey(), entry.getValue().toString());
        }
        String article = execution.getVariable("article").toString();
        System.out.println("Article: " + article);
        rootNode.set("variables", variablesNode);
        //String machineId = execution.getVariable("machineId").toString();
        //System.out.println("machine: " + machineId);
        //Object objMat =  execution.getVariable("material");
        //System.out.println("Material: " + objMat.toString());
        //System.out.println("Material type: " + objMat.getClass().getCanonicalName());
        //JsonNode jsonMat = objMapper.valueToTree(myMat);
        //String matString = objMapper.writeValueAsString(myMat);
        //JsonNode matNode = objMapper.readTree(matString);
        if (obj instanceof IntermediateThrowEventImpl) {
            sendMessageEvent((IntermediateThrowEvent) obj, rootNode, headerNode);
        }
        else if(obj instanceof EndEventImpl){
            ModelElementInstance parentElement = ((EndEventImpl)obj).getParentElement();
            if(SubProcess.class.isInstance(parentElement)) {
                SubProcess subProcess = (SubProcess) parentElement;
                lastProcess = subProcess.getId();
            }
            sendProcessEndMessage((EndEvent) obj, rootNode, headerNode);

            if("Process_Lager".equals(lastProcess)){
                nextProcess = (String) execution.getVariable("nextProcessStep");
            } else {
                nextProcess = "Process_Lager";
                variables.put("lastProcessStep", lastProcess);
            }
            if(nextProcess != null && nextProcess != "" && nextProcess != "x"){
                sendNewProcessMessage(execution, nextProcess, article, lastProcess);
            }
        }
        else {
            System.out.println("BPMN element is not a message! Aborting: " + obj.toString());
            return;
        }
        System.out.println("Json message sent via Hazelcast!" );
    }

    private void sendMessageEvent(IntermediateThrowEvent event, ObjectNode rootNode, ObjectNode headerNode) {
        rootNode.put("topic", "messageEvent");
        if(event.getEventDefinitions().size()>0) {
            MessageEventDefinition med = (MessageEventDefinition) event.getEventDefinitions().iterator().next();
            headerNode.put("messageName", med.getMessage().getName());
        }
        headerNode.put("eventName", event.getName());
        rootNode.set("header", headerNode);
        //rootNode.set("material", jsonMat);
        System.out.println("Message Event json to send: " + rootNode);
        HazelController.getHzInstance().getTopic("message").publish(rootNode.toString());
    }

    private void sendProcessEndMessage(EndEvent event, ObjectNode rootNode, ObjectNode headerNode) {
        rootNode.put("topic", HazelController.processEnded);
        headerNode.put("eventId", event.getId());
        if(event.getEventDefinitions().size()>0) {
            MessageEventDefinition med = (MessageEventDefinition) event.getEventDefinitions().iterator().next();
            headerNode.put("messageName", med.getMessage().getName());
        }
        rootNode.set("header", headerNode);
        System.out.println("Process End json to send: " + rootNode.toString());
        HazelController.getTopic(HazelController.processEnded).publish(rootNode.toString());
    }

    private void sendNewProcessMessage(DelegateExecution execution, String nextProcess,
                                       Object objMat, String lastProcess) {
        ObjectMapper objMapper = new ObjectMapper();
        RepositoryService repoService = execution.getProcessEngine().getRepositoryService();
        System.out.println("Next Process: " + nextProcess);
        //ToDo: replace by async Hazel Message to complete previous process
        ObjectNode newRoot = objMapper.createObjectNode();
        newRoot.put("topic", HazelController.processStarted);
        //ProcessDefinition nextProcDef = repoService.getProcessDefinition(nextProcess);
        //String processDefinitionId = nextProcDef.getId();
        newRoot.put("processDefinitionId", nextProcess);
        newRoot.put("processId", execution.getProcessInstanceId());
        newRoot.put("businessKey", execution.getProcessBusinessKey());
        newRoot.put("lastProcessStep", lastProcess);
        //ToDo: HazelMessageSender - make machineID dynamic instead of hard coding it!
        newRoot.put("machineId", "CNC-machine");
        //rootNode.set("header", headerNode);
        newRoot.set("material", objMapper.valueToTree(objMat));
        System.out.println("Start new process message to be sent: " + newRoot.toString());
        HazelController.getTopic(HazelController.processStarted).publish(newRoot.toString());
    }

    private void sendEventSubprocessStart(DelegateExecution execution, String nextProcess,
                                          Object objMat, String lastProcess){
       String message = "Message_SubProcess_Ueberfraesen";
    }
}
