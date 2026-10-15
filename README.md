# Bank Account Management System II — Week 2

A console-based banking application in Java. Week 2 refactors the Week 1 code for clean
code and the SOLID principles, adds custom exceptions, a JUnit 5 test suite, and a Git
workflow with feature branches, merges and cherry-picks.

**Author:** Annick Ujeneza
**Week 1 repository:** https://github.com/123annick/Bank-Account-Management

## Features

- **Manage Accounts** – create savings or checking accounts for regular or premium customers, view all accounts with totals
- **Perform Transactions** – deposit, withdraw and transfer, each with a confirmation prompt
- **Generate Account Statements** – transactions sorted newest first, totals and net change with 2-decimal precision
- **Run Tests** – runs the core transaction checks from the menu and prints a JUnit-style summary
- **Error handling** – invalid input never crashes the program; clear `❌ Error:` messages and retry prompts

## Account and customer rules

| Type | Rules |
|---|---|
| Savings | 3.5% annual interest, $500 minimum balance (also the minimum opening deposit) |
| Checking | $1,000 overdraft limit, $10 monthly fee |
| Regular customer | Standard services |
| Premium customer | Monthly fees waived, $10,000 minimum opening deposit |

## Custom exceptions

All custom exceptions extend `BankException`, which extends `Exception` (checked exceptions).

| Exception | Thrown when |
|---|---|
| `InvalidAmountException` | Amount is zero, negative, not a number, or below an opening minimum |
| `InsufficientFundsException` | A savings withdrawal would leave less than $500 |
| `OverdraftExceededException` | A checking withdrawal would go below -$1,000 |
| `InvalidAccountException` | Account number not found, or transfer to the same account |
| `StorageFullException` | The account array (50) or transaction array (200) is full |

Wrong menu input (for example letters) raises `InputMismatchException`, which `ConsoleInput`
catches and turns into a retry prompt.

## Project structure

```
Bank-Account-Management-II/
├── pom.xml                         Maven build file (Java 17, JUnit 5)
├── .editorconfig                   Google Java Style indentation for IntelliJ
├── src/
│   ├── main/java/
│   │   ├── Main.java               Builds all objects and starts the app
│   │   ├── models/                 Account, SavingsAccount, CheckingAccount, Customer,
│   │   │   │                       RegularCustomer, PremiumCustomer, Transaction,
│   │   │   │                       CustomerDetails, AccountType, CustomerType,
│   │   │   │                       TransactionType, Transactable, InterestBearing
│   │   │   └── exceptions/         BankException, InvalidAmountException,
│   │   │                           InsufficientFundsException, OverdraftExceededException,
│   │   │                           InvalidAccountException, StorageFullException
│   │   ├── services/               AccountRepository, AccountManager, AccountFactory,
│   │   │                           TransactionHistory, TransactionManager,
│   │   │                           StatementGenerator, SampleDataLoader,
│   │   │                           SelfTestRunner, TestResult
│   │   ├── utils/                  ValidationUtils, FormatUtils, IdGenerator,
│   │   │                           TransactionSorter
│   │   └── ui/                     BankConsoleApp, MenuAction, AccountMenu,
│   │                               TransactionMenu, StatementMenu, TestMenu,
│   │                               AccountSelector, ConsoleInput, ConsoleOutput
│   └── test/java/                  AccountTest, TransactionManagerTest, ExceptionTest,
│                                   AccountManagerTest, StatementGeneratorTest,
│                                   TransactionSorterTest, TestData, ConsoleTestLogger
├── docs/
│   └── git-workflow.md
└── README.md
```

The code uses the Maven standard layout (`src/main/java` and `src/test/java`) so that IntelliJ
and JUnit 5 work without extra setup. The packages follow the lab structure (`models`,
`models.exceptions`, `services`, `utils`), plus a `ui` package that keeps console input and
output out of the models and services.

## What changed from Week 1

| Week 1 | Week 2 |
|---|---|
| `processTransaction(amount, "DEPOSIT")` with text types | `deposit()` / `withdraw()` and a `TransactionType` enum |
| Methods returned `false` on failure | Methods throw custom exceptions with clear messages |
| Models printed themselves with `System.out` | Models only hold data and rules; `ui` classes print |
| `instanceof PremiumCustomer` to waive fees | `customer.hasWaivedFees()` (polymorphism) |
| Static counters inside constructors | `IdGenerator` objects in `AccountFactory` and `TransactionManager` |
| `withdraw()` copied in both account classes | One `withdraw()` in `Account`; subclasses only add their rule |
| `Main` had about 300 lines | `Main` only wires objects; each menu is its own class |
| Money formatting repeated everywhere | `FormatUtils` (DRY) |
| No tests | 49 JUnit 5 tests |

## SOLID principles in this project

| Principle | Where |
|---|---|
| Single Responsibility | `AccountManager` stores accounts, `TransactionManager` moves money, `StatementGenerator` builds statements, `ConsoleInput` reads, `ConsoleOutput` prints |
| Open/Closed | New account types extend `Account` and override `validateWithdrawalRules()`; new main menu options implement `MenuAction` without changing `BankConsoleApp` |
| Liskov Substitution | Any `Account` can be passed to `TransactionManager`; every subclass keeps the same `withdraw()` promise |
| Interface Segregation | Small interfaces: `Transactable`, `InterestBearing` (only savings), `TransactionHistory` (read-only for statements), `MenuAction` |
| Dependency Inversion | Menus depend on `AccountRepository`, `StatementGenerator` depends on `TransactionHistory`; `Main` injects the real classes through constructors |

