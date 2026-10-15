package models;

public interface InterestBearing {

  /**
   * Returns the yearly interest rate as a percentage.
   *
   * @return the interest rate, for example 3.5
   */
  double getInterestRate();

  /**
   * Calculates the interest earned in one year on the current balance.
   *
   * @return the yearly interest amount
   */
  double calculateInterest();
}
