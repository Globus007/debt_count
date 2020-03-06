package com.gleb.st.debt_count.component.expiration;

import org.springframework.stereotype.Component;

@Component
public class Expiration {

    private long value;

    public Expiration() {}

    public Expiration(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Expiration{" +
                "value=" + value +
                '}';
    }
}
