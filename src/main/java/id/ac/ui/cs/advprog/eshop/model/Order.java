package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
public class Order {
    private String id;
    private List<Product> products;
    private Long orderTime;
    private String author;
    @Setter
    private String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = OrderStatus.WAITING_PAYMENT.getValue(); // Gunakan enum [cite: 2974]

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) { // Validasi menggunakan method static di enum [cite: 2987]
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
    public Order(String id, List<Product> products, Long orderTime, String author, String status) {

        this(id, products, orderTime, author);


        List<String> validStatuses = List.of("WAITING_PAYMENT", "FAILED", "CANCELLED", "SUCCESS");
        if (!validStatuses.contains(status)) {
            throw new IllegalArgumentException();
        }

        this.status = status;
    }
}