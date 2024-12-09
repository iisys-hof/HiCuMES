package de.iisys.sysint.hicumes.manufacturingTerminalBackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaStartProcess;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import static de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IStaticDependencies.hazelServer;

@Path("/dev/")
@DenyAll
@Singleton
public class DevController
{

    private final Logger logger = new Logger(this.getClass().getName());
    @EJB
    PersistService persistService;
    @Inject
    private IEmitEvent events;
    private final JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewCoreData.class);
    SSESessionManager sessionManager = SSESessionManager.getInstance();

    @POST
    @Path("/postHazel")
    @RolesAllowed({"dev"})
    public Response postHZMessage(@Context HttpServletRequest request, JsonNode input) throws JsonParsingUtilsException, DatabaseQueryException {
        try
        {
            hazelServer.sendMultipleEvents((ArrayNode) input);
            return Response.ok().build();
        }
        catch (Exception e)
        {
            return Response.serverError().build();
        }

    }

    @POST
    @Path("/clearSSE/nice")
    @RolesAllowed({"dev"})
    public Response clearSSE(@Context HttpServletRequest request, JsonNode input) throws JsonParsingUtilsException, DatabaseQueryException {
        sessionManager.clearPoolNice();
        return Response.ok().build();

    }

    @POST
    @Path("/clearSSE/dirty")
    @RolesAllowed({"dev"})
    public Response clearSSEDirty(@Context HttpServletRequest request, JsonNode input) throws JsonParsingUtilsException, DatabaseQueryException {
        sessionManager.clearPoolDirty();
        return Response.ok().build();
    }
}
