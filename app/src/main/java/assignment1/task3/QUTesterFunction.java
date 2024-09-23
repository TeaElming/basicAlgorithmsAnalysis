package assignment1.task3;

public class QUTesterFunction {
  public static void main(String[] args) {
    QuickUnion qu = new QuickUnion(10);

    qu.union(1, 2);
    qu.union(2, 3);
    qu.union(4, 5);
    qu.union(6, 7);
    qu.union(5, 6);

    System.out.println(qu.connected(1, 3)); // true, since 1 and 3 are connected
    System.out.println(qu.connected(4, 7)); // true, since 4, 5, 6, 7 are connected
    System.out.println(qu.connected(0, 9)); // false, since 0 and 9 are not connected
  }

}
