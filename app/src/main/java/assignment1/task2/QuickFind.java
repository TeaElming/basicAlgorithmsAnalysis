package assignment1.task2;

public class QuickFind {
  private int[] id;

  public QuickFind(int size) {
    id = new int[size];
    // The root is itself to begin with
    for (int i = 0; i < size; i++) {
      id[i] = i;
    }
  }

  // Find operation to get the set identifier of the element
  public int find(int x) {
    return id[x]; // Directly returns the set identifier (O(1) time complexity)
  }

  // Union operation to merge two sets
  public void union(int x, int y) {
    int idX = find(x); // Get the set identifier for x
    int idY = find(y); // Get the set identifier for y

    // If they are not already in the same set, merge them
    if (idX != idY) {
      // Update all elements with idX to have idY
      for (int i = 0; i < id.length; i++) {
        if (id[i] == idX) {
          id[i] = idY;
        }
      }
    }
  }

  // Helper to check if two are connected or not
  public boolean connected(int x, int y) {
    return find(x) == find(y);
  }
}
