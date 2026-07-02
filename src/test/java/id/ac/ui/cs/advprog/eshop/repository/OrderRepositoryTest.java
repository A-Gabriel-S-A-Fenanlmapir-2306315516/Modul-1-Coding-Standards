package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderRepositoryTest {
    private OrderRepository orderRepository;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(1);
        products = List.of(product);
    }

    @Test
    void testSaveNewOrder() {
        Order order = createOrder("order-1", "Gabriel");

        Order savedOrder = orderRepository.save(order);

        assertEquals(order, savedOrder);
        assertEquals(order, orderRepository.findById("order-1"));
    }

    @Test
    void testSaveUpdatesExistingOrder() {
        orderRepository.save(createOrder("order-1", "Gabriel"));
        Order updatedOrder = new Order(
                "order-1",
                products,
                1708560001L,
                "Gabriel",
                OrderStatus.SUCCESS.getValue()
        );

        orderRepository.save(updatedOrder);

        assertEquals(OrderStatus.SUCCESS.getValue(), orderRepository.findById("order-1").getStatus());
        assertEquals(1, orderRepository.findAllByAuthor("Gabriel").size());
    }

    @Test
    void testFindByIdFound() {
        orderRepository.save(createOrder("order-1", "Gabriel"));

        Order order = orderRepository.findById("order-1");

        assertEquals("Gabriel", order.getAuthor());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(orderRepository.findById("missing-order"));
    }

    @Test
    void testFindAllByAuthorFound() {
        orderRepository.save(createOrder("order-1", "Gabriel"));
        orderRepository.save(createOrder("order-2", "Gabriel"));
        orderRepository.save(createOrder("order-3", "Safira"));

        List<Order> orders = orderRepository.findAllByAuthor("Gabriel");

        assertEquals(2, orders.size());
    }

    @Test
    void testFindAllByAuthorIsCaseSensitive() {
        orderRepository.save(createOrder("order-1", "Gabriel"));

        List<Order> orders = orderRepository.findAllByAuthor("gabriel");

        assertTrue(orders.isEmpty());
    }

    private Order createOrder(String id, String author) {
        return new Order(id, products, 1708560000L, author);
    }
}
