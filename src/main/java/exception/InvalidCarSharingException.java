package exception;

public class InvalidCarSharingException extends Exception {
    public InvalidCarSharingException(String errorMessage){
        super(errorMessage);
    }
}
