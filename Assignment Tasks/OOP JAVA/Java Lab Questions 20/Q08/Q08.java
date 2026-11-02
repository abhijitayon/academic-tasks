/*
Question 8: Online Food Delivery and Order Processing System
10 Marks
An online food delivery service accepts Regular Orders and Express Orders. Delivery fees depend on order type and distance.
Requirements:
Create an abstract class FoodOrder containing private attributes orderId, customerName, and foodPrice.
Create subclasses:
RegularOrder
ExpressOrder
Implement an abstract method calculateTotalCost(double distance).
Delivery policies:
Regular Order: Tk. 40 plus Tk. 5 per kilometer.
Express Order: Tk. 80 plus Tk. 10 per kilometer.
Orders with food prices above Tk. 1,500 receive a 10% discount on food price only.
Create an interface OrderStatus with methods confirmOrder() and cancelOrder().
Implement:
Create new food orders.
Confirm or cancel orders.
Calculate delivery and total charges.
Store orders using ArrayList<FoodOrder>.
Display order history with current status.
A cancelled order must not be processed for delivery.
Concepts: OOP pillars, interfaces, collections, method overriding, state validation.
*/




import java.util.ArrayList;

// Interface
interface OrderStatus {
    void confirmOrder();
    void cancelOrder();
}

// Abstract parent class
abstract class FoodOrder implements OrderStatus {
    private int orderId;
    private String customerName;
    private double foodPrice;
    private String status;

    FoodOrder(int id, String name, double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Invalid food price");
        }

        this.orderId = id;
        this.customerName = name;
        this.foodPrice = price;
        this.status = "Pending";
    }

    abstract double calculateTotalCost(double distance);

    double getFoodPrice() {
        return foodPrice;
    }

    int getOrderId() {
        return orderId;
    }

    String getCustomerName() {
        return customerName;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    public void confirmOrder() {
        if (status.equals("Pending")) {
            status = "Confirmed";
            System.out.println("Order confirmed.");
        } else {
            System.out.println("Order cannot be confirmed.");
        }
    }

    public void cancelOrder() {
        if (status.equals("Pending") || status.equals("Confirmed")) {
            status = "Cancelled";
            System.out.println("Order cancelled.");
        } else {
            System.out.println("Order cannot be cancelled.");
        }
    }

    double calculateDiscountedPrice() {
        if (foodPrice > 1500) {
            return foodPrice * 0.90;
        }

        return foodPrice;
    }
}

// Regular order
class RegularOrder extends FoodOrder {
    RegularOrder(int id, String name, double price) {
        super(id, name, price);
    }

    double calculateTotalCost(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Invalid distance");
        }

        double deliveryFee = 40 + (distance * 5);
        return calculateDiscountedPrice() + deliveryFee;
    }
}

// Express order
class ExpressOrder extends FoodOrder {
    ExpressOrder(int id, String name, double price) {
        super(id, name, price);
    }

    double calculateTotalCost(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Invalid distance");
        }

        double deliveryFee = 80 + (distance * 10);
        return calculateDiscountedPrice() + deliveryFee;
    }
}

// Food delivery system
class FoodDelivery {
    ArrayList<FoodOrder> orders = new ArrayList<FoodOrder>();

    void createOrder(FoodOrder order) {
        orders.add(order);
        System.out.println("Order created successfully.");
    }

    void processOrder(int id, double distance) {
        for (int i = 0; i < orders.size(); i++) {
            FoodOrder order = orders.get(i);

            if (order.getOrderId() == id) {
                if (!order.getStatus().equals("Confirmed")) {
                    System.out.println("Order must be confirmed before delivery.");
                    return;
                }

                System.out.println("\n===== ORDER DETAILS =====");
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Customer: " + order.getCustomerName());
                System.out.println("Food Price: Tk. " + order.getFoodPrice());
                System.out.println("Final Cost: Tk. " + order.calculateTotalCost(distance));
                return;
            }
        }

        System.out.println("Order not found.");
    }

    void displayOrderHistory() {
        System.out.println("\n===== ORDER HISTORY =====");

        for (int i = 0; i < orders.size(); i++) {
            FoodOrder order = orders.get(i);

            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Customer: " + order.getCustomerName());
            System.out.println("Status: " + order.getStatus());
            System.out.println();
        }
    }
}

// Main class
public class Q08 {
    public static void main(String[] args) {
        FoodDelivery delivery = new FoodDelivery();

        FoodOrder o1 = new RegularOrder(101, "Ayon", 1200);
        FoodOrder o2 = new ExpressOrder(102, "Sakib", 2000);
        FoodOrder o3 = new RegularOrder(103, "Hasan", 1800);

        delivery.createOrder(o1);
        delivery.createOrder(o2);
        delivery.createOrder(o3);

        o1.confirmOrder();
        o2.confirmOrder();
        o3.cancelOrder();

        delivery.processOrder(101, 10);
        delivery.processOrder(102, 5);
        delivery.processOrder(103, 8);

        delivery.displayOrderHistory();
    }
}