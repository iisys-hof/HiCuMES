package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;

public class DeployBpmnProcessHandler extends DefaultHandler implements HazelcastHandler {

    public DeployBpmnProcessHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("deployBpmnProcess");
        var bpmn = contentNode.get("bpmn").asText();
        var name = contentNode.get("name").asText();

        processEngine.getRepositoryService().createDeployment().addString(name, bpmn).deployWithResult();
        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_DEPLOYED_PROCESS);
        return newEvent;
    }
}
