package com.example.demo.exceptions;

public class ItemNotAvailableException extends Exception{
    public ItemNotAvailableException(){
        super();
    }
    public ItemNotAvailableException(String message){
        super(message);
    }
}
