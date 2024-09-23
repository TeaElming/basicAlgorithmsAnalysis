package assignment1.task5;

import java.util.ArrayList;
import java.util.List;

public class Sum3_Brute {
  private List<int[]> results = new ArrayList<>(); // List to store combinations of 3 numbers

  // Constructor accepts a list of integers and a target sum
  public Sum3_Brute(int[] listToCheck, int sum) {
    int size = listToCheck.length; // Easier to compute length of list
    // Iterate over all possible combinations of i, j, k
    for (int i = 0; i < size; i++) {
      for (int j = i+1; j < size; j++) {
        for (int k = j+1; k < size; k++) {
          // Check if indices are the same
          if (i == j || j == k || i == k) {
            continue; // Skip if any indices are the same
          }
          // Check if the sum of the three numbers equals the target sum
          if (listToCheck[i] + listToCheck[j] + listToCheck[k] == sum) {
            results.add(new int[] { listToCheck[i], listToCheck[j], listToCheck[k] }); // Add to results
          }
        }
      }
    }
  }

  // Method to get the results
  public List<int[]> getResults() {
    return results;
  }
}
