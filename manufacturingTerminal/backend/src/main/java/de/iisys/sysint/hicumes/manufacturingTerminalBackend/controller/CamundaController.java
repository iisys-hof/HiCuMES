package de.iisys.sysint.hicumes.manufacturingTerminalBackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.*;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.json.JsonTransformerMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;
import org.eclipse.microprofile.jwt.Claim;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Path("/camunda")
@Singleton
@AccessTimeout(value=60000)
@DenyAll
public class CamundaController {

    private final Logger logger = new Logger(this.getClass().getName());
    @EJB
    PersistService persistService;
    @Inject
    private IEmitEvent events;

    @Inject
    @Claim("preferred_username")
    private String userName;


    private final JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupation.class);
    private final JsonTransformerMachineOccupation jsonTransformerMachineOccupation = new JsonTransformerMachineOccupation();

    SSESessionManager sessionManager = SSESessionManager.getInstance();

    @GET
    @Path("/machineOccupation")
    @RolesAllowed({"worker", "admin"})
    @Lock(LockType.READ)
    public Response getCamundaMachineOccupations(@QueryParam("id") Long id, @QueryParam("businessKey") String businessKey, @QueryParam("machineType") String machineType, @QueryParam("productionOrder") String productionOrder, @QueryParam("loadAllStates")@DefaultValue("false") String loadAllStates) throws JsonParsingUtilsException, DatabaseQueryException {
        boolean isLoadAllStates = loadAllStates.equals("true");
        if(id != null) {
            var order = persistService.getCamundaMachineOccupationById(id);
            return jsonTransformerMachineOccupation.getJsonResponse(order);
        }
        else if(businessKey != null) {
            var order = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);
            return jsonTransformerMachineOccupation.getJsonResponse(order);
        }
        else if(machineType != null) {
            var orders = persistService.getCamundaMachineOccupationByMachineType(machineType, isLoadAllStates);
            return jsonTransformerMachineOccupation.getJsonResponseFromStream(orders);
        }
        else if(productionOrder != null) {
            var orders = persistService.getCamundaMachineOccupationByProductionOrder(productionOrder, isLoadAllStates);
            return jsonTransformerMachineOccupation.getJsonResponseFromStream(orders);
        }

        var orders = persistService.getAllCamundaMachineOccupations(isLoadAllStates);
        return jsonTransformerMachineOccupation.getJsonResponseFromStream(orders);

    }

    @GET
    @Path("/machineOccupation/dept/{deptId}")
    @RolesAllowed({"worker", "admin"})
    @Lock(LockType.READ)
    public Response getCamundaMachineOccupationsDept(@PathParam("deptId") String deptId, @QueryParam("id") Long id, @QueryParam("businessKey") String businessKey, @QueryParam("machineType") String machineType, @QueryParam("productionOrder") String productionOrder, @QueryParam("loadAllStates")@DefaultValue("false") String loadAllStates) throws JsonParsingUtilsException, DatabaseQueryException {
        boolean isLoadAllStates = loadAllStates.equals("true");
        if(id != null) {
            var order = persistService.getCamundaMachineOccupationById(id);
            return jsonTransformerMachineOccupation.getJsonResponse(order);
        }
        else if(businessKey != null) {
            var order = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);
            return jsonTransformerMachineOccupation.getJsonResponse(order);
        }
        else if(machineType != null) {
            var orders = persistService.getCamundaMachineOccupationByMachineType(machineType, isLoadAllStates);
            return jsonTransformerMachineOccupation.getJsonResponseFromStream(orders);
        }
        else if(productionOrder != null) {
            var orders = persistService.getCamundaMachineOccupationByProductionOrder(productionOrder, isLoadAllStates);
            return jsonTransformerMachineOccupation.getJsonResponseFromStream(orders);
        }

        var orders = persistService.getAllCamundaMachineOccupations(isLoadAllStates);
        return jsonTransformerMachineOccupation.getJsonResponseFromStream(orders);
    }

    @GET
    @Path("/machineOccupation/open")
    @RolesAllowed({"worker", "admin"})
    @Lock(LockType.READ)
    public Response getOpenCamundaMachineOccupations(@QueryParam("userName") String userName) throws JsonParsingUtilsException, DatabaseQueryException {

        var orders = persistService.getAllOpenCamundaMachineOccupationsDTOForUser(userName);
        return jsonTransformer.getJsonResponseFromStream(orders);
    }

    @GET
    @Path("/machineOccupationDTO")
    @PermitAll
    @Lock(LockType.READ)
    public Response getCamundaMachineOccupationsDTO(@QueryParam("id") Long id, @QueryParam("businessKey") String businessKey, @QueryParam("machineTypes") List<String> machineTypes, @QueryParam("productionOrder") String productionOrder, @QueryParam("loadAllStates")@DefaultValue("false") String loadAllStates) throws JsonParsingUtilsException, DatabaseQueryException {
        boolean isLoadAllStates = loadAllStates.equals("true");
        if (machineTypes != null && !machineTypes.isEmpty()) {
            var orders = persistService.getCamundaMachineOccupationsDTOByMachineTypes(machineTypes, isLoadAllStates);
            return jsonTransformer.getJsonResponseFromStream(orders);
        }
        var orders = persistService.getAllCamundaMachineOccupationsDTO(isLoadAllStates);
        return jsonTransformer.getJsonResponseFromStream(orders);
    }

    @GET
    @Path("/machineOccupationDTO/dept/{deptId}")
    @PermitAll
    @Lock(LockType.READ)
    public Response getCamundaMachineOccupationsDTODept(@PathParam("deptId") String deptId, @QueryParam("id") Long id, @QueryParam("businessKey") String businessKey, @QueryParam("machineTypes") List<String> machineTypes, @QueryParam("productionOrder") String productionOrder, @QueryParam("loadAllStates")@DefaultValue("false") String loadAllStates) throws JsonParsingUtilsException, DatabaseQueryException {
        boolean isLoadAllStates = loadAllStates.equals("true");
        if(machineTypes != null && !machineTypes.isEmpty()) {
            var orders = persistService.getCamundaMachineOccupationsDTOByMachineTypeDept(deptId, machineTypes, isLoadAllStates);
            return jsonTransformer.getJsonResponseFromStream(orders);
        }
        var orders = persistService.getAllCamundaMachineOccupationsDTODept(deptId, isLoadAllStates);
        return jsonTransformer.getJsonResponseFromStream(orders);

    }

    @GET
    @Path("/machineOccupationDTO/departments")
    @PermitAll
    @Lock(LockType.READ)
    public Response getCamundaMachineOccupationsDTODepartments(@QueryParam("departments") List<String> departments, @QueryParam("id") Long id, @QueryParam("businessKey") String businessKey, @QueryParam("machineTypes") List<String> machineTypes, @QueryParam("productionOrder") String productionOrder, @QueryParam("loadAllStates")@DefaultValue("false") String loadAllStates) throws JsonParsingUtilsException, DatabaseQueryException {
        boolean isLoadAllStates = loadAllStates.equals("true");
        if(machineTypes != null && !machineTypes.isEmpty()) {
            var orders = persistService.getCamundaMachineOccupationsDTOByMachineTypeDepartments(departments, machineTypes, isLoadAllStates);
            return jsonTransformer.getJsonResponseFromStream(orders);
        }
        var orders = persistService.getAllCamundaMachineOccupationsDTODepartments(departments, isLoadAllStates);
        return jsonTransformer.getJsonResponseFromStream(orders);

    }


    @GET
    @Path("/bookingOverview")
    @RolesAllowed({"admin"})
    @Lock(LockType.READ)
    public Response getBookingOverview(@QueryParam("machineType") String machineType,
                                       @DefaultValue("-1") @QueryParam("start") String startDate,
                                       @DefaultValue("-1") @QueryParam("end") String endDate
                                       ) throws JsonParsingUtilsException, DatabaseQueryException {

        if (startDate.equals("-1"))
        {
            startDate = LocalDate.now().toString();
        }

        if (endDate.equals("-1"))
        {
            endDate = LocalDate.now().toString();
        }

        LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate).atStartOfDay().plusDays(1).minusSeconds(1);

        var orders = persistService.getAllCamundaMachineOccupationsByTimeRange(true, startDateTime, endDateTime);
        return jsonTransformerMachineOccupation.getJsonResponseFromStream(orders);

    }


    @GET
    @Path("/bookingOverview2")
    @RolesAllowed({"admin"})
    @Lock(LockType.READ)
    public Response getBookingOverview2(
                                       @DefaultValue("-1") @QueryParam("start") String startDate,
                                       @DefaultValue("-1") @QueryParam("end") String endDate
    ) throws JsonParsingUtilsException, DatabaseQueryException {

        if (startDate.equals("-1"))
        {
            startDate = LocalDate.now().toString();
        }

        if (endDate.equals("-1"))
        {
            endDate = LocalDate.now().toString();
        }

        LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate).atStartOfDay().plusDays(1).minusSeconds(1);

        var orders = persistService.getAllBookingEntriesByTimeRange(true, startDateTime, endDateTime);
        return jsonTransformer.getJsonResponseFromStream(orders);

    }

    @GET
    @Path("/bookingOverview/overheadCosts")
    @RolesAllowed({"admin"})
    @Lock(LockType.READ)
    public Response getBookingOverviewOverheadCosts(
                                       @DefaultValue("-1") @QueryParam("start") String startDate,
                                       @DefaultValue("-1") @QueryParam("end") String endDate
    ) throws JsonParsingUtilsException, DatabaseQueryException {

        if (startDate.equals("-1"))
        {
            startDate = LocalDate.now().toString();
        }

        if (endDate.equals("-1"))
        {
            endDate = LocalDate.now().toString();
        }

        LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate).atStartOfDay().plusDays(1).minusSeconds(1);

        var orders = persistService.getAllBookingEntriesOverheadCostsByTimeRange(true, startDateTime, endDateTime);
        return jsonTransformer.getJsonResponseFromStream(orders);

    }

    @GET
    @Path("/bookingOverview2/{userName}")
    @RolesAllowed({"admin", "worker"})
    @Lock(LockType.READ)
    public Response getBookingOverview2ForUser(
                                       @DefaultValue("-1") @QueryParam("start") String startDate,
                                       @DefaultValue("-1") @QueryParam("end") String endDate,
                                       @PathParam("userName") String userName
    ) throws JsonParsingUtilsException, DatabaseQueryException {

        if (startDate.equals("-1"))
        {
            startDate = LocalDate.now().toString();
        }

        if (endDate.equals("-1"))
        {
            endDate = LocalDate.now().toString();
        }

        LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate).atStartOfDay().plusDays(1).minusSeconds(1);

        var orders = persistService.getAllBookingEntriesByTimeRangeAndUser(true, startDateTime, endDateTime, userName);
        return jsonTransformer.getJsonResponseFromStream(orders);
    }

    @GET
    @Path("/bookingOverview/overheadCosts/{userName}")
    @RolesAllowed({"admin", "worker"})
    @Lock(LockType.READ)
    public Response getBookingOverviewOverheadCostsForUser(
                                       @DefaultValue("-1") @QueryParam("start") String startDate,
                                       @DefaultValue("-1") @QueryParam("end") String endDate,
                                       @PathParam("userName") String userName
    ) throws JsonParsingUtilsException, DatabaseQueryException {

        if (startDate.equals("-1"))
        {
            startDate = LocalDate.now().toString();
        }

        if (endDate.equals("-1"))
        {
            endDate = LocalDate.now().toString();
        }

        LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate).atStartOfDay().plusDays(1).minusSeconds(1);

        var orders = persistService.getAllBookingEntriesOverheadCostsByTimeRangeAndUser(true, startDateTime, endDateTime, userName);
        return jsonTransformer.getJsonResponseFromStream(orders);
    }

    @POST
    @Path("/startMachineOccupation")
    @RolesAllowed({"worker", "admin"})
    public Response startMachineOccupation(@Context HttpServletRequest request, JsonNode input) {
        //var requesterId = "1234";
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, input.get("machineOccupationId").asText());
        var event = new EventCamundaStartProcess(input.get("machineOccupationId").asInt(), input.get("machineId").asInt(), input.get("userName").asText());
        events.emit(event,(result) -> {
            System.out.println("EVENT CALLBACK GEHT");
        });
        return Response.ok().build();
    }

    @POST
    @Path("/pauseMachineOccupation")
    @RolesAllowed({"worker", "admin"})
    @Lock(LockType.READ)
    public Response pauseMachineOccupation(@Context HttpServletRequest request, JsonNode input) throws JsonParsingUtilsException {
        //var requesterId = "1234";
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, input.get("machineOccupationId").asText());
        var event = new EventProcessPaused(input.get("businessKey").asText(), input.get("camundaProcessInstanceId").asText(), input.get("userName").asText(), input.get("formKey").asText(), input.get("suspensionType").asText());
        events.emit(event);
        return Response.ok().build();
    }

    @POST
    @Path("/continueMachineOccupation")
    @RolesAllowed({"worker", "admin"})
    public Response continueMachineOccupation(@Context HttpServletRequest request, JsonNode input) throws JsonParsingUtilsException {
        //var requesterId = "1234";
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, input.get("machineOccupationId").asText());

        var event = new EventProcessContinued(input.get("businessKey").asText(), input.get("camundaProcessInstanceId").asText(), input.get("userName").asText(), input.get("formKey").asText());
        events.emit(event);
        return Response.ok().build();
    }

    @POST
    @Path("/correctTimeMachineOccupation")
    @RolesAllowed({"worker", "admin"})
    @Lock(LockType.READ)
    public Response correctTimeMachineOccupation(@Context HttpServletRequest request, JsonNode input) throws JsonParsingUtilsException {
        //var requesterId = "1234";
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, input.get("machineOccupationId").asText());
        var event = new EventMachineOccupationCorrectTime(input.get("businessKey").asText(), input.get("userName").asText(), input.get("durationSeconds").asLong());
        events.emit(event);
        return Response.ok().build();
    }

    @POST
    @Path("/changeStatus")
    @RolesAllowed({"admin"})
    public Response changeStatus(@Context HttpServletRequest request, JsonNode input) throws JsonParsingUtilsException {
        //var requesterId = "1234";
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, input.get("machineOccupationId").asText());

        var event = new EventChangeMachineOccupationStatus(input.get("machineOccupationId").asInt(), input.get("status").asText(), input.get("userName").asText());
        events.emit(event);
        return Response.ok().build();
    }

    @POST
    @Path("/resendBooking")
    @RolesAllowed({"worker", "admin"})
    public Response resendBooking(@Context HttpServletRequest request, JsonNode input) throws JsonParsingUtilsException {
        //var requesterId = "1234";
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, input.get("machineOccupationId").asText());

        var event = new EventResendBooking(input.get("id").asLong());
        events.emit(event);
        return Response.ok().build();
    }

    @GET
    @PermitAll
    @Path("/activeTask")
    public Response activeTask(@Context HttpServletRequest request, @QueryParam("businessKey") String businessKey,  JsonNode input) {
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, businessKey);

        var userName = input.get("userName").asText();
        var event = new EventRequestActiveTask(businessKey, userName);
        events.emit(event);
        return Response.ok().build();
    }

    @POST
    @Path("/finishFormField")
    @RolesAllowed({"worker", "admin"})
    public Response finishFormField(@Context HttpServletRequest request, JsonNode input) {
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, input.get("id").asText());
        EventCamundaFinishFormField event = new EventCamundaFinishFormField(input.get("taskId").asText(), input.get("id").asInt(), (ObjectNode) input.get("formField"), input.get("userName").asText());

        if(input.get("suspensionType") != null)
        {
            event.setSuspensionType(input.get("suspensionType").asText());
        }

        events.emit(event,(result) -> {
            System.out.println("EVENT CALLBACK GEHT");
        });
        System.out.println("BITTE NACH DEM CALLBACK");
        return Response.ok().build();
    }

    @POST
    @Path("/persistFormField")
    @RolesAllowed({"worker", "admin"})
    public Response persistFormField(@Context HttpServletRequest request, JsonNode input) {
        //var requesterId = request.getHeader("User-Agent");
        //sessionManager.addAssigment(requesterId, input.get("id").asText());
        EventCamundaPersistFormField event;
        event = new EventCamundaPersistFormField(input.get("parentCamundaMOId").asInt(), input.get("subMachineOccupationId").asInt(), (ObjectNode) input.get("formFields"), input.get("userName").asText());

        events.emit(event,(result) -> {
            System.out.println("EVENT CALLBACK GEHT");
        });
        System.out.println("BITTE NACH DEM CALLBACK");
        return Response.ok().build();
    }

    @GET
    @Path("/startSSESession")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @PermitAll
    @Lock(LockType.READ)
    public void startSSESession(@Context HttpServletRequest request, @Context Sse sse, @Context SseEventSink eventSink) {
        var requesterId = request.getHeader("User-Agent");
        System.out.println("Client opened Session with id: " + requesterId);
        //sessionManager.registerClient(requesterId, sse, eventSink);
        sessionManager.registerAnonymousClient(sse, eventSink);
    }

    /*@GET
    @Path("/heartbeat")
    @PermitAll
    @Lock(LockType.READ)
    public Response heartbeat(@Context HttpServletRequest request) {
        return Response.ok().build();
    }*/
}
