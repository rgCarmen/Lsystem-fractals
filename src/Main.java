import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Main {
    public static void main(String[] args){

        Map<Character,String> rule = new HashMap<>();
        //rule.put('F',"F+F-F-FF+F+F-F");
        rule.put('F',"FF+F-F+F+FF");
        
        LSystem f = new LSystem("F+F+F+F", 90, rule);
        f.iterations(4);
        System.out.println(f.getAxiom());
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("L-Systems Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Paint demo = new Paint(f.getAxiom(), f.getAngle());
            frame.add(demo);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
