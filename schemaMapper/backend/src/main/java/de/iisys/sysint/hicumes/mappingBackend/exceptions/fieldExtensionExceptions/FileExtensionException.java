package de.iisys.sysint.hicumes.mappingBackend.exceptions.fieldExtensionExceptions;

import de.iisys.sysint.hicumes.mappingBackend.exceptions.MappingBackendBaseException;

public class FileExtensionException extends MappingBackendBaseException {
    public FileExtensionException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
