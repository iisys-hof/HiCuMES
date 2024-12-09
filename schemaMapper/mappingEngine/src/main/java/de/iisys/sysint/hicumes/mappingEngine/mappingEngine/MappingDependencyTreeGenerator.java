package de.iisys.sysint.hicumes.mappingEngine.mappingEngine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.iisys.sysint.hicumes.core.entities.annotations.CustomerField;
import de.iisys.sysint.hicumes.core.entities.annotations.MapperIgnore;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTree;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTreeElement;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.WordUtils;

import javax.persistence.Embedded;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;

public class MappingDependencyTreeGenerator {

    public MappingDependencyTree generate(Metamodel metamodel, String prefix) {
        var types = metamodel.getManagedTypes();
        var innerElements = new HashMap<String, MappingDependencyTreeElement>();
        for (ManagedType<?> managedTyp : types) {
            Class<?> javaType = managedTyp.getJavaType();
            if (!isMappingClass(prefix, javaType.getName())) {
                continue;
            }

            var classObject = handleOneClass(javaType, prefix);
            var className = WordUtils.uncapitalize(javaType.getSimpleName());
            innerElements.put(className,classObject );
        }
        return new MappingDependencyTree(innerElements) ;

    }

    private boolean isMappingClass(String prefix, String className) {
        return className.startsWith(prefix);
    }

    private MappingDependencyTreeElement handleOneClass(Class<?> currentClass, String prefix) {
        String className = currentClass.getName();
        var innerArrays = new HashMap<String, MappingDependencyTreeElement>();
        var innerFields = new HashMap<String, String>();
        var isInnerFieldCustomerField = new HashMap<String, Boolean>();
        var objectsReference = new HashMap<String, MappingDependencyTreeElement>();
        var embeddedObjects = new HashMap<String, MappingDependencyTreeElement>();
        var fields = currentClass.getDeclaredFields();
        var fieldsSuperclass = currentClass.getSuperclass().getDeclaredFields();
        for (var field : ArrayUtils.addAll(fieldsSuperclass, fields )) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            var fieldName = field.getName();
            var fieldTypeName = field.getGenericType().getTypeName();

            if (field.getGenericType() instanceof ParameterizedType) {
                var listClass = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                if(field.isAnnotationPresent(MapperIgnore.class))
                {
                    continue;
                }
                var innerTree = handleOneClass((Class<?>) listClass, prefix);
                innerArrays.put(fieldName,innerTree);
            } else if(isMappingClass(prefix, fieldTypeName)) {
                if (field.isAnnotationPresent(Embedded.class)) {
                    var innerTree = handleOneClass((Class<?>) field.getGenericType(), prefix);
                    embeddedObjects.put(fieldName, innerTree);
                } else {
                    var innerTree = handleReferenceClass((Class<?>) field.getGenericType(), prefix);
                    objectsReference.put(fieldName, innerTree);
                }
            } else {
                if (!isFieldFromPersistenceManager(fieldName, fieldTypeName)) {
                    innerFields.put(fieldName, fieldTypeName);
                    var isCustomerField = field.isAnnotationPresent(CustomerField.class);
                    isInnerFieldCustomerField.put(fieldName, isCustomerField);
                }
            }
        }
        return new MappingDependencyTreeElement(className, innerArrays, innerFields,isInnerFieldCustomerField, embeddedObjects, objectsReference);
    }

    private MappingDependencyTreeElement handleReferenceClass(Class<?> currentClass, String prefix) {
        String className = currentClass.getName();
        var innerArrays = new HashMap<String, MappingDependencyTreeElement>();
        var innerFields = new HashMap<String, String>();
        var isInnerFieldCustomerField = new HashMap<String, Boolean>();
        var objectsReference = new HashMap<String, MappingDependencyTreeElement>();
        var embeddedObjects = new HashMap<String, MappingDependencyTreeElement>();
        var fields = currentClass.getDeclaredFields();
        var fieldsSuperclass = currentClass.getSuperclass().getDeclaredFields();
        for (var field : ArrayUtils.addAll(fieldsSuperclass, fields )) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            var fieldName = field.getName();
            var fieldTypeName = field.getGenericType().getTypeName();

            if (!(field.getGenericType() instanceof ParameterizedType) && !isMappingClass(prefix, fieldTypeName)) {
                if (fieldName.equalsIgnoreCase("externalId")) {
                    innerFields.put(fieldName, fieldTypeName);
                }
            }
        }
        return new MappingDependencyTreeElement(className, innerArrays, innerFields,isInnerFieldCustomerField, embeddedObjects, objectsReference);
    }

    public static boolean isFieldFromPersistenceManager(String fieldName,String fieldType)
    {
        String[] notAllowedTypes = {"java.lang.Object", "java.lang.String[]", "java.lang.Class", "org.apache.openjpa.enhance.StateManager", "java.lang.Class[]", "byte[]"};
        String[] notAllowedNames = {"pcInheritedFieldCount", };

        return (Arrays.asList(notAllowedTypes).contains(fieldType)) || (Arrays.asList(notAllowedNames).contains(fieldName));
    }
}
