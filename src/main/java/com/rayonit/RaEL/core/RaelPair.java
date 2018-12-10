package com.rayonit.RaEL.core;

/**
 * A simple key : value pair pojo, to extract and store information for every atomic {@link Rael}
 *
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public class RaelPair {

    private String key;
    private Object value;

    public RaelPair(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public RaelPair() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
