/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.administration.frontend;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

/**
 *
 * @author pc
 */
public class TechnikerController extends BasicTabController {

    public JFXTextField suchfeld;
    public JFXButton suche;
    public TableColumn chipNrTabelle;
    public TableColumn benutzerTabelle;
    public TableColumn rolleTabelle;
    public TableColumn statusTabelle;
    public TableView tableView;
    public JFXButton speichern;

    private ObservableList<ObservableList> data = FXCollections.observableArrayList();
    private ObservableList<Integer> indexes=FXCollections.observableArrayList();
    
    @FXML
    private void save(ActionEvent event) {
        indexes.forEach(index ->{
            CardInfo card = new CardInfo();
            card.cardID = Integer.parseInt(data.get(index).get(0).toString());
            card.userName = data.get(index).get(1).toString();
            card.role = Role.valueOf(data.get(index).get(2).toString());
            card.status=data.get(index).get(3).toString();
            card.ref=Integer.parseInt(data.get(index).get(4).toString());
            System.out.println(card.role);
            dbConnector.setCardInfo(card, getUser());
        });
    }

    @FXML
    private void edit(TableColumn.CellEditEvent event){
        if(!indexes.contains(event.getTablePosition().getRow())){
            indexes.add(event.getTablePosition().getRow());
        }
        data.get(event.getTablePosition().getRow()).set(event.getTablePosition().getColumn(),event.getNewValue());
    }

    @FXML
    private void editStatus(TableColumn.CellEditEvent event){
        if(!indexes.contains(event.getTablePosition().getRow())){
            indexes.add(event.getTablePosition().getRow());
        }
        data.get(event.getTablePosition().getRow()).set(event.getTablePosition().getColumn(),event.getNewValue());
    }

    @FXML
    private void editRole(TableColumn.CellEditEvent event){
        if(!indexes.contains(event.getTablePosition().getRow())){
            indexes.add(event.getTablePosition().getRow());
        }
        data.get(event.getTablePosition().getRow()).set(event.getTablePosition().getColumn(),event.getNewValue());
    }

    @FXML
    private void search(ActionEvent event) {
        if(suchfeld.getText().equals("free")){
            data.clear();
            ArrayList<CardInfo> c = dbConnector.getFreeCards(getUser());
            c.forEach(card ->{
                ObservableList<String> s = FXCollections.observableArrayList();
                s.add(String.valueOf(card.cardID));
                s.add(card.userName);
                s.add("");
                s.add(card.status);
                data.add(s);
            });
            tableView.getItems().clear();
            tableView.getItems().addAll(data);
        } else if(suchfeld.getText().equals("")){
            data.clear();
            ArrayList<CardInfo> c = dbConnector.getCards(getUser());
            c.forEach(card ->{
                ObservableList<String> s = FXCollections.observableArrayList();
                s.add(String.valueOf(card.cardID));
                s.add(card.userName);
                if(card.role!=null){
                    s.add(card.role.toString());}
                else{
                    s.add("");}
                s.add(card.status);
                s.add(String.valueOf(card.ref));
                data.add(s);
            });
            tableView.getItems().clear();
            tableView.getItems().addAll(data);
        } else {
            data.clear();
            ArrayList<CardInfo> c = dbConnector.getCards(suchfeld.getText(),getUser());
            c.forEach(card ->{
                ObservableList<String> s = FXCollections.observableArrayList();
                s.add(String.valueOf(card.cardID));
                s.add(card.userName);
                if(card.role!=null){
                    s.add(card.role.toString());}
                else{
                    s.add("");}
                s.add(card.status);
                s.add(String.valueOf(card.ref));
                data.add(s);
            });
            tableView.getItems().clear();
            tableView.getItems().addAll(data);
        }
    }

    @Override
    public void setup(User u) {
        setUser(u);
        ObservableList<String>s1=FXCollections.observableArrayList("","","","");
        data.add(s1);
        chipNrTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(0).toString());
            }
        });
        benutzerTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(1).toString());
            }
        });
        benutzerTabelle.setCellFactory(TextFieldTableCell.forTableColumn());

        rolleTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(2).toString());
            }
        });
        rolleTabelle.setCellFactory(ChoiceBoxTableCell.forTableColumn("arzt","pflege","personal","admin","techniker"));
        statusTabelle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(3).toString());
            }
        });
        statusTabelle.setCellFactory(ChoiceBoxTableCell.forTableColumn("free","ok","lost"));
        ArrayList<CardInfo> cards = dbConnector.getCards(getUser());
        data.clear();
        cards.forEach(card->{
            ObservableList<String> s = FXCollections.observableArrayList();
            s.add(String.valueOf(card.cardID));
            s.add(card.userName);
            s.add(card.role.toString());
            s.add(card.status);
            s.add(String.valueOf(card.ref));
            data.add(s);
        });
        tableView.getItems().addAll(data);
    }

    @Override
    public void setup(User u, Patient pid) {
        setup(u);
    }
}
