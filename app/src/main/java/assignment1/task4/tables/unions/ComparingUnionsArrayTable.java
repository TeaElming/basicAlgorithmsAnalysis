package assignment1.task4.tables.unions;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import assignment1.task1.ExecutionTimer;
import assignment1.task2.QuickFind;
import assignment1.task3.QuickUnion;
import assignment1.task3.WeightedUnion;

public class ComparingUnionsArrayTable {

  public static void main(String[] args) {
    // Test with varying sizes and consistent number of unions
    int unions = 99;

    int startSize = 100; // Starting size
    int endSize = 10000; // Ending size
    int increment = 500; // Increment step
    int[] sizes = generateSizesArray(startSize, endSize, increment);

    // Create tables for each algorithm
    createAndShowTables(sizes, unions);
  }

  // Method to generate an array of sizes in increments
  public static int[] generateSizesArray(int start, int end, int increment) {
    int length = ((end - start) / increment) + 1; // Calculate the length of the array
    int[] sizes = new int[length];
    for (int i = 0; i < length; i++) {
      sizes[i] = start + (i * increment); // Populate the array with increments
    }
    return sizes;
  }

  // Create and display tables for each algorithm and a comparative table
  public static void createAndShowTables(int[] sizes, int numberOfUnions) {
    String[] columnNames = { "Array Size", "Avg Time (ns)", "Error Margin (%)" };
    String[] comparativeColumnNames = { "Array Size", "Avg QF (ns)", "Avg QU (ns)", "Avg WU (ns)" };

    DefaultTableModel qfModel = new DefaultTableModel(columnNames, 0);
    DefaultTableModel quModel = new DefaultTableModel(columnNames, 0);
    DefaultTableModel wuModel = new DefaultTableModel(columnNames, 0);
    DefaultTableModel comparativeModel = new DefaultTableModel(comparativeColumnNames, 0);

    for (int size : sizes) {
      // Measure times for QuickFind
      MeasurementResult qfResult = measureMultipleRuns(() -> measureUnionTimeQF(size, numberOfUnions));
      qfModel.addRow(new Object[] { size, qfResult.avgTime, qfResult.errorMargin, 0 });

      // Measure times for QuickUnion
      MeasurementResult quResult = measureMultipleRuns(() -> measureUnionTimeQU(size, numberOfUnions));
      quModel.addRow(new Object[] { size, quResult.avgTime, quResult.errorMargin, 0 });

      // Measure times for WeightedUnion
      MeasurementResult wuResult = measureMultipleRuns(() -> measureUnionTimeWU(size, numberOfUnions));
      wuModel.addRow(new Object[] { size, wuResult.avgTime, wuResult.errorMargin, 0 });

      // Add to comparative table
      comparativeModel.addRow(new Object[] { size, qfResult.avgTime, quResult.avgTime, wuResult.avgTime });
    }

    // Create the JFrame to hold all tables
    JFrame frame = new JFrame("Comparing Union Algorithms");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridLayout(2, 2)); // 2x2 grid for 4 tables

    // Add each table to the frame
    frame.add(createTablePanel(qfModel, "QuickFind"));
    frame.add(createTablePanel(quModel, "QuickUnion"));
    frame.add(createTablePanel(wuModel, "WeightedUnion"));
    frame.add(createTablePanel(comparativeModel, "Comparative"));

    // Set the frame size and make it visible
    frame.setSize(1200, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setVisible(true);
  }

  // Utility method to create a JScrollPane containing a JTable
  private static JScrollPane createTablePanel(DefaultTableModel model, String title) {
    JTable table = new JTable(model);
    table.setFillsViewportHeight(true);
    table.setRowHeight(30);
    table.setShowGrid(true);
    table.setGridColor(Color.LIGHT_GRAY);

    // Center the table cells
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    table.setDefaultRenderer(Object.class, centerRenderer);

    // Add the table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createTitledBorder(title));
    return scrollPane;
  }

  // Method to run the measurement multiple times and calculate average and error
  // margin
  private static MeasurementResult measureMultipleRuns(Runnable measurementTask) {
    final int runs = 10;
    long minTime = Long.MAX_VALUE;
    long maxTime = Long.MIN_VALUE;
    long totalTime = 0;

    for (int i = 0; i < runs; i++) {
      long time = ExecutionTimer.measureTime(() -> {
        measurementTask.run();
        return null;
      });
      minTime = Math.min(minTime, time);
      maxTime = Math.max(maxTime, time);
      totalTime += time;
    }

    double avgTime = (double) totalTime / runs;
    double errorMargin = ((double) (maxTime/runs - minTime/runs) / avgTime) * 100;

    // Create a DecimalFormat with '.' as the decimal separator
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    symbols.setDecimalSeparator('.');
    DecimalFormat df = new DecimalFormat("#.##", symbols);

    // Format the error margin to two decimal places
    errorMargin = Double.parseDouble(df.format(errorMargin));

    return new MeasurementResult(avgTime, errorMargin);
  }

  // Measurement result class to store average time and error margin
  static class MeasurementResult {
    double avgTime;
    double errorMargin;

    MeasurementResult(double avgTime, double errorMargin) {
      this.avgTime = avgTime;
      this.errorMargin = errorMargin;
    }
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
    }, 10, (result, duration) -> {
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
