package de.iisys.sysint.hicumes.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class HiCuMESDoc implements Serializable {

    private String SourcePath;
    private String DocIdentifier;
    private String DocUsageIdentifier;
    private boolean RequestResult;
    private String RequestMessage;
    private String HiCuMESPath;


    @JsonProperty("SourcePath")
    public String getSourcePath() {
        return SourcePath;
    }

    @JsonProperty("SourcePath")
    public void setSourcePath(String sourcePath) {
        SourcePath = sourcePath;
    }

    @JsonProperty("DocIdentifier")
    public String getDocIdentifier() {
        return DocIdentifier;
    }

    @JsonProperty("DocIdentifier")
    public void setDocIdentifier(String docIdentifier) {
        DocIdentifier = docIdentifier;
    }

    @JsonProperty("DocUsageIdentifier")
    public String getDocUsageIdentifier() {
        return DocUsageIdentifier;
    }

    @JsonProperty("DocUsageIdentifier")
    public void setDocUsageIdentifier(String docUsageIdentifier) {
        DocUsageIdentifier = docUsageIdentifier;
    }

    @JsonProperty("RequestResult")
    public boolean getRequestResult() {
        return RequestResult;
    }

    @JsonProperty("RequestResult")
    public void setRequestResult(boolean requestResult) {
        RequestResult = requestResult;
    }

    @JsonProperty("RequestMessage")
    public String getRequestMessage() {
        return RequestMessage;
    }

    @JsonProperty("RequestMessage")
    public void setRequestMessage(String requestMessage) {
        RequestMessage = requestMessage;
    }

    @JsonProperty("HiCuMESPath")
    public String getHiCuMESPath() {
        return HiCuMESPath;
    }

    @JsonProperty("HiCuMESPath")
    public void setHiCuMESPath(String hiCuMESPath) {
        HiCuMESPath = hiCuMESPath;
    }

    public HiCuMESDoc() {

    }

    public HiCuMESDoc(String sourcePath, String docIdentifier, String docUsageIdentifier) {

        this.SourcePath = sourcePath;
        this.DocIdentifier = docIdentifier;
        this.DocUsageIdentifier = docUsageIdentifier;
    }


    public void setRequestInformation(boolean requestResult, String requestMessage){

        this.RequestMessage = requestMessage;
        this.RequestResult = requestResult;
    }
}
