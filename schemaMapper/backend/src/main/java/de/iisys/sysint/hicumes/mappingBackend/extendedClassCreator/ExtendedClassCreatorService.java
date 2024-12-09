package de.iisys.sysint.hicumes.mappingBackend.extendedClassCreator;


import de.iisys.sysint.hicumes.core.entities.adapters.DurationAdapter;
import de.iisys.sysint.hicumes.core.entities.adapters.LocalDateTimeAdapter;
import de.iisys.sysint.hicumes.core.entities.annotations.CustomerField;
import de.iisys.sysint.hicumes.core.entities.annotations.CustomerMethod;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.fieldExtensionExceptions.FieldExtensionException;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.AllClassExtension;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.ClassExtension;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.MemberExtension;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExtendedClassCreatorService {


    public List<DynamicType.Unloaded<?>> generateExtendedClasses(AllClassExtension allClassExtension) throws FieldExtensionException {
        List<DynamicType.Unloaded<?>> result = new ArrayList<>();
        for (var extension: allClassExtension.getClasses()) {
            result.addAll(getExtendedClass(extension));
        }
        return result;
    }

    private List<DynamicType.Unloaded<?>> getExtendedClass(ClassExtension classExtension) throws FieldExtensionException {
        try {
            var createdClasses = new ArrayList<DynamicType.Unloaded<?>>();

            String fullName = classExtension.getId();
            String fullNameOriginalClass = fullName + "OriginalClass";
            Class<?> entityType;

            if (doesClassExist(fullNameOriginalClass)) {
                entityType = Class.forName(fullNameOriginalClass);
            } else {
                entityType = Class.forName(fullName);
                var builderOriginal = new ByteBuddy().redefine(entityType).name(fullNameOriginalClass).make();
                createdClasses.add(builderOriginal);
            }

            var builder = new ByteBuddy().redefine(entityType).name(fullName);
            builder = addAllMembers(classExtension, entityType, builder);

            DynamicType.Unloaded<?>  builded = builder.make();
            createdClasses.add(builded);
            return createdClasses;
        } catch (Exception e) {
            throw new FieldExtensionException("Failed to create extended Class: "  + classExtension.getId(), e);
        }
    }

    private DynamicType.Builder<?> addAllMembers(ClassExtension classExtension, Class<?> entityType, DynamicType.Builder<?> builder) throws FieldExtensionException {
        for (MemberExtension memberExtension : classExtension.getMembers()) {
            var fieldDoesNotExist = !doesObjectContainField(entityType, memberExtension.getName());
            if(fieldDoesNotExist) {
                builder = addMember(builder, memberExtension);
            } else {
                System.out.println("Field does exist and is not created or changed: "  + memberExtension.getName() +" class: " +  classExtension.getId());
            }
        }
        return builder;
    }


    private DynamicType.Builder<?> addMember(DynamicType.Builder<?> builder, MemberExtension memberExtension) throws FieldExtensionException {
        try {
            Class<?> memberType = Class.forName(memberExtension.getType());
                var field = builder.defineField(memberExtension.getName(), memberType, Visibility.PRIVATE);
                field = (DynamicType.Builder.FieldDefinition.Optional.Valuable<?>) field.annotateField(AnnotationDescription.Builder.ofType(CustomerField.class).build());
                if(memberType == LocalDateTime.class)
                {
                    field = (DynamicType.Builder.FieldDefinition.Optional.Valuable<?>) field.annotateField(AnnotationDescription.Builder.ofType(XmlJavaTypeAdapter.class).define("value", LocalDateTimeAdapter.class).build());
                }
                else if(memberType == Duration.class)
                {
                    field = (DynamicType.Builder.FieldDefinition.Optional.Valuable<?>) field.annotateField(AnnotationDescription.Builder.ofType(XmlJavaTypeAdapter.class).define("value", DurationAdapter.class).build());
                }

                var columnAnnotation = generateColumnAnnotation(memberExtension, field);
                if(columnAnnotation != null)
                {
                    field = (DynamicType.Builder.FieldDefinition.Optional.Valuable<?>) field.annotateField(columnAnnotation);
                }

                builder = field;
                builder = builder
                        .defineMethod("get" + StringUtils.capitalize(memberExtension.getName()), memberType, Visibility.PUBLIC)
                        .intercept(FieldAccessor.ofBeanProperty())
                        .annotateMethod(AnnotationDescription.Builder.ofType(CustomerMethod.class).build());
                builder = builder
                        .defineMethod("set" + StringUtils.capitalize(memberExtension.getName()), void.class, Visibility.PUBLIC)
                        .withParameter(memberType).intercept(FieldAccessor.ofBeanProperty())
                        .annotateMethod(AnnotationDescription.Builder.ofType(CustomerMethod.class).build());

        } catch (Exception e) {
            throw new FieldExtensionException("Failed to create field : "  + memberExtension.getName() + " type: " + memberExtension.getType(), e);
        }
        return builder;
    }

    private AnnotationDescription generateColumnAnnotation(MemberExtension memberExtension, DynamicType.Builder.FieldDefinition.Optional.Valuable<?> field) {
        if( memberExtension.getUnique() != null ||
            memberExtension.getNullable() != null ||
            memberExtension.getInsertable() != null ||
            memberExtension.getUpdatable() != null ||
            memberExtension.getLength() != null ||
            memberExtension.getPrecision() != null ||
            memberExtension.getScale() != null)
        {
            var columnAnnotationBuilder = AnnotationDescription.Builder.ofType(Column.class);
            if(memberExtension.getUnique() != null)
            {
                columnAnnotationBuilder = columnAnnotationBuilder.define("unique", memberExtension.getUnique());
            }
            if(memberExtension.getNullable() != null)
            {
                columnAnnotationBuilder = columnAnnotationBuilder.define("nullable", memberExtension.getNullable());
            }
            if(memberExtension.getInsertable() != null)
            {
                columnAnnotationBuilder = columnAnnotationBuilder.define("insertable", memberExtension.getInsertable());
            }
            if(memberExtension.getUpdatable() != null)
            {
                columnAnnotationBuilder = columnAnnotationBuilder.define("updatable", memberExtension.getUpdatable());
            }
            if(memberExtension.getLength() != null)
            {
                columnAnnotationBuilder = columnAnnotationBuilder.define("length", memberExtension.getLength());
            }
            if(memberExtension.getPrecision() != null)
            {
                columnAnnotationBuilder = columnAnnotationBuilder.define("precision", memberExtension.getPrecision());
            }
            if(memberExtension.getScale() != null)
            {
                columnAnnotationBuilder = columnAnnotationBuilder.define("scale", memberExtension.getScale());
            }

            return columnAnnotationBuilder.build();
        }
        return null;
    }

    public boolean doesObjectContainField(Class<?> object, String fieldName) {
        var fields = object.getDeclaredFields();
        var fieldsSuperclass = object.getSuperclass().getDeclaredFields();
        for (Field f : ArrayUtils.addAll(fieldsSuperclass, fields )) {
            if(!f.isAnnotationPresent(CustomerField.class)){
                if (f.getName().equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean doesClassExist(String className) {
        try {
            Class<?> entityType = Class.forName(className);
            return true;

        }catch (ClassNotFoundException e) {
            return false;
        }
    }
}
