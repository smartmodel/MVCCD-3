package exceptions.orderbuildnaming;


public class OrderBuildNameMarkerSizeLimitException extends OrderBuildNameException {


	public OrderBuildNameMarkerSizeLimitException() {
		super();
	}

	public OrderBuildNameMarkerSizeLimitException(String msg) {
		super(msg);
	}

	public OrderBuildNameMarkerSizeLimitException(Throwable e) {
		super(e);
	}

	public OrderBuildNameMarkerSizeLimitException(String msg, Throwable e) {
		super(msg, e);
	}
}