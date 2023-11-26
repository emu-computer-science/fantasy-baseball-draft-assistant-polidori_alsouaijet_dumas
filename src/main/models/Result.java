package main.models;

public class Result {

	private final boolean successful;
	private final String message;
	
	public Result(boolean successful, String message) {
		this.successful = successful;
		this.message = message;
	}

	public boolean successful() {
		return successful;
	}

	public String getMessage() {
		return message;
	}

}
