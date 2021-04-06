package exceptions;

public class TransformMLDRException extends RuntimeException{

    public TransformMLDRException() {
    }

    public TransformMLDRException(String message) {
        super(message);
    }

    public TransformMLDRException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransformMLDRException(Throwable cause) {
        super(cause);
    }
}
