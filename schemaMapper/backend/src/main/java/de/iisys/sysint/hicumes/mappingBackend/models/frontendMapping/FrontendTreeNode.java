package de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class FrontendTreeNode implements  IJson {
    private String selector;
    private String text;
    private FrontendTreeNodeIcons icon;
    private FrontendTreeNodeIcons type;
    private List<FrontendTreeNode> children;
    private String exampleData;

    public FrontendTreeNode(String selector, String text, FrontendTreeNodeIcons icon, ArrayList<FrontendTreeNode> children) {
    this.selector= selector;
    this.text = text;
    this.children = children;
    this.setIcon(icon);

    }

    public void setIcon(FrontendTreeNodeIcons icon) {
        this.icon = icon;
        this.type = icon;
    }

    public String toJson() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String result = objectMapper.writeValueAsString(this);
        return result;
    }
}
