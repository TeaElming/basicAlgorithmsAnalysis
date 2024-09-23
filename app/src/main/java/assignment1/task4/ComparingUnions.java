package assignment1.task4;

import java.util.concurrent.ThreadLocalRandom;
import assignment1.task1.ExecutionTimer;
import assignment1.task2.QuickFind;
import assignment1.task3.QuickUnion;
import assignment1.task3.WeightedUnion;

public class ComparingUnions {

  public static void main(String[] args) {
    int size = 1000000; // Example size of the structure
    int numberOfUnions = 10000; // Number of union operations to perform

    // Measure and print execution times for different union implementations
    System.out
        .println("QuickFind union operations duration: " + measureUnionTimeQF(size, numberOfUnions) + " nanoseconds");
    System.out
        .println("QuickUnion union operations duration: " + measureUnionTimeQU(size, numberOfUnions) + " nanoseconds");
    System.out.println(
        "WeightedUnion union operations duration: " + measureUnionTimeWU(size, numberOfUnions) + " nanoseconds");
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

  // Generate an array of random index pairs for union operations
  public static int[][] generateUnionPairs(int size, int numberOfUnions) {
    int[][] pairs = new int[numberOfUnions][2];
    for (int i = 0; i < numberOfUnions; i++) {
      pairs[i][0] = ThreadLocalRandom.current().nextInt(0, size);
      pairs[i][1] = ThreadLocalRandom.current().nextInt(0, size);
    }
    return pairs;
  }

  // Measure union time for QuickFind
  public static long measureUnionTimeQF(int size, int numberOfUnions) {
    QuickFind qf = createQuickFind(size);
    int[][] unionPairs = generateUnionPairs(size, numberOfUnions); // Pre-generate union pairs

    // Measure the time taken to perform the union operations
    return ExecutionTimer.measureTime(() -> {
      for (int[] pair : unionPairs) {
        qf.union(pair[0], pair[1]); // Perform union operations using pre-generated pairs
      }
      return null; // Return type for Callable
    });
  }

  // Measure union time for QuickUnion
  public static long measureUnionTimeQU(int size, int numberOfUnions) {
    QuickUnion qu = createQuickUnion(size);
    int[][] unionPairs = generateUnionPairs(size, numberOfUnions); // Pre-generate union pairs

    // Measure the time taken to perform the union operations
    return ExecutionTimer.measureTime(() -> {
      for (int[] pair : unionPairs) {
        qu.union(pair[0], pair[1]); // Perform union operations using pre-generated pairs
      }
      return null; // Return type for Callable
    });
  }

  // Measure union time for WeightedUnion
  public static long measureUnionTimeWU(int size, int numberOfUnions) {
    WeightedUnion wu = createWeightedUnion(size);
    int[][] unionPairs = generateUnionPairs(size, numberOfUnions); // Pre-generate union pairs

    // Measure the time taken to perform the union operations
    return ExecutionTimer.measureTime(() -> {
      for (int[] pair : unionPairs) {
        wu.unionWithWeight(pair[0], pair[1]); // Perform union operations using pre-generated pairs
      }
      return null; // Return type for Callable
    });
  }
}
