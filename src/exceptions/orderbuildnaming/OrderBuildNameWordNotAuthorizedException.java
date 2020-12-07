package exceptions.orderbuildnaming;


public class OrderBuildNameWordNotAuthorizedException extends OrderBuildNameException {


	public OrderBuildNameWordNotAuthorizedException() {
		super();
	}

	public OrderBuildNameWordNotAuthorizedException(String msg) {
		super(msg);
	}

	public OrderBuildNameWordNotAuthorizedException(Throwable e) {
		super(e);
	}

	public OrderBuildNameWordNotAuthorizedException(String msg, Throwable e) {
		super(msg, e);
	}
}