package de.iisys.sysint.hicumes.mappingEngine.exceptions;

public class ExtensionNotFoundException extends MappingBaseException {
    public ExtensionNotFoundException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
