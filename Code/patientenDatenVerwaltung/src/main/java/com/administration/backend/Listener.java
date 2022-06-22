package com.administration.backend;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;


public class Listener {
    private SerialPort serialPort = SerialPort.getCommPort("COM10");

    public void start() {
        serialPort.openPort();
        dbConnector dbConnector1 = new dbConnector();
        User user = new User();
        //serialPort.setBaudRate(9600);
        EventListener eventListener = new EventListener();
        serialPort.addDataListener(eventListener);

        while (true) {
            if (eventListener.getMes() != "") {

                user = dbConnector1.checkCard(eventListener.getMes());
                System.out.println(eventListener.getMes());
                if (user != null) {
                    System.out.println(user.name);
                }
            }
            eventListener.setMes("");
            try {
                Thread.sleep(500);
            } catch (IllegalArgumentException E) {
                System.out.println(E);
            } catch (InterruptedException EX) {
                System.out.println(EX);
            }
        }

    }


}


