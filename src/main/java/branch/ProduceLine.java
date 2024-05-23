package branch;

import java.util.Stack;
import java.util.concurrent.ExecutorService;

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

public class ProduceLine implements Runnable{
    private final Monitor monitor;
    private final String Lfractal;
    private final float angle;
    private double startX, startY;
    private float currentAngle;
    private static ExecutorService executor;

    public ProduceLine(Monitor monitor, String Lfractal, float angle, double startX, double startY) {
        this.monitor = monitor;
        this.Lfractal = Lfractal;
        this.angle = angle;
        this.startX = startX;
        this.startY = startY;
        this.currentAngle=this.angle;
    }

    public ProduceLine(Monitor monitor, String Lfractal, float angle,float currentAngle, double startX, double startY) {
        this.monitor = monitor;
        this.Lfractal = Lfractal;
        this.angle = angle;
        this.startX = startX;
        this.startY = startY;
        this.currentAngle=currentAngle;
    }

    @Override
    public void run() {
        double currentX = startX;
        double currentY = startY;
        //float currentAngle = angle;
        float length = 20;

        for (int i = 0; i < Lfractal.length(); i++) {
            char c = Lfractal.charAt(i);
            if (c == 'F') {
                double endX = currentX + (Math.cos(Math.toRadians(currentAngle)) * length);
                double endY = currentY - (Math.sin(Math.toRadians(currentAngle)) * length);
                monitor.addLine(new Line((int) currentX, (int) currentY, (int) endX, (int) endY));
                System.out.println("Drawing line: (" + startX + ", " + startY + ") -> (" + endX + ", " + endY + ")");
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
                int pop = Branch.ScanPopPush(Lfractal, i);
                String newSubString = Lfractal.substring(pop + 1, Lfractal.length());
                executor.execute(new ProduceLine(monitor, newSubString, this.angle, currentAngle, currentX, currentY));
            } else if(c==']'){
                break;
            }
        }
    }

    public static void setExecutor(ExecutorService e){
        executor=e;
    }
    
}


