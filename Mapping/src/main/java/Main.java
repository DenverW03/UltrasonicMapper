package src.main.java;
import javax.swing.*;
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
        testingMap();
    }

    public static void testingMap(){
        int i = 100;
        int j = 0;
        for(j = 0; j<361; j++){
            map.addMeasurement(i, j);
        }
    }
}