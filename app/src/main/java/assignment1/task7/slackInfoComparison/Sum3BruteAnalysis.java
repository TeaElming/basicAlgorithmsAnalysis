package assignment1.task7.slackInfoComparison;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import assignment1.task1.ExecutionTimer;
import assignment1.task5.Sum3_Brute;

public class Sum3BruteAnalysis {

    public static void main(String[] args) {
        int startSize = 10; // Starting size
        int endSize = 1000; // Ending size
        int increment = 10; // Increment step
        int runTimes = 5; // Number of repetitions per size
        int targetSum = 0; // Target sum to find

        // Step 1: Collect data for Sum3_Brute
        List<DataPoint> dataPoints = collectExecutionData(startSize, endSize, increment, runTimes, targetSum);

        // Step 2: Display the data in a table
        showDataTable(dataPoints);

        // Step 3: Perform calculations and plot results
        calculateAndPlot(dataPoints);
    }

    // Data point class to store size and timing for Sum3_Brute
    static class DataPoint {
        int size;
        long bruteForceTime;

        DataPoint(int size, long bruteForceTime) {
            this.size = size;
            this.bruteForceTime = bruteForceTime;
        }
    }

    // Collect execution data for Sum3_Brute
    private static List<DataPoint> collectExecutionData(int startSize, int endSize, int increment, int runTimes,
                                                        int targetSum) {
        List<DataPoint> dataPoints = new ArrayList<>();
        for (int size = startSize; size <= endSize; size += increment) {
            int[] numbers = generateRandomList(size, -size, size);
            long bruteForceTime = measureRepeatedSum3Brute(numbers, targetSum, runTimes);
            dataPoints.add(new DataPoint(size, bruteForceTime));
        }
        return dataPoints;
    }

    // Generate a random list of integers within a specified range
    public static int[] generateRandomList(int size, int minValue, int maxValue) {
        Random random = new Random();
        int[] randomList = new int[size];

        // Generate random numbers within the specified range
        for (int i = 0; i < size; i++) {
            randomList[i] = random.nextInt(maxValue - minValue + 1) + minValue;
        }

        return randomList;
    }

    // Measure average execution time for Sum3_Brute over multiple runs
    public static long measureRepeatedSum3Brute(int[] numbers, int targetSum, int numberOfRuns) {
        return ExecutionTimer.measureTimeMultipleRuns(() -> {
            Sum3_Brute s3brute = new Sum3_Brute(numbers, targetSum);
            List<int[]> results = s3brute.getResults();
            return results;
        }, numberOfRuns, (results, duration) -> {
        });
    }

    // Display data points in a table
    private static void showDataTable(List<DataPoint> dataPoints) {
        // Table setup
        String[] columnNames = {"Array Size", "Brute Force Time (ns)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate table rows
        for (DataPoint point : dataPoints) {
            model.addRow(new Object[]{point.size, point.bruteForceTime});
        }

        // Create and show the table in a JFrame
        JTable table = new JTable(model);
        JFrame frame = new JFrame("Execution Time Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    // Perform calculations and plot results
    private static void calculateAndPlot(List<DataPoint> dataPoints) {
        // Select specific sizes for calculations
        DataPoint point200 = dataPoints.stream().filter(p -> p.size == 200).findFirst().orElse(null);
        DataPoint point400 = dataPoints.stream().filter(p -> p.size == 400).findFirst().orElse(null);
        DataPoint point800 = dataPoints.stream().filter(p -> p.size == 800).findFirst().orElse(null);

        if (point200 == null || point400 == null || point800 == null) {
            System.out.println("Required data points for N=200, N=400, and N=800 not found.");
            return;
        }

        // Calculate b and c for N=200 & N=400
        double[] bc1 = calculateBC(point200, point400);
        double b1 = bc1[0];
        double c1 = bc1[1];

        // Calculate b and c for N=400 & N=800
        double[] bc2 = calculateBC(point400, point800);
        double b2 = bc2[0];
        double c2 = bc2[1];

        // Print calculated values
        System.out.println("For N=200 and N=400:");
        System.out.println("The b-value is: " + b1);
        System.out.println("The c-value is: " + c1);

        System.out.println("\nFor N=400 and N=800:");
        System.out.println("The b-value is: " + b2);
        System.out.println("The c-value is: " + c2);

        // Prepare datasets for plotting
        XYSeries bruteForceSeries = new XYSeries("Sum3_Brute");
        XYSeries predictedSeries1 = new XYSeries("Predicted Fit N=200 & N=400");
        XYSeries predictedSeries2 = new XYSeries("Predicted Fit N=400 & N=800");

        // Populate series with data points and predicted values
        for (DataPoint point : dataPoints) {
            bruteForceSeries.add(point.size, point.bruteForceTime);
            double predictedTime1 = Math.pow(2, b1 * Math.log(point.size) / Math.log(2) + c1);
            double predictedTime2 = Math.pow(2, b2 * Math.log(point.size) / Math.log(2) + c2);
            predictedSeries1.add(point.size, predictedTime1);
            predictedSeries2.add(point.size, predictedTime2);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(bruteForceSeries);
        dataset.addSeries(predictedSeries1);
        dataset.addSeries(predictedSeries2);

        // Create scatter plot with predicted fits
        JFreeChart scatterChart = ChartFactory.createScatterPlot(
                "Execution Time vs. Array Size (Sum3_Brute)",
                "Array Size",
                "Execution Time (ns)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        XYPlot scatterPlot = scatterChart.getXYPlot();

        // Custom renderer for scatter plot with different colors for points
        XYLineAndShapeRenderer scatterRenderer = new XYLineAndShapeRenderer();
        scatterRenderer.setSeriesLinesVisible(0, true); // Enable lines connecting actual data points
        scatterRenderer.setSeriesShapesVisible(0, true); // Enable points for actual data
        scatterRenderer.setSeriesLinesVisible(1, true); // Enable line for predicted fit N=200 & N=400
        scatterRenderer.setSeriesShapesVisible(1, false); // Disable points for predicted fit N=200 & N=400
        scatterRenderer.setSeriesLinesVisible(2, true); // Enable line for predicted fit N=400 & N=800
        scatterRenderer.setSeriesShapesVisible(2, false); // Disable points for predicted fit N=400 & N=800

        // Define colors for each series
        scatterRenderer.setSeriesPaint(0, Color.RED); // Actual data points with connecting lines
        scatterRenderer.setSeriesPaint(1, Color.BLUE); // Predicted fit line N=200 & N=400
        scatterRenderer.setSeriesPaint(2, Color.GREEN); // Predicted fit line N=400 & N=800

        scatterPlot.setRenderer(scatterRenderer);

        // Highlight data points for N = 200, N = 400, and N = 800
        scatterRenderer.setSeriesShapesVisible(0, true);
        scatterRenderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));

        // Display the scatter plot in a JFrame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sum3_Brute Analysis");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChartPanel(scatterChart));
            frame.pack();
            frame.setVisible(true);
        });
    }

    // Calculate b and c for two given data points
    private static double[] calculateBC(DataPoint p1, DataPoint p2) {
        double log2Time1 = Math.log(p1.bruteForceTime) / Math.log(2);
        double log2Time2 = Math.log(p2.bruteForceTime) / Math.log(2);
        double log2N1 = Math.log(p1.size) / Math.log(2);
        double log2N2 = Math.log(p2.size) / Math.log(2);

        double b = (log2Time2 - log2Time1) / (log2N2 - log2N1);
        double c = log2Time1 - b * log2N1;

        return new double[]{b, c};
    }
}
