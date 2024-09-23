package assignment1.task3;

public class WUTesterFunction {

  public static void main(String[] args) {
    WeightedUnion wu = new WeightedUnion(10); // Create a Weighted Union with 10 elements (0 to 9)

    // Check initial states
    printParentsAndRanks(wu);

    // Perform union operations and verify results
    performAndCheckUnion(wu, 1, 2);
    performAndCheckUnion(wu, 2, 3);
    performAndCheckUnion(wu, 4, 5);
    performAndCheckUnion(wu, 6, 7);
    performAndCheckUnion(wu, 5, 6); // This should connect 4, 5, 6, 7 together
    performAndCheckUnion(wu, 3, 7); // This should connect all previous elements together

    // Final verification
    printParentsAndRanks(wu);

    // Check connections
    checkConnection(wu, 1, 3, true); // Should be true, since 1, 2, 3, and 7 are all connected
    checkConnection(wu, 4, 7, true); // Should be true, since 4, 5, 6, and 7 are all connected
    checkConnection(wu, 0, 9, false); // Should be false, since 0 and 9 are not connected
    checkConnection(wu, 1, 7, true); // Should be true, now 1-3 and 4-7 are all connected through 3-7 union
  }

  // Helper function to print current parents and ranks
  private static void printParentsAndRanks(WeightedUnion wu) {
    System.out.println("Current state:");
    System.out.print("Parents: ");
    for (int i = 0; i < 10; i++) {
      System.out.print(wu.find(i) + "  ");
    }
    System.out.println();

    System.out.print("Ranks:   ");
    for (int i = 0; i < 10; i++) {
      System.out.print(wu.getRank(i) + " ");
    }
    System.out.println("\n");
  }

  // Helper function to perform a union and check parents and ranks after
  private static void performAndCheckUnion(WeightedUnion wu, int x, int y) {
    System.out.println("Before union of " + x + " and " + y + ":");
    printParentsAndRanks(wu);

    wu.unionWithWeight(x, y);

    System.out.println("After union of " + x + " and " + y + ":");
    printParentsAndRanks(wu);
  }

  // Helper function to check if two elements are connected and print the result
  private static void checkConnection(WeightedUnion wu, int x, int y, boolean expected) {
    boolean result = wu.connected(x, y);
    System.out.println("Checking connection between " + x + " and " + y + ": " + result);
    if (result == expected) {
      System.out.println("Test passed.");
    } else {
      System.out.println("Test failed. Expected " + expected + " but got " + result);
    }
  }
}
