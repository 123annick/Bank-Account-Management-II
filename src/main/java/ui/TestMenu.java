package ui;

import services.SelfTestRunner;
import services.TestResult;

public class TestMenu implements MenuAction {
  private final ConsoleOutput output;
  private final SelfTestRunner testRunner;

  /**
   * Creates the Run Tests menu.
   *
   * @param output prints messages
   * @param testRunner runs the built-in checks
   */
  public TestMenu(ConsoleOutput output, SelfTestRunner testRunner) {
    this.output = output;
    this.testRunner = testRunner;
  }

  /**
   * Returns the main menu title of this option.
   *
   * @return the title
   */
  @Override
  public String getTitle() {
    return "Run Tests";
  }

  /**
   * Runs the built-in checks and prints a JUnit-style summary.
   */
  @Override
  public void execute() {
    output.println("\nRunning core transaction tests...\n");
    TestResult[] results = testRunner.runAll();
    int passed = 0;
    for (TestResult result : results) {
      String status = result.passed() ? "PASSED" : "FAILED";
      output.println("Test: " + result.testName() + "() .......... " + status);
      if (result.passed()) {
        passed++;
      }
    }
    printSummary(passed, results.length);
  }

  private void printSummary(int passed, int total) {
    output.println("");
    if (passed == total) {
      output.printSuccess("All " + total + " tests passed successfully!");
    } else {
      output.printError((total - passed) + " of " + total + " tests failed.");
    }
    output.println("Full JUnit 5 suite: right-click src/test/java in IntelliJ > Run 'All Tests'.");
  }
}
