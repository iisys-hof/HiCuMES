package de.iisys.sysint.hicumes.mappingEngine.models.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
public class MappingConfiguration {
    @Id
    @GeneratedValue
    protected long id;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MappingRule> mappings =  new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MappingConfiguration> loops = new ArrayList<>();

    private String inputSelector;
    private String outputSelector;

    @Lob
    @Column( length = 1000000 )
    private String xsltRules;

    public String toJson() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String result = objectMapper.writeValueAsString(this);
        return result;
    }

    public MappingConfiguration() {
    }
}
