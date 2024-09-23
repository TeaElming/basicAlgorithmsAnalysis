package assignment1.task3;

public class QuickUnion {

  private int[] parent;

  public QuickUnion(int size) {
    parent = new int[size];
    // The root is itself to begin with
    for (int i = 0; i < size; i++) {
      parent[i] = i;
    }
  }

  // Find op to ge the root of the element
  // If the x doesn't match the palcement, e.g. previously merged, we enter the while loop
  public int find(int x) {
    while (parent[x] != x) {
      x = parent[x];
    }
    return x;
  }

  // Union op to merge two sets
  public void union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);

    // Check that they are not already united
    if (rootX != rootY) {
      parent[rootY] = rootX; // Remember that U(x, y) makes x the new root/parent in teh simple version
    }
  }

  // Helper to check if two are connected or not
  public boolean connected(int x, int y) {
    return find(x) == find(y);
  }
}
