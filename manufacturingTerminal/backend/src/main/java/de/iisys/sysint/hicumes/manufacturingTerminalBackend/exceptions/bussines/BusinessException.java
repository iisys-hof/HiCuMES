package de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.ManufacturingBaseException;


public class BusinessException extends ManufacturingBaseException {
    public BusinessException(String message, Exception raisedException) {
        super(message, raisedException);
    }

    public BusinessException(String message) {
        super(message);
    }
}
