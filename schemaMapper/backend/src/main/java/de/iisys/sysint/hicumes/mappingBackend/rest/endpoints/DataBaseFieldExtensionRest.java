package de.iisys.sysint.hicumes.mappingBackend.rest.endpoints;

import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.MappingBackendBaseException;
import de.iisys.sysint.hicumes.mappingBackend.extendedClassCreator.ExtendedClassPersistService;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.AllClassExtension;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;


@Path("/fieldExtension")
public class DataBaseFieldExtensionRest {

    @EJB
    ExtendedClassPersistService extendedClassPersistService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateField(AllClassExtension allClassExtension) throws MappingBackendBaseException, UtilsBaseException, URISyntaxException {
        extendedClassPersistService.saveExtended(allClassExtension);
        return Response.ok().build();
    }

    @GET
    public Response getAllClasses() {
        var classes = extendedClassPersistService.getAllClasses();
        return Response.ok(classes).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
