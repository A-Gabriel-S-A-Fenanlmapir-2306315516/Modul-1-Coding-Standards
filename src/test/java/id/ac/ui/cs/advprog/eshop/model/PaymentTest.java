package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {
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
    void testCreatePaymentWithValidStatus() {
        Payment payment = new Payment(
                "payment-1",
                order,
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.SUCCESS.getValue(),
                Map.of("voucherCode", "ESHOP1234ABC5678")
        );

        assertEquals("payment-1", payment.getId());
        assertEquals(order, payment.getOrder());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testCreatePaymentWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("payment-1", order, PaymentMethod.VOUCHER_CODE.getValue(), "INVALID", Map.of()));
    }

    @Test
    void testSetStatusWithValidStatus() {
        Payment payment = new Payment(
                "payment-1",
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                PaymentStatus.WAITING_PAYMENT.getValue(),
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );

        payment.setStatus(PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusWithInvalidStatus() {
        Payment payment = new Payment(
                "payment-1",
                order,
                PaymentMethod.BANK_TRANSFER.getValue(),
                PaymentStatus.WAITING_PAYMENT.getValue(),
                Map.of("bankName", "Bank UI", "referenceCode", "INV-001")
        );

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("INVALID"));
    }
}
