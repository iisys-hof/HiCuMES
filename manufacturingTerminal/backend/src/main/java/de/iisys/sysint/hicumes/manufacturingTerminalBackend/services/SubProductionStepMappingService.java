package de.iisys.sysint.hicumes.manufacturingTerminalBackend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.TerminalMappingException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.startup.StartupController;
import de.iisys.sysint.hicumes.mappingEngine.MappingWorkflow;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingOutput;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class SubProductionStepMappingService {
    @EJB
    private CamundaMappingStateService camundaMappingStateService;
    private static final MappingWorkflow mappingWorkflow = new MappingWorkflow(StartupController.hazelServer);

    @EJB
    PersistService persistService;

    private final Logger logger = new Logger(this.getClass().getName());


    public MappingOutput map(JsonNode formfield, String taskDefinitionKey) throws TerminalMappingException, MappingException {
        return map(formfield, null, taskDefinitionKey);
    }

    public MappingOutput map(JsonNode formfield, JsonNode processVariables, String taskDefinitionKey) throws TerminalMappingException, MappingException {
        if(formfield != null) {
            logger.logMessage("formfield", "x", formfield.toPrettyString());
        }
        logger.logMessage("taskDefinitionKey", "x", taskDefinitionKey);

        var mapping = camundaMappingStateService.getMappingById(taskDefinitionKey);
        ObjectNode fields = (ObjectNode) formfield;
        if(processVariables != null && fields != null) {
                fields.set("processVariables", processVariables);
        }
        //var result = mappingWorkflow.internalWriter(persistService.getPersistenceManager(), mapping, new MappingInput(formfield), true);
        var result = mappingWorkflow.doMapping(persistService.getPersistenceManager(), mapping, true, new MappingInput(fields));

        logger.logMessage("result", "x", result.getMappingOutput().resultAsList());
        logger.logMessage("result", "x", result.getMappingOutput().resultAsList().get(0));
        return result.getMappingOutput();
    }
}
