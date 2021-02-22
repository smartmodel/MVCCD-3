package exceptions.orderbuildnaming;


public class OrderBuildNameWordNullException extends OrderBuildNameException {


	public OrderBuildNameWordNullException() {
		super();
	}

	public OrderBuildNameWordNullException(String msg) {
		super(msg);
	}

	public OrderBuildNameWordNullException(Throwable e) {
		super(e);
	}

	public OrderBuildNameWordNullException(String msg, Throwable e) {
		super(msg, e);
	}
}