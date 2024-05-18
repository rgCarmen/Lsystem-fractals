import javax.swing.*;
import java.awt.*;
import java.util.Stack;

class PointState {
    public int x;
    public int y;
    public float angle;
    

    public PointState(int x, int y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
}

public class Paint extends JPanel {
    public String Lfractal;
    public float angle;
    private Stack<PointState> stateStack;


    public Paint(String f, float a){
        Lfractal=f;
        angle=a;
        stateStack= new Stack<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.BLACK);
        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.RED);

        double startX = getWidth()/2 ;
        double startY = getHeight()/2;
        double length = 10;
        float angle = this.angle;

        for (int i = 0; i < Lfractal.length(); i++) {
            char c = Lfractal.charAt(i);
            if (c == 'F') {
                double endX = startX +  (Math.cos(Math.toRadians(angle)) * length);
                double endY = startY -(Math.sin(Math.toRadians(angle)) * length); 
                g2d.drawLine( (int) startX, (int) startY, (int) endX, (int) endY);
                startX = endX;
                startY = endY;
            } else if (c=='f'){
                startX= startX +  (Math.cos(Math.toRadians(angle)) * length);
                startY = startY -(Math.sin(Math.toRadians(angle)) * length); 
            }else if (c == '+') {
                angle += this.angle;
            } else if (c == '-') {
                angle -= this.angle;
            }else if (c == '[') {
                    // Guardar el estado actual en la pila
                    stateStack.push(new PointState((int) startX, (int) startY, angle));
                } else if (c == ']') {
                    // Restaurar el estado anterior desde la pila
                    if (!stateStack.isEmpty()) {
                        PointState state = stateStack.pop();
                        startX = state.x;
                        startY = state.y;
                        angle = state.angle;
                    }
                }
        }
    }

    
}
