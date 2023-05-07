package src.main.java;
import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
        //Scanner scanner = new Scanner(System.in);
        System.out.println("Pick the port to use :)");
        //String input = scanner.nextLine();
        //SerialPort serialPort = SerialPort.getCommPort(input);
        // manually setting just for testing
        SerialPort serialPort = SerialPort.getCommPort("cu.usbmodem1112101");
        serialPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        serialPort.openPort();
        try{Thread.sleep(1000);}catch(Exception e){System.out.println(e);};
        System.out.println("Starting Mapper");
        sendStartMessage(serialPort);
        System.out.println("Reading Data");
        readData(serialPort);
        serialPort.closePort();
    }

    /**
     * Starts a start message to the micro-controller using the active serial port
     */
    public static void sendStartMessage(SerialPort serialPort) {
        try{
            String startMsg = "start";
            byte[] byteMsg = startMsg.getBytes(StandardCharsets.US_ASCII);
            //serialPort.writeBytes(byteMsg, byteMsg.length);
            OutputStream outputStream = serialPort.getOutputStream();
            outputStream.write(byteMsg);
        }
        catch(Exception e){ System.out.println(e); }
    }

    /**
     * This method reads and prints data from a selected serial port
     * @param serialPort
     */
    public static void readData(SerialPort serialPort){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] tokens = line.split("\\+");
                map.addMeasurement(Integer.parseInt(tokens[0]), Double.parseDouble(tokens[1]));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return;
    }

    public static void testingMap(){
        int i = 100;
        Double j = 0.00;
        for(j = 0.00; j<361.00; j++){
            map.addMeasurement(i, j);
        }
    }
}