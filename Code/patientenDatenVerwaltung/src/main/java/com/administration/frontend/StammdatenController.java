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
import com.jfoenix.controls.JFXButton;
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
    private JFXTextField nachname;
    @FXML
    private JFXTextField geschlecht;
    @FXML
    private JFXTextField zimmerNr;
    @FXML
    private JFXTextField alter;
    @FXML
    private JFXTextField einlieferung;
    @FXML
    private JFXTextField patientenID;
    @FXML
    private JFXTextField geburtstag;
    @FXML
    private JFXTextField entlassung;
    @FXML
    private JFXTextField strasse;
    @FXML
    private JFXTextField hausNr;
    @FXML
    private JFXTextField land;
    @FXML
    private JFXTextField plz;
    @FXML
    private JFXTextField ort;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField telMobil;
    @FXML
    private JFXTextField telFest;
    @FXML
    private JFXTextField kostentraeger;
    @FXML
    private JFXTextField versicherungsnummer;
    @FXML
    private JFXButton speichern;
    @FXML
    private JFXTextField vorname;


    /**
     * Initializes the controller class.
     */

    @FXML
    private void save(ActionEvent event){
        if(
                !vorname.getText().equalsIgnoreCase(getPatient().vorname)
        ){
            Patient p = new Patient();
            p.vorname=vorname.getText();

            //dbConnector
        }

    }
}
