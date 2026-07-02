package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentMethod {
    VOUCHER_CODE,
    BANK_TRANSFER;

    public String getValue() {
        return name();
    }

    public static boolean contains(String value) {
        if (value == null) {
            return false;
        }
        for (PaymentMethod method : values()) {
            if (method.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
