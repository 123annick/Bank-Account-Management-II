package utils;

import models.exceptions.InvalidAmountException;

public final class ValidationUtils {

  private ValidationUtils() {
  }

  /**
   * Checks that an amount is a real number greater than zero.
   *
   * @param amount the amount to check
   * @throws InvalidAmountException when the amount is zero, negative or not a real number
   */
  public static void validateAmount(double amount) throws InvalidAmountException {
    if (!Double.isFinite(amount) || amount <= 0) {
      throw new InvalidAmountException("Invalid amount. Amount must be greater than 0.");
    }
  }

  /**
   * Checks that an opening deposit reaches the required minimum.
   *
   * @param amount the deposit to check
   * @param minimum the smallest allowed deposit
   * @param subject who the rule applies to, for example "Savings accounts"
   * @throws InvalidAmountException when the deposit is below the minimum
   */
  public static void validateInitialDeposit(double amount, double minimum, String subject)
      throws InvalidAmountException {
    if (amount < minimum) {
      throw new InvalidAmountException(subject + " need an initial deposit of at least "
          + FormatUtils.currency(minimum) + ".");
    }
  }
}
