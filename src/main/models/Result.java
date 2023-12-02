package main.models;

public class Result {

	private final boolean successful;
	private final String message;
	private final Object payload;
	
	public Result(boolean successful, String message) {
		this.successful = successful;
		this.message = message;
		this.payload = null;
	}
	
	public Result(boolean successful, String message, Object payload) {
		this.successful = successful;
		this.message = message;
		this.payload = payload;
	}

	public boolean successful() {
		return successful;
	}

	public String getMessage() {
		return message;
	}

	public Object getPayload() {
		return payload;
	}

}
