package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;
import org.w3c.dom.Node;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;

public class RequestAllFormsHandler extends DefaultHandler implements HazelcastHandler {

    public RequestAllFormsHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {

        logger.logMessage("requestAllForms");
        var result = mapper.createArrayNode();
        var alreadyProcessedCollaborationIds = new ArrayList<String>();

        var deployedProcessDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery().list();

        for (var processDefinition : deployedProcessDefinitions) {
            var bpmnRootNode = processEngine.getRepositoryService().getBpmnModelInstance(processDefinition.getId()).getDocument().getDomSource().getNode();
            JsonNode bpmnJsonNode = xmlToJson(bpmnRootNode);

            if(!bpmnJsonNode.has("collaboration")) {
                logger.logMessage("ProcessModel Error", "!", "No collaboration field available", "ID: " + processDefinition.getId());
                continue;
            }

            var collaborationId = getTextFromNode(bpmnJsonNode.get("collaboration"), "id");
            ObjectNode processAdditionalInformation = generateAdditionalProcessInformation(bpmnJsonNode.get("collaboration"));
            if (alreadyProcessedCollaborationIds.contains(collaborationId)) {
                continue;
            }
            alreadyProcessedCollaborationIds.add(collaborationId);

            var allProcesses = getArrayNode(bpmnJsonNode, "process");
            for (var process : allProcesses) {
                var processId = getTextFromNode(process, "id");
                var processName = getTextFromNode(process, "name");
                var processGeneralId = processAdditionalInformation.get(processId).get("id").asText();
                var processShowName = processAdditionalInformation.get(processId).get("name").asText();

                var allSubProcesses = getArrayNode(process, "subProcess");
                for (var subProcess : allSubProcesses) {
                    var subProcessId = getTextFromNode(subProcess, "id");
                    var subProcessName = getTextFromNode(subProcess, "name");

                    var allTasks = getArrayNode(subProcess, "userTask");

                    for (var task : allTasks) {
                        var taskId = getTextFromNode(task, "id");
                        var taskName = getTextFromNode(task, "name");
                        var formKey = getTextFromNode(task, "formKey");
                        var form = getFormFromTask(task);
                        var inputParameter = getInputParameterFromTask(task);
                        var outputParameter = getOutputParameterFromTask(task);
                        /*if (form.isEmpty()) {
                            continue;
                        }*/

                        var taskResult = mapper.createObjectNode();
                        taskResult.put("collaborationId", collaborationId);
                        taskResult.put("processId", processId);
                        taskResult.put("processName", processName);
                        taskResult.put("processGeneralId", processGeneralId);
                        taskResult.put("processShowName", processShowName);
                        taskResult.put("subProcessId", subProcessId);
                        taskResult.put("subProcessName", subProcessName);
                        taskResult.put("taskId", taskId);
                        taskResult.put("taskName", taskName);
                        taskResult.put("formKey", formKey);
                        if (!form.isEmpty()) {
                            taskResult.putArray("form").addAll(form);
                        }
                        else {
                            taskResult.putArray("form");
                        }
                        if (!inputParameter.isEmpty()) {
                            taskResult.withArray("processVariables").addAll(inputParameter);
                        }
                        else {
                            taskResult.withArray("processVariables");
                        }
                        if (!outputParameter.isEmpty()) {
                            taskResult.withArray("processVariables").addAll(outputParameter);
                        }
                        else {
                            taskResult.withArray("processVariables");
                        }

                        result.add(taskResult);
                    }
                }
            }
        }

        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_ALL_FORMS, result);
        return newEvent;
    }

    private ObjectNode generateAdditionalProcessInformation(JsonNode collaboration) {
        var information = mapper.createObjectNode();
        var allProcesses = getArrayNode(collaboration, "participant");

        for (var process : allProcesses) {
            information.putObject(process.get("processRef").asText()).setAll((ObjectNode) process);
        }
        return information;
    }

    private String getTextFromNode(JsonNode node, String key) {
        if (!node.has(key)) {
            return "";
        }
        if (!node.get(key).isTextual()) {
            return "";
        }
        return node.get(key).asText();
    }

    private ArrayNode getFormFromTask(JsonNode task) {
        if (!task.has("extensionElements")) {
            return mapper.createArrayNode();
        }
        var form = task.get("extensionElements").get("formData");
        return getArrayNode(form, "formField");
    }

    private ArrayNode getInputParameterFromTask(JsonNode task) {
        if (!task.has("extensionElements")) {
            return mapper.createArrayNode();
        }
        var inputOutput = task.get("extensionElements").get("inputOutput");
        if(inputOutput == null || !inputOutput.has("inputParameter"))
        {
            return mapper.createArrayNode();
        }
        return getArrayNode(inputOutput, "inputParameter");
    }

    private ArrayNode getOutputParameterFromTask(JsonNode task) {
        if (!task.has("extensionElements")) {
            return mapper.createArrayNode();
        }
        var inputOutput = task.get("extensionElements").get("inputOutput");
        if(inputOutput == null || !inputOutput.has("outputParameter"))
        {
            return mapper.createArrayNode();
        }
        return getArrayNode(inputOutput, "outputParameter");
    }

    private ArrayNode getArrayNode(JsonNode node, String objectName) {
        var newArray = mapper.createArrayNode();
        if (!node.has(objectName)) {
            return newArray;
        }
        var element = node.get(objectName);
        if (element.isArray()) {
            return (ArrayNode) element;
        }

        newArray.add(element);
        return newArray;
    }


    private JsonNode xmlToJson(Node bpmnRootNode) {
        XmlMapper mapperXml = new XmlMapper();
        ObjectMapper mapper = new ObjectMapper();
        try {
            var node = mapperXml.readTree(nodeToString(bpmnRootNode));
            return node;
        } catch (JsonProcessingException | TransformerException e) {
            e.printStackTrace();
            //TODO send error event to backend
            return null;
        }
    }

    private String nodeToString(Node node) throws TransformerException {
        StringWriter writer = new StringWriter();

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(node), new StreamResult(writer));
        String xml = writer.toString();
        return xml;
    }
}
