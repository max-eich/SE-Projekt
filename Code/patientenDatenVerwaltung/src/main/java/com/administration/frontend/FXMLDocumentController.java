/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.administration.Main;
import com.administration.backend.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author pc
 */
public class FXMLDocumentController extends BasicController{


    @FXML
    private AnchorPane benutzername;
    @FXML
    private JFXTextField Benutzername;
    @FXML
    private JFXPasswordField Password;
    @FXML
    private JFXButton ButtonLogin;
    @FXML
    private Label fehlermeldung;



    @FXML
    public void initialize() {
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws InterruptedException {
        if(
                         !Password.getText().contains("'")
                        && !Password.getText().contains("\"")
                        && !Password.getText().contains(";")
                && !Benutzername.getText().contains("'")
                                 && !Benutzername.getText().contains("\"")
                                 && !Benutzername.getText().contains(";")
        ){
            setUser(dbConnector.userLogin(Benutzername.getText(),Password.getText()));
            if(dbConnector.userLogin(Benutzername.getText(),Password.getText())!=null){
                try{
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("frontend/originPane.fxml"));
                    Parent root = loader.load();
                    ((OriginPaneController) loader.getController()).setup(getUser());
                    var stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle("Patientenverwaltung");
                    stage.setScene(new Scene(root));
                    stage.setUserData(loader);
                    stage.show();
                    Stage.getWindows().stream().filter(foundStage -> ((Stage) foundStage).getTitle().equalsIgnoreCase("Login")).forEach(window -> ((Stage) window).close());
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else {
                fehlermeldung.setText("Benutzername/Passwort\nincorrect");
                Benutzername.clear();
                Password.clear();
            }
        }
    }


}
