package de.iisys.sysint.hicumes.core.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonTransformer {
    private final ObjectMapper objectMapper;
    private final Class serializationView;
    private final ObjectWriter writer;

    public JsonTransformer(Class<? extends JsonViews.IJsonView> serializationView) {
        this.serializationView = serializationView;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        writer  = objectMapper.writerWithView(serializationView);
    }

    public JsonNode transformObjectToJson(Object value) throws JsonParsingUtilsException {
        var jsonString = writeValueAsString(value);
        try {
            var json = objectMapper.readTree(jsonString);
            return json;
        } catch (JsonProcessingException e) {
            throw new JsonParsingUtilsException("Tried to transform class to jsonNode, Class: " + value.getClass().getName() + "\nClass as JsonSting: " + jsonString, e);
        }
    }

    public JsonNode transformStringToJson(String jsonString) throws JsonParsingUtilsException {
        if (jsonString == null) {
            return null;
        }
        try {
            var json = objectMapper.readTree(jsonString);
            return json;
        } catch (JsonProcessingException e) {
            throw new JsonParsingUtilsException("Tried to transform String to jsonNode \n\tJsonSting: " + jsonString, e);
        }
    }

    public String writeValueAsString(Object value) throws JsonParsingUtilsException {
        try {
            return writer.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new JsonParsingUtilsException("Tried to parse class to json string, Class: " + value.getClass().getName() + "\nClass.toString: " + value.toString(), e);
        }
    }

    public <T> T readValue(JsonNode jsonNode, Class<T> valueType) throws JsonParsingUtilsException {
        var jsonText = writeValueAsString(jsonNode);
        var result = readValue(jsonText,valueType);

        return result;
    }


    public <T> T readValue(String content, Class<T> valueType) throws JsonParsingUtilsException {
        try {
            return (T) objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new JsonParsingUtilsException(" Tried to parse json string into class: " + valueType.getName() + "\nJSON: " + content, e);
        }
    }

    public EntitySuperClass readValueFromEntitySuperClass(String content, String className) throws JsonParsingUtilsException {
        Class classForGeneration = null;
        try {
            classForGeneration = Class.forName(EntitySuperClass.class.getPackageName() + "." + className);
        } catch (ClassNotFoundException e) {
            throw new JsonParsingUtilsException("Tried to parse json string into EntitySuperClass, but not found the classname: " + className + "\nJSON: " + content, e);
        }
        EntitySuperClass generatedClass = (EntitySuperClass) readValue(content, classForGeneration);
        return generatedClass;
    }

    public Response getJsonResponseFromStream(Stream value) throws JsonParsingUtilsException {
        return getJsonResponse(value.collect(Collectors.toList()));

    }

    public Response getJsonResponse(Object value) throws JsonParsingUtilsException {
        String json = writeValueAsString(value);
        return Response.ok(json).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
