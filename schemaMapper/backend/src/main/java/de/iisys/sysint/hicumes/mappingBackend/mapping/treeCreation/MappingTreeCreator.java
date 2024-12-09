package de.iisys.sysint.hicumes.mappingBackend.mapping.treeCreation;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.mappingExceptions.ParsingMappingException;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping.FrontendTreeNode;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping.FrontendTreeNodeIcons;
import de.iisys.sysint.hicumes.mappingEngine.MappingWorkflow;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTree;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTreeElement;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import org.apache.commons.collections4.IteratorUtils;

import javax.ejb.Singleton;
import java.util.ArrayList;

@Singleton
public class MappingTreeCreator {

    public FrontendTreeNode createInputTree(MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource) throws ParsingMappingException {
        if(mappingAndDataSource.getDataReader() != null) {
            switch (mappingAndDataSource.getDataReader().getReaderID()) {
                default:
                {
                    return createFromJSON(mappingWorkflowResult.getMappingInput(), "input", "InputData");
                }
            }
        }
        return null;
    }


    public FrontendTreeNode createOutputTree(MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource)
    {
        if(mappingAndDataSource.getDataWriter() != null)
        {
            switch (mappingAndDataSource.getDataWriter().getWriterID())
            {
                case "outputPlugin-DatabaseWriter":
                {
                    return createFromDatabase(mappingWorkflowResult.getMappingDependencyTree(), "output", "InternalSchema");
                }
            }
        }
        return null;
    }

    private FrontendTreeNode createFromDatabase(MappingDependencyTree mappingDependencyTree, String selector, String identifier) {
        if (mappingDependencyTree== null) {
            return null;
        }

        var innerTreeNode = new ArrayList<FrontendTreeNode>();
        for (var treeElementName: mappingDependencyTree.getTree().keySet()){
            var innerId = selector + '.' + treeElementName;
            var treeElement = mappingDependencyTree.getTree().get(treeElementName);

            var innerNode = handleOneTreeNode(innerId, treeElement);

            innerTreeNode.add(innerNode);
        }

        var frontendTreeNode = new FrontendTreeNode(selector,identifier, FrontendTreeNodeIcons.OBJECT, innerTreeNode );

        return frontendTreeNode;
    }

    private FrontendTreeNode handleOneTreeNode(String outerId, MappingDependencyTreeElement treeElement) {
        var treeElementId = treeElement.getId().split("\\.");
        var treeElementName = treeElementId[treeElementId.length - 1];
        var innerTreeNode = new ArrayList<FrontendTreeNode>();

        for (var name: treeElement.getArrays().keySet()){
            var innerId = outerId + '.' + name;
            var innerNode = handleOneTreeNode("output", treeElement.getArrays().get(name));
            innerNode.setIcon(FrontendTreeNodeIcons.LOOP);
            innerNode.setSelector(innerId);
            innerNode.setText("---->Loop: " + innerNode.getText());
            innerTreeNode.add(innerNode);
        }

        for (var name: treeElement.getObjects().keySet()){
            var innerId = outerId + '.' + name;
            var innerNode = handleOneTreeNode(innerId, treeElement.getObjects().get(name));
            innerTreeNode.add(innerNode);
        }

        for (var name: treeElement.getFields().keySet()){
            var innerId = outerId + '.' + name;
            var type = FrontendTreeNodeIcons.fromJavaClassString(treeElement.getFields().get(name));
            var innerNode = new FrontendTreeNode(innerId, name + " | " + type,type, new ArrayList<>() );
            innerTreeNode.add(innerNode);
        }

        var node = new FrontendTreeNode(outerId, treeElementName,FrontendTreeNodeIcons.OBJECT, innerTreeNode  );
        return node;
    }

    private FrontendTreeNode createFromJSON(MappingInput inputData, String selector, String identifier) throws ParsingMappingException {
        if (inputData == null) {
            return null;
        }
        var outerId = "input";
        var outerName = "InputData";

        return getJsonSchema(selector, identifier, inputData.getJsonNode());
    }


    private FrontendTreeNode getJsonSchema(String outerId, String outerName , JsonNode jsonWithData) throws ParsingMappingException {
        var innerTreeNode = new ArrayList<FrontendTreeNode>();

        if (jsonWithData.isObject()) {
            for (String name : IteratorUtils.toList(jsonWithData.fieldNames())) {
                JsonNode value = jsonWithData.get(name);
                var innerId = outerId + '.' + name;
                if (value.isObject() || value.isArray()) {
                    var innerNode = getJsonSchema(innerId,name,value);
                    innerTreeNode.add(innerNode);
                }
                else {
                    var type = FrontendTreeNodeIcons.fromJsonValue(value);
                    var innerNode = new FrontendTreeNode(innerId, name + " | " + type,type, new ArrayList<>() );
                    innerNode.setExampleData(value.asText());
                    innerTreeNode.add(innerNode);
                }
            }
            var frontendTreeNode = new FrontendTreeNode(outerId,outerName, FrontendTreeNodeIcons.OBJECT, innerTreeNode );
            return frontendTreeNode;
        }
        else if(jsonWithData.isArray()) {
            var schema = getJsonSchema("input", "---->Loop: " + outerName  ,jsonWithData.get(0));
            schema.setSelector(outerId);
            schema.setIcon(FrontendTreeNodeIcons.LOOP);
            return schema;
        } else {
            throw new ParsingMappingException("ERPMapCreator has an invalid state: " + jsonWithData, null);
        }
    }
}
