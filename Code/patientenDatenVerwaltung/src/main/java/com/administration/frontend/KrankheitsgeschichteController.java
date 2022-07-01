/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.administration.backend.User;
import com.administration.backend.dbConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;


public class KrankheitsgeschichteController extends BasicTabController {


    @FXML
    private TableView tableView;
    @FXML
    private TableColumn datumTabelle;
    @FXML
    private TableColumn typTabelle;
    @FXML
    private TableColumn icdTabelle;
    @FXML
    private TableColumn beschreibungTabelle;
    @FXML
    private TableColumn arztTabelle;
    @FXML
    private JFXButton speichern;
    @FXML
    private JFXTextField arztEingabe;
    @FXML
    private JFXButton drucken;
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

    private ObservableList<ObservableList> data;

    @Override
    public void setup(User u) {
        System.err.println("Wrong Setup methode for AnamneseTab!");
    }

    @Override
    public void setup(User u, int pid) {
        setUser(u);
        setPid(pid);
        setPatient(dbConnector.getPatient(getUser().role, pid));
        setStammdata();
        data = FXCollections.observableArrayList();
        datumTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(0).toString());
            }
        });
        typTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(1).toString());
            }
        });
        icdTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(2).toString());
            }
        });
        beschreibungTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(3).toString());
            }
        });
        arztTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(4).toString());
            }
        });
        ObservableList<String>s1 = FXCollections.observableArrayList();
        for (int i=0; i<5;i++)
            s1.add("");
        data.add(s1);
        getPatient().krankheits.forEach(krankheit -> {
            ObservableList<String>s = FXCollections.observableArrayList();
            s.add(krankheit.erstellung.toString());
            s.add(krankheit.type.toString());
            s.add(krankheit.icd10);
            s.add(krankheit.beschreibung);
            s.add(krankheit.arzt);
            data.add(s);
        });
        tableView.setItems(data);
    }

    private void setStammdata() {
        nachname.setText(getPatient().nachname);
        vorname.setText(getPatient().vorname);
        geschlecht.setText(getPatient().geschlecht.toString());
        geburtstag.setText(getPatient().geburtsdatum.toString());
        LocalDate g = convertToLocalDate(getPatient().geburtsdatum);
        alter.setText(String.valueOf((Period.between(g, LocalDate.now())).getYears()));
        zimmerNr.setText(getPatient().unterbringung.zimmer);
        einlieferung.setText(getPatient().unterbringung.einlieferung);
        entlassung.setText(getPatient().unterbringung.entlassung);
        patientenID.setText(String.valueOf(getPatient().patientID));
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

}
