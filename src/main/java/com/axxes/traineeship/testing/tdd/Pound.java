package com.axxes.traineeship.testing.tdd;

import java.util.Objects;

public class Pound extends Money {

    private final int value;

    public Pound(int value) {
        super(value, "GBP");
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pound dollar = (Pound) o;
        return value == dollar.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
