package de.iisys.sysint.hicumes.mappingBackend.exceptions.mappingExceptions;

import de.iisys.sysint.hicumes.mappingBackend.exceptions.MappingBackendBaseException;

public class EmptyFormsException extends MappingBackendBaseException
{
    public EmptyFormsException(String message)
    {
        super(message, null);
    }
}
