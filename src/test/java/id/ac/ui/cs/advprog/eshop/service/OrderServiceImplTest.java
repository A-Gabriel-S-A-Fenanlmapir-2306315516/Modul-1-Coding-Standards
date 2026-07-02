package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(1);
        order = new Order("order-1", List.of(product), 1708560000L, "Gabriel");
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.findById("order-1")).thenReturn(null);
        when(orderRepository.save(order)).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertEquals(order, createdOrder);
        verify(orderRepository).save(order);
    }

    @Test
    void testCreateOrderAlreadyExists() {
        when(orderRepository.findById("order-1")).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNull(createdOrder);
        verify(orderRepository, never()).save(order);
    }

    @Test
    void testUpdateStatus() {
        when(orderRepository.findById("order-1")).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        Order updatedOrder = orderService.updateStatus("order-1", OrderStatus.SUCCESS.getValue());

        assertEquals(OrderStatus.SUCCESS.getValue(), updatedOrder.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void testUpdateStatusWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> orderService.updateStatus("order-1", "INVALID"));
        verifyNoInteractions(orderRepository);
    }

    @Test
    void testUpdateStatusWithMissingOrder() {
        when(orderRepository.findById("missing-order")).thenReturn(null);

        assertThrows(NoSuchElementException.class,
                () -> orderService.updateStatus("missing-order", OrderStatus.SUCCESS.getValue()));
        verify(orderRepository, never()).save(order);
    }

    @Test
    void testFindByIdFound() {
        when(orderRepository.findById("order-1")).thenReturn(order);

        Order foundOrder = orderService.findById("order-1");

        assertEquals(order, foundOrder);
    }

    @Test
    void testFindByIdNotFound() {
        when(orderRepository.findById("missing-order")).thenReturn(null);

        Order foundOrder = orderService.findById("missing-order");

        assertNull(foundOrder);
    }

    @Test
    void testFindAllByAuthorFound() {
        when(orderRepository.findAllByAuthor("Gabriel")).thenReturn(List.of(order));

        List<Order> orders = orderService.findAllByAuthor("Gabriel");

        assertEquals(1, orders.size());
    }

    @Test
    void testFindAllByAuthorIsCaseSensitive() {
        when(orderRepository.findAllByAuthor("gabriel")).thenReturn(List.of());

        List<Order> orders = orderService.findAllByAuthor("gabriel");

        assertEquals(0, orders.size());
    }
}
