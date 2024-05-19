
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.Color;
import java.awt.BasicStroke;

public class Estadisticas extends ApplicationFrame {

    public Estadisticas(String title) {
        super(title);
    }

    public XYSeriesCollection obtenerTiempo(LSystem l) throws InterruptedException, ExecutionException {
        int[] iterations = { 1, 2, 5, 8 }; // Example iteration values
        int numRuns = 3; // Number of runs to average

        XYSeries dataset1 = new XYSeries("Secuencial");
        XYSeries dataset2 = new XYSeries("Parallel");
        XYSeries dataset3 = new XYSeries("Parallel2");

        for (int iter : iterations) {
            l.setIteration(iter);

            double totalTimeSequential = 0;
            double totalTimeParallel = 0;
            double totalTimeParallel2 = 0;

            for (int i = 0; i < numRuns; i++) {
                // Measure time for sequential fractal generation
                double timeStart = System.nanoTime();
                l.getFractal();
                double timeFinal = System.nanoTime() - timeStart;
                totalTimeSequential += timeFinal;

                // Measure time for parallel fractal generation
                double timeStart2 = System.nanoTime();
                l.getFractalParallel();
                double timeFinal2 = System.nanoTime() - timeStart2;
                totalTimeParallel += timeFinal2;

                double timeStart3 = System.nanoTime();
                l.getFractalParallel2();
                double timeFinal3 = System.nanoTime() - timeStart3;
                totalTimeParallel2 += timeFinal3;
            }

            double averageTimeSequential = (totalTimeSequential / numRuns) / 1000000;
            double averageTimeParallel = (totalTimeParallel / numRuns) / 1000000;
            double averageTimeParallel2 = (totalTimeParallel2 / numRuns) / 1000000;

            dataset1.add(iter,averageTimeSequential);
            dataset2.add(iter, averageTimeParallel);
            dataset3.add(iter, averageTimeParallel2);

        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(dataset1);
        dataset.addSeries(dataset2);
        dataset.addSeries(dataset3);
        return dataset;
    }

    public void plotResults(LSystem l) throws InterruptedException, ExecutionException {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Fractal Generation Performance",
                "Iterations",
                "Time (ms)",
                obtenerTiempo(l),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Map<Character, String> rule = new HashMap<>();
        rule.put('F', "F[+F]F");
        LSystem f = new LSystem("F", 10, rule);

        Estadisticas chart = new Estadisticas("Fractal Generation Performance");
        chart.plotResults(f);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

    }
}
