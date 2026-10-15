package models;

public enum AccountType {
  SAVINGS("Savings", SavingsAccount.MINIMUM_BALANCE),
  CHECKING("Checking", 0);

  private final String displayName;
  private final double minimumOpeningDeposit;

  AccountType(String displayName, double minimumOpeningDeposit) {
    this.displayName = displayName;
    this.minimumOpeningDeposit = minimumOpeningDeposit;
  }

  /**
   * Returns the name shown to users.
   *
   * @return the display name
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Returns the smallest deposit allowed when opening this account type.
   *
   * @return the minimum opening deposit
   */
  public double getMinimumOpeningDeposit() {
    return minimumOpeningDeposit;
  }
}
