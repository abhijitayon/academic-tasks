/*
Question 15: Supermarket Inventory and Expiry Management System
10 Marks
A supermarket maintains Perishable Products and Non-Perishable Products. The system must manage stock and apply product-specific sale prices.
Requirements:
Create an abstract class InventoryItem containing private attributes itemId, name, unitPrice, and stockQuantity.
Create subclasses:
PerishableItem: Includes expiry date.
NonPerishableItem: Includes warranty duration where applicable.
Implement an abstract method calculateSellingPrice().
Pricing rules:
Perishable items expiring within 3 days receive a 25% discount.
Expired items cannot be sold.
Non-perishable items receive a 5% discount for purchases of at least 5 units.
Implement:
Add products to inventory.
Update stock quantities.
Search products using product IDs.
Process sales and update stock.
Display expired products and products with stock below 5 units.
Use HashMap<String, InventoryItem> to manage inventory.
Concepts: OOP pillars, HashMap, date handling, conditional pricing, exception handling.
*/





import java.util.HashMap; //h

abstract class InventoryItem {
    private String itemId, name;
    private double unitPrice;
    private int stockQuantity;

    InventoryItem(String id, String name, double price, int stock) {
        itemId = id;
        this.name = name;
        unitPrice = price;
        stockQuantity = stock;
    }

    abstract double calculateSellingPrice(int quantity);

    String getId() { return itemId; }
    String getName() { return name; }
    double getPrice() { return unitPrice; }
    int getStock() { return stockQuantity; }

    void updateStock(int quantity) {
        stockQuantity += quantity;
    }
}

class PerishableItem extends InventoryItem {
    private int daysToExpire;

    PerishableItem(String id, String name, double price, int stock, int days) {
        super(id, name, price, stock);
        daysToExpire = days;
    }

    double calculateSellingPrice(int quantity) {
        if (daysToExpire < 0)
            return -1;
        else if (daysToExpire <= 3)
            return getPrice() * 0.75;
        else
            return getPrice();
    }

    boolean isExpired() {
        return daysToExpire < 0;
    }
}

class NonPerishableItem extends InventoryItem {
    private int warrantyMonths;

    NonPerishableItem(String id, String name, double price, int stock, int warranty) {
        super(id, name, price, stock);
        warrantyMonths = warranty;
    }

    double calculateSellingPrice(int quantity) {
        if (quantity >= 5)
            return getPrice() * 0.95;
        else
            return getPrice();
    }
}

class Supermarket {
    HashMap<String, InventoryItem> inventory = new HashMap<>(); //h

    void addProduct(InventoryItem item) {
        inventory.put(item.getId(), item); //h
    }

    void updateStock(String id, int quantity) {
        InventoryItem item = inventory.get(id); //h

        if (item != null && quantity > 0)
            item.updateStock(quantity);
    }

    void searchProduct(String id) {
        InventoryItem item = inventory.get(id); //h

        if (item != null)
            System.out.println(item.getName() + " | Stock: " + item.getStock());
        else
            System.out.println("Product not found.");
    }

    void sellProduct(String id, int quantity) {
        InventoryItem item = inventory.get(id); //h

        if (item == null || quantity <= 0 || item.getStock() < quantity) {
            System.out.println("Invalid product or insufficient stock.");
            return;
        }

        double price = item.calculateSellingPrice(quantity);

        if (price == -1) {
            System.out.println("Product has expired.");
            return;
        }

        double total = price * quantity;
        item.updateStock(-quantity);

        System.out.println("Total Bill: Tk. " + total);
    }

    void displayAlerts() {
        for (InventoryItem item : inventory.values()) {
            if (item.getStock() < 5)
                System.out.println("Low Stock: " + item.getName());

            if (item instanceof PerishableItem) {
                PerishableItem p = (PerishableItem) item;

                if (p.isExpired())
                    System.out.println("Expired: " + item.getName());
            }
        }
    }
}

public class Q15 {
    public static void main(String[] args) {
        Supermarket shop = new Supermarket();

        shop.addProduct(new PerishableItem("P101", "Milk", 100, 10, 2));
        shop.addProduct(new PerishableItem("P102", "Bread", 80, 3, -1));
        shop.addProduct(new NonPerishableItem("P103", "Soap", 50, 20, 12));

        shop.searchProduct("P101");

        shop.sellProduct("P101", 2);
        shop.sellProduct("P103", 5);

        shop.displayAlerts();
    }
}