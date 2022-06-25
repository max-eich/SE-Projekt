package com.administration.frontend;

import com.administration.Main;
import com.administration.backend.Role;
import com.administration.backend.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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

    public void setup(@NotNull User user){
        setUser(user);
        if(getUser().role==Role.techniker){
            setTechnikerTabs();
        } else {
            setTabs();
        }
    }

    private void setTechnikerTabs() {
        try{
            tabsPane.getTabs().clear();
            Tab t = new Tab();
            tabsPane.getTabs().add(t);
            Tab t1 = new Tab();
            tabsPane.getTabs().add(t1);
            t.setContent((Node) FXMLLoader.load(getClass().getResource("Accont.fxml")));
            t.setText("Account");
            t1.setContent((Node)FXMLLoader.load(getClass().getResource("techniker.fxml")));
            t1.setText("Cards");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTabs(){
            try {
                tabsPane.getTabs().clear();
                Tab t = new Tab();
                tabsPane.getTabs().add(t);
                Tab t1 = new Tab();
                tabsPane.getTabs().add(t1);
                t.setContent((Node) FXMLLoader.load(getClass().getResource("Accont.fxml")));
                t.setText("Account");
                t1.setContent((Node) FXMLLoader.load(getClass().getResource("patientenliste.fxml")));
                t1.setText("Patientensuche");
            } catch(IOException e) {
                e.printStackTrace();
            }
    }



    @FXML
    public void initialize() {}

}
