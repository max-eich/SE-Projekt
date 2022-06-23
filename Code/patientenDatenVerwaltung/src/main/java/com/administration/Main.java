package com.administration;

import com.administration.backend.dbConnector;
import com.administration.backend.Listener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Application {

    public static Set<Stage> stages = new HashSet<>();

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader root = new FXMLLoader(getClass().getResource("frontend/FXMLDocument.fxml"));

        Scene scene = new Scene(root.load());
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        stages.add(stage);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Listener listener = new Listener();
        listener.start();
        launch(args);
    }
}
