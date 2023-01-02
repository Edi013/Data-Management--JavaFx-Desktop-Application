package com.example.demo.models;

import com.example.demo.exceptions.MustBePositiveException;
import com.example.demo.exceptions.QuantityNotAvailableException;

import java.util.*;

public class Item {
    private String name;
    private Float price;
    private Integer quantity;

    public Item(String name, Float price, Integer quantity)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public boolean decreaseQuantityUntillRemoval(int quantitySold) throws QuantityNotAvailableException, MustBePositiveException {
        if(quantitySold < 1)
            throw new MustBePositiveException();
        if( quantity < quantitySold)
            throw new QuantityNotAvailableException();
        if(quantity == quantitySold){
            quantity = 0;
            return true;
        }

        quantity -= quantitySold;
        return false;
    }
    public void increaseQuantityOfItemFromStorage(int quantity){
        this.setQuantity(this.quantity + quantity);
    }

    public String buildStringFromItemForStorageFile(){
        return
                this.name + "," +
                        this.price.toString() + "," +
                        this.quantity.toString();
    }
    public String buildStringFromItemForSoldFile(Integer quantitySold){
        return
                this.name + "," +
                        this.price.toString() + "," +
                        quantitySold.toString();

    }
    public String buildStringFromItemForDisplay(){
        return
                "Name: "+ this.name + " |" +
                " Price: " + this.price.toString() + " |" +
                " Quantity: " + this.quantity.toString()+ " |";
    }

    public List<Item> toList(){
        List<Item> newList=new ArrayList<Item>();
        newList.add(this);
        return newList;
    }

    public String getName() {
        return name;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
