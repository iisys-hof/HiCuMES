package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.ProcessDefinition;

public class UndeployAllBpmnProcessesHandler extends DefaultHandler implements HazelcastHandler {

    public UndeployAllBpmnProcessesHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("undeployAllBpmnProcesses");
        var processDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery().list();
        for (ProcessDefinition processDefinition : processDefinitions) {
            processEngine.getRepositoryService().deleteProcessDefinition(processDefinition.getId(), true);
        }
        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_UNDEPLOYED_PROCESSES);
        return newEvent;
    }
}

