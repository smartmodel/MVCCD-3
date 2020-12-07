package exceptions;


public class TransformMCDException extends RuntimeException {


	public TransformMCDException() {
		super();
	}

	public TransformMCDException(String msg) {
		super(msg);
	}

	public TransformMCDException(Throwable e) {
		super(e);
	}

	public TransformMCDException(String msg, Throwable e) {
		super(msg, e);
	}
}