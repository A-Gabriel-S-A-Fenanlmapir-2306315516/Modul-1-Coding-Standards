package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentStatus {
    WAITING_PAYMENT,
    SUCCESS,
    REJECTED;

    public String getValue() {
        return name();
    }

    public static boolean contains(String value) {
        if (value == null) {
            return false;
        }
        for (PaymentStatus status : values()) {
            if (status.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
