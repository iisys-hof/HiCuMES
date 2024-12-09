package de.iisys.sysint.hicumes.mappingBackend.exceptions.mappingExceptions;

import de.iisys.sysint.hicumes.mappingBackend.exceptions.MappingBackendBaseException;

public class ParsingMappingException extends MappingBackendBaseException {
    public ParsingMappingException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
