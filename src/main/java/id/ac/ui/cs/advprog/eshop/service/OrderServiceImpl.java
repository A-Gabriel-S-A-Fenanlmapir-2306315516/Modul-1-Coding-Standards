package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        if (orderRepository.findById(order.getId()) != null) {
            return null;
        }
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(String orderId, String status) {
        if (!OrderStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid order status");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new NoSuchElementException("Order not found");
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public Order findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> findAllByAuthor(String author) {
        return orderRepository.findAllByAuthor(author);
    }
}
