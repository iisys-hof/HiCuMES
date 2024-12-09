package de.iisys.sysint.hicumes.manufaturingTerminal.testData.extendedClass.generators;

import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.manufaturingTerminal.testData.extendedClass.entities.ClassExtension;
import de.iisys.sysint.hicumes.manufaturingTerminal.testData.extendedClass.entities.MemberExtension;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

public class ExtendedClassesGenerator {

    public Stream<DynamicType.Unloaded<?>> generateExtendedClasses(Stream<ClassExtension> classExtensions) {
        Stream<DynamicType.Unloaded<?>> generatedExtendedClasses = classExtensions.map(this::getExtendedClass);
        return generatedExtendedClasses;
    }

    private DynamicType.Unloaded<?> getExtendedClass(ClassExtension classExtension) {
        DynamicType.Unloaded<?> builded = null;
        try {

            String fullName = EntitySuperClass.class.getPackageName() + "." + classExtension.getClassName();
            Class<?> entityType = Class.forName(fullName);


            DynamicType.Builder<?> builder = new ByteBuddy()
                    .redefine(entityType);

            for (MemberExtension memberExtension : classExtension.getMembers()) {
                builder = addMember(builder, memberExtension);
            }

            builded = builder.make();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builded;


    }

    private DynamicType.Builder<?> addMember(DynamicType.Builder<?> builder, MemberExtension memberExtension) throws ClassNotFoundException {
        Class<?> memberType = Class.forName(memberExtension.getType());
        builder = builder.defineField(memberExtension.getName(), memberType, Visibility.PRIVATE);
        builder = builder
                .defineMethod("get" + StringUtils.capitalize(memberExtension.getName()), memberType, Visibility.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty());
        builder = builder
                .defineMethod("set" + StringUtils.capitalize(memberExtension.getName()), void.class, Visibility.PUBLIC)
                .withParameter(memberType).intercept(FieldAccessor.ofBeanProperty());
        return builder;
    }
}
