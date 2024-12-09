package de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions;

public class ManufacturingBaseException extends Exception {
    private Exception raisedException;
    private final String message;

    public ManufacturingBaseException(String message, Exception raisedException) {
        super(message, raisedException);
        this.message = message;
        this.raisedException = raisedException;
    }

    public ManufacturingBaseException(String message) {
        super(message, null);
        this.message = message;
    }

    @Override
    public String toString() {
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
