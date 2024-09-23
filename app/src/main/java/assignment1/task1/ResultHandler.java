package assignment1.task1;

// Interface allowing user to handle the timing results however they like, print, store etc
@FunctionalInterface
public interface ResultHandler<T> {
  void handleResult(T result, long duration);
}
