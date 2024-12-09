package de.iisys.sysint.hicumes.core.utils.exceptions.reflection;

import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;

public class ReflectionUtilsException  extends UtilsBaseException {
    public ReflectionUtilsException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
