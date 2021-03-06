/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;


import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.administration.backend.*;
import com.administration.backend.Patient;
import com.administration.backend.User;
import com.administration.backend.dbConnector;
import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.time.*;
import javafx.fxml.Initializable;
import org.controlsfx.control.CheckComboBox;
import com.administration.backend.pdf.Drucken;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class AnamneseController extends BasicTabController {

    @FXML
    private JFXTextField nachname;
    @FXML
    private JFXTextField geschlecht;
    @FXML
    private JFXTextField zimmerNr;
    @FXML
    private JFXTextField vorname;
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
    private JFXTextField groesse;
    @FXML
    private JFXTextField gewicht;
    @FXML
    private JFXCheckBox behinderung;
    @FXML
    private JFXTextField behinderungsGrad;
    @FXML
    private CheckComboBox<String> stoerungen;
    @FXML
    private CheckComboBox<String> syndrome;
    @FXML
    private CheckComboBox<String> verdacht;
    @FXML
    private JFXTextField syndromehinzufuegen;
    @FXML
    private JFXTextField verdachthinzufuegen;
    @FXML
    private JFXTextField chronErkrakungen;
    @FXML
    private JFXButton speichern;

    private ObservableList<String> stoerung = FXCollections.observableArrayList();
    private ObservableList<String> syndrom = FXCollections.observableArrayList();
    private ObservableList<String> med = FXCollections.observableArrayList();


    @Override
    public void setup(User u) {
        System.err.println("Wrong Setup methode for AnamneseTab!");
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    private void setupComboBoxes(){
        stoerung.clear();
        stoerung.add("Adipositas bei Hypothyreose");
        stoerung.add("Cushing-Syndrom");
        stoerung.add("genetisch bedingter Leptinmangel");
        stoerung.add("Kraniopharyngeom");
        stoerung.add("Leptirezeptormutation");
        stoerung.add("MC4-Rezeptormutationen");
        stoerung.add("POMC-Mutationen");
        stoerung.add("prim??rer Hyperinsulinismus/Wiedemann-Beckwith");
        stoerung.add("STH-Mangel");
        stoerung.add("Morbus_Cushing");
        stoerungen.getItems().addAll(stoerung);
        syndrom.clear();
        syndrom.add("Laurence-Moon-Bardet-Biedel");
        syndrom.add("Prader-Willi");
        syndrom.add("Simpson-Golabi-Behmel");
        syndrom.add("Sotos");
        syndrom.add("Trisomie 21");
        syndrom.add("Weaver");
        syndrome.getItems().addAll(syndrom);
        med.clear();
        med.add("Glukokortikoide");
        med.add("Insulingabe");
        med.add("Valproat");
        med.add("Phenothiazine");
        verdacht.getItems().addAll(med);
        stoerungen.getCheckModel().clearChecks();
        syndrome.getCheckModel().clearChecks();
        verdacht.getCheckModel().clearChecks();
        checkStoerungen();
        checkSyndrome();
        checkMed();
    }

    @FXML
    private void save(ActionEvent event){
        Anamnese a = new Anamnese();
        if(getUser().role== Role.pflege){
            a=getPatient().anamnese;
            a.groesse=Integer.parseInt(groesse.getText());
            a.gewicht=Double.parseDouble(gewicht.getText());
            dbConnector.setPatientAnamnese(a,getPatient().patientID,getUser());
        } else {
            a.groesse=Integer.parseInt(groesse.getText());
            a.gewicht=Double.parseDouble(gewicht.getText());
            a.behinderung=behinderung.isSelected();
            a.grad=Double.parseDouble(behinderungsGrad.getText());
            a.chronischeKrankheiten=chronErkrakungen.getText();
            a.adipositasMedikamente=getAdiMed();
            a.adipositasSyndrome=getAdiSyn();
            a.endokrinologisch=getEndo();
            dbConnector.setPatientAnamnese(a, getPatient().patientID, getUser());
        }
    }

    private Endokrinologisch getEndo() {
        Endokrinologisch e = new Endokrinologisch();
        ObservableList<Integer> list = stoerungen.getCheckModel().getCheckedIndices();
        e.STH_Mangel=list.contains(8);
        e.prim??rerHyperinsulinismusWiedemann_Beckwith=list.contains(7);
        e.POMC_Mutationen=list.contains(6);
        e.MC4_Rezeptormutationen=list.contains(5);
        e.Leptirezeptormutation=list.contains(4);
        e.Kraniopharyngeom=list.contains(3);
        e.genetisch_bedingter_Leptinmangel=list.contains(2);
        e.Cushing_Syndrom=list.contains(1);
        e.AdipositasBeiHypothyreose=list.contains(0);
        e.Morbus_Cushing=list.contains(9);
        return e;
    }

    private AdipositasSyndrome getAdiSyn() {
        AdipositasSyndrome a = new AdipositasSyndrome();
        ObservableList<Integer> list = syndrome.getCheckModel().getCheckedIndices();
        a.Weaver = list.contains(5);
        a.Trisomie_21 = list.contains(4);
        a.Sotos = list.contains(3);
        a.Simpson_Golabi_Behmel = list.contains(2);
        a.Prader_Willi= list.contains(1);
        a.Laurence_Moon_Bardet_Biedel = list.contains(0);
        a.andere = syndromehinzufuegen.getText();
        return a;
    }

    private AdipositasMedikamente getAdiMed(){
        AdipositasMedikamente a = new AdipositasMedikamente();
        ObservableList<Integer> list = verdacht.getCheckModel().getCheckedIndices();
        a.Glukokortikoide=list.contains(0);
        a.Insulingabe=list.contains(1);
        a.Phenothiazine=list.contains(3);
        a.Valproat=list.contains(2);
        a.andere=verdachthinzufuegen.getText();
        return a;
    }

    private void checkMed(){
        ArrayList<Integer> a =new ArrayList<>();
        if(getPatient().anamnese.adipositasMedikamente.Glukokortikoide){
            a.add(0);
        }
        if(getPatient().anamnese.adipositasMedikamente.Insulingabe){
            a.add(1);
        }
        if(getPatient().anamnese.adipositasMedikamente.Valproat){
            a.add(2);
        }
        if(getPatient().anamnese.adipositasMedikamente.Phenothiazine){
            a.add(3);
        }
        verdacht.getCheckModel().checkIndices(a.stream().mapToInt(Integer::intValue).toArray());
    }

    private void checkSyndrome() {
        ArrayList<Integer> a = new ArrayList<>();
        if(getPatient().anamnese.adipositasSyndrome.Laurence_Moon_Bardet_Biedel){
            a.add(0);
        }
        if(getPatient().anamnese.adipositasSyndrome.Prader_Willi){
            a.add(1);
        }
        if(getPatient().anamnese.adipositasSyndrome.Simpson_Golabi_Behmel){
            a.add(2);
        }
        if(getPatient().anamnese.adipositasSyndrome.Sotos){
            a.add(3);
        }
        if(getPatient().anamnese.adipositasSyndrome.Trisomie_21){
            a.add(4);
        }
        if(getPatient().anamnese.adipositasSyndrome.Weaver){
            a.add(5);
        }
        if(a.size()>0)
            syndrome.getCheckModel().checkIndices(a.stream().mapToInt(Integer::intValue).toArray());
    }

    private void checkStoerungen() {
        ArrayList<Integer> st= new ArrayList<>();
        if(getPatient().anamnese.endokrinologisch.AdipositasBeiHypothyreose){
            st.add(0);
        }
        if(getPatient().anamnese.endokrinologisch.Cushing_Syndrom){
           st.add(1);
        }
        if(getPatient().anamnese.endokrinologisch.genetisch_bedingter_Leptinmangel){
            st.add(2);
        }
        if(getPatient().anamnese.endokrinologisch.Kraniopharyngeom){
            st.add(3);
        }
        if(getPatient().anamnese.endokrinologisch.Leptirezeptormutation){
            st.add(4);
        }
        if(getPatient().anamnese.endokrinologisch.MC4_Rezeptormutationen){
            st.add(5);
        }
        if(getPatient().anamnese.endokrinologisch.POMC_Mutationen){
            st.add(6);
        }
        if(getPatient().anamnese.endokrinologisch.prim??rerHyperinsulinismusWiedemann_Beckwith){
            st.add(7);
        }
        if(getPatient().anamnese.endokrinologisch.STH_Mangel){
            st.add(8);
        }
        if(getPatient().anamnese.endokrinologisch.Morbus_Cushing){
            st.add(9);
        }
        if(st.size()>0)
        verdacht.getCheckModel().checkIndices(st.stream().mapToInt(Integer::intValue).toArray());
    }

    @Override
    public void setup(User u, Patient pid) {
        setUser(u);
        setPatient(pid);
        setStammdata();
        groesse.setText(String.valueOf(getPatient().anamnese.groesse));
        gewicht.setText(String.valueOf(getPatient().anamnese.gewicht));
        setupComboBoxes();
        behinderung.setSelected(getPatient().anamnese.behinderung);
        behinderungsGrad.setText(String.valueOf(getPatient().anamnese.grad));
        syndromehinzufuegen.setText(getPatient().anamnese.adipositasSyndrome.andere);
        verdachthinzufuegen.setText(getPatient().anamnese.adipositasMedikamente.andere);
        chronErkrakungen.setText(getPatient().anamnese.chronischeKrankheiten);
    }

    private void setStammdata() {
        nachname.setText(getPatient().nachname);
        vorname.setText(getPatient().vorname);
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
        zimmerNr.setText(getPatient().unterbringung.zimmer);
        einlieferung.setText(getPatient().unterbringung.einlieferung);
        entlassung.setText(getPatient().unterbringung.entlassung);
        patientenID.setText(String.valueOf(getPatient().patientID));
    }
    public void speichern() throws DocumentException, FileNotFoundException {
            Drucken.drucken(getPatient());
    };


    @Override
    public void update(User user){
        System.err.println("Wrong Setup methode for AnamneseTab!");
    }

    @Override
    public void update(User user, Patient pid){
        setup(user,pid);
    }
}
