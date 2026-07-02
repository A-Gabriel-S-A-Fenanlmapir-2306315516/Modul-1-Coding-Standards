package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Order {
    private final String id;
    private final List<Product> products;
    private final Long orderTime;
    private final String author;
    private String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        validateProducts(products);
        validateAuthor(author);
        this.id = id;
        this.products = new ArrayList<>(products);
        this.orderTime = orderTime;
        this.author = author;
        this.status = OrderStatus.WAITING_PAYMENT.getValue();
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);
        if (!OrderStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid order status");
        }
        this.status = status;
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) {
            this.status = status;
        }
    }

    private void validateProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product");
        }
    }

    private void validateAuthor(String author) {
        if (author == null) {
            throw new IllegalArgumentException("Order author must not be null");
        }
    }
}
