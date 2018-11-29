package rason.app.model;

@SuppressWarnings("serial")
public class RasonException extends RuntimeException {
	public RasonException() {
		super();
	}
	public RasonException(String message) {
		super(message);
	}
	public RasonException(String message, Throwable cause) {
		super(message, cause);
	}
	public RasonException(String message, Throwable cause, Boolean enableSuppression, Boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}