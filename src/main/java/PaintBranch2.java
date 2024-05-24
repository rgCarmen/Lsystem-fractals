

import javax.swing.*;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class PaintBranch2 extends JPanel {
    private final ConcurrentLinkedQueue<Line> lines;

    public PaintBranch2() {
        setBackground(Color.BLACK);
        this.lines = new ConcurrentLinkedQueue<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        long startTime = System.nanoTime();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.BLACK);
        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.RED);


        
        for (Line l : lines) {
            g2d.drawLine(l.startX, l.startY, l.endX, l.endY);
            //System.out.println("Draw line: (" + l.startX + ", " + l.startY + ") -> (" + l.endX + ", " + l.endY + ")");
        }


        long finalTime= System.nanoTime()-startTime;

        System.out.println("Tiempo transcurrido en dibujar: " + finalTime/1000000 + " ms");

    }

    public void addLine(Line line) {
        lines.add(line);
    }
}