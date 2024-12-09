package de.iisys.sysint.hicumes.core.utils.exceptions;

import javax.persistence.RollbackException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionInfo {
    public String getFullMessage(RollbackException e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionMessage = sw.toString();
        return exceptionMessage;
    }
}
