/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import com.administration.backend.Patient;
import com.administration.backend.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author pc
 */
public class TechnikerController extends BasicTabController {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }


    @Override
    public void setup(User u) {

    }

    @Override
    public void setup(User u, Patient pid) {

    }
}
