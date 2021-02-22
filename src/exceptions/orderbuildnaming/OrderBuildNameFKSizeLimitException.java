package exceptions.orderbuildnaming;


public class OrderBuildNameFKSizeLimitException extends OrderBuildNameException {


	public OrderBuildNameFKSizeLimitException() {
		super();
	}

	public OrderBuildNameFKSizeLimitException(String msg) {
		super(msg);
	}

	public OrderBuildNameFKSizeLimitException(Throwable e) {
		super(e);
	}

	public OrderBuildNameFKSizeLimitException(String msg, Throwable e) {
		super(msg, e);
	}
}