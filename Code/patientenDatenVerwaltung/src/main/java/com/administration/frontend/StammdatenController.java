/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import com.administration.backend.Patient;
import com.administration.backend.dbConnector;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class StammdatenController extends BasicController {

    @FXML
    private JFXTextField vorname;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event){
        if(
                !vorname.getText().equalsIgnoreCase(getPatient().vorname)
        ){
            Patient p = new Patient();
            p.vorname=vorname.getText();

            dbConnector
        }

    }
}
