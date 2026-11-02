/*
Question 10: E-Commerce Payment and Refund Management System
10 Marks
An e-commerce company supports different payment methods and must calculate transaction charges according to the selected method.
Requirements:
Create an interface Refundable containing:
void refund(double amount);
Create an abstract class Payment containing private attributes transactionId, amount, and paymentStatus.
Create subclasses:
CardPayment: Transaction fee 2%.
MobileBankingPayment: Transaction fee 1.5%.
BankTransferPayment: No transaction fee.
Make the payment classes implement Refundable and override an abstract method processPayment().
Implement:
Process payments using different methods.
Calculate the transaction fee and total payable amount.
Store payment details.
Refund a successful transaction.
Reject duplicate refunds and refunds exceeding the refundable balance.
Concepts: Abstract classes, interfaces, polymorphism, encapsulation, exception handling.
*/





import java.util.ArrayList;

// Refundable interface
interface Refundable {
    void refund(double amount);
}

// Abstract parent class
abstract class Payment implements Refundable {
    private int transactionId;
    private double amount;
    private String paymentStatus;
    protected double refundedAmount;

    Payment(int id, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        this.transactionId = id;
        this.amount = amount;
        this.paymentStatus = "Pending";
        this.refundedAmount = 0;
    }

    abstract void processPayment();

    int getTransactionId() {
        return transactionId;
    }

    double getAmount() {
        return amount;
    }

    String getPaymentStatus() {
        return paymentStatus;
    }

    void setPaymentStatus(String status) {
        paymentStatus = status;
    }

    abstract double calculateFee();

    void displayDetails() {
        System.out.println("\nTransaction ID: " + transactionId);
        System.out.println("Amount: Tk. " + amount);
        System.out.println("Transaction Fee: Tk. " + calculateFee());
        System.out.println("Total Payable: Tk. " + (amount + calculateFee()));
        System.out.println("Status: " + paymentStatus);
        System.out.println("Refunded Amount: Tk. " + refundedAmount);
    }
}

// Card payment
class CardPayment extends Payment {
    CardPayment(int id, double amount) {
        super(id, amount);
    }

    double calculateFee() {
        return getAmount() * 0.02;
    }

    void processPayment() {
        setPaymentStatus("Successful");
        System.out.println("Card payment successful.");
    }

    public void refund(double amount) {
        if (!getPaymentStatus().equals("Successful")) {
            System.out.println("Payment was not successful.");
            return;
        }

        if (refundedAmount > 0 || amount > getAmount() - refundedAmount || amount <= 0) {
            System.out.println("Invalid or duplicate refund.");
            return;
        }

        refundedAmount = refundedAmount + amount;
        System.out.println("Refund successful: Tk. " + amount);
    }
}

// Mobile banking payment
class MobileBankingPayment extends Payment {
    MobileBankingPayment(int id, double amount) {
        super(id, amount);
    }

    double calculateFee() {
        return getAmount() * 0.015;
    }

    void processPayment() {
        setPaymentStatus("Successful");
        System.out.println("Mobile banking payment successful.");
    }

    public void refund(double amount) {
        if (!getPaymentStatus().equals("Successful")) {
            System.out.println("Payment was not successful.");
            return;
        }

        if (refundedAmount > 0 || amount > getAmount() - refundedAmount || amount <= 0) {
            System.out.println("Invalid or duplicate refund.");
            return;
        }

        refundedAmount = refundedAmount + amount;
        System.out.println("Refund successful: Tk. " + amount);
    }
}

// Bank transfer payment
class BankTransferPayment extends Payment {
    BankTransferPayment(int id, double amount) {
        super(id, amount);
    }

    double calculateFee() {
        return 0;
    }

    void processPayment() {
        setPaymentStatus("Successful");
        System.out.println("Bank transfer successful.");
    }

    public void refund(double amount) {
        if (!getPaymentStatus().equals("Successful")) {
            System.out.println("Payment was not successful.");
            return;
        }

        if (refundedAmount > 0 || amount > getAmount() - refundedAmount || amount <= 0) {
            System.out.println("Invalid or duplicate refund.");
            return;
        }

        refundedAmount = refundedAmount + amount;
        System.out.println("Refund successful: Tk. " + amount);
    }
}

// E-commerce payment management
class PaymentSystem {
    ArrayList<Payment> payments = new ArrayList<Payment>();

    void processNewPayment(Payment p) {
        payments.add(p);
        p.processPayment();
    }

    Payment searchPayment(int id) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getTransactionId() == id) {
                return payments.get(i);
            }
        }

        return null;
    }

    void displayPayments() {
        for (int i = 0; i < payments.size(); i++) {
            payments.get(i).displayDetails();
        }
    }
}

// Main class
public class Q10 {
    public static void main(String[] args) {
        PaymentSystem system = new PaymentSystem();

        Payment p1 = new CardPayment(101, 10000);
        Payment p2 = new MobileBankingPayment(102, 5000);
        Payment p3 = new BankTransferPayment(103, 8000);

        system.processNewPayment(p1);
        system.processNewPayment(p2);
        system.processNewPayment(p3);

        p1.refund(5000);
        p1.refund(5000);

        p2.refund(6000);

        System.out.println("\n===== PAYMENT HISTORY =====");
        system.displayPayments();
    }
}