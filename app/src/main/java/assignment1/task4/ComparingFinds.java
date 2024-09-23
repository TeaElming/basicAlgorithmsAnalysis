package assignment1.task4;

import java.util.concurrent.ThreadLocalRandom;
import assignment1.task1.ExecutionTimer;
import assignment1.task2.QuickFind;
import assignment1.task3.QuickUnion;
import assignment1.task3.WeightedUnion;

public class ComparingFinds {

  public static void main(String[] args) {
    int size = 100000; // Example size of the structure
    int numberOfFinds = 10000; // Number of find operations to perform

    // Measure and print execution times for different find implementations
    System.out
        .println("QuickFind find operations duration: " + measureFindTimeQF(size, numberOfFinds) + " nanoseconds");
    System.out
        .println("QuickUnion find operations duration: " + measureFindTimeQU(size, numberOfFinds) + " nanoseconds");
    System.out
        .println("WeightedUnion find operations duration: " + measureFindTimeWU(size, numberOfFinds) + " nanoseconds");
  }

  // Method to create QuickFind structure
  public static QuickFind createQuickFind(int size) {
    return new QuickFind(size);
  }

  // Method to create QuickUnion structure
  public static QuickUnion createQuickUnion(int size) {
    return new QuickUnion(size);
  }

  // Method to create WeightedUnion structure
  public static WeightedUnion createWeightedUnion(int size) {
    return new WeightedUnion(size);
  }

  // Generate an array of random integers between 0 and size based on how many finds we want to do
  public static int[] generateFindInts(int size, int numberOfFinds) {
    int[] randomInts = new int[numberOfFinds];
    for (int i = 0; i < numberOfFinds; i++) {
      randomInts[i] = ThreadLocalRandom.current().nextInt(0, size);
    }
    return randomInts;
  }

  // Measure find time for QuickFind
  public static long measureFindTimeQF(int size, int numberOfFinds) {
    QuickFind qf = createQuickFind(size);
    int[] findIndices = generateFindInts(size, numberOfFinds); // Pre-generate find indices

    // Measure the time taken to perform the find operations
    return ExecutionTimer.measureTime(() -> {
      for (int index : findIndices) {
        qf.find(index); // Perform find operations using pre-generated indices
      }
      return null; // Return type for Callable
    });
  }

  // Measure find time for QuickUnion
  public static long measureFindTimeQU(int size, int numberOfFinds) {
    QuickUnion qu = createQuickUnion(size);
    int[] findIndices = generateFindInts(size, numberOfFinds); // Pre-generate find indices

    // Measure the time taken to perform the find operations
    return ExecutionTimer.measureTime(() -> {
      for (int index : findIndices) {
        qu.find(index); // Perform find operations using pre-generated indices
      }
      return null; // Return type for Callable
    });
  }

  // Measure find time for WeightedUnion
  public static long measureFindTimeWU(int size, int numberOfFinds) {
    WeightedUnion wu = createWeightedUnion(size);
    int[] findIndices = generateFindInts(size, numberOfFinds); // Pre-generate find indices

    // Measure the time taken to perform the find operations
    return ExecutionTimer.measureTime(() -> {
      for (int index : findIndices) {
        wu.find(index); // Perform find operations using pre-generated indices
      }
      return null; // Return type for Callable
    });
  }

  public static long expectedTimeQuickFind(long measuredTime, int currentSize, int newSize) {
    // The find operation is O(1), so we use the size to estimate constant C.
    double constantC = (double) measuredTime / currentSize;
    // Expected time scales linearly with the number of finds, not the structure size.
    return (long) (constantC * newSize);
}


public static long expectedTimeQuickUnion(long measuredTime, int currentSize, int newSize) {
  // Estimate constant C based on measured time and complexity O(N) for find.
  double constantC = (double) measuredTime / currentSize;
  // Expected time scales linearly with the structure size.
  return (long) (constantC * newSize);
}


public static long expectedTimeWeightedUnion(long measuredTime, int currentSize, int newSize) {
  // Estimate constant C using measured time and log of the current size.
  double constantC = (double) measuredTime / Math.log(currentSize);
  // Expected time scales with log of the new size.
  return (long) (constantC * Math.log(newSize));
}



}
