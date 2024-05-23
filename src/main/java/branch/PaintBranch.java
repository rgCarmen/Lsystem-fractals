package branch;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PaintBranch extends JPanel {
    private final Monitor monitor;
    private final BlockingQueue<Line> linesToDraw;


    public PaintBranch(Monitor monitor) {
        this.monitor = monitor;
        setBackground(Color.BLACK);
        this.linesToDraw = new LinkedBlockingQueue<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.BLACK);
        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.RED);

/*
        while (!monitor.isEmpty()) {
            Line l = monitor.getLine();
            if (l != null) {
                System.out.println("Drawing line: (" + l.startX + ", " + l.startY + ") -> (" + l.endX + ", " + l.endY + ")");
                g2d.drawLine(l.startX, l.startY, l.endX, l.endY);
            }
        } */
        while (!monitor.isEmpty()) {
            Line l = monitor.getLine();
            if (l != null) {
                linesToDraw.add(l);
            }
        }

        // Draw all lines in the local queue
        int linesDrawn = 0;
        for (Line l : linesToDraw) {
            g2d.drawLine(l.startX, l.startY, l.endX, l.endY);
            linesDrawn++;
        }

        // Print number of lines drawn for debugging
        System.out.println("Lines drawn: " + linesDrawn);
    }
}

