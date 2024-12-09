package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.impl.instance.EndEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.IntermediateThrowEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.ServiceTaskImpl;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.SubProcess;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.HashMap;

public class HazelServiceTaskDelegate implements JavaDelegate {

    public HazelServiceTaskDelegate(){
    }

    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("################# Camunda Service Task Stub ###############");
        Object obj = execution.getBpmnModelElementInstance();
        // fill all the information from the event
        ObjectMapper objMapper = new ObjectMapper();
        ObjectNode rootNode = objMapper.createObjectNode();
        ObjectNode headerNode = objMapper.createObjectNode();
        headerNode.put("businessKey", execution.getProcessBusinessKey());
        headerNode.put("processInstanceId", execution.getProcessInstanceId());
        headerNode.put("processDefinitionId", execution.getProcessDefinitionId());
        System.out.println("Process definition Id: " + execution.getProcessDefinitionId());
        HashMap<String, Object> variables = new HashMap<String, Object>();
        RepositoryService repoService = execution.getProcessEngineServices().getRepositoryService();
        ProcessDefinition processDef = repoService.getProcessDefinition(
                execution.getProcessDefinitionId());
        String lastProcess = processDef.getKey();
        System.out.println("Last process was " + lastProcess);
        String mappingName =  (String) execution.getVariable("mappingName");
        rootNode.put("mappingName", mappingName);
        if (obj instanceof ServiceTaskImpl) {
            sendMessageEvent((ServiceTaskImpl) obj, rootNode, headerNode);
        }
        else {
            System.out.println("BPMN element is not a serviceTask! Aborting: " + obj.toString());
            return;
        }
        System.out.println("Json message sent via Hazelcast!" );
    }

    private void sendMessageEvent(ServiceTaskImpl event, ObjectNode rootNode, ObjectNode headerNode) {
        rootNode.put("topic", HazelController.serviceTaskStarted);
        headerNode.put("eventName", event.getName());
        rootNode.set("header", headerNode);
        //rootNode.set("material", jsonMat);
        System.out.println("Message Event json to send: " + rootNode);
        HazelController.getHzInstance().getTopic(HazelController.serviceTaskStarted).publish(rootNode.toString());
    }

}
