package com.rayonit.RaEL.core;

/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public enum RaelOperator {
    AND('&'),OR('|'),CUNJUCTION(',');

    private final char operator;

    RaelOperator(char operator) {
        this.operator = operator;
    }

    public char getOperator() {
        return operator;
    }
}
