package com.example.demo.exception;

/**
 * @author : Manoj Vitharana <mvitharana@onepeterson.com>
 * @since : 25/03/2024
 **/

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}
