package com.administration.backend;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

public class EventListener implements SerialPortMessageListener {
    String mes;

    public String getMes() {
        if(mes!=null && mes.length() >2) {
            return mes.substring(1).replace("\n","").replace("\r","");
        }
        return mes;
    }

    public void setMes(String s) {
        mes = s;
    }

    @Override
    public byte[] getMessageDelimiter() {
        return new byte[]{(byte) 0x0A};
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        var message = serialPortEvent.getReceivedData();
        mes = new String(message);
        //System.out.println(mes);
    }
}

