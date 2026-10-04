import java.util.*;

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}

interface PaymentMethod {
    boolean processPayment(double amount);
    String getName();
}

class CreditCardPayment implements PaymentMethod {
    public boolean processPayment(double amount) {
        return true;
    }

    public String getName() {
        return "Credit Card";
    }
}

class PayPalPayment implements PaymentMethod {
    public boolean processPayment(double amount) {
        return false;
    }

    public String getName() {
        return "PayPal";
    }
}

class BankTransferPayment implements PaymentMethod {
    public boolean processPayment(double amount) {
        return true;
    }

    public String getName() {
        return "Bank Transfer";
    }
}

enum OrderStatus {
    PENDING,
    PAID
}

class Order {
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;

    public Order(Customer customer) {
        this.customer = customer;
        this.status = OrderStatus.PENDING;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            return;
        }

        items.add(new OrderItem(product, quantity));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void markPaid() {
        status = OrderStatus.PAID;
    }
}

class PaymentService {
    public void pay(Order order, PaymentMethod paymentMethod) {
        if (order.isEmpty()) {
            System.out.println(
                    "Cannot process payment for an empty order.");
            return;
        }

        System.out.println(
                "Payment initiated via " +
                paymentMethod.getName() +
                " for Order " +
                order.getCustomer().getName() +
                ".");

        boolean success =
                paymentMethod.processPayment(
                        order.getTotal());

        if (success) {
            order.markPaid();

            System.out.println(
                    "Payment for Order " +
                    order.getCustomer().getName() +
                    " successful.");

            System.out.println(
                    "Order status: " +
                    formatStatus(order.getStatus()));
        } else {
            System.out.println(
                    "Payment for Order " +
                    order.getCustomer().getName() +
                    " failed.");

            System.out.println(
                    "Order status: " +
                    formatStatus(order.getStatus()));
        }
    }

    private String formatStatus(OrderStatus status) {
        String value = status.name().toLowerCase();

        return Character.toUpperCase(value.charAt(0))
                + value.substring(1);
    }
}

public class PaymentProcessingSystem {
    public static void main(String[] args) {
        Customer customerX =
                new Customer("X");

        Customer customerY =
                new Customer("Y");

        Customer customerZ =
                new Customer("Z");

        Product productA =
                new Product("Product A", 100);

        Product productB =
                new Product("Product B", 200);

        Product productC =
                new Product("Product C", 300);

        Order orderX =
                new Order(customerX);

        System.out.println(
                "Order created for Customer X.");

        orderX.addProduct(productA, 2);
        orderX.addProduct(productB, 1);

        PaymentService paymentService =
                new PaymentService();

        paymentService.pay(
                orderX,
                new CreditCardPayment());

        Order orderY =
                new Order(customerY);

        paymentService.pay(
                orderY,
                new CreditCardPayment());

        Order orderZ =
                new Order(customerZ);

        System.out.println(
                "Order created for Customer Z.");

        orderZ.addProduct(productC, 1);

        paymentService.pay(
                orderZ,
                new PayPalPayment());
    }
}