## Setup in IntelliJ IDEA

**Requirements:** IntelliJ IDEA (Community or Ultimate) and JDK 17 or newer.

1. Clone the repository: **File > New > Project from Version Control**, paste the GitHub URL.
2. IntelliJ detects `pom.xml` and imports it as a Maven project. If asked, click **Load Maven Project**.
3. Wait for Maven to download JUnit 5 (bottom status bar).
4. Check **File > Project Structure > Project SDK** is JDK 17 or newer.
5. Code style: IntelliJ reads `.editorconfig` automatically, so **Code > Reformat Code** keeps the Google Java Style 2-space indentation.

## Running the application

Open `src/main/java/Main.java` and click the green **Run** arrow next to `main`.

The app starts with the five Week 1 sample accounts (ACC001 – ACC005), so new accounts start at
ACC006. To start empty, edit the run configuration (**Run > Edit Configurations > Program
arguments**) and type `--empty`.

## Running the tests

- **All tests:** right-click `src/test/java` > **Run 'All Tests'**.
- **One class:** open `AccountTest`, `TransactionManagerTest` or `ExceptionTest` and click the green arrow next to the class name.
- **From the menu:** run the app and choose **4. Run Tests** for the quick in-app summary.
- **Maven (optional):** if Maven is installed, `mvn test` runs the same suite.

`@BeforeEach` creates fresh accounts before every test, so tests never affect each other.
`ConsoleTestLogger` (a JUnit `TestWatcher` extension) prints each result to the console.

## Test results

49 tests in 6 classes, all passing:

| Test class | Tests | Covers |
|---|---|---|
| `AccountTest` | 14 | deposit, withdraw, minimum balance, overdraft, fees, interest, rounding |
| `TransactionManagerTest` | 12 | deposit, withdraw, transfer, transaction records, IDs, full log |
| `ExceptionTest` | 10 | every custom exception and its message |
| `AccountManagerTest` | 5 | linear search, totals, insertion order, ID generation |
| `StatementGeneratorTest` | 4 | empty statements, newest first, totals, 2-decimal format |
| `TransactionSorterTest` | 4 | insertion sort order, ties, original array untouched |

Console output from `ConsoleTestLogger` (shortened):

```
Test: depositUpdatesBalance() .......... PASSED
Test: withdrawBelowMinimumThrowsException() .......... PASSED
Test: overdraftWithinLimitAllowed() .......... PASSED
Test: overdraftExceedThrowsException() .......... PASSED
...
✓ All 14 tests passed successfully in AccountTest!

Test: transferUpdatesBothAccounts() .......... PASSED
Test: transferWithInsufficientFundsChangesNothing() .......... PASSED
...
✓ All 12 tests passed successfully in TransactionManagerTest!
```

In-app **Run Tests** output:

```
Running core transaction tests...

Test: depositUpdatesBalance() .......... PASSED
Test: negativeDepositThrowsException() .......... PASSED
Test: withdrawBelowMinimumThrowsException() .......... PASSED
Test: overdraftWithinLimitAllowed() .......... PASSED
Test: overdraftExceedThrowsException() .......... PASSED
Test: transferUpdatesBothAccounts() .......... PASSED

✓ All 6 tests passed successfully!
```

## Data structures and algorithms

- Accounts and transactions are stored in fixed-size arrays (50 and 200) with a counter.
- `AccountManager.findAccount()` uses **linear search**, O(n).
- `TransactionSorter` uses **insertion sort** to order transactions newest first, O(n²) worst case, O(n) when the array is already nearly sorted (which it is, because transactions are recorded in time order).
- `getAllAccounts()` and `getTransactionsFor()` return copies, so callers cannot change the stored arrays.

## Git workflow

Full details, with every command and commit hash, are in [`docs/git-workflow.md`](docs/git-workflow.md).

### Branches

| Branch | Purpose |
|---|---|
| `main` | Stable code; receives merges |
| `feature/refactor` | Maven layout, refactored models and services, insertion sort |
| `feature/exceptions` | Custom exceptions, validation and the console UI |
| `feature/testing` | JUnit 5 tests, rounding fix, Run Tests menu option |
| `feature/error-handling` | NaN/infinity validation fix and cherry-picked rounding fix |

### Commands used

```bash
git checkout -b feature/refactor
git add .
git commit -m "Refactored AccountManager and TransactionManager"

git checkout -b feature/exceptions
git commit -m "Add custom exception hierarchy and ValidationUtils"
git checkout main
git merge --no-ff feature/exceptions

git checkout -b feature/testing
git add src/test/java
git commit -m "Add JUnit 5 tests for deposit, withdraw and transfer"
git cherry-pick -x c47c6b0

git checkout -b feature/error-handling main
git cherry-pick -x 9da543f

git checkout main
git merge --no-ff feature/testing
git merge --no-ff feature/error-handling
git push -u origin --all
```

### Pushing this repository to GitHub

1. On GitHub create a new **empty public** repository named `Bank-Account-Management-II` (no README, no .gitignore).
2. In IntelliJ open **View > Tool Windows > Terminal** in the project folder and run:

```bash
git remote add origin https://github.com/123annick/Bank-Account-Management-II.git
git push -u origin --all
```

3. On GitHub, check that all five branches and the commit history appear.

## Ready for Week 3

- `AccountRepository` is an interface, so a file-based or Collections-based repository can replace the array version without changing the menus.
- `TransactionHistory` lets the statement code read transactions from any future storage.
- Arrays are hidden inside `AccountManager` and `TransactionManager`, so switching to `ArrayList` or `HashMap` changes only those classes.
