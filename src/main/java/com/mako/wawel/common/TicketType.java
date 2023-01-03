package com.mako.wawel.common;

public enum TicketType {
    ULGOWY(21),
    NORMALNY(37),
    SENIOR(26);

    private final int value;

    TicketType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
