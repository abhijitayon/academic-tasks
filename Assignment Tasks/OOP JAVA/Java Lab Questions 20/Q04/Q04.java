/*
Question 4: Bank Account and Transaction Management System
10 Marks
A bank offers Savings Accounts and Current Accounts. The bank wants to manage deposits, withdrawals, and account-specific charges.
Requirements:
Create an abstract class BankAccount containing private attributes accountNumber, accountHolderName, and balance.
Define common deposit and withdrawal operations with appropriate validation.
Create subclasses:
SavingsAccount: Minimum balance Tk. 1,000 and annual interest rate of 5%.
CurrentAccount: No minimum balance, but charges Tk. 20 for every successful withdrawal.
Create an abstract method calculateAnnualBenefit() that calculates annual interest for Savings Accounts and returns zero for Current Accounts.
Implement:
Open Savings and Current Accounts.
Deposit money into an account.
Withdraw money according to account-specific rules.
Transfer money from one account to another.
Display account details and transaction history.
Create a custom exception InsufficientBalanceException.
Special condition: A failed transfer must not deduct money from the sender's account.
Concepts: Abstraction, inheritance, polymorphism, encapsulation, custom exceptions, collections.
*/




import java.util.ArrayList;

// Custom exception
class InsufficientBalanceException extends Exception {
    InsufficientBalanceException(String message) {
        super(message);
    }
}

// Abstract parent class
abstract class BankAccount {
    private int accountNumber;
    private String accountHolderName;
    protected double balance;
    ArrayList<String> history = new ArrayList<String>();

    BankAccount(int number, String name, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Invalid balance");
        }

        this.accountNumber = number;
        this.accountHolderName = name;
        this.balance = balance;
    }

    void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }

        balance = balance + amount;
        history.add("Deposited: Tk. " + amount);
    }

    abstract void withdraw(double amount) throws InsufficientBalanceException;

    abstract double calculateAnnualBenefit();

    int getAccountNumber() {
        return accountNumber;
    }

    String getAccountHolderName() {
        return accountHolderName;
    }

    double getBalance() {
        return balance;
    }

    void displayDetails() {
        System.out.println("\nAccount Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: Tk. " + balance);
        System.out.println("Annual Benefit: Tk. " + calculateAnnualBenefit());

        System.out.println("Transaction History:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i));
        }
    }
}

// Savings account
class SavingsAccount extends BankAccount {
    SavingsAccount(int number, String name, double balance) {
        super(number, name, balance);
    }

    void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }

        if (balance - amount < 1000) {
            throw new InsufficientBalanceException("Minimum balance of Tk. 1000 required.");
        }

        balance = balance - amount;
        history.add("Withdrawn: Tk. " + amount);
    }

    double calculateAnnualBenefit() {
        return balance * 0.05;
    }
}

// Current account
class CurrentAccount extends BankAccount {
    CurrentAccount(int number, String name, double balance) {
        super(number, name, balance);
    }

    void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }

        if (balance < amount + 20) {
            throw new InsufficientBalanceException("Insufficient balance including withdrawal charge.");
        }

        balance = balance - amount - 20;
        history.add("Withdrawn: Tk. " + amount + " (Tk. 20 charge)");
    }

    double calculateAnnualBenefit() {
        return 0;
    }
}

// Bank class
class Bank {
    ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();

    void openAccount(BankAccount account) {
        accounts.add(account);
        System.out.println(account.getAccountHolderName() + "'s account opened.");
    }

    BankAccount searchAccount(int number) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber() == number) {
                return accounts.get(i);
            }
        }

        return null;
    }

    void transfer(int senderNumber, int receiverNumber, double amount)
            throws InsufficientBalanceException {

        BankAccount sender = searchAccount(senderNumber);
        BankAccount receiver = searchAccount(receiverNumber);

        if (sender == null || receiver == null) {
            System.out.println("Account not found.");
            return;
        }

        sender.withdraw(amount);
        receiver.deposit(amount);

        sender.history.add("Transferred Tk. " + amount + " to account " + receiverNumber);
        receiver.history.add("Received Tk. " + amount + " from account " + senderNumber);

        System.out.println("Transfer successful.");
    }

    void displayAllAccounts() {
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).displayDetails();
        }
    }
}

// Main class
public class Q04 {
    public static void main(String[] args) throws InsufficientBalanceException {
        Bank bank = new Bank();

        bank.openAccount(new SavingsAccount(101, "Rahim", 10000));
        bank.openAccount(new CurrentAccount(102, "Karim", 15000));

        BankAccount a1 = bank.searchAccount(101);
        BankAccount a2 = bank.searchAccount(102);

        a1.deposit(2000);

        a1.withdraw(3000);
        a2.withdraw(2000);

        bank.transfer(101, 102, 2000);

        System.out.println("\n===== ALL ACCOUNT DETAILS =====");
        bank.displayAllAccounts();
    }
}