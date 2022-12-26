package com.example.demo.exceptions;

public class MustBePositiveException extends Exception{
    public MustBePositiveException(){
        super();
    }
    public MustBePositiveException(String message){
        super(message);
    }
}
