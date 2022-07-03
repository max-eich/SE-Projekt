/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.administration.backend.Patient;
import com.administration.backend.User;
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
public class StammdatenController extends BasicTabController {

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

    @Override
    public void setup(User u) {
        System.err.println("Wrong setup.");
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }


    @Override
    public void setup(User u, Patient pid) {
        setUser(u);
        setPatient(pid);
        Patient p = getPatient();
        nachname.setText(p.nachname);
        vorname.setText(p.vorname);
        zimmerNr.setText(p.unterbringung.zimmer);
        einlieferung.setText(p.unterbringung.einlieferung);
        patientenID.setText(String.valueOf(p.patientID));
        if(getPatient().geschlecht!=null)
            geschlecht.setText(getPatient().geschlecht.toString());
        else geschlecht.setText("");
        if(getPatient().geburtsdatum!=null) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            geburtstag.setText(df.format(getPatient().geburtsdatum));
            LocalDate g = convertToLocalDate(getPatient().geburtsdatum);
            alter.setText(String.valueOf((Period.between(g, LocalDate.now())).getYears()));
        } else {
            geburtstag.setText("");
            alter.setText("");
        }
        entlassung.setText(p.unterbringung.entlassung);
        strasse.setText(p.stamdaten.straße);
        land.setText(p.stamdaten.land);
        plz.setText(String.valueOf(p.stamdaten.plz));
        ort.setText(p.stamdaten.ort);
        email.setText(p.stamdaten.eMail);
        telFest.setText(p.stamdaten.telefon);
        telMobil.setText(p.stamdaten.handy);
        kostentraeger.setText(p.stamdaten.kostenträger);
        versicherungsnummer.setText(String.valueOf(p.stamdaten.versicherungsnummer));
    }
}
