import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PaintBranch extends JPanel {
    private final ConcurrentLinkedQueue<Line> lines;
    private Image offscreenImage;

    public PaintBranch() {
        setBackground(Color.BLACK);
        this.lines = new ConcurrentLinkedQueue<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        long startTime = System.nanoTime();

        // Double buffering: create offscreen image
        if (offscreenImage == null || offscreenImage.getWidth(null) != getWidth() || offscreenImage.getHeight(null) != getHeight()) {
            offscreenImage = createImage(getWidth(), getHeight());
        }

        // Get the offscreen graphics context
        Graphics offscreenGraphics = offscreenImage.getGraphics();

        // Clear the offscreen image
        offscreenGraphics.setColor(getBackground());
        offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());

        // Draw onto the offscreen image
        drawLines(offscreenGraphics);

        // Draw the offscreen image onto the panel
        g.drawImage(offscreenImage, 0, 0, null);

        long finalTime = System.nanoTime() - startTime;
        System.out.println("Tiempo transcurrido en dibujar: " + finalTime / 1000000 + " ms");
    }

    private void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);

        for (Line l : lines) {
            g2d.drawLine(l.startX, l.startY, l.endX, l.endY);
        }
    }

    public void addLine(Line line) {
        lines.add(line);
        // Trigger repaint to update the view
        repaint();
    }
}

