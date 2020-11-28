package exceptions;


public class OrderBuildNameException extends RuntimeException {


	public OrderBuildNameException() {
		super();
	}

	public OrderBuildNameException(String msg) {
		super(msg);
	}

	public OrderBuildNameException(Throwable e) {
		super(e);
	}

	public OrderBuildNameException(String msg, Throwable e) {
		super(msg, e);
	}
}