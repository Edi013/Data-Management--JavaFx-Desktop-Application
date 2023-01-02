package com.example.demo.pages.app.demo1.Main;

import com.example.demo.config.Config;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        stage.setTitle("Stock Management");

        try {
            FXMLLoader root = new FXMLLoader(Main.class.getResource(Config.LOGIN_PAGE_PATH));
            Scene scene = new Scene(root.load(), Config.WINDOW_HEIGHT, Config.WINDOW_WIDTH);

            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            System.out.println("eroare in main - start - ioexception");
        }
    }

    public static void main(){
        launch();
    }
}