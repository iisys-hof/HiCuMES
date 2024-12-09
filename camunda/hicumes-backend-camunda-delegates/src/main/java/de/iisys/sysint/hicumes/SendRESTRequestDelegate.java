package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SendRESTRequestDelegate extends DelegateSuperClass implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception
    {
        super.init(execution);

        if(!super.isDisabled)
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++++++ SendRESTRequestDelegate ++++++++++++++");
            System.out.println("--------------------------------------------------");
            sendRequest(execution);

        }
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++++++ SendRESTRequestDelegate ++++++++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }

    private void sendRequest(DelegateExecution execution) {
        String requestAddress = null;
        if(execution.hasVariable("requestAddress"))
        {
            requestAddress  = (String) execution.getVariable("requestAddress");
        }
        else
        {
            System.out.println("No requestAddress provided");
            return;
        }
        String requestType = null;
        if(execution.hasVariable("requestType"))
        {
            requestType= (String) execution.getVariable("requestType");
        }
        Map<String, String> headerParameters = null;
        if(execution.hasVariable("headerParameters"))
        {
            String headerParametersString = (String) execution.getVariable("headerParameters");
            headerParameters = getParameterList(headerParametersString);
        }
        Map<String, String> queryParameters = null;
        if(execution.hasVariable("queryParameters"))
        {
            String queryParametersString = (String) execution.getVariable("queryParameters");
            queryParameters = getParameterList(queryParametersString);
        }



        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

        RequestBody body = null;
        if(execution.hasVariable("body")) {
            body = RequestBody.create((String) execution.getVariable("body"), MediaType.parse("application/json"));
        }

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestAddress)).newBuilder();

        if(queryParameters != null && !queryParameters.isEmpty()) {
            for (var param : queryParameters.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        String url = urlBuilder.build().toString();

        var request = new Request.Builder().url(url);

        switch (requestType) {
            case "GET": {
                request.method("GET", null);
                break;
            }
            case "POST": {
                request.method("POST", body);
                break;
            }
            case "PUT": {
                request.method("PUT", body);
                break;
            }
            case "DELETE": {
                request.method("DELETE", body);
                break;
            }
            default: {
                return;
            }
        }

        if(headerParameters != null && !headerParameters.isEmpty()) {
            for (var param : headerParameters.entrySet()) {
                request.addHeader(param.getKey(), param.getValue());
            }
        }

        try {
            Response response = client.newCall(request.build()).execute();
            System.out.println(response.code());
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    private Map<String, String> getParameterList(String params) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> paramsMap = new HashMap<>();
        try {
            paramsMap = mapper.readValue(params,  Map.class);
            return paramsMap;
        }
        catch (JsonProcessingException exception)
        {
            return null;
        }
    }
}
