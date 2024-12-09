package de.iisys.sysint.hicumes.business.handler.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;

import java.util.List;
import java.util.Map;

public class FormFieldMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ArrayNode encodeFields(List<FormField> formFields) {
        ArrayNode fieldsArray = objectMapper.createArrayNode();
        String jsonFields = "";
        for (int i = 0; i < formFields.size(); i++) {
            try {
                FormField f1 = formFields.get(i);
                jsonFields += objectMapper.writeValueAsString(f1);
                ObjectNode objNode = objectMapper.createObjectNode();
                objNode.put("key", f1.getId());
                switch (f1.getTypeName()) {
                    case "enum":
                        objNode.put("controlType", "dropdown");
                        break;
                    case "boolean":
                        objNode.put("controlType", "checkbox");
                        break;
                    case "date":
                        objNode.put("controlType", "datepicker");
                        break;
                    default:
                        objNode.put("controlType", "textbox");
                        break;
                }
                if (f1.getTypeName().equals("long"))
                    objNode.put("type", "number");
                else
                    objNode.put("type", f1.getType().getName());
                objNode.put("label", f1.getLabel());
                if (f1.getValue().getValue() != null)
                    objNode.put("value", f1.getValue().getValue().toString());
                if (f1.getValidationConstraints().size() > 0) {
                    try
                    {
                        String json = objectMapper.writeValueAsString(f1.getValidationConstraints());
                        objNode.put("validationConstraints", json);
                    }
                    catch (JsonProcessingException e)
                    {
                        System.err.println("Could not parse Validation Constraints as json: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                if (f1.getProperties().size() > 0) {
                    try
                    {
                        String json = objectMapper.writeValueAsString(f1.getProperties());
                        objNode.put("properties", json);
                    }
                    catch (JsonProcessingException e)
                    {
                        System.err.println("Could not parse Properties as json: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                objNode.put("order", i);
                if (f1.getTypeName().equals("enum")) {
                    try {
                        ArrayNode optionsArray = objectMapper.createArrayNode();
                        EnumFormType enumFormType = (EnumFormType) f1.getType();
                        Map<String, String> props = enumFormType.getValues();
                        props.forEach((k, v) ->
                        {
                            ObjectNode optNode = objectMapper.createObjectNode();
                            optNode.put("key", k);
                            optNode.put("value", v);
                            optionsArray.add(optNode);
                        });
                        objNode.set("options", optionsArray);
                    } catch (Exception e2) {
                        System.err.println("Error with options: " + e2.getMessage());
                        e2.printStackTrace();
                    }
                }
                fieldsArray.add(objNode);
                //TODO: Exception Handling
            } catch (Exception e1) {
                System.err.println("Error serializing form field to json: " + e1.getMessage());
                e1.printStackTrace();
            }
        }
        return fieldsArray;
    }
}
