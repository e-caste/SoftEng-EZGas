package exception;

public class InvalidLoginDataException extends Exception {
	
	
	public InvalidLoginDataException(String errorMessage) {
		
		super(errorMessage);
	}

}
