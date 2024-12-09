package de.iisys.sysint.hicumes.mappingBackend.rest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.mappingBackend.events.EventController;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.MappingBackendBaseException;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.mappingExceptions.EmptyFormsException;
import de.iisys.sysint.hicumes.mappingBackend.mapping.services.FormsStateService;
import de.iisys.sysint.hicumes.mappingBackend.mapping.services.MappingPersistService;
import de.iisys.sysint.hicumes.mappingBackend.mapping.treeCreation.MappingDependencyTreeToFrontendTreeNode;
import de.iisys.sysint.hicumes.mappingBackend.mapping.treeCreation.MappingInputToFrontendTreeNode;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping.FrontendMappingRequest;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping.FrontendMappingResponse;
import de.iisys.sysint.hicumes.mappingBackend.startup.StartupController;
import de.iisys.sysint.hicumes.mappingEngine.MappingWorkflow;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.plugins.MapperPluginManager;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/mappingEndpoint")
public class MappingRest {

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        long start = System.currentTimeMillis();
        try {
            var result = ctx.proceed();

            long timeElapsed = System.currentTimeMillis() - start;
            System.out.println("Finished request in " + timeElapsed  + " ms");
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            Response response = null;
            try {
                response = Response.serverError().entity(mapper.writeValueAsString(ex) ).build();
            } catch (JsonProcessingException e) {
                System.err.println("Exception during parsing the exception.");
                e.printStackTrace();
                throw ex;
            }
            long timeElapsed = System.currentTimeMillis() - start;
            System.err.println("Exception in request in " + timeElapsed  + " ms");
            return  response;
        }
    }

    MappingWorkflow mappingWorkflow = new MappingWorkflow(StartupController.hazelServer);

    MapperPluginManager mapperPluginManager = MapperPluginManager.getInstance();

    @EJB
    MappingDependencyTreeToFrontendTreeNode mappingDependencyTreeToFrontendTreeNode;

    @EJB
    MappingInputToFrontendTreeNode mappingInputToTreeMapTransformer;

    @EJB
    MappingPersistService mappingPersistService;

    @EJB
    FormsStateService formsStateService;

    @EJB
    EventController eventController;

    @POST
    @Path("/updateMappingState")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMappingState(FrontendMappingRequest request) throws JsonProcessingException, MappingBackendBaseException, MappingBaseException {
        var persistenceManager = mappingPersistService.getPersistenceManager();

        var dataSource = request.getMappingAndDataSource();
        var mappingResult = mappingWorkflow.run(persistenceManager, dataSource,request.isUseSavedData(), request.isSimulate());

        var outputTree = mappingDependencyTreeToFrontendTreeNode.transform(mappingResult.getMappingDependencyTree());
        var inputTree = mappingInputToTreeMapTransformer.transform(mappingResult.getMappingInput());

        //TODO make tree generation more generic
        //var outputTree = mappingTreeCreator.createOutputTree(mappingResult, datasource)
        //var inputTree = mappingTreeCreator.createInputTree(mappingResult, datasource)

        var newState = new FrontendMappingResponse(inputTree, outputTree, mappingResult.getMappingInput(), mappingResult.getMappingOutput(), mappingResult.getMappingLogging(), mappingResult.getReaderResult());

        return Response.ok( newState.toJson()).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Path("/saveMapping")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveMapping(MappingAndDataSource mappingAndDataSource) throws DatabasePersistException {
        //TODO check encoding error when writing readerresult in db
        mappingPersistService.persistMappingAndDatasourceAndRemoveOld(mappingAndDataSource);
        eventController.mappingSaved(mappingAndDataSource);
        return Response.ok().build();
    }

    @POST
    @Path("/runMapping")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response runMapping(FrontendMappingRequest request) {
        var persistenceManager = mappingPersistService.getPersistenceManager();
        MappingAndDataSource dataSource = null;

        var mappingName = request.getMappingAndDataSource().getName();
        var useSavedData = request.isUseSavedData();
        var isSimulate = request.isSimulate();
        var writerConfig = request.getMappingAndDataSource().getDataWriter().getWriterKeyValueConfigs();
        var writerParserConfig = request.getMappingAndDataSource().getDataWriter().getParserKeyValueConfigs();
        var readerConfig = request.getMappingAndDataSource().getDataReader().getReaderKeyValueConfigs();
        var readerParserConfig = request.getMappingAndDataSource().getDataReader().getParserKeyValueConfigs();
        var readerResult = request.getMappingAndDataSource().getReaderResult();

        try {
            dataSource = mappingPersistService.getMappingAndDataSourceByName(mappingName);

            if(writerConfig != null)
            {
                dataSource.getDataWriter().setWriterKeyValueConfigs(writerConfig);
            }
            if(writerParserConfig != null)
            {
                dataSource.getDataWriter().setParserKeyValueConfigs(writerParserConfig);
            }
            if(readerConfig != null)
            {
                dataSource.getDataReader().setReaderKeyValueConfigs(readerConfig);
            }
            if(readerParserConfig != null)
            {
                dataSource.getDataReader().setParserKeyValueConfigs(readerParserConfig);
            }
            if(readerResult != null)
            {
                dataSource.setReaderResult(readerResult);
            }

            //System.out.println(dataSource);

            var mappingResult = mappingWorkflow.run(persistenceManager, dataSource, useSavedData, isSimulate);
            return Response.ok( mappingResult.getMappingOutput()).type(MediaType.APPLICATION_JSON_TYPE).build();
        } catch (DatabaseQueryException | MappingBaseException e) {
            System.err.println("Exception during parsing the exception.");
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/availableMappings")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response availableMappings() {
        var mappings = mappingPersistService.getPersistenceManager().getAllByClass(MappingAndDataSource.class);
        var mappingsList = mappings.collect(Collectors.toList());
        var response = Response.ok( mappingsList).type(MediaType.APPLICATION_JSON_TYPE).build();
        return response;
    }

    @GET
    @Path("/camundaForms")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response camundaForms() throws EmptyFormsException
    {
        var forms = formsStateService.getForms();
        var response = Response.ok( forms).type(MediaType.APPLICATION_JSON_TYPE).build();
        return response;
    }

    @GET
    @Path("/pluginInfos")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPluginInfos()
    {
        var pluginInfos = mapperPluginManager.getPluginInfos();
        var response = Response.ok( pluginInfos).type(MediaType.APPLICATION_JSON_TYPE).build();
        return response;
    }
}
