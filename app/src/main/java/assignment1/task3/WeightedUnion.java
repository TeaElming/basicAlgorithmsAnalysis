package assignment1.task3;

public class WeightedUnion {
  private int[] parent;
  private int[] rank;

  // Initialise for weighted similarly as QuickUnion but set rank to -1
  public WeightedUnion(int size) {
    parent = new int[size];
    rank = new int[size]; // Startign at -1, want to keep teh ranks negative to make it easier to
                          // distinguish

    for (int i = 0; i < size; i++) {
      parent[i] = i;
      rank[i] = -1;
    }
  }

  public int find(int x) {
    while (parent[x] != x) {
      x = parent[x];
    }
    return x;
  }

  // Helper to get rank/depth of tree
  public int getRank(int x) {
    int rootX = find(x);
    return rank[rootX];
  }

  public void unionWithWeight(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);

    if (rootX != rootY) {
      // Union by weight/rank
      if (rank[rootX] < rank[rootY]) { // rootX is larger tree
        parent[rootY] = rootX;
      } else if (rank[rootX] > rank[rootY]) { // rootY is larger tree
        parent[rootX] = rootY;
      } else { // If ranks are equal
        rank[rootX]--; // Only update rank of new root
        parent[rootY] = rootX; // Attach rootY to rootX

      }
    }
  }

  // Helper to check if two are connected or not
  public boolean connected(int x, int y) {
    return find(x) == find(y);
  }

}
