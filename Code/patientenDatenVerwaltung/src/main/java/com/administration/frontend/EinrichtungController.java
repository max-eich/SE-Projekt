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

import com.administration.backend.Einrichtungen;
import com.administration.backend.Patient;
import com.administration.backend.User;
import com.administration.backend.dbConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class EinrichtungController extends BasicTabController {

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
    private TableView tableview;
    @FXML
    private TableColumn nameTabelle;
    @FXML
    private TableColumn adresseTabelle;
    @FXML
    private TableColumn arztTabelle;
    @FXML
    private TableColumn telTabelle;
    @FXML
    private JFXButton speichern;

    private ObservableList<ObservableList> data;
    private ObservableList<Integer> indexes = FXCollections.observableArrayList();

    @FXML
    private void save(ActionEvent event){
        indexes.forEach(integer -> {
            Einrichtungen e = new Einrichtungen();
            e.name= data.get(integer).get(0).toString();
            e.adresse=data.get(integer).get(1).toString();
            e.telefonnummer=data.get(integer).get(2).toString();
            e.art=data.get(integer).get(3).toString();
            if(data.get(integer).get(4).toString()=="0"){
                e.referenceID=dbConnector.getNewEinrichtungsID(getPatient().patientID);
            } else {
                e.referenceID=Integer.parseInt(data.get(integer).get(4).toString());
            }
            dbConnector.setPatientEinrichtung(e,getPatient().patientID,getUser());
        });
    }

    @FXML
    private void edit(TableColumn.CellEditEvent event){
        data.get(event.getTablePosition().getRow()).set(event.getTablePosition().getColumn(),event.getNewValue());
        if(!indexes.contains(Integer.valueOf(event.getTablePosition().getRow()))){
            indexes.add(Integer.valueOf(event.getTablePosition().getRow()));
        }
    }

    @Override
    public void setup(User u) {
        System.err.println("Wrong Setup methode for AnamneseTab!");
    }

    @Override
    public void setup(User u, Patient pid) {
        setUser(u);
        setPatient( pid);
        data = FXCollections.observableArrayList();
        nameTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(0).toString());
            }
        });
        nameTabelle.setCellFactory(TextFieldTableCell.forTableColumn());
        adresseTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(1).toString());
            }
        });
        adresseTabelle.setCellFactory(TextFieldTableCell.forTableColumn());
        arztTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(3).toString());
            }
        });
        arztTabelle.setCellFactory(TextFieldTableCell.forTableColumn());
        telTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(2).toString());
            }
        });
        telTabelle.setCellFactory(TextFieldTableCell.forTableColumn());
        ObservableList<String>s1 = FXCollections.observableArrayList();
        for (int i=0; i<4;i++)
            s1.add("");
        s1.add("0");
        data.add(s1);
        getPatient().einrichtungen.forEach(einrichtungen -> {
            ObservableList<String>s=FXCollections.observableArrayList();
            s.add(einrichtungen.name);
            s.add(einrichtungen.adresse);
            s.add(einrichtungen.telefonnummer);
            s.add(einrichtungen.art);
            s.add(String.valueOf(einrichtungen.referenceID));
            data.add(s);
        });
        tableview.setItems(data);
        setStammdata();
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

    private LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

}
