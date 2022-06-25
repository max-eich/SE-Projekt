package com.administration.backend;

import com.administration.Main;
import com.administration.frontend.BasicController;
import com.administration.frontend.OriginPaneController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class LoginThread implements Runnable{
    private String name;
    private boolean exitThread;
    Thread thread;

    private EventListener eventListener=null;

    public LoginThread(String name, EventListener event) {
        this.name = name;
        eventListener=event;
        thread = new Thread(this, name);
        System.out.println("Created Thread: " + thread);
        exitThread = false;
        thread.setDaemon(true);
    }

    public void startThread(){
        try {
            thread.start();
        } catch (IllegalThreadStateException ex){
            ex.printStackTrace();
        } finally {
            System.out.println("Thread "+thread+" started.");
        }

    }

    @Override
    public void run() {

        while (!exitThread) {
            //System.out.println(name + " is running");
            if (eventListener.getMes() != null && !eventListener.getMes().equals("")) {
                var user = dbConnector.checkCard(eventListener.getMes());
                if (user.name != null) {
                    Platform.runLater(() -> {
                        try {
                            Window open = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
                            Stage current = (Stage) open;
                            User u = ((BasicController) ((FXMLLoader) current.getUserData()).getController()).getUser();
                            if (u.name == null) {
                                FXMLLoader loader = new FXMLLoader(Main.class.getResource("frontend/originPane.fxml"));
                                Parent root = loader.load();
                                ((OriginPaneController) loader.getController()).setup(user);
                                var stage = new Stage();
                                stage.setResizable(false);
                                stage.setTitle("Patientenverwaltung");
                                stage.setScene(new Scene(root));
                                stage.setUserData(loader);
                                stage.show();
                                Stage.getWindows().stream().filter(foundStage -> ((Stage) foundStage).getTitle().equalsIgnoreCase("Login")).forEach(window -> ((Stage) window).close());
                            } else {
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                }
            }
            eventListener.setMes("");
            try {
                Thread.sleep(1000);
            } catch (IllegalArgumentException | InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println(name + " has been Stopped.");

    }

    public void stopThread() {
        exitThread = true;
    }
}

