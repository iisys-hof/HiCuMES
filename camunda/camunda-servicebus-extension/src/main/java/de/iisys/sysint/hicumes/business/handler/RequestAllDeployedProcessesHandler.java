package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.ProcessDefinition;

public class RequestAllDeployedProcessesHandler extends DefaultHandler implements HazelcastHandler {

    public RequestAllDeployedProcessesHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("requestAllDeployedProcesses");
        var deployedProcessDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery().list();
        var deployedProcessDefinitionsJsonArrayNode = mapper.createArrayNode();
        for (ProcessDefinition processDefinition : deployedProcessDefinitions) {
            var process = mapper.createObjectNode();
            process.put("description", processDefinition.getDescription());
            process.put("name", processDefinition.getName());
            process.put("id", processDefinition.getId());
            process.put("versionTag", processDefinition.getVersionTag());
            process.put("category", processDefinition.getCategory());
            process.put("version", processDefinition.getVersion());
            process.put("deploymentId", processDefinition.getDeploymentId());
            process.put("diagramResourceName", processDefinition.getDiagramResourceName());
            process.put("historyTimeToLive", processDefinition.getHistoryTimeToLive());
            process.put("key", processDefinition.getKey());
            process.put("resourceName", processDefinition.getResourceName());
            process.put("tenantId", processDefinition.getTenantId());
            deployedProcessDefinitionsJsonArrayNode.add(process);
        }
        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_ALL_DEPLOYED_PROCESSES, deployedProcessDefinitionsJsonArrayNode);
        return newEvent;
    }
}
