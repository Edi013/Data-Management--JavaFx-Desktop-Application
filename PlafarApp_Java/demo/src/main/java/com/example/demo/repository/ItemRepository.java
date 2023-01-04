package com.example.demo.repository;

import com.example.demo.config.Config;
import com.example.demo.exceptions.ItemNotAvailableException;
import com.example.demo.exceptions.MustBePositiveException;
import com.example.demo.exceptions.MustFillAllTextFieldsException;
import com.example.demo.exceptions.QuantityNotAvailableException;
import com.example.demo.models.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ItemRepository {
    private final List<Item> storage;
    private final File storageFile;
    private final File soldFile;

    public ItemRepository(){ // FileNotFoundException
        this.storage = new ArrayList<>();
        this.storageFile = new File(Config.STORAGE_FILE_PATH);
        this.soldFile = new File(Config.SOLD_FILE_PATH);

        readStorage();
    }

    private void readStorage(){
        try
        {
                Scanner storageScanner = new Scanner(storageFile);

                while(storageScanner.hasNextLine())
                {
                    String newLine = storageScanner.nextLine();
                    addReadItemToStorageList(parseLine(newLine));
                }
        }
        catch (IOException e){
            System.out.println("Database error when reading file");
            System.out.println(Config.ERROR_MESSAGE_CONTACT_US);
            e.printStackTrace();
        }
    }
    private String[] parseLine(String newLine){
        return newLine.split(",");
    }
    private void addReadItemToStorageList(String[] parsedLine){
        storage.add(new Item(parsedLine[0],
                             Float.parseFloat(parsedLine[1]),
                             Integer.parseInt(parsedLine[2]) ));
    }

    public void logTransaction(List<Item> items, int quantitySold){
        try {
            writeItemToFile(soldFile, items, true, quantitySold);
        } catch (IOException e) {
            System.out.println(Config.ERROR_MESSAGE_CONTACT_US);
            e.printStackTrace();
        }
    }
    public void overwriteStorage(List<Item> items){
        try {
            writeItemToFile(storageFile, items, false, 0);
        } catch (IOException e) {
            System.out.println("overwriteStorage in ItemRepository thrown IOException");
            System.out.println(Config.ERROR_MESSAGE_CONTACT_US);
            e.printStackTrace();
        }
    }
    private void writeItemToFile(File file, List<Item> list, boolean append, int quantity) throws IOException {
        FileWriter writer = new FileWriter(file, append);
        PrintWriter printer = new PrintWriter(writer);

        if(file.equals(storageFile)){
            for(Item item: list){
                printer.println(item.buildStringFromItemForStorageFile());
            }
        }
        if(file.equals(soldFile) && quantity > 0){
            for(Item item: list){
                printer.println(item.buildStringFromItemForSoldFile(quantity));
            }
        }

        printer.close();
    }
    public List<Item> getStorage(){
        return storage;
    }
    public void addToStorage(String name, float price, int quantity) throws MustFillAllTextFieldsException, MustBePositiveException {
        if(Objects.equals(name, "")){
            throw new MustFillAllTextFieldsException();
        }
        if(quantity < 1 || price < 0)
            throw new MustBePositiveException();

        Item existingItem = findInListByName(name);

        if(existingItem == null){
            Item newItem = new Item(name, price, quantity);
            storage.add(newItem);
            return;
        }

        existingItem.increaseQuantityOfItemFromStorage(quantity);
    }
    public void decreaseQuantityOfItemFromStorage(Item item, int quantitySold) throws QuantityNotAvailableException, ItemNotAvailableException, MustBePositiveException {
        if( item == null)
            throw new ItemNotAvailableException();

        boolean quantityIsZero = item.decreaseQuantityUntillRemoval(quantitySold);

        if (quantityIsZero)
        {
            removeFromStorage(item);
        }
    }
    public void removeFromStorage(Item item) throws ItemNotAvailableException {
        if(item == null)
            throw new ItemNotAvailableException();

        storage.remove(item);
    }
    public Item findInListByName(String name){
        for(Item item : storage)
        {
            if(item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }
}
