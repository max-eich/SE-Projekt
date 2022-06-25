package com.administration.backend;

import com.administration.Main;
import com.administration.frontend.BasicController;
import com.administration.frontend.OriginPaneController;
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


public class Listener {
    private final SerialPort serialPort = SerialPort.getCommPort("COM10");

    private LoginThread thread;
    private EventListener eventListener = new EventListener();

    public Listener(){
        thread = new LoginThread("PortListener", eventListener);

    }

    public void start() {
        serialPort.openPort();
        serialPort.addDataListener(eventListener);
        thread.startThread();
    }

    public void disconnect() {
        thread.stopThread();
        if (serialPort != null) {
            serialPort.removeDataListener();
            serialPort.closePort();
        }
    }


}


