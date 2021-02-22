package exceptions.orderbuildnaming;


public class OrderBuildNameColumnAttrSizeLimitException extends OrderBuildNameException {


	public OrderBuildNameColumnAttrSizeLimitException() {
		super();
	}

	public OrderBuildNameColumnAttrSizeLimitException(String msg) {
		super(msg);
	}

	public OrderBuildNameColumnAttrSizeLimitException(Throwable e) {
		super(e);
	}

	public OrderBuildNameColumnAttrSizeLimitException(String msg, Throwable e) {
		super(msg, e);
	}
}