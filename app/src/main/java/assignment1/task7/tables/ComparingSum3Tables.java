package assignment1.task7.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import assignment1.task1.ExecutionTimer;
import assignment1.task7.ComparingSum3;

public class ComparingSum3Tables {

  public static void main(String[] args) {
    int startSize = 100; // Starting array size
    int maxSize = 1000; // Maximum array size
    int increment = 100; // Increment step
    int numberOfRuns = 10; // Number of runs for averaging

    int[] sizes = generateArraySizes(startSize, maxSize, increment);

    createAndShowTables(sizes, numberOfRuns);
  }

  // Generate array sizes in increments
  public static int[] generateArraySizes(int start, int end, int increment) {
    int length = ((end - start) / increment) + 1;
    int[] sizes = new int[length];
    for (int i = 0; i < length; i++) {
      sizes[i] = start + (i * increment);
    }
    return sizes;
  }

  // Create and display the tables
  public static void createAndShowTables(int[] sizes, int numberOfRuns) {
    String[] columnNames = { "Array Size", "Avg Time (ns)", "Error Margin (%)", "Expected Time (ns)" };
    String[] comparisonColumnNames = { "Array Size", "Avg Sum3_Brute (ns)", "Avg Sum3_Imp (ns)" };

    DefaultTableModel bruteModel = new DefaultTableModel(columnNames, 0);
    DefaultTableModel impModel = new DefaultTableModel(columnNames, 0);
    DefaultTableModel comparisonModel = new DefaultTableModel(comparisonColumnNames, 0);

    // Define a DecimalFormat for scientific notation
    DecimalFormat scientificFormat = new DecimalFormat("0.######E0", new DecimalFormatSymbols(Locale.US));

    for (int size : sizes) {
      int[] numbers = ComparingSum3.generateRandomList(size, -10, 10);

      // Measure times for Sum3_Brute
      MeasurementResult bruteResult = measureMultipleRuns(
          () -> ComparingSum3.measureRepeatedSum3Brute(numbers, 0, numberOfRuns));
      long expectedBruteTime = ComparingSum3.calculateExpectedTime(bruteResult.avgTime, sizes[0], size);
      bruteModel.addRow(new Object[] {
          size,
          scientificFormat.format(bruteResult.avgTime),
          bruteResult.errorMargin,
          scientificFormat.format(expectedBruteTime)
      });

      // Measure times for Sum3_Imp
      MeasurementResult impResult = measureMultipleRuns(
          () -> ComparingSum3.measureRepeatedSum3Imp(numbers, 0, numberOfRuns));
      long expectedImpTime = ComparingSum3.calculateExpectedTimeForImp(impResult.avgTime, sizes[0], size);
      impModel.addRow(new Object[] {
          size,
          scientificFormat.format(impResult.avgTime),
          impResult.errorMargin,
          scientificFormat.format(expectedImpTime)
      });

      // Add to comparison table
      comparisonModel.addRow(new Object[] {
          size,
          scientificFormat.format(bruteResult.avgTime),
          scientificFormat.format(impResult.avgTime)
      });
    }

    // Create the JFrame to hold all tables
    JFrame frame = new JFrame("Comparing Sum3 Algorithms");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridLayout(2, 2)); // 2x2 grid for 3 tables

    // Add each table to the frame
    frame.add(createTablePanel(bruteModel, "Sum3_Brute"));
    frame.add(createTablePanel(impModel, "Sum3_Imp"));
    frame.add(createTablePanel(comparisonModel, "Comparison"));

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
    final int runs = 10; // Number of runs for averaging
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
    double errorMargin = ((double) (maxTime / runs - minTime / runs) / avgTime) * 100;

    // Create a DecimalFormat with '.' as the decimal separator
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.UK);
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
}
