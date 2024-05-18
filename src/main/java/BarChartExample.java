import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class BarChartExample extends JFrame {

    public BarChartExample(String title) {
        super(title);

        DefaultCategoryDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Ejemplo de Gráfico de Barras",
                "Categoría",
                "Valor",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Crear el panel de gráficos y añadirlo a nuestro JFrame
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Añadir datos al dataset
        dataset.addValue(1, "Grupo 1", "Categoría 1");
        dataset.addValue(4, "Grupo 1", "Categoría 2");
        dataset.addValue(3, "Grupo 1", "Categoría 3");
        dataset.addValue(5, "Grupo 1", "Categoría 4");

        dataset.addValue(5, "Grupo 2", "Categoría 1");
        dataset.addValue(7, "Grupo 2", "Categoría 2");
        dataset.addValue(6, "Grupo 2", "Categoría 3");
        dataset.addValue(8, "Grupo 2", "Categoría 4");

        dataset.addValue(4, "Grupo 3", "Categoría 1");
        dataset.addValue(3, "Grupo 3", "Categoría 2");
        dataset.addValue(2, "Grupo 3", "Categoría 3");
        dataset.addValue(3, "Grupo 3", "Categoría 4");

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BarChartExample example = new BarChartExample("Ejemplo de Gráfico de Barras");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}

