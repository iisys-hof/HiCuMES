package de.iisys.sysint.hicumes.mappingEngine.models.dataSource.writer;

import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.writerParser.EWriterParserType;
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
public class DataWriter implements Serializable {
    @Id
    @GeneratedValue
    protected long id;
    private String writerID;
    private String parserID;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="WriterConfigID")
    private long writerConfigID;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ParserConfigID")
    private long parserConfigID;

    @Type(type = "de.iisys.sysint.hicumes.core.utils.json.JsonListType")
    private List<KeyValueConfig> writerKeyValueConfigs = new ArrayList<>();

    @Type(type = "de.iisys.sysint.hicumes.core.utils.json.JsonListType")
    private List<KeyValueConfig> parserKeyValueConfigs = new ArrayList<>();

    public DataWriter() {
    }

    public String getParserID() {
        return parserID;
    }

    public void addWriterConfig(KeyValueConfig keyValueConfig)
    {
        this.writerKeyValueConfigs.add(keyValueConfig);
    }

    public KeyValueConfig getWriterConfigByKey(String key)
    {
        for (KeyValueConfig config: this.writerKeyValueConfigs) {
            if(config.getConfigKey().equalsIgnoreCase(key))
            {
                return config;
            }
        }
        return null;
    }

    public Map<String, String> getWriterConfigMap()
    {
        Map<String, String> map = new HashMap<>();
        for (KeyValueConfig config : writerKeyValueConfigs) {
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
