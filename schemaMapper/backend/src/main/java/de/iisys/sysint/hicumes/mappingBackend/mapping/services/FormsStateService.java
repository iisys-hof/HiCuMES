package de.iisys.sysint.hicumes.mappingBackend.mapping.services;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.mappingExceptions.EmptyFormsException;

import javax.ejb.Singleton;

@Singleton
public class FormsStateService
{
    private JsonNode forms;

    public void update(JsonNode forms)
    {
        this.forms = forms;
    }

    public JsonNode getForms() throws EmptyFormsException
    {
        if(this.forms == null)
        {
            throw new EmptyFormsException("No forms contained, please request data from camunda first");
        }
        return this.forms;
    }
}
