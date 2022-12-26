package com.example.demo.exceptions;

public class CredentialsTooShortException extends Exception{
    public CredentialsTooShortException(){
        super();
    }
    public CredentialsTooShortException(String message){
        super(message);
    }
}

