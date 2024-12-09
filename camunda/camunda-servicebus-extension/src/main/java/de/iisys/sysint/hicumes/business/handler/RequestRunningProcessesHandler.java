package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class RequestRunningProcessesHandler extends DefaultHandler implements HazelcastHandler {


    public RequestRunningProcessesHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("requestRunningProcesses");
        var activeProcessInstances = processEngine.getRuntimeService().createProcessInstanceQuery().active().list();

        var runningProcessInstancesJsonArrayNode = mapper.createArrayNode();
        for (ProcessInstance processInstance : activeProcessInstances) {
            var process = mapper.createObjectNode();
            process.put("id", processInstance.getId());
            process.put("caseInstanceId", processInstance.getCaseInstanceId());
            process.put("rootProcessInstanceId", processInstance.getRootProcessInstanceId());
            process.put("processInstanceId", processInstance.getProcessInstanceId());
            process.put("tenantId", processInstance.getTenantId());
            process.put("businessKey", processInstance.getBusinessKey());
            process.put("processDefinitionId", processInstance.getProcessDefinitionId());
            process.put("isSuspended", processInstance.isSuspended());
            process.put("isEnded", processInstance.isEnded());
            runningProcessInstancesJsonArrayNode.add(process);
        }

        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_RUNNING_PROCESSES, runningProcessInstancesJsonArrayNode);
        return newEvent;
    }
}
