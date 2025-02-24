package dkhpweb.dkhp_backend.exceptions;

public class SendEmailException extends RuntimeException{
    public SendEmailException(String message) {
        super(message);
    }
}
