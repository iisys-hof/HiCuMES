package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;

import java.util.Map;

public class StartProcessHandler extends DefaultHandler implements HazelcastHandler {


    public StartProcessHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("startProcess");
        var key = contentNode.get("key").asText();
        var businessKey = contentNode.get("businessKey").asText();
        var variables = mapper.convertValue(contentNode.get("variables"), Map.class);
        var processInstanceStart = processEngine.getRuntimeService().startProcessInstanceByKey(key, businessKey, variables);

        var event = mapper.createObjectNode();
        event.put("businessKey", businessKey)
                .put("processInstanceId", processInstanceStart.getProcessInstanceId());

        if(contentNode.get("userName") != null)
        {
            var userName = contentNode.get("userName").asText();
            event.put("userName", userName);
        }

        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_START_PROCESS, event);
        return newEvent;
    }
}
