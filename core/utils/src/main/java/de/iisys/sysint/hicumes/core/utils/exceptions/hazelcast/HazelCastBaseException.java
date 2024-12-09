package de.iisys.sysint.hicumes.core.utils.exceptions.hazelcast;

import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;

public class HazelCastBaseException extends UtilsBaseException
{
    public HazelCastBaseException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
