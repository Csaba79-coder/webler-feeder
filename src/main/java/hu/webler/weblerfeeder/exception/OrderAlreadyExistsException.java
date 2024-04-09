package hu.webler.weblerfeeder.exception;

public class OrderAlreadyExistsException extends RuntimeException{

    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
