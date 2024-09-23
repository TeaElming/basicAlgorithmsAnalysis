package assignment1.task4.graphing.scatter.unions;

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
import java.util.concurrent.ThreadLocalRandom;

import assignment1.task1.ExecutionTimer;
import assignment1.task2.QuickFind;
import assignment1.task3.QuickUnion;
import assignment1.task3.WeightedUnion;

public class ComparingUnionsUnionsGraph {
  public static void main(String[] args) {

    // Test with varying sizes and consistent number of unions
    int size = 10000;

    int startNumber = 0;
    int endNumber = 9999;
    int increment = 100;

    int[] numberOfUnions = generateNumberOfUnions(startNumber, endNumber, increment);

    XYSeriesCollection varyingSizesDataset = generateUnionTimeDataset(size, numberOfUnions);

    // Example scatter plot
    createAndShowScatterPlot(varyingSizesDataset, "Execution Time vs. Number of Unions (size: 10,000)", "Number of Unions");
  }

  // Method to generate an array of sizes in increments
  public static int[] generateNumberOfUnions(int start, int end, int increment) {
    int length = ((end - start) / increment) + 1; // Calculate the length of the array
    int[] sizes = new int[length];
    for (int i = 0; i < length; i++) {
      sizes[i] = start + (i * increment); // Populate the array with increments
    }
    return sizes;
  }

  // Generates dataset for varying numbers of unions with a consistent array size
  public static XYSeriesCollection generateUnionTimeDataset(int size, int[] numberOfUnions) {
    XYSeries quickFindSeries = new XYSeries("QuickFind");
    XYSeries quickUnionSeries = new XYSeries("QuickUnion");
    XYSeries weightedUnionSeries = new XYSeries("WeightedUnion");

    for (int unions : numberOfUnions) {
      quickFindSeries.add(unions, measureUnionTimeQF(size, unions));
      quickUnionSeries.add(unions, measureUnionTimeQU(size, unions));
      weightedUnionSeries.add(unions, measureUnionTimeWU(size, unions));
    }

    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(quickFindSeries);
    dataset.addSeries(quickUnionSeries);
    dataset.addSeries(weightedUnionSeries);

    return dataset;
  }

  // Generates dataset for a consistent number of unions with varying array sizes
  public static XYSeriesCollection generateUnionTimeWithVaryingSizesDataset(int[] sizes, int numberOfUnions) {
    XYSeries quickFindSeries = new XYSeries("QuickFind");
    XYSeries quickUnionSeries = new XYSeries("QuickUnion");
    XYSeries weightedUnionSeries = new XYSeries("WeightedUnion");

    for (int size : sizes) {
      quickFindSeries.add(size, measureUnionTimeQF(size, numberOfUnions));
      quickUnionSeries.add(size, measureUnionTimeQU(size, numberOfUnions));
      weightedUnionSeries.add(size, measureUnionTimeWU(size, numberOfUnions));
    }

    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(quickFindSeries);
    dataset.addSeries(quickUnionSeries);
    dataset.addSeries(weightedUnionSeries);

    return dataset;
  }

  // Utility function to create and display a scatter plot with the given dataset
  public static void createAndShowScatterPlot(XYSeriesCollection dataset, String title, String xAxisLabel) {
    // Create the scatter plot
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
    Color[] pointColors = { Color.BLUE, Color.GREEN, Color.MAGENTA }; // Point colors

    // Customize the appearance of each series
    for (int i = 0; i < dataset.getSeriesCount(); i++) {
      scatterRenderer.setSeriesShapesVisible(i, true); // Enable shapes (points)
      scatterRenderer.setSeriesPaint(i, pointColors[i % pointColors.length]); // Set point color
      scatterRenderer.setSeriesShape(i, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6)); // Set point shape
      scatterRenderer.setSeriesShapesFilled(i, true); // Fill the shapes
      scatterRenderer.setSeriesOutlinePaint(i, pointColors[i % pointColors.length]); // Set point outline color
    }

    // Set the renderer to the plot
    scatterPlot.setRenderer(scatterRenderer);

    // Display the chart in a JFrame on the Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
      try {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(scatterChart));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
      } catch (Exception e) {
        e.printStackTrace(); // Print any exceptions that occur
      }
    });
  }

  // Measure union time for QuickFind
  public static long measureUnionTimeQF(int size, int numberOfUnions) {
    QuickFind qf = new QuickFind(size);
    int[][] unionPairs = generateUnionPairs(size, numberOfUnions);

    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      for (int[] pair : unionPairs) {
        qf.union(pair[0], pair[1]);
      }
      return null;
    }, 10, (result, duration) -> {
    });
  }

  // Measure union time for QuickUnion
  public static long measureUnionTimeQU(int size, int numberOfUnions) {
    QuickUnion qu = new QuickUnion(size);
    int[][] unionPairs = generateUnionPairs(size, numberOfUnions);

    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      for (int[] pair : unionPairs) {
        qu.union(pair[0], pair[1]);
      }
      return null;
    }, 10, (result, duration) -> {
    });
  }

  // Measure union time for WeightedUnion
  public static long measureUnionTimeWU(int size, int numberOfUnions) {
    WeightedUnion wu = new WeightedUnion(size);
    int[][] unionPairs = generateUnionPairs(size, numberOfUnions);

    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      for (int[] pair : unionPairs) {
        wu.unionWithWeight(pair[0], pair[1]);
      }
      return null;
    }, 1000, (result, duration) -> {
    });
  }

  // Generate an array of random index pairs for union operations
  public static int[][] generateUnionPairs(int size, int numberOfUnions) {
    int[][] pairs = new int[numberOfUnions][2];
    for (int i = 0; i < numberOfUnions; i++) {
      pairs[i][0] = ThreadLocalRandom.current().nextInt(0, size);
      pairs[i][1] = ThreadLocalRandom.current().nextInt(0, size);
    }
    return pairs;
  }
}
