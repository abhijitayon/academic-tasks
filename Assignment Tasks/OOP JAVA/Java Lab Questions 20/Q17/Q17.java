/*
Question 17: Online Banking System with File Handling
10 Marks
A commercial bank needs an application that maintains customer accounts and preserves transaction information after the program is closed.
Requirements:
Create an abstract class Account containing private attributes accountNumber, holderName, and balance.
Create:
SavingsAccount
CurrentAccount
Implement an abstract method calculateServiceCharge().
Policies:
Savings Account: No service charge.
Current Account: Tk. 100 monthly service charge, charged only when explicitly requested by an administrator.
Develop the following operations:
Create new accounts.
Deposit and withdraw money.
Transfer funds between accounts.
Save account information to a file.
Load previously saved account information when the program starts.
Display transaction details.
Use Java file handling with BufferedReader/BufferedWriter or object serialization.
Implement exception handling for invalid transactions, malformed records, and file-related failures.
Concepts: All OOP pillars, file I/O, collections, exception handling.
*/





import java.io.*;
import java.util.ArrayList;

abstract class Account {
    private String accountNumber, holderName;
    private double balance;

    Account(String n, String name, double b) {
        accountNumber = n;
        holderName = name;
        balance = b;
    }

    abstract double calculateServiceCharge();

    String getNumber() { return accountNumber; }
    String getName() { return holderName; }
    double getBalance() { return balance; }

    void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount.");
        balance += amount;
    }

    void withdraw(double amount) {
        if (amount <= 0 || amount > balance)
            throw new IllegalArgumentException("Insufficient balance.");
        balance -= amount;
    }
}

class SavingsAccount extends Account {
    SavingsAccount(String n, String name, double b) {
        super(n, name, b);
    }

    double calculateServiceCharge() { return 0; }
}

class CurrentAccount extends Account {
    CurrentAccount(String n, String name, double b) {
        super(n, name, b);
    }

    double calculateServiceCharge() { return 100; }
}

class Bank {
    ArrayList<Account> accounts = new ArrayList<>();
    ArrayList<String> history = new ArrayList<>();

    Account find(String number) {
        for (Account a : accounts)
            if (a.getNumber().equals(number)) return a;
        return null;
    }

    void createAccount(Account a) {
        if (find(a.getNumber()) == null) {
            accounts.add(a);
            history.add("Account created: " + a.getNumber());
        }
    }

    void deposit(String id, double amount) {
        Account a = find(id);
        if (a == null) throw new IllegalArgumentException("Account not found.");

        a.deposit(amount);
        history.add("Deposited: " + amount);
    }

    void withdraw(String id, double amount) {
        Account a = find(id);
        if (a == null) throw new IllegalArgumentException("Account not found.");

        a.withdraw(amount);
        history.add("Withdrawn: " + amount);
    }

    void transfer(String from, String to, double amount) {
        Account a = find(from), b = find(to);

        if (a == null || b == null || a == b)
            throw new IllegalArgumentException("Invalid account.");

        a.withdraw(amount);
        b.deposit(amount);
        history.add("Transferred: " + amount + " from " + from + " to " + to);
    }

    void chargeService(String id) {
        Account a = find(id);
        if (a == null) throw new IllegalArgumentException("Account not found.");

        a.withdraw(a.calculateServiceCharge());
        history.add("Service charge: " + id);
    }

    void save() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("bank.txt"));

        for (Account a : accounts) {
            String type = (a instanceof SavingsAccount) ? "S" : "C";
            out.write(type + "," + a.getNumber() + "," + a.getName()
                    + "," + a.getBalance());
            out.newLine();
        }

        for (String t : history) {
            out.write("T," + t);
            out.newLine();
        }

        out.close();
    }

    void load() throws IOException {
        File file = new File("bank.txt");
        if (!file.exists()) return;

        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;

        while ((line = in.readLine()) != null) {
            String[] data = line.split(",");

            try {
                if (data[0].equals("S") && data.length == 4)
                    accounts.add(new SavingsAccount(data[1], data[2],
                            Double.parseDouble(data[3])));

                else if (data[0].equals("C") && data.length == 4)
                    accounts.add(new CurrentAccount(data[1], data[2],
                            Double.parseDouble(data[3])));

                else if (data[0].equals("T") && data.length == 2)
                    history.add(data[1]);
            }
            catch (RuntimeException e) {
                System.out.println("Invalid record skipped.");
            }
        }

        in.close();
    }

    void display() {
        for (Account a : accounts)
            System.out.println(a.getNumber() + " | " + a.getName()
                    + " | Balance: " + a.getBalance());

        System.out.println("\nTransaction History:");
        for (String t : history)
            System.out.println(t);
    }
}

public class Q17 {
    public static void main(String[] args) throws IOException {
        Bank bank = new Bank();

        bank.load();

        if (bank.accounts.isEmpty()) {
            bank.createAccount(new SavingsAccount("101", "Ratul", 10000));
            bank.createAccount(new CurrentAccount("102", "Ayon", 20000));
        }

        bank.deposit("101", 2000);
        bank.withdraw("102", 1000);
        bank.transfer("101", "102", 3000);
        bank.chargeService("102");

        bank.save();
        bank.display();
    }
}