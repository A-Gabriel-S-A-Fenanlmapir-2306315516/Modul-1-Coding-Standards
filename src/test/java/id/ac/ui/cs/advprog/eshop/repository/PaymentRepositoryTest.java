package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(1);
        order = new Order("order-1", List.of(product), 1708560000L, "Gabriel");
    }

    @Test
    void testSaveNewPayment() {
        Payment payment = createPayment("payment-1", PaymentStatus.WAITING_PAYMENT.getValue());

        Payment savedPayment = paymentRepository.save(payment);

        assertEquals(payment, savedPayment);
        assertEquals(payment, paymentRepository.findById("payment-1"));
    }

    @Test
    void testSaveUpdatesExistingPayment() {
        paymentRepository.save(createPayment("payment-1", PaymentStatus.WAITING_PAYMENT.getValue()));
        Payment updatedPayment = createPayment("payment-1", PaymentStatus.SUCCESS.getValue());

        paymentRepository.save(updatedPayment);

        assertEquals(PaymentStatus.SUCCESS.getValue(), paymentRepository.findById("payment-1").getStatus());
        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(paymentRepository.findById("missing-payment"));
    }

    @Test
    void testFindAll() {
        paymentRepository.save(createPayment("payment-1", PaymentStatus.WAITING_PAYMENT.getValue()));
        paymentRepository.save(createPayment("payment-2", PaymentStatus.SUCCESS.getValue()));

        assertEquals(2, paymentRepository.findAll().size());
    }

    private Payment createPayment(String id, String status) {
        return new Payment(
                id,
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                status,
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );
    }
}
