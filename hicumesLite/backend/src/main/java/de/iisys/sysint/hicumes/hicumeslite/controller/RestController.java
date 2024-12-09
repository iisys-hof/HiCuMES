package de.iisys.sysint.hicumes.hicumeslite.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.iisys.sysint.hicumes.hicumeslite.entities.BookingEntry;
import de.iisys.sysint.hicumes.hicumeslite.entities.RestRequestConfig;
import de.iisys.sysint.hicumes.hicumeslite.entities.User;
import de.iisys.sysint.hicumes.hicumeslite.manager.PersistService;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Singleton
@AccessTimeout(value=60000)
public class RestController {
    @EJB
    PersistService persistService;

    @Inject
    @ConfigProperty(name="MappingBackendAddress", defaultValue = "http://localhost:8080/mappingBackend/data/mappingEndpoint/runMapping")
    private Optional<String> mappingBackendAddress;

    final ObjectMapper objectMapper;

    private Response buildResponseForObject(Object object) {
        try {
            var responseBody = objectMapper.writeValueAsString(object);
            return Response.ok(responseBody).type(MediaType.APPLICATION_JSON_TYPE).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public RestController() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public Response createBookingEntry(BookingEntry updatedEntry)
    {
        return generateNewEntry(updatedEntry);
    }

//    public Response createBookingEntryDuration(String externalId, BookingEntry updatedEntry) {
//        updatedEntry.setStopDatetime(LocalDateTime.now());
//        updatedEntry.setStartDatetime(LocalDateTime.now().minus(updatedEntry.getDuration()));
//        return createBookingEntry(externalId, updatedEntry);
//    }
//
//    public Response createBookingEntryStart(String externalId, BookingEntry updatedEntry) {
//        updatedEntry.setStartDatetime(LocalDateTime.now());
//        return createBookingEntry(externalId, updatedEntry);
//    }
//
//    public Response createBookingEntryStop(String externalId, BookingEntry updatedEntry) {
//        updatedEntry.setStartDatetime(LocalDateTime.now());
//        return createBookingEntry(externalId, updatedEntry);
//    }

    private Response generateNewEntry(BookingEntry entry) {
        User user = persistService.findUserByPersonalNumber(entry.getUser().getPersonalNumber());
        if(user != null)
        {
            BookingEntry newEntry  = new BookingEntry();
            newEntry.setUser(user);
            newEntry.setExternalId(entry.getExternalId());
            newEntry.setAmount(entry.getAmount());

            if(entry.getDuration() != null && entry.getDuration() != Duration.ZERO)
            {
                //Use duration instead of start/stop

                if(entry.getBreakDuration() != null && entry.getBreakDuration() != Duration.ZERO)
                {
                    newEntry.setBreakDuration(entry.getBreakDuration());
                }
                newEntry.setDuration(entry.getDuration());
                newEntry.setStartDateTime(null);
                newEntry.setEndDateTime(LocalDateTime.now());
                newEntry.setMessage(sendToSchemaMapper(newEntry));
                newEntry.setStatus(BookingEntry.Status.FINISHED);
                System.out.println(newEntry.getMessage());
            }
            else
            {
                //Create a new booking without an endDateTime
                newEntry.setStartDateTime(LocalDateTime.now());
                newEntry.setStatus(BookingEntry.Status.CREATED);
            }
            newEntry.setNote(entry.getNote());

            persistService.save(newEntry);
            return Response.status(Response.Status.CREATED).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response getBookingEntriesByExternalId(String externalId)
    {
        List<BookingEntry> bookingEntry = persistService.getAllBookingEntriesByExternalId(externalId);
        if (bookingEntry == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else
        {
            return buildResponseForObject(bookingEntry);
        }
    }

    public Response updateBookingEntry(BookingEntry updatedEntry)
    {
        BookingEntry existingEntry = null;
        if(updatedEntry.getId() != null)
        {
            existingEntry = persistService.getSingleBookingEntryById(updatedEntry.getId());
        }
        if (existingEntry == null) {
            return generateNewEntry(updatedEntry);
        }

        // Update fields
        existingEntry.setAmount(updatedEntry.getAmount());
        existingEntry.setNote(updatedEntry.getNote());

        if(updatedEntry.getBreakDuration() != null && updatedEntry.getBreakDuration() != Duration.ZERO)
        {
            existingEntry.setBreakDuration(updatedEntry.getBreakDuration());
        }

        if(updatedEntry.getDuration() != null && updatedEntry.getDuration() != Duration.ZERO)
        {
            //Use duration instead of start/stop
            existingEntry.setDuration(updatedEntry.getDuration());
            existingEntry.setStartDateTime(null);
            existingEntry.setEndDateTime(LocalDateTime.now());
            existingEntry.setMessage(sendToSchemaMapper(existingEntry));
            existingEntry.setStatus(BookingEntry.Status.FINISHED);
            System.out.println(existingEntry.getMessage());
        }
        else
        {
            //Stop the existing booking and set endDateTime
            existingEntry.setEndDateTime(LocalDateTime.now());
            existingEntry.setDuration(Duration.between(existingEntry.getStartDateTime(), existingEntry.getEndDateTime()));
            existingEntry.setMessage(sendToSchemaMapper(existingEntry));
            existingEntry.setStatus(BookingEntry.Status.FINISHED);
            System.out.println(existingEntry.getMessage());
        }

        persistService.save(existingEntry);
        return buildResponseForObject(existingEntry);
    }

    public Response getBookingEntriesByDateRange(String start, String end)
    {
        LocalDateTime startDatetime = LocalDateTime.parse(start);
        LocalDateTime endDatetime = LocalDateTime.parse(end);

        List<BookingEntry> entries = persistService.findAllByDateRange(startDatetime, endDatetime);
        return buildResponseForObject(entries);
    }


    public Response getSingleBookingEntryByExternalId(Long id) {
        BookingEntry bookingEntry = persistService.getSingleBookingEntryById(id);
        if (bookingEntry == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else
        {
            return buildResponseForObject(bookingEntry);
        }
    }

    private String sendToSchemaMapper(BookingEntry bookingEntry) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.TEXT_PLAIN_TYPE;

        RequestBody body = null;
        try {

            String bodyContent = createBody(bookingEntry);
            body = RequestBody.create(bodyContent, okhttp3.MediaType.parse(MediaType.APPLICATION_JSON));

            var urlRunMapping = mappingBackendAddress.orElse("http://localhost:8080/mappingBackend/data/mappingEndpoint/runMapping");
            System.out.println(urlRunMapping);
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(urlRunMapping)).newBuilder();

            String url = urlBuilder.build().toString();

            var request = new Request.Builder().url(url);
            request.method("POST", body);
            okhttp3.Response response = client.newCall(request.build()).execute();
            if (response.body() != null) {
                JsonNode node = objectMapper.readTree(response.body().string());
                String exportJson = node.findValue("EXPORT_JSON").textValue();
                JsonNode exportNode = objectMapper.readTree(exportJson).findValue("EXPORT_JSON");
                return exportNode.toPrettyString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private String createBody(BookingEntry bookingEntry) throws JsonProcessingException {
        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Create root object
        ObjectNode root = mapper.createObjectNode();

        // Create "mappingAndDataSource" object
        ObjectNode mappingAndDataSource = root.putObject("mappingAndDataSource");
        mappingAndDataSource.put("name", "GenerateMessage");
        mappingAndDataSource.putObject("dataReader");
        mappingAndDataSource.putObject("dataWriter");

        // Create "readerResult" object inside "mappingAndDataSource"
        ObjectNode readerResult = mappingAndDataSource.putObject("readerResult");
        readerResult.put("result", objectMapper.writeValueAsString(bookingEntry)); // JSON string inside a field
        readerResult.put("additionalData", "");

        // Add other root-level properties
        root.put("simulate", true);
        root.put("useSavedData", true);

        // Convert to JSON string
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
    }

    public Response getUserByPersonalNumber(String personalNumber) {
        User user = persistService.findUserByPersonalNumber(personalNumber);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else
        {
            return buildResponseForObject(user);
        }
    }

    public Response getBookingEntriesOpenByUserPersonalNumber(String personalNumber) {
        List<BookingEntry> bookingEntry = persistService.getAllOpenBookingEntriesByUser(personalNumber);
        if (bookingEntry == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else
        {
            return buildResponseForObject(bookingEntry);
        }
    }

    public Response sendFinished(RestRequestConfig requestConfig) {

        List<BookingEntry> bookingEntryList = persistService.getAllNewAndResendBookingEntries();


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        for( BookingEntry bookingEntry : bookingEntryList) {

            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");

            RequestBody body = null;

            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestConfig.getUrl())).newBuilder();

            if (requestConfig.getQueryParameters() != null && !requestConfig.getQueryParameters().isEmpty()) {
                for (var parameter : requestConfig.getQueryParameters().entrySet()) {
                    urlBuilder.addQueryParameter(parameter.getKey(), parameter.getValue());
                }
            }
            String url = urlBuilder.build().toString();

            var request = new Request.Builder().url(url);

            switch (requestConfig.getRequestType()) {
                case "GET": {
                    request.method("GET", null);
                    break;
                }
                case "POST": {
                    if(bookingEntry.getMessage() == null)
                    {
                        continue;
                    }
                    body = RequestBody.create(bookingEntry.getMessage(), okhttp3.MediaType.parse("application/json"));
                    request.method("POST", body);
                    break;
                }
                case "PUT": {
                    body = RequestBody.create(bookingEntry.getMessage(), okhttp3.MediaType.parse("application/json"));
                    request.method("PUT", body);
                    break;
                }
                case "DELETE": {
                    body = RequestBody.create(bookingEntry.getMessage(), okhttp3.MediaType.parse("application/json"));
                    request.method("DELETE", body);
                    break;
                }
                default: {
                    break;
                }
            }

            if (requestConfig.getHeaderParameters() != null && !requestConfig.getHeaderParameters().isEmpty()) {
                for (var header : requestConfig.getHeaderParameters().entrySet()) {
                    request.addHeader(header.getKey(), header.getValue());
                }
            }


            try {
                okhttp3.Response response = client.newCall(request.build()).execute();
                int responseStatusCode = response.code();

                if(responseStatusCode >= 200 && responseStatusCode < 300)
                {
                    bookingEntry.setStatus(BookingEntry.Status.CONFIRMED);
                }
                else if (responseStatusCode >= 400 && responseStatusCode < 600)
                {
                    bookingEntry.setStatus(BookingEntry.Status.ERROR);
                }
                else
                {
                    bookingEntry.setStatus(BookingEntry.Status.OTHER);
                }

                bookingEntry.setResponse(response.body().string());

                response.close();
                //System.out.println(response.body().string());
            } catch (IOException exception) {

                bookingEntry.setResponse(exception.getMessage());

                if(bookingEntry.getStatus() == BookingEntry.Status.TIMEOUT)
                {
                    bookingEntry.setStatus(BookingEntry.Status.ERROR);
                }
                else {
                    bookingEntry.setStatus(BookingEntry.Status.TIMEOUT);
                }
                //throw new MappingException("Failed to send http request: " + request, exception);
            }

            persistService.save(bookingEntry);
        }

        return Response.status(Response.Status.OK).build();

    }


}
