package de.iisys.sysint.hicumes.mappingEngine.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class KeyValueConfig implements Serializable {

    private String configKey;
    private String configValue;

    public KeyValueConfig(String configKey, String configValue) {
        this.configKey = configKey;
        this.configValue = configValue;
    }

    public KeyValueConfig() {
    }
}

