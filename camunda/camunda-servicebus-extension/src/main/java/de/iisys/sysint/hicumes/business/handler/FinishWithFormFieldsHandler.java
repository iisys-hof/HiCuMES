package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.camunda.bpm.engine.ProcessEngine;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FinishWithFormFieldsHandler extends DefaultHandler implements HazelcastHandler {
    public FinishWithFormFieldsHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("finishWithFormFields");
        String taskId = contentNode.get("taskId").asText();
        Map<String, Object> formfieldMap = new HashMap<>();
        JsonNode formField = contentNode.get("formField");

        //Update username and variables in process variables
        if(contentNode.get("processInstanceId") != null) {
            String processInstanceId = contentNode.get("processInstanceId").asText();
            if(contentNode.get("userName") != null) {
                var userName = contentNode.get("userName").asText();
                var executions = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId).list();
                executions.forEach(execution -> {
                    processEngine.getRuntimeService().setVariable(execution.getId(), "userName", userName);
                });
            }
            if(contentNode.get("variables") != null)
            {
                var variables = mapper.convertValue(contentNode.get("variables"), Map.class);
                var executions = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId).list();
                executions.forEach(execution -> {
                    processEngine.getRuntimeService().setVariables(execution.getId(), variables);
                });
            }
            if(contentNode.get("suspensionType") != null) {
                var suspensionType = contentNode.get("suspensionType").asText();
                var executions = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId).list();
                executions.forEach(execution -> {
                    processEngine.getRuntimeService().setVariable(execution.getId(), "suspensionType", suspensionType);
                });
            }
            else
            {
                var executions = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId).list();
                executions.forEach(execution -> {
                    processEngine.getRuntimeService().removeVariable(execution.getId(), "suspensionType");
                });
            }
        }
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        try {


            Map<String, Object> jsonMap = mapper.readValue(formField.toString(), typeRef);
            Map<String, Object> jsonMapCleaned = new HashMap<>();

            for (Map.Entry<String, Object> entry: jsonMap.entrySet()) {
                String key = entry.getKey();

                //Check if it is a formfield
                if(key.toLowerCase(Locale.ROOT).startsWith("ff_")) {
                    //Try to convert to valid datetimeformat for camunda, if it is a datetime
                    try {
                        String value = (String) entry.getValue();
                        LocalDateTime dateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
                        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

                        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        //entry.setValue(dateTime.format(formatter));
                        entry.setValue(date);
                    } catch (DateTimeParseException dtpe) {
                        System.out.println("The string is not a date and time: " + dtpe.getMessage());
                    } catch (Exception e) {
                        System.out.println("Cannot check value: " + e.getMessage());
                    }
                }
                if(!key.toLowerCase(Locale.ROOT).startsWith("info_"))
                {
                    jsonMapCleaned.put(key, jsonMap.get(key));
                }
            }

            processEngine.getFormService().submitTaskForm(taskId, jsonMapCleaned);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //TODO: Handle exception
        }
        return null;
    }
}
