package de.iisys.sysint.hicumes.mappingBackend.extendedClassCreator;

import de.iisys.sysint.hicumes.mappingBackend.extendedClassCreator.transformer.ExtendedClassToFlatMemberTransformer;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.AllClassExtension;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.FlatMemberExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExtendedClassChangeService {

    public List<FlatMemberExtension> getDeletedMembers(AllClassExtension allClassExtension, AllClassExtension oldClassExtension) {
        var transformer = new ExtendedClassToFlatMemberTransformer();
        var newMembers = transformer.flatten(allClassExtension);
        var oldMembers = transformer.flatten(oldClassExtension);

        var result = oldMembers.stream().filter(flatMemberExtension -> flatMemberExtension.isCustomerField() && !newMembers.contains(flatMemberExtension));
        return new ArrayList(result.collect(Collectors.toList()));
    }
}
