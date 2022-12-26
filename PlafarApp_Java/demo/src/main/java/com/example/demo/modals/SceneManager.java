package com.example.demo.modals;

import com.example.demo.dependency_injection.Config;
import com.example.demo.pages.app.demo1.pages.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SceneManager {
    public static void redirectTo(String path, Node anchor) throws IOException {
        var loader = new FXMLLoader(Main.class.getResource(path));
        var scene = new Scene(loader.load(), Config.WINDOW_HEIGHT, Config.WINDOW_WIDTH);

        var stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /*public async static void setVisibilityAfterDelay(Node anchor, boolean visibility) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        anchor.setVisible(visibility);
    }

    static CompletableFuture<Integer> AccessTheWebAsync(Node anchor, boolean visibility) throws InterruptedException {

        var a =  new CompletableFuture(
                {
                        TimeUnit.SECONDS.sleep(5);
                });
        anchor.setVisible(visibility);
        return a;
    }*/
}
