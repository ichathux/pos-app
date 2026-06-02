package com.example.demo.exception;

/**
 * @author : Manoj Vitharana <mvitharana@onepeterson.com>
 * @since : 25/03/2024
 **/

public class EntryAlreadyExistsException extends RuntimeException{
    public EntryAlreadyExistsException(String message) {
        super(message);
    }
}
