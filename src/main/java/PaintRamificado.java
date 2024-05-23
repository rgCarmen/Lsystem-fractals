import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Branch {
    public static int ScanPopPush(String system, int index) {
        Stack<Integer> stack = new Stack<>();
        int pop = -1;

        for (int i = 0; i < system.length(); i++) {
            if (system.charAt(i) == '[') {
                stack.push(i);
            } else if (system.charAt(i) == ']') {
                if (stack.pop() == index) {
                    pop = i;
                    break;
                }

            }
        }
        return pop;
    }

    public static int ScanNextPush(String system, int index) {
        return system.indexOf(']', index + 1);
    }

}

class BranchTask implements Runnable {
    private String subString;
    private double startX;
    private double startY;
    private float angle;
    private double length;
    private int weight;
    private int height;
    private static ExecutorService executor;
    private BufferedImage image;

    public BranchTask(String s, double x, double y, float a, double l, int w, int h) {
        subString = s;
        startX = x;
        startY = y;
        angle = a;
        length = l;
        weight = w;
        height = h;
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        // executor = e;
        // g2d = g;
    }

    public static void setUp(ExecutorService e, Graphics2D g) {
        executor = e;
    }

    @Override
    public void run() {
        // Crear un JPanel temporal para dibujar la rama (o pasar el Graphics2D)

        double currentX = startX;
        double currentY = startY;
        float currentAngle = angle;
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.black);

        int i = 0;
        while (i < subString.length()) {
            char c = subString.charAt(i);
            if (c == 'F') {
                double endX = currentX + (Math.cos(Math.toRadians(currentAngle)) * length);
                double endY = currentY - (Math.sin(Math.toRadians(currentAngle)) * length);
                g2d.drawLine((int) currentX, (int) currentY, (int) endX, (int) endY);
                currentX = endX;
                currentY = endY;
            } else if (c == 'f') {
                currentX = currentX + (Math.cos(Math.toRadians(currentAngle)) * length);
                currentY = currentY - (Math.sin(Math.toRadians(currentAngle)) * length);
            } else if (c == '+') {
                currentAngle += this.angle;
            } else if (c == '-') {
                currentAngle -= this.angle;
            } else if (c == '[') {
                int pop = Branch.ScanPopPush(subString, i);
                String newSubString = subString.substring(pop + 1, subString.length());
                executor.execute(
                        new BranchTask(newSubString, currentX, currentY, currentAngle, length, weight, height));

            } else if (c == ']') {
                g2d.dispose();
                break; // Terminar la rama actual

            }

            i++;
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}

public class PaintRamificado extends JPanel {
    public String Lfractal;
    public float angle;
    public ExecutorService executor;

    public PaintRamificado(String f, float a) {
        Lfractal = f;
        angle = a;
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.BLACK);
        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.RED);

        double startX = getWidth() / 2;
        double startY = getHeight() / 2;
        double length = 20;
        float angle = this.angle;

        BranchTask.setUp(executor, g2d);

        executor.execute(new BranchTask(Lfractal, startX, startY, angle, length, getWidth(), getHeight()));

        
        
    }

    public static void main(String[] args) {
        String s = "F[+F]F[+F[+F]F]F[+F]F[+F[+F]F[+F[+F]F]F[+F]F]F[+F]F[+F[+F]F]F[+F]F";

        JFrame frame = new JFrame("L-Systems Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        PaintRamificado demo = new PaintRamificado(s, 10);
        frame.getContentPane().add(demo);
        frame.pack();
        frame.setVisible(true);

    }
}
