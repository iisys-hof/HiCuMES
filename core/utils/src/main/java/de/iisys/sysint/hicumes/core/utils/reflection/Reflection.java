package de.iisys.sysint.hicumes.core.utils.reflection;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Reflection {
    public List<FieldWrapper> getFieldsWithAnnotation(Object entity, Class<? extends Annotation> annotationClass) {
        var allFields = getFields(entity);
        var annotatedField = allFields.stream().filter(fieldWrapper -> fieldWrapper.isAnnotationPresent(annotationClass)).collect(Collectors.toList());
        return annotatedField;
    }

    public List<FieldWrapper> getFields(Object entity) {
        Class<?> cl = entity.getClass();
        var fields = cl.getDeclaredFields();
        var fieldWrapper = Arrays.stream(fields).map(field -> new FieldWrapper(field, entity)).collect(Collectors.toList());
        return fieldWrapper;
    }
}
