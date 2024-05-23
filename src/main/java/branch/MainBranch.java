package branch;
import javax.swing.*;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class MainBranch {

    public static void main(String[] args) throws InterruptedException {
        String s = "F[+F]F[+F[+F]F]F[+F]F[+F[+F]F[+F[+F]F]F[+F]F]F[+F]F[+F[+F]F]F[+F]F";
        //String s= "FF+F-F+FFF+F-F+F+FF+F-F+F-FF+F-F+F+FF+F-F+F+FF+F-F+FFF+F-F+F+FF+F-F+F-FF+F-F+F+FF+F-F+F+FF+F-F+FFF+F-F+F+FF+F-F+F-FF+F-F+F+FF+F-F+F+FF+F-F+FFF+F-F+F+FF+F-F+F-FF+F-F+F+FF+F-F+F";
        //String s="FF+F-F+F+FF+F-F+F+FF+F-F+F+FF+F-F+F";

        Monitor monitor = new Monitor();
        double startX = 800 / 2;
        double startY = 600 / 2;

        ExecutorService executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ProduceLine.setExecutor(executor);

        ProduceLine producer = new ProduceLine(monitor, s, 10, startX, startY);
        executor.submit(producer);



        JFrame frame = new JFrame("L-Systems Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.setSize(800, 600);
        PaintBranch paintBranch = new PaintBranch(monitor);
        // Timer para refrescar el panel periódicamente
        frame.getContentPane().add(paintBranch);
        frame.setVisible(true);

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
    }
}
