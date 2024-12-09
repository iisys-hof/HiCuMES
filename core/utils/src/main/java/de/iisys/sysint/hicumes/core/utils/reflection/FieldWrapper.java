package de.iisys.sysint.hicumes.core.utils.reflection;

import de.iisys.sysint.hicumes.core.utils.exceptions.reflection.ReflectionUtilsException;
import lombok.AllArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
@AllArgsConstructor
public class FieldWrapper {

    private final Field field;
    private final Object object;

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return field.isAnnotationPresent(annotationClass);
    }


    public void setField(Object entity) throws ReflectionUtilsException {
        field.setAccessible(true);
        try {
            field.set(object, entity);
        } catch (IllegalAccessException e) {
            throw new ReflectionUtilsException("Failed to set field: " +
                    "\n\tTarget Class: " + object.getClass().getName() +
                    "\n\tField name: " + field.getName() +
                    "\n\tClass to set" + entity.getClass().getName(),e);
        }
    }

    public Object getField() throws ReflectionUtilsException {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new ReflectionUtilsException("Failed to get field: " +
                    "\n\tTarget Class: " + object.getClass().getName() +
                    "\n\tField name: " + field.getName(),e);
        }
    }

    public Object getFieldNotNull() throws ReflectionUtilsException {
        var result = getField();
        if (result == null) {
            throw new ReflectionUtilsException("Failed to get field that should be not null, but it was null: " +
                    "\n\tTarget Class: " + object.getClass().getName() +
                    "\n\tField name: " + field.getName(), null);
        }
        return  result;
    }

    public String getName() {
        return field.getName();
    }

}
