package exceptions.orderbuildnaming;


public class OrderBuildNameColumnFKSizeLimitException extends OrderBuildNameException {


	public OrderBuildNameColumnFKSizeLimitException() {
		super();
	}

	public OrderBuildNameColumnFKSizeLimitException(String msg) {
		super(msg);
	}

	public OrderBuildNameColumnFKSizeLimitException(Throwable e) {
		super(e);
	}

	public OrderBuildNameColumnFKSizeLimitException(String msg, Throwable e) {
		super(msg, e);
	}
}