package de.iisys.sysint.hicumes.manufacturingTerminalBackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaFinishFormField;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaStartProcess;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventRequestActiveTask;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/coreData")
@Singleton
public class CoreDataController
{

    private final Logger logger = new Logger(this.getClass().getName());
    @EJB
    PersistService persistService;
    @Inject
    private IEmitEvent events;
    private final JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewCoreData.class);

    @GET
    @Path("/product")
    public Response getProduct(@QueryParam("id") Long id) throws JsonParsingUtilsException, DatabaseQueryException {
        if(id != null) {
            var product = persistService.getProductById(id);
            return jsonTransformer.getJsonResponse(product);
        }
        var products = persistService.getAllProducts();
        return jsonTransformer.getJsonResponseFromStream(products);
    }

    @GET
    @Path("/productRelationship")
    public Response getProductRelations(@QueryParam("id") Long id, @QueryParam("extIdProductionOrder") String extIdProductionOrder) throws JsonParsingUtilsException, DatabaseQueryException {
        if(id != null) {
            if(extIdProductionOrder == null) {
                var productRelationship = persistService.getProductRelationshipsByProductId(id);
                return jsonTransformer.getJsonResponseFromStream(productRelationship);
            }
            else {
                var productRelationship = persistService.getProductRelationshipsByProductIdFilterByProductionOrder(id, extIdProductionOrder);
                return jsonTransformer.getJsonResponseFromStream(productRelationship);
            }
        }
        return null;
    }
}
