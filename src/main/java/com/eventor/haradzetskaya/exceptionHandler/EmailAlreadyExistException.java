package com.eventor.haradzetskaya.exceptionHandler;


public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String message) {
        super(message);
    }

}
