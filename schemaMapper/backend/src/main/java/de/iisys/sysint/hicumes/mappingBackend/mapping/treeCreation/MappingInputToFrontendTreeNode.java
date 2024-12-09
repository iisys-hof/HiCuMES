package de.iisys.sysint.hicumes.mappingBackend.mapping.treeCreation;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.mappingExceptions.ParsingMappingException;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping.FrontendTreeNode;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping.FrontendTreeNodeIcons;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import org.apache.commons.collections4.IteratorUtils;

import javax.ejb.Singleton;
import java.util.ArrayList;


@Singleton
public class MappingInputToFrontendTreeNode {
    public FrontendTreeNode transform(MappingInput inputData) throws ParsingMappingException {
        if (inputData== null) {
            return null;
        }
        var outerId = "input";
        var outerName = "InputData";

        var jsonSchema = getJsonSchema(outerId, outerName, inputData.getJsonNode());
        return jsonSchema;
    }


        public FrontendTreeNode getJsonSchema(String outerId, String outerName ,JsonNode jsonWithData) throws ParsingMappingException {
            var innerTreeNode = new ArrayList<FrontendTreeNode>();

            if(jsonWithData == null)
            {
                return new FrontendTreeNode(outerId,outerName, FrontendTreeNodeIcons.UNKNOWN, null );
            }
            else if (jsonWithData.isObject()) {
                for (String name : IteratorUtils.toList(jsonWithData.fieldNames())) {
                    JsonNode value = jsonWithData.get(name);
                    var innerId = outerId + '.' + name;
                    if (value.isObject() || value.isArray()) {
                        var innerNode = getJsonSchema(innerId,name,value);
                        innerTreeNode.add(innerNode);
                    }
                    else {
                        var type = FrontendTreeNodeIcons.fromJsonValue(value);
                        var innerNode = new FrontendTreeNode(innerId, name + " | " + type,type, new ArrayList<>() );
                        innerNode.setExampleData(value.asText());
                        innerTreeNode.add(innerNode);
                    }
                }
                var frontendTreeNode = new FrontendTreeNode(outerId,outerName, FrontendTreeNodeIcons.OBJECT, innerTreeNode );
                return frontendTreeNode;
            }
            else if(jsonWithData.isArray()) {
                var schema = getJsonSchema("input", "---->Loop: " + outerName  ,jsonWithData.get(0));
                schema.setSelector(outerId);
                schema.setIcon(FrontendTreeNodeIcons.LOOP);
                return schema;
            } else if(jsonWithData.isValueNode()) {
                return new FrontendTreeNode(outerId, outerName, FrontendTreeNodeIcons.UNKNOWN, null );
            }
            else
            {
                throw new ParsingMappingException("ERPMapCreator has an invalid state: " + jsonWithData, null);
            }
    }
    /**
     *
     *
     * [
     *  [1,2,2 ]
     * ]
     *
     * loop->
     *      loop->
     *         integer
            '
    public FrontendTreeNode getJsonSchema(String outerId, JsonNode jsonWithData) throws InvalidObjectException {
        ArrayNode jsonSchemaArray = JsonNodeFactory.instance.arrayNode();

        if (jsonWithData.isObject()) {
            for (String key : IteratorUtils.toList(jsonWithData.fieldNames())) {
                JsonNode value = jsonWithData.get(key);
                if (value.isObject() || value.isArray()) {
                    ObjectNode jsonSchemaObject = JsonNodeFactory.instance.objectNode();
                    jsonSchemaObject.set(key,getJsonSchema(value));
                    jsonSchemaArray.add(jsonSchemaObject);
                }
                else {
                    jsonSchemaArray.add(key);
                }
            }
        }
        else if(jsonWithData.isArray()) {
            return getJsonSchema(jsonWithData.get(0));
        }
        else {
            throw new InvalidObjectException("ERPMapCreator has an invalid state: " + jsonWithData);
        }

        return jsonSchemaArray;
    }     */

}
