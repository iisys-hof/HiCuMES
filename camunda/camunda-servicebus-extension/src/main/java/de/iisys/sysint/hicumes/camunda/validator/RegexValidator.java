package de.iisys.sysint.hicumes.camunda.validator;

import org.camunda.bpm.engine.impl.form.validator.AbstractTextValueValidator;

public class RegexValidator extends AbstractTextValueValidator{

    @Override
    protected boolean validate(String submittedValue, String configuration) {
        String pattern = configuration;
        return submittedValue.matches(pattern);
    }
}
