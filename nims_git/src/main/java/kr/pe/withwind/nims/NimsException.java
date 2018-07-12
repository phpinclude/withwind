package kr.pe.withwind.nims;

public class NimsException extends Exception{

	public NimsException() {
		super();
	}
	
	public NimsException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NimsException(String message) {
		super(message);
	}

	public NimsException(Throwable cause) {
		super(cause);
	}
}
