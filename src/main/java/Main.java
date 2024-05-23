import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Map<Character, String> rule = new HashMap<>();
        //rule.put('F',"F+F-F-FF+F+F-F");

        //prueba
        //rule.put('F',"FF+F-F+F");
        //LSystem f = new LSystem("F+F+F+F", 90, rule);

        // rule.put('F',"F-FF+FF+F+F-F-FF+F+F-F-FF-FF+F");
        // LSystem f= new LSystem("F+F+F+F", 90, rule);

        rule.put('F',"FF");
        rule.put('X', "F-[[X]+X]+F[+FX]-X");
        LSystem f= new LSystem("X", (float) 22.5, rule);

        //rule.put('F', "F[+F]F");
        //LSystem f = new LSystem("F", 10, rule);

        //rule.put('X',"XF+F+XF-F-F-XF-F+F+F-F+F+F-X");
        //LSystem f= new LSystem("XF+F+XF+F+XF+F", 60, rule);

                // dragon
        // rule.put('l', "l+rF+");
        // rule.put('r', "-Fl-r");
        // LSystem f= new LSystem("Fl", 90, rule);

        // rule.put('F',"FF+F-F+F");
        // LSystem f = new LSystem("F+F+F+F", 60, rule);
        
        
        f.setIteration(4);
        double timeStart= System.nanoTime();
        String result= f.getFractal();
        double timeFinal= System.nanoTime()-timeStart;
        System.out.println(timeFinal/1000000+ " ms");

        double timeStart1= System.nanoTime();
        String result1= f.getFractal();
        //ystem.out.println(result1);
        double timeFinal1= System.nanoTime()-timeStart1;
        System.out.println(timeFinal1/1000000+ " ms");
       

        double timeStart2= System.nanoTime();
        String result2= f.getFractalParallel();
        double timeFinal2= System.nanoTime()-timeStart2;
        System.out.println(timeFinal2/1000000+ " ms");

        
        
        //tarda menos pero quizás es porque las hebras ya estan creadas (se ahorra la creación de las nuevas hebras)
        double timeStart3= System.nanoTime();
        String result3= f.getFractalParallel2();
        double timeFinal3= System.nanoTime()-timeStart3;
        System.out.println(timeFinal3/1000000+ " ms");

        double timeStart4= System.nanoTime();
        String result4= f.getFractalParallelIter();
        //System.out.println(result4);
        double timeFinal4= System.nanoTime()-timeStart4;
        System.out.println(timeFinal4/1000000+ " ms");



        if(result.equals(result2)){
            System.out.println("perfe");
        }

        if(result.equals(result4)){
            System.out.println("perfe iter");
        }

        if(result.equals(result3)){
            System.out.println("perfe 2");
        }


       


        
         
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("L-Systems Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBackground(Color.BLACK);
            Paint demo = new Paint(result2, f.getAngle());
            // frame.add(demo);
            frame.getContentPane().add(demo);
            frame.pack();
            frame.setVisible(true);
        });

        /*  SwingUtilities.invokeLater(() -> {
            JFrame frame2 = new JFrame("L-Systems Demo");
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.setBackground(Color.BLACK);
            Paint demo2 = new Paint(result2, f.getAngle());
            // frame.add(demo);
            frame2.getContentPane().add(demo2);
            frame2.pack();
            frame2.setVisible(true);
        });*/
    }

}
