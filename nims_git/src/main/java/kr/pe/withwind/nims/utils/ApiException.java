package kr.pe.withwind.nims.utils;

public class ApiException extends Exception {

	private static final long serialVersionUID = -7697772275047738390L;

	public ApiException() {
		super();
	}

	public ApiException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ApiException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApiException(String arg0) {
		super(arg0);
	}

	public ApiException(Throwable arg0) {
		super(arg0);
	}
}
