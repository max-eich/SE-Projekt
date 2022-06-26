/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import com.administration.backend.User;
import com.administration.backend.dbConnector;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class AccontController extends BasicController {

    @FXML
    private JFXTextField userName;
    @FXML
    private JFXTextField userRole;
    @FXML
    private JFXPasswordField firstPassword;
    @FXML
    private JFXPasswordField secondPassword;

    @FXML
    private void passwordChange(ActionEvent event){
        if(
                firstPassword.getText().equals(secondPassword.getText())
                && !firstPassword.getText().contains("'")
                && !firstPassword.getText().contains("\"")
                && !firstPassword.getText().contains(";")
        ) {
            dbConnector.setPassword(getUser(),firstPassword.getText());
            getUser().password=firstPassword.getText();
            showData(dbConnector.getUser(getUser()));
        }
    }

    @FXML
    private void cardLost(ActionEvent event){
        dbConnector.cardLost(getUser());
        showData(dbConnector.getUser(getUser()));
    }

    public void showData(User user){
        setUser(user);
        userName.setText(getUser().name);
        userRole.setText(getUser().role.toString());
    }

}
