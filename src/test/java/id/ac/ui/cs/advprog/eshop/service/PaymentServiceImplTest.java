package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentServiceImplTest {
    private PaymentService paymentService;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentServiceImpl(new PaymentRepository());

        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(1);
        order = new Order("order-1", List.of(product), 1708560000L, "Gabriel");
    }

    @Test
    void testAddPaymentWithValidVoucherCode() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.VOUCHER_CODE.getValue(),
                Map.of("voucherCode", "ESHOP1234ABC5678")
        );

        assertNotNull(payment.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucherCodeLength() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.VOUCHER_CODE.getValue(),
                Map.of("voucherCode", "ESHOP123")
        );

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucherCodeDigitCount() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.VOUCHER_CODE.getValue(),
                Map.of("voucherCode", "ESHOP12ABCDEF34")
        );

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithValidBankTransfer() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );

        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithEmptyBankName() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                Map.of("bankName", "", "referenceCode", "INV-001")
        );

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithMissingReferenceCode() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                Map.of("bankName", "Bank UI")
        );

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(order, "CASH_ON_DELIVERY", Map.of()));
    }

    @Test
    void testSetStatusToSuccessUpdatesOrderStatus() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusToRejectedUpdatesOrderStatus() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), updatedPayment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusWithInvalidStatus() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );

        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "INVALID"));
    }

    @Test
    void testGetPayment() {
        Payment payment = paymentService.addPayment(
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );

        Payment foundPayment = paymentService.getPayment(payment.getId());

        assertEquals(payment, foundPayment);
    }

    @Test
    void testGetAllPayments() {
        paymentService.addPayment(
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );

        assertEquals(1, paymentService.getAllPayments().size());
    }
}
