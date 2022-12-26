package com.example.demo.exceptions;

public class MustFillAllTextFieldsException extends Exception{
    public MustFillAllTextFieldsException(){
        super();
    }
    public MustFillAllTextFieldsException(String message){
        super(message);
    }
}

