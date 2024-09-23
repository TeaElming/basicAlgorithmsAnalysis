package assignment1.task5;

import java.util.Arrays;
import java.util.Random;

public class TesterFunctionSum3_Brute {

  // Main method for testing
  public static void main(String[] args) {
    int size = 35; // Size of the random list
    int targetSum = 0; // Example sum to find
    int minValue = -100; // Minimum value for random numbers
    int maxValue = 100; // Maximum value for random numbers

    // Generate a random list using the helper function
    int[] numbers = generateRandomList(size, minValue, maxValue);

    // Create a Sum3_Brute object with the generated list and target sum
    Sum3_Brute sum3 = new Sum3_Brute(numbers, targetSum); // Correctly pass the arguments

    // Print the generated random list for reference
    System.out.print("Generated list: ");
    for (int num : numbers) {
      System.out.print(num + " ");
    }
    System.out.println();

    // Print results of combinations that sum to the target sum
    System.out.println("Combinations that sum to " + targetSum + ":");
    for (int[] combination : sum3.getResults()) { // Correctly call getResults()
      System.out.println(combination[0] + ", " + combination[1] + ", " + combination[2]);
    }
  }

  // Helper method to generate a random list of integers within a specified range
  private static int[] generateRandomList(int size, int minValue, int maxValue) {
    Random random = new Random();
    int[] randomList = new int[size];

    // Generate random numbers within the specified range
    for (int i = 0; i < size; i++) {
      randomList[i] = random.nextInt(maxValue - minValue + 1) + minValue;
    }

    // Sort the array in ascending order
    Arrays.sort(randomList);

    return randomList;
  }

}
