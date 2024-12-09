package de.iisys.sysint.hicumes.manufacturingTerminalBackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.entities.AuxiliaryMaterials;
import de.iisys.sysint.hicumes.core.entities.CD_OverheadCostCenter;
import de.iisys.sysint.hicumes.core.entities.SubProductionStep;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.entities.unit.Measurement;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.*;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import org.eclipse.microprofile.jwt.Claim;
import tech.units.indriya.quantity.Quantities;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.inject.Inject;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.iisys.sysint.hicumes.core.entities.unit.ExtendedUnits.PIECE;
import static javax.measure.MetricPrefix.KILO;
import static javax.measure.MetricPrefix.MEGA;
import static tech.units.indriya.AbstractUnit.ONE;
import static tech.units.indriya.unit.Units.*;

@Path("/production")
@Singleton
@DenyAll
public class ProductionController {


    private final Logger logger = new Logger(this.getClass().getName());
    @EJB
    PersistService persistService;
    @Inject
    private IEmitEvent events;
    private final JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupation.class);

    @GET
    @Path("/productionOrder")
    @PermitAll
    @Lock(LockType.READ)
    public Response getProductionOrders(@QueryParam("id") Long id) throws JsonParsingUtilsException, DatabaseQueryException {
        if(id != null) {
            var order = persistService.getProductionOrderById(id);
            return jsonTransformer.getJsonResponse(order);
        }
        var orders = persistService.getAllProductionOrders();
        return jsonTransformer.getJsonResponseFromStream(orders);
    }

    @GET
    @Path("machine")
    @PermitAll
    @Lock(LockType.READ)
    public Response getMachine() throws JsonParsingUtilsException {
        var machines = persistService.getAllMachines();
        return jsonTransformer.getJsonResponseFromStream(machines);
    }

    @GET
    @Path("machineType")
    @PermitAll
    @Lock(LockType.READ)
    public Response getMachineType() throws JsonParsingUtilsException {
        var machineTypes = persistService.getAllMachineTypes();
        return jsonTransformer.getJsonResponseFromStream(machineTypes);
    }

    @GET
    @Path("departments")
    @PermitAll
    @Lock(LockType.READ)
    public Response getDepartments() throws JsonParsingUtilsException {
        var departments = persistService.getAllDepartments();
        return jsonTransformer.getJsonResponseFromStream(departments);
    }

    @POST
    @Path("/setActiveToolSetting")
    @RolesAllowed({"worker", "admin"})
    public Response setActiveToolSetting(@Context HttpServletRequest request, JsonNode input) {
        var machineOccupationId = input.get("machineOccupationId").asInt();
        var toolSettingsNode = input.get("toolSettingId");
        List<Integer> toolSettingIds = new ArrayList<Integer>();

        if(toolSettingsNode.isArray())
        {
            for(JsonNode arrayElement: toolSettingsNode)
            {
                toolSettingIds.add(arrayElement.asInt());
            }
        }

        var event = new EventSetActiveToolSettings(machineOccupationId, toolSettingIds);
        events.emit(event);
        return Response.ok().build();
    }


    @POST
    @Path("/createCollectionOrder")
    @RolesAllowed({"worker", "admin"})
    public Response createCollectionOrder(@Context HttpServletRequest request, JsonNode input) {
        var subMachineOccupationsNode = input.get("machineOccupations");

        CamundaMachineOccupation collectionOrder = null;
        //List<CamundaMachineOccupation> camundaMachineOccupations = new ArrayList<CamundaMachineOccupation>();
        Map<CamundaMachineOccupation, Double> subMachineOccupationAmount = new HashMap<>();

        if(subMachineOccupationsNode.isArray())
        {
            for(JsonNode arrayElement: subMachineOccupationsNode)
            {
                try {
                    var machineOccupation = persistService.getCamundaMachineOccupationById(arrayElement.get("id").asInt());
                    //camundaMachineOccupations.add(machineOccupation);
                    subMachineOccupationAmount.put(machineOccupation, arrayElement.get("amount").asDouble());
                } catch (DatabaseQueryException e) {
                    e.printStackTrace();
                }
            }
        }
        if(input.get("collectionOrderId") != null)
        {
            try
            {
                var collectionOrderId = input.get("collectionOrderId").asInt();
                collectionOrder = persistService.getCamundaMachineOccupationById(collectionOrderId);
            } catch (DatabaseQueryException e) {
                e.printStackTrace();
            }
        }

        //TODO check if Occupations can be collected
        var event = new EventCreateCollectionOrder(collectionOrder, subMachineOccupationAmount);
        events.emit(event);
        return Response.ok().build();
    }

    @POST
    @Path("/addToCollectionOrder")
    @RolesAllowed({"worker", "admin"})
    public Response addToCollectionOrder(@Context HttpServletRequest request, JsonNode input) {
        var collectionOrderId = input.get("collectionOrderId").asInt();
        var subMachineOccupationId = input.get("subMachineOccupationId").asInt();
        try {
            var collectionOrder = persistService.getCamundaMachineOccupationById(collectionOrderId);
            var subMachineOccupation = persistService.getCamundaMachineOccupationById(subMachineOccupationId);

            var event = new EventAddToCollectionOrder(collectionOrder, subMachineOccupation);
            events.emit(event);
            return Response.ok().build();

        } catch (DatabaseQueryException e) {
            e.printStackTrace();

            return Response.serverError().build();
        }

    }

    @POST
    @Path("/splitOrder")
    @RolesAllowed({"worker", "admin"})
    public Response createSplitOrder(@Context HttpServletRequest request, JsonNode input) {
        //TODO error handling
        var camundaMachineOccupationId = input.get("machineOccupation").asInt();

        try {
            var camundaMachineOccupation = persistService.getCamundaMachineOccupationById(camundaMachineOccupationId);
            var splitsNode = input.get("splits");

            List<Double> splits = new ArrayList<Double>();

            if(splitsNode.isArray())
            {
                for(JsonNode arrayElement: splitsNode)
                {
                    splits.add(arrayElement.asDouble());
                }
            }

            var event = new EventCreateSplitOrder(camundaMachineOccupation, splits);
            events.emit(event);
            return Response.ok().build();

        } catch (DatabaseQueryException e) {
            e.printStackTrace();

            return Response.serverError().build();
        }


    }

    @POST
    @Path("/addNote")
    @RolesAllowed({"worker", "admin"})
    public Response addNote(@Context HttpServletRequest request, JsonNode input) {
        //TODO error handling
        var machineOccupationId = input.get("machineOccupation").asInt();
        var note = input.get("note").asText();
        var userName = input.get("userName").asText();

        var event = new EventAddNote(machineOccupationId, note, userName);
        events.emit(event);
        return Response.ok().build();
    }

    @GET
    @Path("/overheadCosts")
    @RolesAllowed({"worker", "admin"})
    public Response getOverheadCosts(@Context HttpServletRequest request, @QueryParam("userName") String userName, @DefaultValue("-1") @QueryParam("start") String startDate,
                                     @DefaultValue("-1") @QueryParam("end") String endDate) {
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

        try {
            var costs = persistService.getAllOverheadCostsForUser(userName, startDateTime, endDateTime);
            return jsonTransformer.getJsonResponse(costs);
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/overheadCosts/open")
    @RolesAllowed({"worker", "admin"})
    public Response getOverheadCosts(@Context HttpServletRequest request, @QueryParam("userName") String userName) {

        try {
            var costs = persistService.getAllOpenOverheadCostsForUser(userName);
            return jsonTransformer.getJsonResponse(costs);
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/overheadCostCenters")
    @RolesAllowed({"worker", "admin"})
    public Response getOverheadCostCenters(@Context HttpServletRequest request) {
        try {
            var costCenters = persistService.getPersistenceManager().getAllByClass(CD_OverheadCostCenter.class);
            return jsonTransformer.getJsonResponseFromStream(costCenters);
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/overheadCostCenters/top")
    @RolesAllowed({"worker", "admin"})
    public Response getTopOverheadCosts(@Context HttpServletRequest request, @QueryParam("userName") String userName) throws DatabaseQueryException {
        try {
            var topCostCenters = persistService.getTopOverheadCostCenters(userName);
            return jsonTransformer.getJsonResponseFromStream(topCostCenters);
            } catch (Exception e) {
                return Response.serverError().build();
            }
    }

    @POST
    @Path("/overheadCosts/{id}")
    @RolesAllowed({"worker", "admin"})
    public Response addOverheadCosts(@Context HttpServletRequest request, @PathParam("id") int id, JsonNode input) {
        var userName = input.get("userName").asText();
        var costCenterExtId = input.get("costCenter").asText();
        var note = input.get("note").asText();

        var event = new EventOverheadCosts(id, userName, costCenterExtId, note, true);
        events.emit(event);
        return Response.ok().build();
    }

    @POST
    @Path("/overheadCosts/{id}/editNote")
    @RolesAllowed({"worker", "admin"})
    public Response editOverheadCosts(@Context HttpServletRequest request, @PathParam("id") int id, JsonNode input) {
        var userName = input.get("userName").asText();
        var costCenterExtId = input.get("costCenter").asText();
        var note = input.get("note").asText();

        var event = new EventOverheadCosts(id, userName, costCenterExtId, note, false);
        events.emit(event);
        return Response.ok().build();
    }

    @GET
    @Path("/userOccupation/mo/{id}")
    @RolesAllowed({"worker", "admin"})
    public Response getUserOccupationByMO(@Context HttpServletRequest request, @PathParam("id") int id) {
        try {
            var userOccupation = persistService.getUserOccupationByMachineOccupationId(id);
            return jsonTransformer.getJsonResponse(userOccupation);
        } catch (DatabaseQueryException | JsonParsingUtilsException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/userOccupation/cmo/{id}")
    @RolesAllowed({"worker", "admin"})
    public Response getUserOccupationByCMO(@Context HttpServletRequest request, @PathParam("id") int id) {
        try {
            var userOccupation = persistService.getUserOccupationByCamundaMachineOccupationId(id);
            return jsonTransformer.getJsonResponse(userOccupation);
        } catch (DatabaseQueryException | JsonParsingUtilsException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/userOccupation/cmo/{id}/add")
    @RolesAllowed({"worker", "admin"})
    public Response addUserToUserOccupationByCMO(@Context HttpServletRequest request, @PathParam("id") int id, JsonNode input) {
        try {

            var userName = input.get("userName").asText();
            var event = new EventUserOccupationJoin(userName, id);
            events.emit(event);

            var userOccupation = persistService.getUserOccupationByCamundaMachineOccupationId(id);
            return jsonTransformer.getJsonResponse(userOccupation);
        } catch (DatabaseQueryException | JsonParsingUtilsException e) {
            return Response.serverError().build();
        }
    }
/*
    @GET
    @Path("test")
    @PermitAll
    @Lock(LockType.READ)
    public Response testMeasurement() throws JsonParsingUtilsException, DatabasePersistException {
        var measurements = new ArrayList<Measurement>();
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, AMPERE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, CANDELA)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, KELVIN)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, KILOGRAM)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, METRE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, MOLE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, SECOND)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, GRAM)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, RADIAN)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, STERADIAN)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, HERTZ)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, NEWTON)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, PASCAL)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, JOULE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, WATT)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, COULOMB)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, VOLT)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, FARAD)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, OHM)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, SIEMENS)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, WEBER)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, TESLA)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, HENRY)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, CELSIUS)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, LUMEN)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, LUX)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, BECQUEREL)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, GRAY)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, SIEVERT)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, KATAL)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, METRE_PER_SECOND)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, METRE_PER_SQUARE_SECOND)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, SQUARE_METRE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, CUBIC_METRE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, KILOMETRE_PER_HOUR)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, PERCENT)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, MINUTE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, HOUR)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, DAY)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, WEEK)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, YEAR)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, MONTH)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, LITRE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, KILO(HERTZ))));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, MEGA((HERTZ)))));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, PIECE)));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, KILO(PIECE))));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, KILO(SQUARE_METRE))));
        measurements.add(new Measurement(Quantities.getQuantity(123456.789, ONE)));


        measurements.add(new Measurement(123456.789, "pcs"));
        measurements.add(new Measurement(123456.789, "Hz"));
        measurements.add(new Measurement(123456.789, "MHz"));
        measurements.add(new Measurement(123456.789, "m²"));
        measurements.add(new Measurement(123456.789, "m/s²"));
        measurements.add(new Measurement(123456.789, "week"));
        var test = new Measurement(123456.789, "MHz");
        var squaremeter = new Measurement(123456.789, "m²");
        var squaremeter2 = new Measurement(Quantities.getQuantity(123456.789, SQUARE_METRE));
        var kilosquaremeter2 = new Measurement(Quantities.getQuantity(123456.789, KILO(SQUARE_METRE)));

        SubProductionStep step = new SubProductionStep();
        persistService.getPersistenceManager().persist(step);

        for (Measurement measurement: measurements) {
            AuxiliaryMaterials materials = new AuxiliaryMaterials(step, measurement);
            persistService.getPersistenceManager().persist(materials);
        }

        Stream<AuxiliaryMaterials> result = persistService.getPersistenceManager().getAllByClass(AuxiliaryMaterials.class);
        var results = result.collect(Collectors.toList());
        System.out.println(results);

        System.out.println(test.getQuantity());
        System.out.println(test.getQuantity().to(KILO(HERTZ)));
        System.out.println(squaremeter.getQuantity());
        System.out.println(squaremeter2.getQuantity());
        System.out.println(kilosquaremeter2.getQuantity());
        System.out.println(kilosquaremeter2.getQuantity().to(SQUARE_METRE));
        System.out.println(kilosquaremeter2.getQuantity().to(MEGA(SQUARE_METRE)));
        return Response.ok().build();
    }*/
}
