package models;

public class PremiumCustomer extends Customer {
  public static final double MINIMUM_BALANCE = 10000;

  /**
   * Creates a premium customer.
   *
   * @param customerId the unique customer id
   * @param details the customer's personal details
   */
  public PremiumCustomer(String customerId, CustomerDetails details) {
    super(customerId, details);
  }

  /**
   * Returns {@link CustomerType#PREMIUM}.
   *
   * @return the customer type
   */
  @Override
  public CustomerType getCustomerType() {
    return CustomerType.PREMIUM;
  }

  /**
   * Premium customers do not pay monthly fees.
   *
   * @return true
   */
  @Override
  public boolean hasWaivedFees() {
    return true;
  }
}
