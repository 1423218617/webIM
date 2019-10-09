package com.hx.webim.Exception;

public class UserException extends RuntimeException {
    private String message;
    public UserException(String message){
        super(message);
        this.message=message;

    }

}
