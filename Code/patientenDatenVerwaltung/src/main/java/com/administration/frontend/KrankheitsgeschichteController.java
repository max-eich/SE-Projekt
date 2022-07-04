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

import com.administration.backend.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Cell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;


public class KrankheitsgeschichteController extends BasicTabController {

    @FXML
    private JFXButton arzt;
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

    @FXML
    private void save(ActionEvent event) {
        if(data.get(0).get(1)!= "" && data.get(0).get(3)!=""){
            Krankheit k = new Krankheit();
            k.type=Type.valueOf(data.get(0).get(1).toString());
            k.icd10=data.get(0).get(2).toString();
            k.beschreibung=data.get(0).get(3).toString();
            if(k.type!=Type.k) {
                if (getUser().role == Role.arzt) {
                    k.arzt=getUser().name;
                } else {
                    k.arzt=arztEingabe.getText();
                }
            }
            dbConnector.setPatientKrankheit(k,getPatient().patientID, getUser());
            Role r;
            if(arztEingabe.getText()!= null && !arztEingabe.getText().equals(""))
                r = Role.arzt;
            else
                r=getUser().role;
            Patient p = dbConnector.getPatient(r,getPatient().patientID);
            update(p,r);
        }
    }

    private void update(Patient p,Role r){
        setPatient(p);
        if(r== Role.arzt)
            typTabelle.setCellFactory(ChoiceBoxTableCell.forTableColumn("d","k","b"));
        if(r==Role.pflege)
            typTabelle.setCellFactory(ChoiceBoxTableCell.forTableColumn("k"));
        data.clear();
        ObservableList<String> s1 = FXCollections.observableArrayList();
        for (int i=0; i<5;i++)
            s1.add("");
        data.add(s1);
        getPatient().krankheits.forEach(krankheit -> {
            ObservableList<String>s = FXCollections.observableArrayList();
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            s.add(df.format(krankheit.erstellung));
            s.add(krankheit.type.toString());
            s.add(krankheit.icd10);
            s.add(krankheit.beschreibung);
            s.add(krankheit.arzt);
            data.add(s);
        });
    }

    @FXML
    private void editType(TableColumn.CellEditEvent event){
        if(event.getTablePosition().getRow()==0){
            data.get(0).set(1,event.getNewValue().toString());
        }
    }

    @FXML
    private void edit(TableColumn.CellEditEvent event){
        if(event.getTablePosition().getRow()==0){
            data.get(0).set(event.getTablePosition().getColumn(),event.getNewValue().toString());
        }
    }

    @Override
    public void setup(User u) {
        System.err.println("Wrong Setup methode for AnamneseTab!");
    }

    @Override
    public void setup(User u, Patient pid) {
        setUser(u);
        setPatient(pid);
        if(getUser().role==Role.arzt){
            arztEingabe.setVisible(false);
            arzt.setVisible(false);
            drucken.setVisible(true);
        } else if(getUser().role == Role.pflege){
            arzt.setVisible(true);
            arztEingabe.setVisible(true);
            drucken.setVisible(false);
        }
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
        if(getUser().role== Role.arzt)
        typTabelle.setCellFactory(ChoiceBoxTableCell.forTableColumn("d","k","b"));
        if(getUser().role==Role.pflege)
            typTabelle.setCellFactory(ChoiceBoxTableCell.forTableColumn("k"));
        icdTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(2).toString());
            }
        });
        icdTabelle.setCellFactory(TextFieldTableCell.forTableColumn());
        beschreibungTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(3).toString());
            }
        });
        beschreibungTabelle.setCellFactory(TextFieldTableCell.forTableColumn());
        arztTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(4).toString());
            }
        });
        ObservableList<String>s1 = FXCollections.observableArrayList();
        getPatient().krankheits=dbConnector.getKrankheiten(getUser().role,getPatient().patientID);
        for (int i=0; i<5;i++)
            s1.add("");
        data.add(s1);
        getPatient().krankheits.forEach(krankheit -> {
            ObservableList<String>s = FXCollections.observableArrayList();
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            s.add(df.format(krankheit.erstellung));
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

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    @FXML
    private void setArzt(ActionEvent event){
        if(arztEingabe.getText()!= null && arztEingabe.getText()!=""){
            drucken.setVisible(true);
            getPatient().krankheits = dbConnector.getKrankheiten(Role.arzt,getPatient().patientID);
            data.clear();
            typTabelle.setCellFactory(ChoiceBoxTableCell.forTableColumn("d","k","b"));
            ObservableList<String>s1 = FXCollections.observableArrayList();
            for (int i=0; i<5;i++)
                s1.add("");
            data.add(s1);
            getPatient().krankheits.forEach(krankheit -> {
                ObservableList<String>s = FXCollections.observableArrayList();
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                s.add(df.format(krankheit.erstellung));
                s.add(krankheit.type.toString());
                s.add(krankheit.icd10);
                s.add(krankheit.beschreibung);
                s.add(krankheit.arzt);
                data.add(s);
            });

        }
    }

}
