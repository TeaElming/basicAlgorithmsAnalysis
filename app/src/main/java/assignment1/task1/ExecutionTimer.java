package assignment1.task1;

import java.util.concurrent.Callable;

public class ExecutionTimer {

  // Measure execution time using a Callable task and a ResultHandler
  public static <T> long measureTime(Callable<T> task, ResultHandler<T> handler) {
    long startTime = System.nanoTime(); // Start time
    long duration = 0;
    try {
      T result = task.call(); // Execute the task
      long endTime = System.nanoTime(); // End time
      duration = endTime - startTime; // Calculate duration
      handler.handleResult(result, duration); // Handle the result and duration
    } catch (Exception e) {
      e.printStackTrace(); // Exception handling
    }
    return duration; // Return the duration
  }

  // Overloaded method to measure time without a specific result handler
  public static <T> long measureTime(Callable<T> task) {
    return measureTime(task, (result, duration) -> {
    }); // Default empty handler
  }

  // Measure execution time for a task multiple times and handle average
  public static <T> long measureTimeMultipleRuns(Callable<T> task, int runs, ResultHandler<T> handler) {
    long totalDuration = 0;
    for (int i = 0; i < runs; i++) {
      long startTime = System.nanoTime();
      long duration = 0;
      try {
        T result = task.call(); // Execute the task
        long endTime = System.nanoTime();
        duration = endTime - startTime;
        totalDuration += duration;
        handler.handleResult(result, duration); // Per-run handling
      } catch (Exception e) {
        e.printStackTrace(); // Exception handling
      }
    }
    long averageDuration = totalDuration / runs; // Calculate average
    return averageDuration; // Return the average duration
  }
}
