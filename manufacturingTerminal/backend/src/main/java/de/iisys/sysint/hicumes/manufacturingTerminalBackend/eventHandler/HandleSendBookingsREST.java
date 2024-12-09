package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.BookingEntry;
import de.iisys.sysint.hicumes.core.entities.enums.EBookingState;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventSendBookingsREST;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HandleSendBookingsREST implements ISubscribeEvent {

    @Subscribe
    public void handle(EventSendBookingsREST eventSendBookingsREST) throws UtilsBaseException, BusinessException {

        List<BookingEntry> bookingEntryList = persistService.getAllNewAndResendBookingEntries();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        for( BookingEntry bookingEntry : bookingEntryList) {

            MediaType mediaType = MediaType.parse("text/plain");

            RequestBody body = null;

            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(eventSendBookingsREST.getRequestAddress())).newBuilder();

            if (eventSendBookingsREST.getQueryParams() != null && !eventSendBookingsREST.getQueryParams().isEmpty()) {
                for (var parameter : eventSendBookingsREST.getQueryParams().entrySet()) {
                    urlBuilder.addQueryParameter(parameter.getKey(), parameter.getValue());
                }
            }
            String url = urlBuilder.build().toString();

            var request = new Request.Builder().url(url);

            switch (eventSendBookingsREST.getRequestType()) {
                case "GET": {
                    request.method("GET", null);
                    break;
                }
                case "POST": {
                    body = RequestBody.create(bookingEntry.getMessage(), MediaType.parse("application/json"));
                    request.method("POST", body);
                    break;
                }
                case "PUT": {
                    body = RequestBody.create(bookingEntry.getMessage(), MediaType.parse("application/json"));
                    request.method("PUT", body);
                    break;
                }
                case "DELETE": {
                    body = RequestBody.create(bookingEntry.getMessage(), MediaType.parse("application/json"));
                    request.method("DELETE", body);
                    break;
                }
                default: {
                    break;
                }
            }

            if (eventSendBookingsREST.getHeaderParams() != null && !eventSendBookingsREST.getHeaderParams().isEmpty()) {
                for (var header : eventSendBookingsREST.getHeaderParams().entrySet()) {
                    request.addHeader(header.getKey(), header.getValue());
                }
            }


            try {
                Response response = client.newCall(request.build()).execute();
                int responseStatusCode = response.code();

                if(responseStatusCode >= 200 && responseStatusCode < 300)
                {
                    bookingEntry.setBookingState(EBookingState.CONFIRMED);
                }
                else if (responseStatusCode >= 400 && responseStatusCode < 600)
                {
                    bookingEntry.setBookingState(EBookingState.ERROR);
                }
                else
                {
                    bookingEntry.setBookingState(EBookingState.OTHER);
                }

                bookingEntry.setResponse(response.body().string());

                response.close();
                //System.out.println(response.body().string());
            } catch (IOException exception) {

                bookingEntry.setResponse(exception.getMessage());

                if(bookingEntry.getBookingState() == EBookingState.TIMEOUT)
                {
                    bookingEntry.setBookingState(EBookingState.ERROR);
                }
                else {
                    bookingEntry.setBookingState(EBookingState.TIMEOUT);
                }
                //throw new MappingException("Failed to send http request: " + request, exception);
            }

            persistService.getPersistenceManager().merge(bookingEntry);
        }


    }
}
