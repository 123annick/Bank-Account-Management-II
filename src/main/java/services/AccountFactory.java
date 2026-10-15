package services;

import models.Account;
import models.AccountType;
import models.CheckingAccount;
import models.Customer;
import models.CustomerDetails;
import models.CustomerType;
import models.PremiumCustomer;
import models.RegularCustomer;
import models.SavingsAccount;
import models.exceptions.InvalidAmountException;
import utils.IdGenerator;
import utils.ValidationUtils;

public class AccountFactory {
  private final IdGenerator accountIds = new IdGenerator("ACC");
  private final IdGenerator customerIds = new IdGenerator("CUS");

  /**
   * Checks the opening rules, then creates the customer and the account with new ids.
   *
   * @param details the customer's personal details
   * @param customerType regular or premium
   * @param accountType savings or checking
   * @param initialDeposit the opening deposit
   * @return the new account
   * @throws InvalidAmountException when the deposit breaks an opening rule
   */
  public Account createAccount(CustomerDetails details, CustomerType customerType,
      AccountType accountType, double initialDeposit) throws InvalidAmountException {
    validateInitialDeposit(customerType, accountType, initialDeposit);
    Customer customer = createCustomer(details, customerType);
    return buildAccount(customer, accountType, initialDeposit);
  }

  private void validateInitialDeposit(CustomerType customerType, AccountType accountType,
      double initialDeposit) throws InvalidAmountException {
    ValidationUtils.validateAmount(initialDeposit);
    ValidationUtils.validateInitialDeposit(initialDeposit,
        customerType.getMinimumOpeningDeposit(), customerType.getDisplayName() + " customers");
    ValidationUtils.validateInitialDeposit(initialDeposit,
        accountType.getMinimumOpeningDeposit(), accountType.getDisplayName() + " accounts");
  }

  private Customer createCustomer(CustomerDetails details, CustomerType customerType) {
    String customerId = customerIds.next();
    if (customerType == CustomerType.PREMIUM) {
      return new PremiumCustomer(customerId, details);
    }
    return new RegularCustomer(customerId, details);
  }

  private Account buildAccount(Customer customer, AccountType accountType, double deposit) {
    String accountNumber = accountIds.next();
    if (accountType == AccountType.SAVINGS) {
      return new SavingsAccount(accountNumber, customer, deposit);
    }
    return new CheckingAccount(accountNumber, customer, deposit);
  }
}
