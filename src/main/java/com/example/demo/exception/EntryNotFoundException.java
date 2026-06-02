package com.example.demo.exception;

/**
 * @author : Manoj Vitharana <mvitharana@onepeterson.com>
 * @since : 25/03/2024
 **/

public class EntryNotFoundException extends RuntimeException{
    public EntryNotFoundException(String message) {
        super(message);
    }
}
