package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Payment {
    private final String id;
    private final Order order;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    public Payment(String id, Order order, String method, String status, Map<String, String> paymentData) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.id = id;
        this.order = order;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData == null ? new HashMap<>() : new HashMap<>(paymentData);
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = status;
    }
}
