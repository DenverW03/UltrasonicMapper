package src.main.java;
import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;
import java.util.Scanner;
import java.io.InputStream;
public class Main{
    private static JFrame window;
    private static Map map;
    public static void main(String[] args){
        window = new JFrame("Surroundings Map");
        map = new Map();
        window.add(map);
        window.pack();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        System.out.println("List of serial ports currently in use on this system --v");
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port: ports) {
			System.out.println(port.getSystemPortName());
		}
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick the port to use :)");
        String input = scanner.nextLine();
        SerialPort serialPort = SerialPort.getCommPort(input);
        serialPort.setBaudRate(9600);
        serialPort.openPort();
        readData(serialPort);
        serialPort.closePort();
    }

    public static void readData(SerialPort serialPort){
        try{
            InputStream inputStream = serialPort.getInputStream();
            byte[] buffer = new byte[1024];
            int numRead;
            while((numRead = inputStream.read(buffer)) > 0){
                String data = new String(buffer, 0, numRead);
                System.out.println("Received data: " + data);
            }
        }
        catch(Exception e){
            readData(serialPort);
        }
        return;
    }

    public static void testingMap(){
        int i = 100;
        int j = 0;
        for(j = 0; j<361; j++){
            map.addMeasurement(i, j);
        }
    }
}