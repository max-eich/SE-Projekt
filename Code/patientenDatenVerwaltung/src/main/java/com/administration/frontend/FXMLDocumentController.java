/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.administration.backend.Patient;
import com.administration.backend.Role;
import com.administration.backend.dbConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author pc
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;

    private Stage stage;
    private Scene scene;
    private Pane root;
    private String s;
    private Patient p;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("patientenliste.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        s="patientenliste";
        stage.show();
    }

    @FXML
    private void buttonSearch(ActionEvent event)throws IOException {
        root = FXMLLoader.load(getClass().getResource("stamdaten.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        s="Stammdaten";
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(s=="patientenliste"){
           p= dbConnector.getPatient(Role.arzt,1);
            TableView tb = (TableView) scene.lookup("tablePatient");
            tb.getItems().add(p);
        }
    }    
    
}
