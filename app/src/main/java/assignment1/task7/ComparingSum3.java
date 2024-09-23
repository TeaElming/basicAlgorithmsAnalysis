package assignment1.task7;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

import assignment1.task1.ExecutionTimer;
import assignment1.task5.Sum3_Brute;
import assignment1.task6.Sum3_Imp;

public class ComparingSum3 {

  public static void main(String[] args) {
    int size = 15; // Size of the random list
    int targetSum = 0; // Example sum to find
    int minValue = -10; // Minimum value for random numbers
    int maxValue = 10; // Maximum value for random numbers
    int numberOfRuns = 10;

    // Generate a random list using the helper function
    int[] numbers = generateRandomList(size, minValue, maxValue);

    // Measure and print execution time for Sum3_Brute
    long bruteForceTime = measureSingularSum3Brute(numbers, targetSum);
    System.out.println("Time taken by Sum3_Brute: " + bruteForceTime + " nanoseconds");

    // Measure and print execution time for Sum3_Imp
    long improvedTime = measureSingularSum3Imp(numbers, targetSum);
    System.out.println("Time taken by Sum3_Imp: " + improvedTime + " nanoseconds");

    long bruteForceAvgTime = measureRepeatedSum3Brute(numbers, targetSum, numberOfRuns);
    System.out.println("Avg time taken by Sum3_Brute: " + bruteForceAvgTime + " nanoseconds");

    // Measure and print execution time for Sum3_Imp
    long improvedAvgTime = measureRepeatedSum3Imp(numbers, targetSum, numberOfRuns);
    System.out.println("Avg time taken by Sum3_Imp: " + improvedAvgTime + " nanoseconds");
  }

  public static Sum3_Brute createSum3Brute(int[] numbers, int targetSum) {
    return new Sum3_Brute(numbers, targetSum);
  }

  public static Sum3_Imp createSum3Imp(int[] numbers, int targetSum) {
    return new Sum3_Imp(numbers, targetSum);
  }

  public static int[] generateRandomList(int size, int minValue, int maxValue) {
    Random random = new Random();
    int[] randomList = new int[size];

    // Generate random numbers within the specified range
    for (int i = 0; i < size; i++) {
      randomList[i] = random.nextInt(maxValue - minValue + 1) + minValue;
    }

    Arrays.sort(randomList);

    return randomList;
  }

  public static long measureSingularSum3Brute(int[] numbers, int targetSum) {
    // Measure the time it takes to find triplets using Sum3_Brute
    return ExecutionTimer.measureTime(() -> {
      Sum3_Brute s3brute = createSum3Brute(numbers, targetSum);
      List<int[]> results = s3brute.getResults(); // Get the results
      return results; // Return the results (not used, but required for Callable)
    });
  }

  public static long measureSingularSum3Imp(int[] numbers, int targetSum) {
    // Measure the time it takes to find triplets using Sum3_Imp
    return ExecutionTimer.measureTime(() -> {
      Sum3_Imp s3imp = createSum3Imp(numbers, targetSum);
      List<int[]> results = s3imp.getResults(); // Assume Sum3_Imp has a getResults() method
      return results; // Return the results (not used, but required for Callable)
    });
  }

  public static long measureRepeatedSum3Brute(int[] numbers, int targetSum, int numberOfRuns) {
    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      Sum3_Brute s3brute = createSum3Brute(numbers, targetSum);
      List<int[]> results = s3brute.getResults();
      return results;
    }, numberOfRuns, (results, duration) -> {
    });
  }

  public static long measureRepeatedSum3Imp(int[] numbers, int targetSum, int numberOfRuns) {
    return ExecutionTimer.measureTimeMultipleRuns(() -> {
      Sum3_Imp s3imp = createSum3Imp(numbers, targetSum);
      List<int[]> results = s3imp.getResults(); // Assume Sum3_Imp has a getResults() method
      return results; // Return the results (not used, but required for Callable)
    }, numberOfRuns, (results, duration) -> {
    });
  }

  /**
   * Full comments added due to slightly confusing for me originally
   *
   * Calculate the expected time for the Sum3_Brute algorithm based on a known
   * time for a given size.
   *
   * @param avgTime The actual measured time in nanoseconds for a given input
   *                     size.
   * @param currentSize  The size of the input list for which the time was
   *                     measured.
   * @param newSize      The new input size for which to calculate the expected
   *                     time.
   * @return The expected execution time in nanoseconds for the new input size.
   */
  public static long calculateExpectedTime(double avgTime, int currentSize, int newSize) {
    // Estimate constant C using measured time and current size
    double constantC = (double) avgTime / Math.pow(currentSize, 3);
    // Calculate expected time for new size
    return (long) (constantC * Math.pow(newSize, 3));
  }

  /**
   * Calculate the expected time for the Sum3_Imp algorithm based on a known time
   * for a given size.
   *
   * @param avgTime The actual measured time in nanoseconds for a given input
   *                     size.
   * @param currentSize  The size of the input list for which the time was
   *                     measured.
   * @param newSize      The new input size for which to calculate the expected
   *                     time.
   * @return The expected execution time in nanoseconds for the new input size.
   */
  public static long calculateExpectedTimeForImp(double avgTime, int currentSize, int newSize) {
    // Estimate constant C using measured time and current size squared
    double constantC = (double) avgTime / Math.pow(currentSize, 2);
    // Calculate expected time for new size squared
    return (long) (constantC * Math.pow(newSize, 2));
  }

}
