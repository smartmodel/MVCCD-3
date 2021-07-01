package exceptions.orderbuildnaming;


public class OrderBuildNameUniqueSizeLimitException extends OrderBuildNameException {


	public OrderBuildNameUniqueSizeLimitException() {
		super();
	}

	public OrderBuildNameUniqueSizeLimitException(String msg) {
		super(msg);
	}

	public OrderBuildNameUniqueSizeLimitException(Throwable e) {
		super(e);
	}

	public OrderBuildNameUniqueSizeLimitException(String msg, Throwable e) {
		super(msg, e);
	}
}