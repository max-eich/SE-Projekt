xypackage de.jon;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

public class Main {

    private SerialPort serialPort = SerialPort.getCommPort("COM10");

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        serialPort.openPort();
        //serialPort.setBaudRate(9600);

        serialPort.addDataListener(new EventListener());
    }

    public static class EventListener implements SerialPortMessageListener {

        @Override
        public byte[] getMessageDelimiter() {
            return new byte[]{(byte)0x0A};
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
            System.out.println(new String(message));
            String mes  = new String(message);
            System.out.println(mes);
        }
    }
}