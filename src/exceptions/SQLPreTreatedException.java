package exceptions;


public class SQLPreTreatedException extends RuntimeException {


	public SQLPreTreatedException() {
		super();
	}

	public SQLPreTreatedException(String msg) {
		super(msg);
	}

	public SQLPreTreatedException(Throwable e) {
		super(e);
	}

	public SQLPreTreatedException(String msg, Throwable e) {
		super(msg, e);
	}
}