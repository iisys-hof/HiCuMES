package de.iisys.sysint.hicumes;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

public class ImportDocumentDelegate extends DelegateSuperClass implements JavaDelegate {

    // const ()
    //---------------------------
    private final String VAR_RESULT = "IMPORT_DOCUMENT_RESULT";             // will contain the result of the import (true, if success / false if sth went wrong....)
    private final String VAR_CERT_CHECK_DISABLED = "varCertCheckDisabled";  // true, to allow non-ssl and to ignore selfsigned or missing certs
    private final String VAR_DOC_IDENTIFIER = "varDocIdentifier";           // doc identifier as needed by the Document Sync API
    private final String VAR_USAGE_IDENTIFIER = "varDocUsageIdentifier";    // doc usage identifier as needed by the Document Sync API
    private final String VAR_DOC_PATH = "varDocPath";                       // the path of the document to copy to hicumes
    private final String VAR_REQUEST_URL = "varRequestURL";                 // the request url WITHOUT parameters (http://<host>:<port>/api/documents/ProviceDocument)


    // fields
    //---------------------------
    private DelegateExecution _execution = null;
    private HttpClient _httpClient = null;
    private Boolean _certCheckDisabled = true;
    private String _requestUrl = "";
    private String _docIdentifier = "";
    private String _docPath = "";
    private String _docUsageIdentifier = "";




    @Override
    public void execute(DelegateExecution execution) throws Exception {

        if(!super.isDisabled) {

            _execution = execution;

            this.parseCamundaFields();

            _httpClient = HttpClient.newHttpClient();

            if (_certCheckDisabled) {
                this.disableEndpointCertCheck();
            }

            boolean requestResult = this.sendDocumentRequest();

            this.reportResult(requestResult);
        }
    }


    private void parseCamundaFields(){

        try {
            Map<String, Object> vars = _execution.getVariables();

            _certCheckDisabled = Boolean.parseBoolean(vars.get(VAR_CERT_CHECK_DISABLED).toString());
            _requestUrl = vars.get(VAR_REQUEST_URL).toString();
            _docIdentifier = vars.get(VAR_DOC_IDENTIFIER).toString();
            _docUsageIdentifier = vars.get(VAR_USAGE_IDENTIFIER).toString();
            _docPath = vars.get(VAR_DOC_PATH).toString();
        }
        catch (Exception ex){

            this.reportResult(false);
        }
    }


    private boolean sendDocumentRequest(){

        try {

            // creating a temp uri object to be able to get the individual uri parts which are needed for the other constructor but without needing to define those all as individual camunda process vars
            URI tempUri = new URI(_requestUrl);

            // using the following constructor since it takes care of escaping special characters by itself (important for the doc path as url parameter...)
            URI requestUri = new URI(tempUri.getScheme(),
                    tempUri.getAuthority(),
                    tempUri.getPath(),
                    "documentIdentifier=" + _docIdentifier + "&documentPath=" + _docPath + "&documentUsageIdentifier=" + _docUsageIdentifier, null);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(requestUri)
                    .header("Accept", "*/*")
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET().build();

            HttpResponse<String> response = _httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200 || response.statusCode() == 204) {

                return true;
            }
        }
        catch (Exception ex){

            this.reportResult(false);
        }

        return false;
    }


    private void reportResult(boolean success){

        _execution.setVariable(VAR_RESULT, success);
    }


    private void disableEndpointCertCheck()  throws NoSuchAlgorithmException, KeyManagementException {

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

        _httpClient = HttpClient.newBuilder().sslContext(sslContext).build();
    }






    private static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };
}
