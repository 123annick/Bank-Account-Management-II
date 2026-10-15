package utils;

public class IdGenerator {
  private final String prefix;
  private int counter;

  /**
   * Creates a generator that produces ids such as ACC001, ACC002.
   *
   * @param prefix the text placed before the number
   */
  public IdGenerator(String prefix) {
    this.prefix = prefix;
  }

  /**
   * Returns the next id and moves the counter forward.
   *
   * @return the next id
   */
  public String next() {
    counter++;
    return format(counter);
  }

  /**
   * Returns the id that {@link #next()} will give, without moving the counter.
   *
   * @return the upcoming id
   */
  public String peek() {
    return format(counter + 1);
  }

  private String format(int number) {
    return String.format("%s%03d", prefix, number);
  }
}
