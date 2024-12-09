package de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;

public class MappingException  extends MappingBaseException {
    public MappingException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
