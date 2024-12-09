package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.PersistenceOrder;
import de.iisys.sysint.hicumes.core.entities.SubProductionStep;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaPersistFormField;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventRunMapping;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.TerminalMappingException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.services.CamundaMappingStateService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.services.SubProductionStepMappingService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.startup.StartupController;
import de.iisys.sysint.hicumes.mappingEngine.MappingWorkflow;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleRunMapping implements ISubscribeEvent {

    @EJB
    private CamundaMappingStateService camundaMappingStateService;

    SSESessionManager sessionManager = SSESessionManager.getInstance();
    private static final MappingWorkflow mappingWorkflow = new MappingWorkflow(StartupController.hazelServer);

    @Subscribe
    public void handle(EventRunMapping eventRunMapping) throws UtilsBaseException, TerminalMappingException, MappingException {
        MappingAndDataSource dataSource = null;

        var mappingName = eventRunMapping.getMappingName();
        var useSavedData = eventRunMapping.isUseSavedData();
        var isSimulate = eventRunMapping.isSimulate();
        var writerConfig = eventRunMapping.getWriterConfig();
        var writerParserConfig = eventRunMapping.getWriterParserConfig();
        var readerConfig = eventRunMapping.getReaderConfig();
        var readerParserConfig = eventRunMapping.getReaderParserConfig();

        try {
            dataSource = camundaMappingStateService.getMappingById(mappingName);

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

            var mappingResult = mappingWorkflow.run(persistService.getPersistenceManager(), dataSource, useSavedData, isSimulate);
        } catch (MappingBaseException e) {
            e.printStackTrace();
            //TODO Handling when no mapping is found
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
