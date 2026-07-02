package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final int VOUCHER_CODE_LENGTH = 16;
    private static final int VOUCHER_CODE_DIGIT_COUNT = 8;
    private static final String VOUCHER_CODE_PREFIX = "ESHOP";

    private final PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method");
        }

        Map<String, String> safePaymentData = paymentData == null ? Map.of() : paymentData;
        String initialStatus = determineInitialStatus(method, safePaymentData);
        Payment payment = new Payment(UUID.randomUUID().toString(), order, method, initialStatus, safePaymentData);
        applyPaymentStatusToOrder(payment);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        applyPaymentStatusToOrder(payment);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private String determineInitialStatus(String method, Map<String, String> paymentData) {
        if (PaymentMethod.VOUCHER_CODE.getValue().equals(method)) {
            return isValidVoucherCode(paymentData.get("voucherCode"))
                    ? PaymentStatus.SUCCESS.getValue()
                    : PaymentStatus.REJECTED.getValue();
        }

        if (hasText(paymentData.get("bankName")) && hasText(paymentData.get("referenceCode"))) {
            return PaymentStatus.WAITING_PAYMENT.getValue();
        }
        return PaymentStatus.REJECTED.getValue();
    }

    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != VOUCHER_CODE_LENGTH) {
            return false;
        }
        if (!voucherCode.startsWith(VOUCHER_CODE_PREFIX)) {
            return false;
        }

        int digitCount = 0;
        for (char character : voucherCode.toCharArray()) {
            if (Character.isDigit(character)) {
                digitCount++;
            }
        }
        return digitCount == VOUCHER_CODE_DIGIT_COUNT;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private void applyPaymentStatusToOrder(Payment payment) {
        if (PaymentStatus.SUCCESS.getValue().equals(payment.getStatus())) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if (PaymentStatus.REJECTED.getValue().equals(payment.getStatus())) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
    }
}
