package org.arpha.dto.order.enums;

public enum PaymentMethod {
    CASH("Cash"), NON_CASH("NonCash"), ONLINE("NonCash");

    private String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
