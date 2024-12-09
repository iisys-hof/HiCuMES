package de.iisys.sysint.hicumes.core.utils.exceptions.fileSystem;

import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;

public class FileSystemUtilsException extends UtilsBaseException {
    public FileSystemUtilsException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
