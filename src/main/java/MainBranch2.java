
import javax.swing.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainBranch2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Map<Character, String> rule = new HashMap<>();
        rule.put('F', "F[+F]F");
        LSystem f = new LSystem("F", 10, rule);


        f.setIteration(12);
        String s = f.getFractalParallelIter();

        double startX = 800 / 2;
        double startY = 600 / 2;

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ProduceLine2.setExecutor(executor);

        SwingUtilities.invokeLater(() -> {
        float time= System.nanoTime();
        JFrame frame = new JFrame("L-Systems Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.setSize(800, 600);
        PaintBranch2 paintBranch = new PaintBranch2();

        ProduceLine2 producer = new ProduceLine2(paintBranch, s, (float) 10, startX, startY);
        executor.execute(producer);

        // Timer para refrescar el panel periódicamente
        frame.getContentPane().add(paintBranch);

        frame.setVisible(true);
        System.out.println((System.nanoTime()-time)/1000000);
    });
        // TimeUnit.SECONDS.sleep(1000000);
        // executor.shutdown();
        // executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

    }
}