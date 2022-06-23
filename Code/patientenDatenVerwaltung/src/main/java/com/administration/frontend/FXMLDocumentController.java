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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author pc
 */
public class FXMLDocumentController {

    @FXML
    private Label label;

    @FXML
    private TableView<searchTable> tablePatient;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String s;
    private Patient p;

    public User user;

    private View currentView = View.LOGIN;

    @FXML
    private Label testLabel;

    @FXML
    public void initialize() {
        searchTable s = new searchTable("1", "a", "m", "05", "3");
        if (tablePatient != null) {
            //tablePatient.getColumns().add(TableColumn<>)
        }
        if(user == null){
            System.out.println("User not found.");
        }
        if (user != null) {
            System.out.println("User: " + user.name);
            testLabel.setText(user.name);
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        currentView = View.MAIN_MENU;

        root = FXMLLoader.load(getClass().getResource("patientenliste.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        s = "patientenliste";
        //listener.disconnect();
        stage.show();
    }

    @FXML
    private void buttonSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("stamdaten.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        s = "Stammdaten";
        stage.show();
    }

    @FXML
    private void patientSelect() throws IOException {
        /**root = FXMLLoader.load(getClass().getResource("stamdaten.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         s="Stammdaten";
         stage.show();**/
    }

    public void setUser(User user) {
    }

    private enum View {
        LOGIN, MAIN_MENU;
    }

}
