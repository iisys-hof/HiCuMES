package de.iisys.sysint.hicumes.core.utils.exceptions;

public class UtilsBaseException extends Exception {
    private Exception raisedException;
    private String message;

    public UtilsBaseException(String message, Exception raisedException) {
        super(message,raisedException);
        this.message = message;
        this.raisedException = raisedException;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.message);
        if (raisedException == null) {
            stringBuilder.append("\n+ without raised exception.");
        } else {
            stringBuilder.append("\n+ with raised Exception: ").append(raisedException.getClass().getSimpleName());
            stringBuilder.append("\n+ and Message: ").append(raisedException.getMessage());
        }

        return stringBuilder.toString();
    }

}
