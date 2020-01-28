package com.waes.assignment.enums;

/**
 * Provides side for diff
 *
 * @developer -- ilkercolakoglu
 */
public enum Side {
    LEFT("left"),
    RIGHT("right");

    private final String value;

    Side(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
