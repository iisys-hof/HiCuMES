package de.iisys.sysint.hicumes;

import de.iisys.sysint.hicumes.models.HiCuMESDoc;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;

public class ImportHiCuMESDocumentDelegate extends DelegateSuperClass implements JavaDelegate {

    // const ()
    //---------------------------
    /*

    private final String VAR_MSG = "IMPORT_DOCUMENT_CONTENT";               // will contain the path of the copied document or a message in case if an error

    private final String VAR_DOC_IDENTIFIER = "varDocIdentifier";           // doc identifier as needed by the Document Sync API
    private final String VAR_USAGE_IDENTIFIER = "varDocUsageIdentifier";    // doc usage identifier as needed by the Document Sync API
    private final String VAR_DOC_PATH = "varDocPath";                       // the path of the document to copy to hicumes
    */







    private final String VAR_CERT_CHECK_DISABLED = "varCertCheckDisabled";  // true, to allow non-ssl and to ignore selfsigned or missing certs
    private final String VAR_UNLOAD = "varUnloadDocument";                  //  parameter to indicate if a document should be loaded or unloaded for HiCuMES

    private final String VAR_EXPIRATION_DISABLED = "varExpirationDisabled"; // parameter to indicate if the document expires and gets cleaned by the Doc Sync Service or if the documents is available until it gets disposed by another request

    private final String VAR_REQUEST_URL = "varRequestURL";                 // the request url WITHOUT parameters (http://<host>:<port>/api/documents/ProviceDocument)
    private final String VAR_RESULT = "IMPORT_DOCUMENT_RESULT";             // will contain the result of the import (true, if success / false if sth went wrong....)

    private final String VAR_HICUMES_DOC_LIST = "varHiCuMESDocList";        // the list of HiCuMESDoc model objects that is input and output of this delegate
    private final String VAR_DOCS_BASE_PATH = "varHiCuMESDocsBasePath";     // the base path of hicumes docs (the path of the hicumes doc folder)


    // fields
    //---------------------------
    private DelegateExecution _execution = null;
    private HttpClient _httpClient = null;
    private Boolean _certCheckDisabled = true;
    private String _requestUrl = "";
    //private String _docIdentifier = "";
    //private String _docPath = "";
    //private String _docUsageIdentifier = "";
    //private String _responseContent = "";

    private boolean _expirationDisabled = false;

    private boolean _unloadDocument = false;

    private ArrayList<Object> _docList = new ArrayList<>();
    private String _hicumesDocsBasePath = "";

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        if(!super.isDisabled) {

            _execution = execution;

            this.parseCamundaFields();

            _httpClient = HttpClient.newHttpClient();

            if (_certCheckDisabled) {
                this.disableEndpointCertCheck();
            }

            boolean requestResult = false;

            if(_unloadDocument){

                requestResult = this.sendUnloadDocumentRequest();
            }
            else {

                requestResult = this.sendLoadDocumentRequest();
            }


            this.reportResult(requestResult);

            execution.setVariable(VAR_HICUMES_DOC_LIST, _docList);
        }
    }


    private void parseCamundaFields(){

        try {
            Map<String, Object> vars = _execution.getVariables();

            _certCheckDisabled = Boolean.parseBoolean(vars.get(VAR_CERT_CHECK_DISABLED).toString());
            _requestUrl = vars.get(VAR_REQUEST_URL).toString();

            _expirationDisabled = vars.containsKey(VAR_EXPIRATION_DISABLED) ? Boolean.parseBoolean(vars.get(VAR_EXPIRATION_DISABLED).toString()) : false;
            _unloadDocument = vars.containsKey(VAR_UNLOAD) ? Boolean.parseBoolean(vars.get(VAR_UNLOAD).toString()) : false;

            _docList = (ArrayList<Object>) vars.get(VAR_HICUMES_DOC_LIST);
            _hicumesDocsBasePath = vars.get(VAR_DOCS_BASE_PATH).toString();
        }
        catch (Exception ex){

            this.reportResult(false);
        }
    }

    private boolean sendLoadDocumentRequest(){

        boolean success = true;

        try {

            // creating a temp uri object to be able to get the individual uri parts which are needed for the other constructor but without needing to define those all as individual camunda process vars
            URI tempUri = new URI(_requestUrl + "ProvideDocument");

            for (Object obj : _docList) {

                HiCuMESDoc doc = (HiCuMESDoc) obj;

                if(!doc.getSourcePath().isBlank() && !doc.getDocIdentifier().isBlank()) {

                    // using the following constructor since it takes care of escaping special characters by itself (important for the doc path as url parameter...)
                    URI requestUri = new URI(tempUri.getScheme(),
                            tempUri.getAuthority(),
                            tempUri.getPath(),
                            "documentIdentifier=" + doc.getDocIdentifier() +
                                    "&documentPath=" + doc.getSourcePath() +
                                    "&documentUsageIdentifier=" + doc.getDocUsageIdentifier() +
                                    "&expirationDisabled=" + _expirationDisabled, null);

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(requestUri)
                            .timeout(Duration.ofSeconds(5))
                            .header("Accept", "*/*")
                            .version(HttpClient.Version.HTTP_1_1)
                            .GET().build();

                    HttpResponse<String> response = _httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    String responseMessage = response.body();
                    boolean requestResult = (response.statusCode() == 200 || response.statusCode() == 204);

                    if(requestResult) {

                        String destFilePath = responseMessage.replace('\\', '/');

                        Path path = Paths.get(destFilePath);
                        String fileName = path.getFileName().toString();

                        doc.setHiCuMESPath(_hicumesDocsBasePath + "/" + fileName);
                    }

                    doc.setRequestInformation(requestResult, responseMessage);

                    success = success && requestResult;
                }
            }
        }
        catch (Exception ex){

            this.reportResult(false);
        }

        return false;
    }


    private boolean sendUnloadDocumentRequest(){

        boolean success = true;

        try {

            // creating a temp uri object to be able to get the individual uri parts which are needed for the other constructor but without needing to define those all as individual camunda process vars
            URI tempUri = new URI(_requestUrl + "UnloadDocument");

            for (Object obj : _docList) {

                HiCuMESDoc doc = (HiCuMESDoc) obj;

                if(!doc.getSourcePath().isBlank() && !doc.getDocIdentifier().isBlank()) {

                    // using the following constructor since it takes care of escaping special characters by itself (important for the doc path as url parameter...)
                    URI requestUri = new URI(tempUri.getScheme(),
                            tempUri.getAuthority(),
                            tempUri.getPath(),
                            "documentIdentifier=" + doc.getDocIdentifier() +
                                    "&documentUsageIdentifier=" + doc.getDocUsageIdentifier(), null);

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(requestUri)
                            .timeout(Duration.ofSeconds(5))
                            .header("Accept", "*/*")
                            .version(HttpClient.Version.HTTP_1_1)
                            .DELETE().build();

                    HttpResponse<String> response = _httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    String responseMessage = response.body();
                    boolean requestResult = (response.statusCode() == 200 || response.statusCode() == 204);

                    if(requestResult) {

                        doc.setHiCuMESPath("");
                    }

                    doc.setRequestInformation(requestResult, responseMessage);

                    success = success && requestResult;
                }
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
