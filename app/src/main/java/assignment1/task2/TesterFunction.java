package assignment1.task2;

public class TesterFunction {

  public static void main(String[] args) {
    QuickFind uf = new QuickFind(10); // Create a Union-Find with 10 elements (0 to 9)

    uf.union(1, 2);
    uf.union(2, 3);
    uf.union(4, 5);
    uf.union(6, 7);
    uf.union(5, 6);

    System.out.println(uf.connected(1, 3)); // true, since 1 and 3 are connected
    System.out.println(uf.connected(4, 7)); // true, since 4, 5, 6, 7 are connected
    System.out.println(uf.connected(0, 9)); // false, since 0 and 9 are not connected
  }

}
