package de.iisys.sysint.hicumes.manufaturingTerminal.testData.extendedClass.entities;

import lombok.Data;

import java.util.List;

@Data
public class ClassExtension {

    private String className;
    private List<MemberExtension> members;
}
