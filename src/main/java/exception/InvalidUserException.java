package exception;

public class InvalidUserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7161716052075460119L;

	public InvalidUserException(String errorMessage) {
		
		super(errorMessage);
	}

}
