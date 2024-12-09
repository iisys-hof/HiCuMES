package de.iisys.sysint.hicumes.hicumeslite.controller;

import de.iisys.sysint.hicumes.hicumeslite.entities.BookingEntry;
import de.iisys.sysint.hicumes.hicumeslite.entities.RestRequestConfig;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
@Path("")
public class RestEndpoint {

    @EJB
    RestController restController;

    //Returns the bookingEntry for the externalId
    @GET
    @Path("/bookingentries/list/{externalId}")
    public Response getBookingEntriesByExternalId(@PathParam("externalId") String externalId){
        return restController.getBookingEntriesByExternalId(externalId);
    }

    @GET
    @Path("/bookingentries/list/user/{personalNumber}")
    public Response getBookingEntriesOpenByUserPersonalNumber(@PathParam("personalNumber") String personalNumber){
        return restController.getBookingEntriesOpenByUserPersonalNumber(personalNumber);
    }

    @GET
    @Path("/bookingentries/{id}")
    public Response getSingleBookingEntryByExternalId(@PathParam("id") Long id){
        return restController.getSingleBookingEntryByExternalId(id);
    }

    @PUT
    @Path("/bookingentries/{externalId}")
    public Response createBookingEntry(@PathParam("externalId") String externalId, BookingEntry updatedEntry){
        return restController.createBookingEntry(updatedEntry);
    }

    @POST
    @Path("/bookingentries/{externalId}")
    public Response updateBookingEntry(@PathParam("externalId") String externalId, BookingEntry updatedEntry){
        return restController.updateBookingEntry(updatedEntry);
    }

    @POST
    @Path("/bookingentries/sendFinished")
    public Response sendFinished(RestRequestConfig config){
        return restController.sendFinished(config);
    }

    @GET
    @Path("/user/{personalNumber}")
    public Response getUserByPersonalNumber(@PathParam("personalNumber") String personalNumber){
        return restController.getUserByPersonalNumber(personalNumber);
    }



//    //BookingEntry with duration
//    @PUT
//    @Path("/{externalId}")
//    public Response createBookingEntryDuration(@PathParam("externalId") String externalId, BookingEntry updatedEntry){
//        return bookingEntryController.createBookingEntryDuration(externalId, updatedEntry);
//    }
//
//    //BookingEntry with start/stop
//    @PUT
//    @Path("/{externalId}/start")
//    public Response createBookingEntryStartTime(@PathParam("externalId") String externalId, BookingEntry updatedEntry){
//        return bookingEntryController.createBookingEntryStart(externalId, updatedEntry);
//    }
//
//    //BookingEntry with start/stop
//    @PUT
//    @Path("/{externalId}/stop")
//    public Response createBookingEntryStopTime(@PathParam("externalId") String externalId, BookingEntry updatedEntry){
//        return bookingEntryController.createBookingEntryStop(externalId, updatedEntry);
//    }

//    @POST
//    @Path("/{externalId}")
//    public Response updateBookingEntryStop(@PathParam("externalId") String externalId, BookingEntry updatedEntry){
//        return bookingEntryController.updateBookingEntry(externalId, updatedEntry);
//    }

    @GET
    public Response getBookingEntriesByDateRange(@QueryParam("start") String start, @QueryParam("end") String end){
       return restController.getBookingEntriesByDateRange(start, end);
    }
}
