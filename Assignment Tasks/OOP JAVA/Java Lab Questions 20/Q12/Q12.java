/*
Question 12: Digital Wallet and Money Transfer System
10 Marks
A fintech company provides Personal Wallets and Merchant Wallets. The company allows users to deposit, withdraw, and transfer money.
Requirements:
Create an abstract class Wallet containing private attributes walletId, ownerName, and balance.
Create two subclasses:
PersonalWallet: Money transfers incur a 1% charge.
MerchantWallet: Money transfers incur a 0.5% charge.
Implement an abstract method calculateTransferCharge(double amount).
Develop a WalletService class maintaining:
HashMap<String, Wallet>
Implement:
Register wallets.
Deposit money.
Withdraw money.
Transfer money between wallets.
Display transaction history.
Reject transfers when the sender's balance cannot cover both the transfer amount and charge.
Special condition: A transfer must update both wallets consistently; failed transactions must leave both balances unchanged.
Concepts: Encapsulation, inheritance, abstraction, polymorphism, HashMap, custom exceptions.
*/





import java.util.ArrayList; //h
import java.util.HashMap;

class WalletException extends Exception {
    WalletException(String message) {
        super(message);
    }
}

abstract class Wallet {
    private String walletId, ownerName;
    private double balance;

    Wallet(String id, String name, double balance) {
        walletId = id;
        ownerName = name;
        this.balance = balance;
    }

    abstract double calculateTransferCharge(double amount);

    String getId() { return walletId; }
    String getOwner() { return ownerName; }
    double getBalance() { return balance; }

    void deposit(double amount) {
        balance += amount;
    }

    void withdraw(double amount) throws WalletException {
        if (amount <= 0 || balance < amount)
            throw new WalletException("Insufficient balance.");

        balance -= amount;
    }
}

class PersonalWallet extends Wallet {
    PersonalWallet(String id, String name, double balance) {
        super(id, name, balance);
    }

    double calculateTransferCharge(double amount) {
        return amount * 0.01;
    }
}

class MerchantWallet extends Wallet {
    MerchantWallet(String id, String name, double balance) {
        super(id, name, balance);
    }

    double calculateTransferCharge(double amount) {
        return amount * 0.005;
    }
}

class WalletService {
    HashMap<String, Wallet> wallets = new HashMap<>(); //h
    ArrayList<String> history = new ArrayList<>();

    void registerWallet(Wallet w) {
        wallets.put(w.getId(), w); //h
    }

    void deposit(String id, double amount) throws WalletException {
        Wallet w = wallets.get(id); //h

        if (w == null || amount <= 0)
            throw new WalletException("Invalid wallet or amount.");

        w.deposit(amount);
        history.add("Deposit: Tk. " + amount + " to " + id);
    }

    void withdraw(String id, double amount) throws WalletException {
        Wallet w = wallets.get(id); //h

        if (w == null)
            throw new WalletException("Wallet not found.");

        w.withdraw(amount);
        history.add("Withdrawal: Tk. " + amount + " from " + id);
    }

    void transfer(String from, String to, double amount) throws WalletException {
        Wallet sender = wallets.get(from); //h
        Wallet receiver = wallets.get(to); //h

        if (sender == null || receiver == null || amount <= 0)
            throw new WalletException("Invalid wallet or amount.");

        double charge = sender.calculateTransferCharge(amount);
        double total = amount + charge;

        if (sender.getBalance() < total)
            throw new WalletException("Insufficient balance for transfer.");

        sender.withdraw(total);
        receiver.deposit(amount);

        history.add("Transferred Tk. " + amount + " from " + from + " to " + to
                    + " | Charge: Tk. " + charge);
    }

    void displayHistory() {
        for (String record : history)
            System.out.println(record);
    }

    void displayBalances() {
        for (Wallet w : wallets.values())
            System.out.println(w.getOwner() + ": Tk. " + w.getBalance());
    }
}

public class Q12 {
    public static void main(String[] args) throws WalletException {
        WalletService service = new WalletService();

        service.registerWallet(new PersonalWallet("W101", "Rahim", 10000));
        service.registerWallet(new MerchantWallet("W102", "Karim", 5000));

        service.deposit("W101", 2000);
        service.withdraw("W102", 500);

        service.transfer("W101", "W102", 3000);

        System.out.println("Wallet Balances:");
        service.displayBalances();

        System.out.println("\nTransaction History:");
        service.displayHistory();
    }
}