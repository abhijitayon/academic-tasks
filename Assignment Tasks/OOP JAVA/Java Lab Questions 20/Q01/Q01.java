/*
Question 1: Smart Shopping Cart and Billing System
10 Marks
An online shopping platform sells two categories of products: Electronics and Clothing. Different product categories have different discount policies. The platform needs a system for calculating bills and managing customer purchases.
Requirements:
Create an abstract class Product containing private attributes productId, name, price, and quantity. Provide an abstract method calculateFinalPrice().
Create two subclasses:
Electronics: Applies a 10% discount on the product price.
Clothing: Applies a 15% discount on the product price.
Create a ShoppingCart class that maintains an ArrayList<Product> and supports adding, removing, and displaying products.
Implement the following operations:
Add products to the shopping cart.
Remove a product using its ID.
Calculate the total bill after category-specific discounts.
Apply an additional 5% discount if the discounted cart total exceeds Tk. 10,000.
Display an itemized invoice containing product details, discounts, and final payable amount.
Special condition: Product price and quantity must be positive. Use an appropriate exception for invalid values.
Expected demonstration: Add two electronics and two clothing items, remove one product, and generate the final invoice.
*/


import java.util.ArrayList;

// Abstract parent class
abstract class Product {    //till line 68
private int productId;
private String name;
private double price;
private int quantity;

Product(int id, String name, double price, int quantity) { //constructor
    if (price <= 0 || quantity <= 0) {
        throw new IllegalArgumentException("Invalid price or quantity");
    }

    this.productId = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
}

abstract double calculateFinalPrice();

// Display product details
void display() {      //display method
    System.out.println("ID: " + productId);
    System.out.println("Name: " + name);
    System.out.println("Price: " + price);
    System.out.println("Quantity: " + quantity);
}

int getProductId() {
    return productId;
}

double getPrice() {
    return price;
}

int getQuantity() {
    return quantity;
}

String getName() {
    return name;
}

}

// Electronics 10% discount
class Electronics extends Product {

Electronics(int id, String name, double price, int quantity) {
    super(id, name, price, quantity);
}

double calculateFinalPrice() {
    return getPrice() * getQuantity() * 0.90;
}

}

// Clothing gets 15% discount
class Clothing extends Product {

Clothing(int id, String name, double price, int quantity) {
    super(id, name, price, quantity);
}

double calculateFinalPrice() {
    return getPrice() * getQuantity() * 0.85;
}

}

// Shopping cart manages products
class ShoppingCart {
ArrayList<Product> products = new ArrayList<Product>();

// Add product
void addProduct(Product p) {
    products.add(p);
}

// Remove product using ID
void removeProduct(int id) {
    for (int i = 0; i < products.size(); i++) {
        if (products.get(i).getProductId() == id) {
            products.remove(i);
            break;
        }
    }
}

// Display cart products
void displayProducts() {
    for (int i = 0; i < products.size(); i++) {
        products.get(i).display();
        System.out.println();
    }
}

// Calculate bill and print invoice
void generateInvoice() {
    double total = 0;

    System.out.println("========== INVOICE ==========");

    for (int i = 0; i < products.size(); i++) {
        Product p = products.get(i);

        double original = p.getPrice() * p.getQuantity();
        double finalPrice = p.calculateFinalPrice();

        System.out.println("\nProduct: " + p.getName());
        System.out.println("Original Price: Tk. " + original);
        System.out.println("Category Discount: Tk. " + (original - finalPrice));
        System.out.println("Final Price: Tk. " + finalPrice);

        total = total + finalPrice;
    }

    // Additional 5% discount
    double extraDiscount = 0;

    if (total > 10000) {
        extraDiscount = total * 0.05;
    }

    System.out.println("\nTotal: Tk. " + total);
    System.out.println("Extra Discount: Tk. " + extraDiscount);
    System.out.println("Final Payable: Tk. " + (total - extraDiscount));
}

}

// Main class
public class Q01 {
public static void main(String[] args) {

    ShoppingCart cart = new ShoppingCart();

    // Add two electronics and two clothing items
    cart.addProduct(new Electronics(101, "Laptop", 80000, 1));
    cart.addProduct(new Electronics(102, "Mobile", 20000, 1));
    cart.addProduct(new Clothing(103, "Jacket", 5000, 1));
    cart.addProduct(new Clothing(104, "Shirt", 2000, 2));

    // Remove one product
    cart.removeProduct(102);

    // Display products and generate invoice
    cart.displayProducts();
    cart.generateInvoice();
}

}