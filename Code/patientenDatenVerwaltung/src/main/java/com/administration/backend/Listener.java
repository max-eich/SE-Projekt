package com.administration.backend;

import com.administration.Main;
import com.administration.frontend.OriginPane;
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Listener {
    private final SerialPort serialPort = SerialPort.getCommPort("COM10");

    public void start() {
        serialPort.openPort();
        EventListener eventListener = new EventListener();
        serialPort.addDataListener(eventListener);

        var thread = new Thread(() -> {
            while (true) {
                if (eventListener.getMes() != null && !eventListener.getMes().equals("")) {
                    var user = dbConnector.checkCard(eventListener.getMes());
                    if (user.name != null) {
                        Platform.runLater(() -> {
                            try {
                                var loader = new FXMLLoader(Main.class.getResource("frontend/originPane.fxml"));
                                var root = (Parent) loader.load();
                                ((OriginPane) loader.getController()).setUser(user);
                                var stage = new Stage();
                                stage.setResizable(false);
                                stage.setTitle("Patientenverwaltung");
                                stage.setScene(new Scene(root));
                                stage.show();
                                Main.stages.remove(stage);
                                Main.stages.add(stage);
                                Main.stages.stream().filter(foundStage -> foundStage.getTitle().equalsIgnoreCase("Login")).findFirst().orElse(null).close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                    } else if(eventListener.getMes().equals("ard read previously.")){
                        System.out.println("second");
                        Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(Main.class.getResource("frontend/FXMLDocument.fxml"));
                            Parent root = (Parent) loader.load();
                            var stage = new Stage();
                            stage.setResizable(false);
                            stage.setTitle("Login");
                            stage.setScene(new Scene(root));
                            stage.show();
                            Main.stages.stream().filter(foundStage -> foundStage.getTitle().equalsIgnoreCase("Patientenverwaltung")).findFirst().orElse(null).close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }});
                    }
                }
                eventListener.setMes("");
                try {
                    Thread.sleep(500);
                } catch (IllegalArgumentException | InterruptedException e) {
                    System.out.println(e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void disconnect() {
        if (serialPort != null) {
            serialPort.removeDataListener();
            serialPort.closePort();
        }
    }


}


