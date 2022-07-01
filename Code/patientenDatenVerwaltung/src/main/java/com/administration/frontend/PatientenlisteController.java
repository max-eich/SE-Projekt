/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import com.administration.backend.User;
import com.administration.backend.dbConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class PatientenlisteController extends BasicTabController {


    @FXML
    private TableView tablePatient;
    @FXML
    private TableColumn patientenID;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn geschlecht;
    @FXML
    private TableColumn geburtsdatum;
    @FXML
    private TableColumn zimmerNr;
    @FXML
    private DatePicker geburtstagSuchen;
    @FXML
    private JFXTextField nameSuchen;
    @FXML
    private FontAwesomeIconView nameSuchknopf;
    @FXML
    private JFXTextField ZimmerNrSuchen;
    @FXML
    private FontAwesomeIconView zimmerNrSuchknopf;
    @FXML
    private JFXButton suchen;
    @FXML
    private JFXButton neuenPatientAnlegen;

    private ObservableList<ObservableList> data= FXCollections.observableArrayList();

    @FXML
    private void search(ActionEvent event){
        ((OriginPaneController)((FXMLLoader)((Stage)((Node)event.getSource()).getScene().getWindow()).getUserData()).getController()).selectPatient(1);
    }

    @Override
    public void setup(User u) {
        setUser(u);
        tablePatient.setRowFactory(tv->{
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() ) {
                    var rowData = row.getItem();
                    ((OriginPaneController)((FXMLLoader)row.getScene().getWindow().getUserData()).getController()).selectPatient(Integer.parseInt(((ObservableList<String>)rowData).get(0)));
                }
            });
            return row ;
        });
        patientenID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(0).toString());
            }
        });
        name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(1).toString());
            }
        });
        geschlecht.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(2).toString());
            }
        });
        geburtsdatum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(3).toString());
            }
        });
        zimmerNr.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(4).toString());
            }
        });
        dbConnector.getPatientlist(getUser()).forEach(shortPatient -> {
            ObservableList<String> s = FXCollections.observableArrayList();
            s.add(shortPatient.patientID);
            s.add(shortPatient.name);
            s.add(shortPatient.geschlecht);
            s.add(shortPatient.gebDatum);
            s.add(shortPatient.zimmer);
            data.add(s);
        });
        tablePatient.setItems(data);
    }

    @Override
    public void setup(User u, int pid) {
        setup(u);
    }
}
