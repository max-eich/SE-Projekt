/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import com.administration.backend.User;
import com.administration.backend.dbConnector;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class AccontController extends BasicTabController {

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
            firstPassword.clear();
            secondPassword.clear();
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
        userName.setText(getUser().name);
        userRole.setText(getUser().role.toString());
    }

    @Override
    public void setup(User u) {
        showData(u);
    }

    @Override
    public void setup(User u, int pid) {
        setup(u);
    }

    @Override
    public void update(User u){
        showData(dbConnector.getUser(getUser()));
    }

    @Override
    public void update(User u, int pid){
        update(u);
    }
}
