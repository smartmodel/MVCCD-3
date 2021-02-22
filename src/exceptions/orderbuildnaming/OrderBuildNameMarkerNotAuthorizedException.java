package exceptions.orderbuildnaming;


public class OrderBuildNameMarkerNotAuthorizedException extends OrderBuildNameException {


	public OrderBuildNameMarkerNotAuthorizedException() {
		super();
	}

	public OrderBuildNameMarkerNotAuthorizedException(String msg) {
		super(msg);
	}

	public OrderBuildNameMarkerNotAuthorizedException(Throwable e) {
		super(e);
	}

	public OrderBuildNameMarkerNotAuthorizedException(String msg, Throwable e) {
		super(msg, e);
	}
}