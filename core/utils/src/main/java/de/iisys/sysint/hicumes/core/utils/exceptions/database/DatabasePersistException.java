package de.iisys.sysint.hicumes.core.utils.exceptions.database;

public class DatabasePersistException extends DatabaseBaseException {
    public DatabasePersistException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}

