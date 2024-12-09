package de.iisys.sysint.hicumes.core.utils.exceptions.json;

import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;

public class JsonParsingUtilsException extends UtilsBaseException{
    public JsonParsingUtilsException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}