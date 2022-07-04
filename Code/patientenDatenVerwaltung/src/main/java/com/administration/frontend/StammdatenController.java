/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import com.administration.backend.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

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
    private void save(ActionEvent event) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        if(
                (getPatient().geburtsdatum==null && !geburtstag.getText().equalsIgnoreCase(""))
                || (getPatient().geschlecht==null && !geschlecht.getText().equalsIgnoreCase(""))
        ) {
            Patient p = new Patient();
            p.vorname = vorname.getText();
            p.patientID=Integer.parseInt( patientenID.getText());
            p.nachname=nachname.getText();
            if(!geschlecht.getText().equalsIgnoreCase(""))
                p.geschlecht= Geschlecht.valueOf(geschlecht.getText());
            try {
                if (!geburtstag.getText().equalsIgnoreCase(""))
                    p.geburtsdatum = new SimpleDateFormat("dd.MM.yyyy").parse(geburtstag.getText());
            } catch (ParseException ex){
                ex.printStackTrace();
            }
            dbConnector.setPatientData(p,getPatient().patientID,getUser());

        } else if (
                !vorname.getText().equalsIgnoreCase(getPatient().vorname)
                || !nachname.getText().equalsIgnoreCase(getPatient().nachname)
                || !geburtstag.getText().equalsIgnoreCase(df.format(getPatient().geburtsdatum))
                || !geschlecht.getText().equalsIgnoreCase(getPatient().geschlecht.toString())
                || !patientenID.getText().equalsIgnoreCase(String.valueOf(getPatient().patientID))
        ) {
            Patient p = new Patient();
            p.vorname = vorname.getText();
            p.patientID=Integer.parseInt( patientenID.getText());
            p.nachname=nachname.getText();
            if(!geschlecht.getText().equalsIgnoreCase(""))
            p.geschlecht= Geschlecht.valueOf(geschlecht.getText());
            try {
                if (!geburtstag.getText().equalsIgnoreCase(""))
                    p.geburtsdatum = new SimpleDateFormat("dd.MM.yyyy").parse(geburtstag.getText());
            } catch (ParseException ex){
                ex.printStackTrace();
            }
            dbConnector.setPatientData(p,getPatient().patientID,getUser());
        }

        if(
                !strasse.getText().equalsIgnoreCase(getPatient().stamdaten.straße)
                || !ort.getText().equalsIgnoreCase(getPatient().stamdaten.ort)
                || !plz.getText().equalsIgnoreCase(String.valueOf(getPatient().stamdaten.plz))
                || !land.getText().equalsIgnoreCase(getPatient().stamdaten.land)
                || !telFest.getText().equalsIgnoreCase(getPatient().stamdaten.telefon)
                || !telMobil.getText().equalsIgnoreCase(getPatient().stamdaten.handy)
                || !email.getText().equalsIgnoreCase(getPatient().stamdaten.eMail)
                || !kostentraeger.getText().equalsIgnoreCase(getPatient().stamdaten.kostenträger)
                || !versicherungsnummer.getText().equalsIgnoreCase(String.valueOf(getPatient().stamdaten.versicherungsnummer))
        ){
            Stamdaten s = new Stamdaten();
            s.versicherungsnummer= Integer.parseInt(versicherungsnummer.getText());
            s.eMail=email.getText();
            s.straße=strasse.getText();
            s.ort=ort.getText();
            s.plz=Integer.parseInt(plz.getText());
            s.land=land.getText();
            s.telefon=telFest.getText();
            s.handy=telMobil.getText();
            s.kostenträger=kostentraeger.getText();
            dbConnector.setPatientStammdaten(s,getPatient().patientID,getUser());
        }

        if(
                !zimmerNr.getText().equalsIgnoreCase(getPatient().unterbringung.zimmer)
                || !entlassung.getText().equalsIgnoreCase(getPatient().unterbringung.entlassung)
                || !einlieferung.getText().equalsIgnoreCase(getPatient().unterbringung.einlieferung)
        ){
            Unterbringung u = new Unterbringung();
            u.zimmer=zimmerNr.getText();
            u.einlieferung=einlieferung.getText();
            u.entlassung=entlassung.getText();
            dbConnector.setPatientUnterbringung(u,getPatient().patientID,getUser());
        }

        Patient p = dbConnector.getPatient(getUser().role,getPatient().patientID);
        setup(getUser(),p);
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

        if(getUser().role==Role.personal) {
            speichern.setVisible(false);
        } else {
            speichern.setVisible(true);
        }
        setPatient(pid);
        Patient p = getPatient();
        nachname.setText(p.nachname);
        vorname.setText(p.vorname);
        zimmerNr.setText(p.unterbringung.zimmer);
        einlieferung.setText(p.unterbringung.einlieferung);
        patientenID.setText(String.valueOf(p.patientID));
        if (getPatient().geschlecht != null)
            geschlecht.setText(getPatient().geschlecht.toString());
        else geschlecht.setText("");
        if (getPatient().geburtsdatum != null) {
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
