package de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions;

public class TerminalMappingException extends ManufacturingBaseException{

    public TerminalMappingException(String message, Exception raisedException) {
        super(message, raisedException);
    }

    public TerminalMappingException(String message) {
        super(message);
    }
}
