package assignment1.task4.graphing.linear.finds;

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

public class ComparingFindsFindsGraph {

  public static void main(String[] args) {
    // Test with consistent size and varying finds
    int size = 10000;

    int minFind = 10; // Starting size
    int maxFind = 9999; // Ending size
    int increment = 100; // Increment step


    int[] numberOfFinds = generateNumberOfFinds(minFind, maxFind, increment);


    XYSeriesCollection varyingFindsDataset = generateFindTimeDataset(size, numberOfFinds);
    createAndShowChart(varyingFindsDataset, "Execution Time vs. Number of Finds (size: 10,000)", "Number of Finds");
  }

    // Method to generate an array of sizes in increments
    public static int[] generateNumberOfFinds(int start, int end, int increment) {
      int length = ((end - start) / increment) + 1; // Calculate the length of the array
      int[] sizes = new int[length];
      for (int i = 0; i < length; i++) {
        sizes[i] = start + (i * increment); // Populate the array with increments
      }
      return sizes;
    }

  // Generates dataset for varying numbers of finds with a consistent array size
  public static XYSeriesCollection generateFindTimeDataset(int size, int[] numberOfFinds) {
    XYSeries quickFindSeries = new XYSeries("QuickFind");
    XYSeries quickUnionSeries = new XYSeries("QuickUnion");
    XYSeries weightedUnionSeries = new XYSeries("WeightedUnion");

    for (int finds : numberOfFinds) {
      quickFindSeries.add(finds, measureFindTimeQF(size, finds));
      quickUnionSeries.add(finds, measureFindTimeQU(size, finds));
      weightedUnionSeries.add(finds, measureFindTimeWU(size, finds));
    }

    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(quickFindSeries);
    dataset.addSeries(quickUnionSeries);
    dataset.addSeries(weightedUnionSeries);

    return dataset;
  }

  // Generates dataset for a consistent number of finds with varying array sizes
  public static XYSeriesCollection generateFindTimeWithVaryingSizesDataset(int[] sizes, int numberOfFinds) {
    XYSeries quickFindSeries = new XYSeries("QuickFind");
    XYSeries quickUnionSeries = new XYSeries("QuickUnion");
    XYSeries weightedUnionSeries = new XYSeries("WeightedUnion");

    for (int size : sizes) {
      quickFindSeries.add(size, measureFindTimeQF(size, numberOfFinds));
      quickUnionSeries.add(size, measureFindTimeQU(size, numberOfFinds));
      weightedUnionSeries.add(size, measureFindTimeWU(size, numberOfFinds));
    }

    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(quickFindSeries);
    dataset.addSeries(quickUnionSeries);
    dataset.addSeries(weightedUnionSeries);

    return dataset;
  }

  // Utility function to create and display a chart with the given dataset
  public static void createAndShowChart(XYSeriesCollection dataset, String title, String xAxisLabel) {
    JFreeChart chart = ChartFactory.createXYLineChart(
        title,
        xAxisLabel,
        "Execution Time (ns)",
        dataset,
        PlotOrientation.VERTICAL,
        true,
        true,
        false);

    XYPlot plot = chart.getXYPlot();

    // Custom renderer to set different colors for lines and points
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

    // Define colors for each series
    Color[] lineColors = { Color.BLUE, Color.GREEN, Color.MAGENTA }; // You can adjust these colors as needed
    Color[] pointColors = { Color.RED, Color.ORANGE, Color.CYAN }; // Different colors for points

    // Customize the appearance of each series
    for (int i = 0; i < dataset.getSeriesCount(); i++) {
      renderer.setSeriesLinesVisible(i, true); // Enable lines
      renderer.setSeriesShapesVisible(i, true); // Enable shapes (points)
      renderer.setSeriesPaint(i, lineColors[i % lineColors.length]); // Set line color
      renderer.setSeriesShape(i, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6)); // Set point shape
      renderer.setSeriesShapesFilled(i, true); // Fill the shapes
      renderer.setSeriesOutlinePaint(i, pointColors[i % pointColors.length]); // Set point outline color
      renderer.setSeriesFillPaint(i, pointColors[i % pointColors.length]); // Set point fill color
    }

    plot.setRenderer(renderer);

    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame(title);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new ChartPanel(chart));
      frame.pack();
      frame.setVisible(true);
    });
  }

  // Measure find time for QuickFind with multiple runs to get an average
  public static long measureFindTimeQF(int size, int numberOfFinds) {
    QuickFind qf = new QuickFind(size);
    int[] findIndices = generateFindInts(size, numberOfFinds);

    // Run the timing task 1000 times to get an average duration
    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      for (int index : findIndices) {
        qf.find(index);
      }
      return null; // No result needed, just measuring time
    }, 10000, (result, duration) -> {
    });
  }

  // Measure find time for QuickUnion
  public static long measureFindTimeQU(int size, int numberOfFinds) {
    QuickUnion qu = new QuickUnion(size);
    int[] findIndices = generateFindInts(size, numberOfFinds);

    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      for (int index : findIndices) {
        qu.find(index);
      }
      return null;
    }, 10000, (result, duration) -> {
    });
  }

  // Measure find time for WeightedUnion
  public static long measureFindTimeWU(int size, int numberOfFinds) {
    WeightedUnion wu = new WeightedUnion(size);
    int[] findIndices = generateFindInts(size, numberOfFinds);

    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      for (int index : findIndices) {
        wu.find(index);
      }
      return null;
    }, 10000, (result, duration) -> {
    });
  }

  // Generate an array of random integers between 0 and size based on how many
  // finds we want to do
  public static int[] generateFindInts(int size, int numberOfFinds) {
    int[] randomInts = new int[numberOfFinds];
    for (int i = 0; i < numberOfFinds; i++) {
      randomInts[i] = ThreadLocalRandom.current().nextInt(0, size);
    }
    return randomInts;
  }
}
