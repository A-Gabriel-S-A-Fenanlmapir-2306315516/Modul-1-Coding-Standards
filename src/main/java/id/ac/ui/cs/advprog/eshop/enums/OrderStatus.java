package id.ac.ui.cs.advprog.eshop.enums;

public enum OrderStatus {
    WAITING_PAYMENT,
    FAILED,
    CANCELLED,
    SUCCESS;

    public String getValue() {
        return name();
    }

    public static boolean contains(String value) {
        if (value == null) {
            return false;
        }
        for (OrderStatus status : values()) {
            if (status.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
