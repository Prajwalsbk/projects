import java.util.ArrayList;
import java.util.Scanner;

class ATM {
    private Account account;
    public ATM(Account account) {
        this.account = account;
    }
    public void showTransactionHistory() {
        account.printTransactionHistory();
    }
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
        } else if (account.getBalance() >= amount) {
            account.debit(amount);
            System.out.println("Successfully withdrew: $" + amount);
        } else {
            System.out.println("Insufficient funds!");
        }
    }
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
        } else {
            account.credit(amount);
            System.out.println("Successfully deposited: $" + amount);
        }
    }
    public void transfer(Account toAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
        } else if (account.getBalance() >= amount) {
            account.debit(amount);
            toAccount.credit(amount);
            System.out.println("Successfully transferred amount: $" + amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }
}
class User {
    private String userId;
    private String userPin;
    private Account account;

    public User(String userId, String userPin, Account account) {
        this.userId = userId;
        this.userPin = userPin;
        this.account = account;
    }
    public String getUserId() {
        return userId;
    }
    public String getUserPin() {
        return userPin;
    }
    public Account getAccount() {
        return account;
    }
}
class Account {
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }
    public double getBalance() {
        return balance;
    }
    public void credit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }
    public void debit(double amount) {
        balance -= amount;
        transactionHistory.add(new Transaction("Withdraw", amount));
    }
    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction r : transactionHistory) {
                System.out.println(r);
            }
        }
    }
}
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return "Transaction: " + type + ", Amount: $" + amount;
    }
}
 class ATMInterface {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Account account1 = new Account(5000); 
        User user1 = new User("12345", "54321", account1);
        User user2 =new User("1267","12378", account1);
        System.out.println("Welcome to the ATM System!");
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your User PIN: ");
        String userPin = scanner.nextLine();

        if (authenticateUser(user1, userId, userPin)) {
            ATM atm = new ATM(user1.getAccount());
            boolean q = false;
            while (!q) {
                System.out.println("\nATM Menu Interface:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Choose an option (1-5): ");
                int opt;
                while (true) {
                    try {
                        opt= Integer.parseInt(scanner.nextLine());
                        if (opt < 1 || opt > 5) {
                            throw new NumberFormatException();
                        }
                        break; 
                    } catch (NumberFormatException e) {
                        System.out.print("Invalid option! Please choose a number between 1 and 5: ");
                    }
                }
                switch (opt) {
                    case 1:
                        atm.showTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = getValidAmount();
                        atm.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = getValidAmount();
                        atm.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = getValidAmount();
                        Account account2 = new Account(1000); 
                        atm.transfer(account2, transferAmount);
                        break;
                    case 5:
                        q= true;
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option! Try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Please try again.");
        }
    }

    private static boolean authenticateUser(User user, String userId, String userPin) {
        return user.getUserId().equals(userId) && user.getUserPin().equals(userPin);
    }

    private static double getValidAmount() {
        double amount;
        while (true) {
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) {
                    throw new NumberFormatException();
                }
                return amount; 
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount!: ");
            }
        }
    }
}
