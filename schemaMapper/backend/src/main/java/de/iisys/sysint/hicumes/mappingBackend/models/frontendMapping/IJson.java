package de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;

public interface IJson {

    default String toJson() throws JsonProcessingException {
        var objectMapper =  new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        var writer = objectMapper.writerWithView(JsonViews.SchemaMapperDefault.class);
        String result = writer.writeValueAsString(this);
        return result;
    }
}
