package com.example.demo.controllers;

import com.example.demo.exceptions.ItemNotAvailableException;
import com.example.demo.repository.ItemRepository;
import com.example.demo.config.Config;
import com.example.demo.exceptions.MustBePositiveException;
import com.example.demo.exceptions.MustFillAllTextFieldsException;
import com.example.demo.exceptions.QuantityNotAvailableException;
import com.example.demo.models.Item;
import com.example.demo.models.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsePageController implements Initializable{
    @FXML
    private ListView<String> listview;
    @FXML
    private Button addBtn;
    @FXML
    private TextField addName;
    @FXML
    private TextField addQuantity;
    @FXML
    private TextField addPrice;

    @FXML
    private Button buyBtn;
    @FXML
    private TextField buyName;
    @FXML
    private TextField buyQuantity;

    @FXML
    private Button exitBtn;

    @FXML
    private Button deleteBtn;
    @FXML
    private TextField deleteName;

    @FXML
    private Label displayLabel;

    private final ItemRepository itemRepo;

    public UsePageController(){
            itemRepo = new ItemRepository();
            listview = new ListView<>();
            listview.setEditable(false);

            this.displayLabel = new Label();
    }

    public void addItem(){
        try{
            String name = addName.getText();
            int quantity = Integer.parseInt(addQuantity.getText());
            float price = Float.parseFloat(addPrice.getText());

            itemRepo.addToStorage(name, price, quantity);

            handelDispaly(Config.ADD_ITEM_MESSAGE);

            overwriteStorage();

            cleanUpAddTextFields();

        }catch (WrongMethodTypeException e){
            displayLabel.setText(Config.ADD_ITEM_INPUT_ERROR_MESSAGE);
        }catch (MustFillAllTextFieldsException e){
            displayLabel.setText(Config.ERROR_MESSAGE_MUST_FILL_ALL_TEXT_FIELD);
        }catch (NumberFormatException e){
            displayLabel.setText(Config.ERROR_MESSAGE_MUST_BE_NUMBER);
        }
        catch (MustBePositiveException e){
            displayLabel.setText(Config.ERROR_MESSAGE_MUST_BE_POSITIVE);
        }catch (Exception e){
            displayLabel.setText(Config.ERROR_MESSAGE_CONTACT_US); //Config.Add_ITEM_INPUT_ERROR_MESSAGE
        }
    }

    public void buyItem(){
        try
        {
            String name = buyName.getText();
            int quantityBought = Integer.parseInt(buyQuantity.getText());

            Item item = itemRepo.findInListByName(name);

            decreaseQuantityOfItem(item, quantityBought);

            handelDispaly(Config.BUY_ITEM_MESSAGE);

            logTranscation(item, quantityBought);
            overwriteStorage();

            cleanUpBuyTextFields();

        }catch (MustBePositiveException e){
            displayLabel.setText(Config.ERROR_MESSAGE_MUST_BE_POSITIVE);
        }
        catch (NumberFormatException e){
            displayLabel.setText(Config.ERROR_MESSAGE_MUST_BE_NUMBER);
        }
        catch (ItemNotAvailableException e){
                displayLabel.setText(Config.BUY_ITEM_ERROR_NOT_EXISTS);
        }catch(QuantityNotAvailableException e){
            displayLabel.setText(Config.BUY_ITEM_ERROR_AMOUT);
        }catch (Exception e){
            displayLabel.setText(Config.ERROR_MESSAGE_CONTACT_US);
        }
    }

    public void deleteItem(){
        try{
            String name = deleteName.getText();
            Item item = itemRepo.findInListByName(name);

            itemRepo.removeFromStorage(item);

            handelDispaly(Config.DELETE_ITEM_MESSAGE);

            overwriteStorage();

            cleanUpDeleteTextFields();

        }catch (ItemNotAvailableException e){
            displayLabel.setText(Config.DELETE_ITEM_ERROR_MESSAGE);
        }catch (Exception e){
            displayLabel.setText(Config.ERROR_MESSAGE_CONTACT_US);
        }
    }

    public void exitDB(){
        Platform.exit();
    }

    public void swapUser(){
        try {
            SceneManager.redirectTo(Config.LOGIN_PAGE_PATH, exitBtn);
        }catch (IOException e) {
            displayLabel.setText(Config.ERROR_MESSAGE_CONTACT_US);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDisplayListViewFromStorage();
    }
    private void decreaseQuantityOfItem(Item item, int quantity) throws QuantityNotAvailableException, MustBePositiveException, ItemNotAvailableException {
            itemRepo.decreaseQuantityOfItemFromStorage(item, quantity);
    }
    private void handelDispaly(String message){
        displayLabel.setText(message);
        refreshListView();
    }
    private void refreshListView(){
        clearListView();
        fillDisplayListViewFromStorage();
    }
    private void fillDisplayListViewFromStorage(){
        for(Item item: itemRepo.getStorage()){
                listview.getItems().add(item.buildStringFromItemForDisplay());
            }
    }
    private void clearListView(){
        listview.getItems().clear();
    }
    private void overwriteStorage(){
        itemRepo.overwriteStorage(itemRepo.getStorage());
    }
    private void logTranscation(Item item, int quantity){
        itemRepo.logTransaction(item.toList(), quantity);
    }

    private void cleanUpAddTextFields(){
        addName.setText("");
        addPrice.setText("");
        addQuantity.setText("");
    }
    private void cleanUpBuyTextFields(){
        buyName.setText("");
        buyQuantity.setText("");
    }
    private void cleanUpDeleteTextFields(){
        deleteName.setText("");
    }
}
