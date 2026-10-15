package models;

public enum CustomerType {
  REGULAR("Regular", 0),
  PREMIUM("Premium", PremiumCustomer.MINIMUM_BALANCE);

  private final String displayName;
  private final double minimumOpeningDeposit;

  CustomerType(String displayName, double minimumOpeningDeposit) {
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
   * Returns the smallest deposit this customer type must make when opening an account.
   *
   * @return the minimum opening deposit
   */
  public double getMinimumOpeningDeposit() {
    return minimumOpeningDeposit;
  }
}
