package de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader;

import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class DataReader implements Serializable {
    @Id
    @GeneratedValue
    protected long id;

    private String readerID;
    private String parserID;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ReaderConfigID")
    private long readerConfigID;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ParserConfigID")
    private long parserConfigID;

    @Type(type = "de.iisys.sysint.hicumes.core.utils.json.JsonListType")
    private List<KeyValueConfig> readerKeyValueConfigs = new ArrayList<>();

    @Type(type = "de.iisys.sysint.hicumes.core.utils.json.JsonListType")
    private List<KeyValueConfig> parserKeyValueConfigs = new ArrayList<>();

    public DataReader() {
    }

    public String getParserID() {
        return parserID;
    }

    public void addReaderConfig(KeyValueConfig keyValueConfig)
    {
        this.readerKeyValueConfigs.add(keyValueConfig);
    }    
    
    public KeyValueConfig getReaderConfigByKey(String key)
    {
        for (KeyValueConfig config: this.readerKeyValueConfigs) {
            if(config.getConfigKey().equalsIgnoreCase(key))
            {
                return config;
            }
        }
        return null;
    }

    public Map<String, String> getReaderConfigMap()
    {
        Map<String, String> map = new HashMap<>();
        for (KeyValueConfig config : readerKeyValueConfigs) {
            map.put(config.getConfigKey(), config.getConfigValue());
        }

        return map;
    }

    public void addParserConfig(KeyValueConfig keyValueConfig)
    {
        this.parserKeyValueConfigs.add(keyValueConfig);
    }

    public KeyValueConfig getParserConfigByKey(String key)
    {
        for (KeyValueConfig config: this.parserKeyValueConfigs) {
            if(config.getConfigKey().equalsIgnoreCase(key))
            {
                return config;
            }
        }
        return null;
    }

    public Map<String, String> getParserConfigMap()
    {
        Map<String, String> map = new HashMap<>();
        for (KeyValueConfig config : parserKeyValueConfigs) {
            map.put(config.getConfigKey(), config.getConfigValue());
        }

        return map;
    }
}
