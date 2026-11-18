import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Simple console banking application.
 * Features: account creation, deposit, withdrawal, balance check.
 *
 * Run:  javac BankApp.java && java BankApp
 *   or: java BankApp.java   (Java 11+)
 */
public class BankApp {

    // ---------------- Custom exception ----------------
    static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    // ---------------- Account ----------------
    static class Account {
        private final int accountNumber;
        private final String holderName;
        private double balance;

        Account(int accountNumber, String holderName, double openingBalance) {
            this.accountNumber = accountNumber;
            this.holderName = holderName;
            this.balance = openingBalance;
        }

        int getAccountNumber() { return accountNumber; }
        String getHolderName() { return holderName; }
        double getBalance()    { return balance; }

        void deposit(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be greater than zero.");
            }
            balance += amount;
        }

        void withdraw(double amount) throws InsufficientFundsException {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
            }
            if (amount > balance) {
                throw new InsufficientFundsException(
                        String.format("Insufficient funds. Available balance: %.2f", balance));
            }
            balance -= amount;
        }
    }

    // ---------------- Bank ----------------
    static class Bank {
        private final Map<Integer, Account> accounts = new HashMap<>();
        private int nextAccountNumber = 1001;

        Account createAccount(String name, double openingBalance) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty.");
            }
            if (openingBalance < 0) {
                throw new IllegalArgumentException("Opening balance cannot be negative.");
            }
            Account acc = new Account(nextAccountNumber++, name.trim(), openingBalance);
            accounts.put(acc.getAccountNumber(), acc);
            return acc;
        }

        Account findAccount(int accountNumber) {
            Account acc = accounts.get(accountNumber);
            if (acc == null) {
                throw new IllegalArgumentException("Account " + accountNumber + " not found.");
            }
            return acc;
        }
    }

    // ---------------- Console UI ----------------
    private static final Scanner scanner = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        System.out.println("===== Welcome to Simple Bank =====");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> checkBalance();
                    case 5 -> {
                        System.out.println("Thank you for banking with us. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please select 1-5.");
                }
            } catch (InsufficientFundsException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--------------------------------");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Check Balance");
        System.out.println("5. Exit");
        System.out.println("--------------------------------");
    }

    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        double opening = readDouble("Enter opening balance: ");

        Account acc = bank.createAccount(name, opening);
        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + acc.getAccountNumber());
        System.out.println("Holder Name   : " + acc.getHolderName());
        System.out.printf("Balance       : %.2f%n", acc.getBalance());
    }

    private static void deposit() {
        int accNo = readInt("Enter account number: ");
        Account acc = bank.findAccount(accNo);
        double amount = readDouble("Enter amount to deposit: ");
        acc.deposit(amount);
        System.out.printf("Deposited %.2f. New balance: %.2f%n", amount, acc.getBalance());
    }

    private static void withdraw() throws InsufficientFundsException {
        int accNo = readInt("Enter account number: ");
        Account acc = bank.findAccount(accNo);
        double amount = readDouble("Enter amount to withdraw: ");
        acc.withdraw(amount);
        System.out.printf("Withdrew %.2f. New balance: %.2f%n", amount, acc.getBalance());
    }

    private static void checkBalance() {
        int accNo = readInt("Enter account number: ");
        Account acc = bank.findAccount(accNo);
        System.out.println("Account Number: " + acc.getAccountNumber());
        System.out.println("Holder Name   : " + acc.getHolderName());
        System.out.printf("Balance       : %.2f%n", acc.getBalance());
    }

    // ---------------- Input helpers ----------------
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
