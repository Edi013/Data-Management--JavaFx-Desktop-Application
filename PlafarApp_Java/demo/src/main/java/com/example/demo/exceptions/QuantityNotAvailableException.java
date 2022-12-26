package com.example.demo.exceptions;

public class QuantityNotAvailableException extends Exception{
    public QuantityNotAvailableException(){
        super();
    }
    public QuantityNotAvailableException(String message){
        super(message);
    }
}
