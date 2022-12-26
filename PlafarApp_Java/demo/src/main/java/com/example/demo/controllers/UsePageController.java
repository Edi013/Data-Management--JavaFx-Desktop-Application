package com.example.demo.controllers;

import com.example.demo.data.ItemRepository;
import com.example.demo.dependency_injection.Config;
import com.example.demo.exceptions.MustBePositiveException;
import com.example.demo.exceptions.MustFillAllTextFieldsException;
import com.example.demo.exceptions.QuantityNotAvailableException;
import com.example.demo.modals.Item;
import com.example.demo.modals.SceneManager;
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
    private Button AddBtn;
    @FXML
    private TextField AddName;
    @FXML
    private TextField AddQuantity;
    @FXML
    private TextField AddPrice;

    @FXML
    private Button BuyBtn;
    @FXML
    private TextField BuyName;
    @FXML
    private TextField BuyQuantity;

    @FXML
    private Button ExitBtn;

    @FXML
    private Button DeleteBtn;
    @FXML
    private TextField DeleteName;

    @FXML
    private Label displayLabel;

    private final ItemRepository itemRepo;

    public UsePageController(){
            itemRepo = new ItemRepository();
            listview = new ListView<>();
            listview.setEditable(false);

            this.displayLabel = new Label();
    }

    //AddName.setText(""); la final de fiecare metoda
    public void AddItem(){
        try{
            String name = AddName.getText();
            int quantity = Integer.parseInt(AddQuantity.getText());
            float price = Float.parseFloat(AddPrice.getText());

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

    public void BuyItem(){
        try
        {
            String name = BuyName.getText();
            int quantityBought = Integer.parseInt(BuyQuantity.getText());

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
        catch (NullPointerException e){
                displayLabel.setText(Config.BUY_ITEM_ERROR_NOT_EXISTS);
        }catch(QuantityNotAvailableException e){
            displayLabel.setText(Config.BUY_ITEM_ERROR_AMOUT);
        }catch (Exception e){
            displayLabel.setText(Config.ERROR_MESSAGE_CONTACT_US);
        }
    }

    public void DeleteItem(){
        try{
            String name = DeleteName.getText();
            Item item = itemRepo.findInListByName(name);

            itemRepo.removeFromStorage(item);

            handelDispaly(Config.DELETE_ITEM_MESSAGE);

            overwriteStorage();

            cleanUpDeleteTextFields();

        }catch (NullPointerException e){
            displayLabel.setText(Config.BUY_ITEM_ERROR_NOT_EXISTS);
        }catch (Exception e){
            displayLabel.setText(Config.ERROR_MESSAGE_CONTACT_US);
        }
    }

    public void ExitDB(){
        Platform.exit();
    }

    public void SwapUser(){
        try {
            SceneManager.redirectTo(Config.LOGIN_PAGE_PATH, ExitBtn);
        }catch (IOException e) {
            displayLabel.setText(Config.ERROR_MESSAGE_CONTACT_US);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDisplayListViewFromStorage();
    }
    private void decreaseQuantityOfItem(Item item, int quantity) throws QuantityNotAvailableException, MustBePositiveException, NullPointerException {
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
        AddName.setText("");
        AddPrice.setText("");
        AddQuantity.setText("");
    }
    private void cleanUpBuyTextFields(){
        BuyName.setText("");
        BuyQuantity.setText("");
    }
    private void cleanUpDeleteTextFields(){
        DeleteName.setText("");
    }
}
