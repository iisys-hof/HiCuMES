package de.iisys.sysint.hicumes.mappingBackend.exceptions.fieldExtensionExceptions;

import de.iisys.sysint.hicumes.mappingBackend.exceptions.MappingBackendBaseException;

public class FieldExtensionException extends MappingBackendBaseException {
    public FieldExtensionException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
