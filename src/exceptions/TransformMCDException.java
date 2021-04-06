package exceptions;

public class TransformMCDException extends RuntimeException{

    public TransformMCDException() {
    }

    public TransformMCDException(String message) {
        super(message);
    }

    public TransformMCDException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransformMCDException(Throwable cause) {
        super(cause);
    }
}
