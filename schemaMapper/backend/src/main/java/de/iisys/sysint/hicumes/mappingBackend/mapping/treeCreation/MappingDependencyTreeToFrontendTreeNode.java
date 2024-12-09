package de.iisys.sysint.hicumes.mappingBackend.mapping.treeCreation;

import de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping.FrontendTreeNode;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping.FrontendTreeNodeIcons;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTree;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTreeElement;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.Arrays;

@Singleton
public class MappingDependencyTreeToFrontendTreeNode {

    public FrontendTreeNode transform(MappingDependencyTree mappingDependencyTree) {
        if (mappingDependencyTree== null) {
            return null;
        }
        var outerId = "output";
        var outerName = "InternalSchema";

        var innerTreeNode = new ArrayList<FrontendTreeNode>();
        for (var treeElementName: mappingDependencyTree.getTree().keySet()){
            var innerId = outerId + '.' + treeElementName;
            var treeElement = mappingDependencyTree.getTree().get(treeElementName);

            var innerNode = handleOneTreeNode(innerId, treeElement);

            innerTreeNode.add(innerNode);
        }

        var frontendTreeNode = new FrontendTreeNode(outerId,outerName, FrontendTreeNodeIcons.OBJECT, innerTreeNode );

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
            innerNode.setText("--> Loop: " + innerNode.getText());
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

        for (var name: treeElement.getObjectsReference().keySet()){
            /*String[] referenceNames = {"productionOrder", "tool", "machineType", "product", "toolType", "customerOrder", "machineStatus", "suspensionType", "timeRecordType", "unit"};
            if(Arrays.asList(referenceNames).contains(name)) {*/
                var innerId = outerId + '.' + name;
                var innerNode = handleOneTreeNode(innerId, treeElement.getObjectsReference().get(name));
                innerNode.setText("--> Reference: " + innerNode.getText());
                innerTreeNode.add(innerNode);
            //}
        }

        var node = new FrontendTreeNode(outerId, treeElementName,FrontendTreeNodeIcons.OBJECT, innerTreeNode  );
        return node;
    }
}
