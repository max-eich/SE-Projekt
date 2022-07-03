/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import com.administration.backend.Patient;
import com.administration.backend.User;
import com.administration.backend.dbConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class AccontController extends BasicTabController {

    @FXML
    private JFXTextField benutzerAnzeige;
    @FXML
    private JFXTextField rolleAnzeige;
    @FXML
    private JFXPasswordField neuesPasswortFeld;
    @FXML
    private JFXPasswordField neuesPasswortWiederholen;
    @FXML
    private JFXButton passwortAendernKnopf;
    @FXML
    private Button karteVerloren;

    @FXML
    private void passwordChange(ActionEvent event){
        if(
                neuesPasswortFeld.getText().equals(neuesPasswortWiederholen.getText())
                && !neuesPasswortFeld.getText().contains("'")
                && !neuesPasswortFeld.getText().contains("\"")
                && !neuesPasswortFeld.getText().contains(";")
        ) {
            dbConnector.setPassword(getUser(),neuesPasswortFeld.getText());
            getUser().password=neuesPasswortFeld.getText();
            neuesPasswortFeld.clear();
            neuesPasswortWiederholen.clear();
            update(getUser());
        }
    }

    @FXML
    private void cardLost(ActionEvent event){
        dbConnector.cardLost(getUser());
        showData(dbConnector.getUser(getUser()));
    }

    public void showData(User user){
        setUser(user);
        benutzerAnzeige.setText(getUser().name);
        rolleAnzeige.setText(getUser().role.toString());
    }

    @Override
    public void setup(User u) {
        showData(u);
    }

    @Override
    public void setup(User u, Patient pid) {
        setup(u);
    }

    @Override
    public void update(User u){
        showData(dbConnector.getUser(getUser()));
    }

    @Override
    public void update(User u, Patient pid){
        update(u);
    }
}
