package com.administration;

import com.administration.backend.Listener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Listener listener = new Listener();

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader root = new FXMLLoader(getClass().getResource("frontend/FXMLDocument.fxml"));

        Scene scene = new Scene(root.load());
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setUserData(root);
        stage.show();
    }
    @Override
    public void stop(){
        listener.disconnect();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        listener.start();
        launch(args);
    }
}
