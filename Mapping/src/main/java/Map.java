package src.main.java;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;
public class Map extends JPanel {
    private ArrayList<Integer> distanceValues;
    private ArrayList<Integer> angleValues;
    private int cx, cy;
    public Map(){
        super();
        this.setPreferredSize(new Dimension(450, 450)); // to fit readings up to 4m
        this.setBackground(Color.BLACK);
        distanceValues = new ArrayList<Integer>();
        angleValues = new ArrayList<Integer>();
        cx = 225;
        cy = 225;
    }

    /**
     * This method is called to add another measurement to the map
     * @param d the distance measured in cm
     * @param a the angle measured in degrees
     */
    public void addMeasurement(int d, int a){
        distanceValues.add(d);
        angleValues.add(a);
        repaint();
    }
    
    /**
     * This method is used to calculate the vector displacement of the point using basic trig
     * @return returns int vector in form [x, y]
     */
    public int[] calcVector(int d, int a){
        double opp = Math.sin((double)((a * Math.PI) / 180)) * ((double)d);
        double adj = Math.sqrt((d * d) - (opp * opp));
        int[] temp = {(int)Math.floor(adj), (int)Math.floor(opp) * -1}; // for if angle is in rotational quadrant 1 or 3
        if(a > 90 && a<= 180){ // if angle is in q2
            temp[0] = (int)Math.floor(opp);
            temp[1] = (int)Math.floor(adj);
        }
        else if(a > 180 && a<= 270){ // if angle is in q3
            temp[0] = (int)Math.floor(adj) * -1;
            temp[1] = (int)Math.floor(opp) * -1;
        }
        else if(a > 270 && a<= 360){ // if angle is in q4
            temp[0] = (int)Math.floor(opp);
            temp[1] = (int)Math.floor(adj) * -1;
        }
        // important to remember that y axis is greater further down the JPanel
        return temp;
    }

    /**
     * Method that draws to the screen
     */
    @Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawRect(225, 0, 0, 450);
        g.drawRect(0, 225, 450, 0);
        g.setColor(Color.WHITE);
        for(int i=0; i<distanceValues.size();i++){
            int[] vector = calcVector(distanceValues.get(i), angleValues.get(i));
            g.fillOval(cx + vector[0], cy + vector[1], 3, 3);
        }
	}
}
