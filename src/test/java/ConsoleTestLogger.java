import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class ConsoleTestLogger implements TestWatcher, AfterAllCallback {
  private int passed;
  private int failed;

  /**
   * Prints a PASSED line after a test succeeds.
   *
   * @param context information about the test that ran
   */
  @Override
  public void testSuccessful(ExtensionContext context) {
    passed++;
    System.out.println("Test: " + context.getDisplayName() + " .......... PASSED");
  }

  /**
   * Prints a FAILED line with the reason after a test fails.
   *
   * @param context information about the test that ran
   * @param cause why the test failed
   */
  @Override
  public void testFailed(ExtensionContext context, Throwable cause) {
    failed++;
    System.out.println("Test: " + context.getDisplayName() + " .......... FAILED ("
        + cause.getMessage() + ")");
  }

  /**
   * Prints a summary line after every test of the class has run.
   *
   * @param context information about the test class
   */
  @Override
  public void afterAll(ExtensionContext context) {
    String testClass = context.getDisplayName();
    if (failed == 0) {
      System.out.println("✓ All " + passed + " tests passed successfully in " + testClass + "!\n");
    } else {
      System.out.println("✗ " + failed + " of " + (passed + failed) + " tests failed in "
          + testClass + ".\n");
    }
  }
}
