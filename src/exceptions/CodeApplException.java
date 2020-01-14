package exceptions;


public class CodeApplException extends RuntimeException {


	public CodeApplException() {
		super();
	}

	public CodeApplException(String msg) {
		super(msg);
	}

	public CodeApplException(Throwable e) {
		super(e);
	}

	public CodeApplException(String msg, Throwable e) {
		super(msg, e);
	}
}