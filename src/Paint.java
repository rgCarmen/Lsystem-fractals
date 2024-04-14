import javax.swing.*;
import java.awt.*;

public class Paint extends JPanel {
    public String Lfractal;
    public int angle;


    public Paint(String f, int a){
        Lfractal=f;
        angle=a;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int startX = getWidth() / 2;
        int startY = getHeight()/2;
        int length = 10;
        int angle = this.angle;

        for (int i = 0; i < Lfractal.length(); i++) {
            char c = Lfractal.charAt(i);
            if (c == 'F') {
                int endX = startX + (int) (Math.cos(Math.toRadians(angle)) * length);
                int endY = startY - (int) (Math.sin(Math.toRadians(angle)) * length);
                g.drawLine(startX, startY, endX, endY);
                startX = endX;
                startY = endY;
            } else if (c == '+') {
                angle += this.angle;
            } else if (c == '-') {
                angle -= this.angle;
            }
        }
    }
    
}
