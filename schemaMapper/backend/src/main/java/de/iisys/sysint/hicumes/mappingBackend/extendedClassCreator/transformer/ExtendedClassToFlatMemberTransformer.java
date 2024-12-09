package de.iisys.sysint.hicumes.mappingBackend.extendedClassCreator.transformer;

import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.AllClassExtension;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.FlatMemberExtension;

import java.util.ArrayList;
import java.util.List;

public class ExtendedClassToFlatMemberTransformer {

    public List<FlatMemberExtension> flatten(AllClassExtension allClassExtension) {
        var list = new ArrayList<FlatMemberExtension>();
        for (var currentClass: allClassExtension.getClasses()) {
            for (var member : currentClass.getMembers()) {
                list.add(new FlatMemberExtension(currentClass.getName(),currentClass.getId(),member.getName(), member.getType(), member.isCustomerField()));
            }
        }
        return list;
    }
}
