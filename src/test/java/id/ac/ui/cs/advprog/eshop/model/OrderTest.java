package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {
    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);
    }

    @Test
    void testCreateOrderWithEmptyProducts() {
        List<Product> emptyProducts = new ArrayList<>();

        assertThrows(IllegalArgumentException.class,
                () -> new Order("order-1", emptyProducts, 1708560000L, "Gabriel"));
    }

    @Test
    void testCreateOrderWithDefaultStatus() {
        Order order = new Order("order-1", products, 1708560000L, "Gabriel");

        assertEquals("order-1", order.getId());
        assertEquals(products.size(), order.getProducts().size());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Gabriel", order.getAuthor());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }

    @Test
    void testCreateOrderWithSuccessStatus() {
        Order order = new Order(
                "order-1",
                products,
                1708560000L,
                "Gabriel",
                OrderStatus.SUCCESS.getValue()
        );

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testCreateOrderWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> new Order("order-1", products, 1708560000L, "Gabriel", "INVALID"));
    }

    @Test
    void testSetStatusWithValidStatus() {
        Order order = new Order("order-1", products, 1708560000L, "Gabriel");

        order.setStatus(OrderStatus.CANCELLED.getValue());

        assertEquals(OrderStatus.CANCELLED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusWithInvalidStatusKeepsPreviousStatus() {
        Order order = new Order("order-1", products, 1708560000L, "Gabriel");

        order.setStatus("INVALID");

        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }
}
