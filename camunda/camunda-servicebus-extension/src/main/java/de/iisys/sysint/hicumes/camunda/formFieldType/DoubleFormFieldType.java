package de.iisys.sysint.hicumes.camunda.formFieldType;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.type.ValueType;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class DoubleFormFieldType extends AbstractFormFieldType {

    public final static String TYPE_NAME = "double";

    public String getName() {
        return TYPE_NAME;
    }

    @Override
    public TypedValue convertToFormValue(TypedValue typedValue) {
        if(typedValue.getValue() == null) {
            return Variables.stringValue("", typedValue.isTransient());
        } else if(typedValue.getType() == ValueType.DOUBLE) {
            return Variables.stringValue((String) Double.toString((Double) typedValue.getValue()), typedValue.isTransient());
        }
        else {
            throw new ProcessEngineException("Expected value to be of type '"+ValueType.DOUBLE+"' but got '"+typedValue.getType()+"'.");
        }
    }

    @Override
    public TypedValue convertToModelValue(TypedValue typedValue) {
        Object value = typedValue.getValue();
        if(value == null) {
            return Variables.doubleValue(null, typedValue.isTransient());
        }
        else if(value instanceof Integer) {
            return Variables.doubleValue(Double.valueOf((Integer) value), typedValue.isTransient());
        }
        else if(value instanceof Long) {
            return Variables.doubleValue(Double.valueOf((Long) value), typedValue.isTransient());
        }
        else if(value instanceof Double) {
            return Variables.doubleValue((Double) value, typedValue.isTransient());
        }
        else if(value instanceof String) {
            String strValue = ((String) value).trim();
            if (strValue.isEmpty()) {
                return Variables.doubleValue(null, typedValue.isTransient());
            }
            return Variables.doubleValue(Double.parseDouble(strValue), typedValue.isTransient());
        }
        else {
            throw new ProcessEngineException("Value '"+value+"' cannot be transformed into a Double.");
        }
    }

    @Override
    public Object convertFormValueToModelValue(Object o) {
        return null;
    }

    @Override
    public String convertModelValueToFormValue(Object o) {
        return null;
    }
}
