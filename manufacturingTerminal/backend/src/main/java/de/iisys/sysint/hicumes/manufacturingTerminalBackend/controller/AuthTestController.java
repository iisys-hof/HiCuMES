package de.iisys.sysint.hicumes.manufacturingTerminalBackend.controller;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventRequestActiveTask;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/user")
public class AuthTestController {

    @GET
    @Path("/workerRole")
    @RolesAllowed({"worker"})
    public String getWithUser(@Context HttpServletRequest request) {
        return "Wroker geht";
    }

    @GET
    @Path("/adminRole")
    @RolesAllowed({"admin"})
    public String getWithAdmin(@Context HttpServletRequest request) {
        return "Admin";
    }

    @GET
    @Path("/workerAndAdminRole")
    @RolesAllowed({"worker", "admin"})
    public String getWithUserAndAdmin(@Context HttpServletRequest request) {
        return "Admin und Worker";
    }
}
