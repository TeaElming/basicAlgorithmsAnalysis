package assignment1.task1;

public class TesterFunction {

  public static void main(String[] args) {
    // Call each example method to run the tests
    System.out.println("Simple Example...");
    simpleExample();

    System.out.println("Custom Results Handler...");
    customResultHandler();

    System.out.println("Multiple Runs...");
    multipleRunsExample();
  }

  // Example 1: Simple usage with a default handler
  public static void simpleExample() {
    long duration = ExecutionTimer.measureTime(() -> {
      // Simulate a task by sleeping for 500 milliseconds
      Thread.sleep(500);
      return "Task completed"; // Return value for the task
    });
    System.out.println("Execution time: " + duration + " nanoseconds");
  }

  // Example 2: Custom result handler with a specific task
  public static void customResultHandler() {
    ExecutionTimer.measureTime(() -> {
      int sum = 0;
      for (int i = 0; i < 1000; i++) {
        sum += i;
      }
      return sum; // Return the sum as the task result
    }, (result, execDuration) -> { // Rename parameter to avoid conflict
      // Custom handling: print result and execDuration
      System.out.println("Execution time: " + execDuration + " nanoseconds");
      System.out.println("Task result: " + result);
    });
  }

  // Example 3: Measure execution time of a task over multiple runs
  public static void multipleRunsExample() {
    long averageDuration = ExecutionTimer.measureTimeMultipleRuns(() -> {
      for (int i = 0; i < 1000; i++) {
        Math.sqrt(i); // Perform some computation
      }
      return null; // No return value needed for this task
    }, 10, (result, execDuration) -> { // Rename parameter to avoid conflict
      // Custom handling for each run: print execDuration
    });
    System.out.println("Average execution time over multiple runs: " + averageDuration + " nanoseconds");
  }
}
