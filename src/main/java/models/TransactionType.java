package models;

public enum TransactionType {
  DEPOSIT(true),
  WITHDRAWAL(false),
  TRANSFER_IN(true),
  TRANSFER_OUT(false);

  private final boolean credit;

  TransactionType(boolean credit) {
    this.credit = credit;
  }

  /**
   * Tells whether this type adds money to an account.
   *
   * @return true for money coming in, false for money going out
   */
  public boolean isCredit() {
    return credit;
  }
}
