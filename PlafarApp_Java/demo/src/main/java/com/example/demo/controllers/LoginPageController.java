package com.example.demo.controllers;

import com.example.demo.config.Config;
import com.example.demo.exceptions.CredentialsTooShortException;
import com.example.demo.exceptions.CredentialsWrong;
import com.example.demo.models.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginPageController {
    @FXML
    private Button login;

    @FXML
    private TextField username ;
    private final String validUserName = Config.VALID_USER_NAME;

    @FXML
    private  PasswordField password;
    private final String validPassword = Config.VALID_PASSWORD;

    @FXML
    private Label displayLabel;

    public LoginPageController(){
        displayLabel = new Label();
        displayLabel.setVisible(true);
    }

    public void onClickLogin(){
        try
        {
            checkCredentials();

            SceneManager.redirectTo(Config.USE_PAGE_PATH, login);
        }
        catch (CredentialsTooShortException e) {
            displayLabel.setText(Config.CREDENTIALS_ERROR_TOO_SHORT);
        }
        catch (CredentialsWrong e) {
            displayLabel.setText(e.getMessage());
        }
        catch (IOException e) {
            displayLabel.setText(Config.ERROR_MESSAGE_CONTACT_US);
        }
    }
    private void checkCredentials() throws CredentialsTooShortException, CredentialsWrong {
        var username1 = username.getText();
        var password1 = password.getText();

        if(username1.length() < 4 || password1.length() < 4)
            throw new CredentialsTooShortException();
        if(!username.getText().equals(validUserName))
            throw new CredentialsWrong("Username not valid!");
        if (!password.getText().equals(validPassword))
            throw new CredentialsWrong("Password not valid!");
    }
}