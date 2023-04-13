package com.ecoway.api.exceptions;

public class EmailExistsException extends  Exception{
    public EmailExistsException(String message) {
        super(message);
    }
}
