package de.iisys.sysint.hicumes.camunda.formFieldType;

import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.type.ValueType;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class DynamicEnumFormFieldType extends AbstractFormFieldType {

    public final static String TYPE_NAME = "dynamicEnum";

    @Override
    public String getName() {
        return TYPE_NAME;
    }

    @Override
    public TypedValue convertToFormValue(TypedValue typedValue) {

        return this.getStringValue(typedValue);
    }

    @Override
    public TypedValue convertToModelValue(TypedValue typedValue) {
        
        return this.getStringValue(typedValue);
    }

    private TypedValue getStringValue(TypedValue inValue){

        if(inValue.getValue() == null) {
            return Variables.stringValue("", inValue.isTransient());
        } else {
            return Variables.stringValue(inValue.getValue().toString(), inValue.isTransient());
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
