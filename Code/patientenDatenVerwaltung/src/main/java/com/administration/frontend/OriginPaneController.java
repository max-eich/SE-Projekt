package com.administration.frontend;

import com.administration.Main;
import com.administration.backend.Patient;
import com.administration.backend.Role;
import com.administration.backend.User;
import com.administration.backend.dbConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class OriginPaneController extends BasicController{

    @FXML
    private Label label;

    @FXML
    private TabPane tabsPane;

    @FXML
    private void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("frontend/FXMLDocument.fxml"));
        Parent root = loader.load();
        var stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.setUserData(loader);
        stage.show();
        Stage.getWindows().stream().filter(foundStage -> ((Stage) foundStage).getTitle().equalsIgnoreCase("Patientenverwaltung")).forEach(window -> ((Stage) window).close());
    }

    public void newPatient(){
        setPatient(new Patient());
        getPatient().patientID=dbConnector.getNewPatientID();
        setPatientTabs();
    }

    public void setup(@NotNull User user){
        setUser(user);
        if(getUser().role==Role.techniker){
            setTechnikerTabs();
        } else {
            setTabs();
        }
    }

    public void selectPatient(int pid){
        setPatient(dbConnector.getPatient(getUser().role,pid));
        setPatientTabs();
    }

    private @NotNull Tab createTab(String url, String tabName){
        Tab t = new Tab();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Node root = loader.load();
            t.setContent(root);
            t.setText(tabName);
            t.setUserData(loader);
        } catch (IOException e){
            e.printStackTrace();
        }
        return t;
    }

    private void setUpTabs(@NotNull ArrayList<Tab> tabs){
        tabs.forEach( tab -> {
            ((BasicTabController)((FXMLLoader)tab.getUserData()).getController()).setup(getUser(),getPatient());
        });
    }


    private void setPatientTabs(){
        label.setText("Patientenverwaltung");
        if(getUser().role.equals(Role.personal)||getUser().role.equals(Role.admin)){
            tabsPane.getTabs().clear();
            ArrayList<Tab> t = new ArrayList<>() ;
            t.add(createTab("Accont.fxml","Account"));
            t.add(createTab("stamdaten.fxml","Stammdaten"));
            t.add(createTab("patientenliste.fxml","Patientensuche"));
            tabsPane.getTabs().addAll(t);
            SingleSelectionModel<Tab> selectionModel = tabsPane.getSelectionModel();
            selectionModel.select(t.get(1));
            setUpTabs(t);
        } else if(getUser().role.equals(Role.arzt)||getUser().role.equals(Role.pflege)){
            tabsPane.getTabs().clear();
            ArrayList<Tab> t = new ArrayList<>();
            t.add(createTab("Accont.fxml","Account"));
            t.add(createTab("Einrichtung.fxml","Einrichtungen"));
            t.add(createTab("stamdaten.fxml","Stammdaten"));
            t.add(createTab("Anamnese.fxml","Anamnese"));
            t.add(createTab("Krankheitsgeschichte.fxml","Krankheitsgeschichte"));
            t.add(createTab("patientenliste.fxml","Patientensuche"));
            tabsPane.getTabs().addAll(t);
            SingleSelectionModel<Tab> selectionModel = tabsPane.getSelectionModel();
            selectionModel.select(t.get(4));
            setUpTabs(t);
        }

    }

    private void setTechnikerTabs() {
        label.setText("Nutzerverwaltung");
        tabsPane.getTabs().clear();
        ArrayList<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Accont.fxml", "Account"));
        tabs.add(createTab("techniker.fxml", "Cards"));
        tabsPane.getTabs().addAll(tabs);
        tabsPane.getSelectionModel().select(tabs.get(1));
        setUpTabs(tabs);
    }

    private void setTabs(){
        label.setText("Patientenverwaltung");
        ArrayList<Tab> t = new ArrayList<>();
                tabsPane.getTabs().clear();
                t.add(createTab("Accont.fxml","Account"));
                t.add(createTab("patientenliste.fxml", "Patientensuche"));
                tabsPane.getTabs().addAll(t);
                tabsPane.getSelectionModel().select(t.get(1));
                setUpTabs(t);
    }



    @FXML
    public void initialize() {}

}
