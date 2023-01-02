package com.example.demo.models;

import com.example.demo.config.Config;
import com.example.demo.pages.app.demo1.Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public static void redirectTo(String path, Node anchor) throws IOException {
        var loader = new FXMLLoader(Main.class.getResource(path));
        var scene = new Scene(loader.load(), Config.WINDOW_HEIGHT, Config.WINDOW_WIDTH);

        var stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
