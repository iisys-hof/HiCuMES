package de.iisys.sysint.hicumes.hicumeslite.entities;

import lombok.Data;

import java.util.Map;

@Data
public class RestRequestConfig {
    String url;
    String requestType;
    Map<String, String> queryParameters;
    Map<String, String> headerParameters;
}
