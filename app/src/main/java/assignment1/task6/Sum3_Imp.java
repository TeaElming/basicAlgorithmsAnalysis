package assignment1.task6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sum3_Imp {

  private List<int[]> results = new ArrayList<>(); // List to store combinations of 3 numbers

  public Sum3_Imp(int[] listToCheck, int targetSum) {
    // Sort the array to use the two-pointer technique effectively - doing it before
    // to not have to repeat it every time
    Arrays.sort(listToCheck);
    int size = listToCheck.length;

    // Iterate through each element and use 2Sum to find the required pairs
    for (int i = 0; i < size - 2; i++) { // don't forget to subtract 2 for the final pair in the partial list
      // Skip duplicates, makes it easier to avoid duplicate sums when going through
      // longer lists
      // E.g. [-4, -4, -1, 0, 1, 2, 3, 3] we only want the first -4 once
      if (i > 0 && listToCheck[i] == listToCheck[i - 1]) {
        continue;
      }

      // Calculate the sum to find using 2Sum
      int sum2target = targetSum - listToCheck[i];

      // Slice the array to pass elements from index i + 1 onwards
      int[] partialList = Arrays.copyOfRange(listToCheck, i + 1, size);

      // Find pairs that sum up to the remaining to make up the target sum
      List<int[]> pairs = sum2Part(partialList, sum2target);

      // Combine the fixed element with each pair found
      for (int[] pair : pairs) {
        results.add(new int[] { listToCheck[i], pair[0], pair[1] });
      }
    }
  }

  public List<int[]> sum2Part(int[] partialListToCheck, int targetSum) {
    List<int[]> results2sum = new ArrayList<>(); // List to store combinations of 2 numbers
    int size = partialListToCheck.length;

    int fp = 0; // front pointer
    int ep = size - 1; // end pointer

    while (fp < ep) {
      int sumValue = partialListToCheck[fp] + partialListToCheck[ep];
      if (sumValue == targetSum) {
        results2sum.add(new int[] { partialListToCheck[fp], partialListToCheck[ep] });
        fp++;
        ep--;

        // Skip duplicates for both pointers
        while (fp < ep && partialListToCheck[fp] == partialListToCheck[fp - 1]) { // added a check to avoid duplicates
                                                                                  // in the 2sum as well
          fp++;
        }
        while (fp < ep && partialListToCheck[ep] == partialListToCheck[ep + 1]) { // added a check to avoid duplicates
                                                                                  // in the 2sum as well
          ep--;
        }
      } else if (sumValue < targetSum) {
        fp++;
      } else {
        ep--;
      }
    }
    return results2sum;
  }

  // Method to get the results
  public List<int[]> getResults() {
    return results;
  }

}
