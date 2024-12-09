package de.iisys.sysint.hicumes.mappingBackend.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.json.JsonListType;
import de.iisys.sysint.hicumes.mappingBackend.mapping.services.FormsStateService;
import de.iisys.sysint.hicumes.mappingBackend.mapping.services.MappingPersistService;
import de.iisys.sysint.hicumes.mappingBackend.startup.StartupController;
import de.iisys.sysint.hicumes.mappingEngine.MappingWorkflow;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Singleton
public class EventController
{
    @EJB
    FormsStateService formsStateService;
    @EJB
    MappingPersistService mappingPersistService;

    MappingWorkflow mappingWorkflow = new MappingWorkflow(StartupController.hazelServer);

    public void updateAllForms(JsonNode allForms)
    {
        //System.out.println(allForms);
        formsStateService.update(allForms);
    }

    public void sendCamundaMappings() {
        var camundaMappings = mappingPersistService.getCamundaMappings();
        var event = new EventGenerator().generateEvent(Events.MappingSendTopic.CAMUNDA_MAPPINGS, camundaMappings);
        StartupController.hazelServer.sendEvent(Events.MappingSendTopic.getTopic(),event);
    }

    public void mappingSaved(MappingAndDataSource mappingAndDataSource) {
        sendCamundaMappings();
    }

    public void runMapping(JsonNode contentNode) {
        MappingAndDataSource dataSource = null;

        var mappingName = contentNode.get("mappingName").toString().replace("\"", "");
        var useSavedData = contentNode.get("useSavedData").asBoolean();
        var isSimulate = contentNode.get("isSimulate").asBoolean();
        var writerConfig = contentNode.get("writerConfig");
        var writerParserConfig = contentNode.get("writerParserConfig");
        var readerConfig = contentNode.get("readerConfig");
        var readerParserConfig = contentNode.get("readerParserConfig");

        try {
            dataSource = mappingPersistService.getMappingAndDataSourceByName(mappingName);

            if(writerConfig != null)
            {
                ObjectMapper mapper = new ObjectMapper();
                List<KeyValueConfig> configList = Arrays.asList(mapper.readValue(writerConfig.asText(), KeyValueConfig[].class));
                dataSource.getDataWriter().setWriterKeyValueConfigs(configList);
            }
            if(writerParserConfig != null)
            {
                ObjectMapper mapper = new ObjectMapper();
                List<KeyValueConfig> configList = Arrays.asList(mapper.readValue(writerParserConfig.asText(), KeyValueConfig[].class));
                dataSource.getDataWriter().setParserKeyValueConfigs(configList);
            }
            if(readerConfig != null)
            {
                ObjectMapper mapper = new ObjectMapper();
                List<KeyValueConfig> configList = Arrays.asList(mapper.readValue(readerConfig.asText(), KeyValueConfig[].class));
                dataSource.getDataReader().setReaderKeyValueConfigs(configList);
            }
            if(readerParserConfig != null)
            {
                ObjectMapper mapper = new ObjectMapper();
                List<KeyValueConfig> configList = Arrays.asList(mapper.readValue(readerParserConfig.asText(), KeyValueConfig[].class));
                dataSource.getDataReader().setParserKeyValueConfigs(configList);
            }

            //System.out.println(dataSource);

            var mappingResult = mappingWorkflow.run(mappingPersistService.getPersistenceManager(), dataSource, useSavedData, isSimulate);
            //System.out.println("DONE WITH MAPPING SYNC");

            //WAIT FOR FINISH
        } catch (DatabaseQueryException | MappingBaseException e) {
            e.printStackTrace();
            //TODO Handling when no mapping is found
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    //public void runMappingDone(JsonNode contentNode) {
    //}
}
