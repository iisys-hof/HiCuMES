package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.EventController;
import de.iisys.sysint.hicumes.business.handler.DefaultHandler;
import de.iisys.sysint.hicumes.business.handler.HazelcastHandler;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;

import java.util.Map;

public class EndProcessHandler extends DefaultHandler implements HazelcastHandler {


    public EndProcessHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("endProcess");
        if(contentNode.get("processInstanceId") != null) {
            String processInstanceId = contentNode.get("processInstanceId").asText();
            processEngine.getRuntimeService().deleteProcessInstanceIfExists(processInstanceId, "HZ Process Stop", false, true, true, false);


            /*if(contentNode.get("businessKey") != null) {
                String businessKey = contentNode.get("businessKey").asText();
                var event = mapper.createObjectNode();

                event.put("id", processInstanceId);
                event.put("businessKey", businessKey);
                event.put("aborted", true);

                var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.END_PROCESS, event);
                return newEvent;
            }*/
        }
        return null;
    }
}
