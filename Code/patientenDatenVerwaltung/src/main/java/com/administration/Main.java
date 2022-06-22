package com.administration;

import com.administration.backend.dbConnector;
import com.administration.backend.Listener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("frontend/FXMLDocument.fxml"));

        Scene scene = new Scene(root.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Listener listen = new Listener();
        listen.start();
        launch(args);
    }
}
