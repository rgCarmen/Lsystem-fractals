import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Main {
    public static void main(String[] args){

        Map<Character,String> rule = new HashMap<>();
        //rule.put('F',"F+F-F-FF+F+F-F");
        //rule.put('F',"FF+F-F+F");
        //LSystem f = new LSystem("F+F+F+F", 90, rule);

        //rule.put('F',"F-FF+FF+F+F-F-FF+F+F-F-FF-FF+F");
        //LSystem f= new LSystem("F+F+F+F", 90, rule);


        //rule.put('F',"FF");
        //rule.put('X', "F-[[X]+X]+F[+FX]-X");
        //LSystem f= new LSystem("X", (float) 22.5, rule);

        rule.put('F',"F[+F]F");
        LSystem f=new LSystem("F", 10, rule);

        //dragon
        //rule.put('l', "l+rF+");
        //rule.put('r', "-Fl-r");
        //LSystem f= new LSystem("Fl", 90, rule);

        //rule.put('F',"FF+F-F+F");
        //LSystem f = new LSystem("F+F+F+F", 60, rule);
        f.iterations(6);
        System.out.println(f.getAxiom());
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("L-Systems Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBackground(Color.BLACK);
            Paint demo = new Paint(f.getAxiom(), f.getAngle());
            //frame.add(demo);
            frame.getContentPane().add(demo);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
