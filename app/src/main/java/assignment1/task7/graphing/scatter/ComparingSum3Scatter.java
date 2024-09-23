package assignment1.task7.graphing.scatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

import assignment1.task1.ExecutionTimer;
import assignment1.task5.Sum3_Brute;
import assignment1.task6.Sum3_Imp;

public class ComparingSum3Scatter {

  public static void main(String[] args) {
    int startSize = 10; // Starting size
    int endSize = 1000; // Ending size
    int increment = 10; // Increment step
    int runTimes = 5; // Multiple runs per size
    int targetSum = 0; // Example sum to find

    // Generate sizes array
    int[] sizes = generateSizesArray(startSize, endSize, increment);

    // Create datasets for Sum3_Brute and Sum3_Imp
    XYSeriesCollection dataset = generateExecutionTimeDataset(sizes, targetSum, runTimes);

    // Create and show the scatter plot
    createAndShowScatterPlot(dataset, "Execution Time vs. Array Size", "Array Size");
  }

  // Generate sizes array in increments
  public static int[] generateSizesArray(int start, int end, int increment) {
    int length = ((end - start) / increment) + 1; // Calculate the length of the array
    int[] sizes = new int[length];
    for (int i = 0; i < length; i++) {
      sizes[i] = start + (i * increment); // Populate the array with increments
    }
    return sizes;
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

  // Generate dataset for execution times of Sum3_Brute and Sum3_Imp
  public static XYSeriesCollection generateExecutionTimeDataset(int[] sizes, int targetSum, int runTimes) {
    XYSeries bruteForceSeries = new XYSeries("Sum3_Brute");
    XYSeries improvedSeries = new XYSeries("Sum3_Imp");

    for (int size : sizes) {
      int minValue = -size;
      int maxValue = size;
      int[] numbers = generateRandomList(size, minValue, maxValue);

      long bruteForceTime = measureRepeatedSum3Brute(numbers, targetSum, runTimes);
      long improvedTime = measureRepeatedSum3Imp(numbers, targetSum, runTimes);

      bruteForceSeries.add(size, bruteForceTime);
      improvedSeries.add(size, improvedTime);
    }

    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(bruteForceSeries);
    dataset.addSeries(improvedSeries);

    return dataset;
  }

  // Utility function to create and display a scatter plot with the given dataset
  public static void createAndShowScatterPlot(XYSeriesCollection dataset, String title, String xAxisLabel) {
    JFreeChart scatterChart = ChartFactory.createScatterPlot(
        title,
        xAxisLabel,
        "Execution Time (ns)",
        dataset,
        PlotOrientation.VERTICAL,
        true,
        true,
        false);

    XYPlot scatterPlot = scatterChart.getXYPlot();

    // Custom renderer for scatter plot with different colors for points
    XYLineAndShapeRenderer scatterRenderer = new XYLineAndShapeRenderer();
    scatterRenderer.setDefaultLinesVisible(false); // Disable connecting lines
    scatterRenderer.setDefaultShapesVisible(true); // Enable points

    // Define colors for each series
    Color[] pointColors = { Color.RED, Color.ORANGE }; // Point colors for Sum3_Brute and Sum3_Imp

    // Customize the appearance of each series
    for (int i = 0; i < dataset.getSeriesCount(); i++) {
      scatterRenderer.setSeriesShapesVisible(i, true); // Enable shapes (points)
      scatterRenderer.setSeriesPaint(i, pointColors[i % pointColors.length]); // Set point color
      scatterRenderer.setSeriesShape(i, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6)); // Set point shape
      scatterRenderer.setSeriesShapesFilled(i, true); // Fill the shapes
      scatterRenderer.setSeriesOutlinePaint(i, pointColors[i % pointColors.length]); // Set point outline color
    }

    scatterPlot.setRenderer(scatterRenderer);

    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame(title);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new ChartPanel(scatterChart));
      frame.pack();
      frame.setVisible(true);
    });
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

  // Measure average execution time for Sum3_Imp over multiple runs
  public static long measureRepeatedSum3Imp(int[] numbers, int targetSum, int numberOfRuns) {
    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      Sum3_Imp s3imp = new Sum3_Imp(numbers, targetSum);
      List<int[]> results = s3imp.getResults(); // Assume Sum3_Imp has a getResults() method
      return results; // Return the results (not used, but required for Callable)
    }, numberOfRuns, (results, duration) -> {
    });
  }
}
