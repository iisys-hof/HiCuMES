package de.iisys.sysint.hicumes.core.utils.exceptions.database;

import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;

public class DatabaseBaseException extends UtilsBaseException {
    public DatabaseBaseException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
