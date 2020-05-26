package com.axxes.traineeship.testing.tdd;

import java.util.Objects;

public class Dollar {

    private final int value;

    public Dollar(int value) {
        this.value = value;
    }

    public Dollar plus(Dollar other) {
        return new Dollar(value + other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dollar dollar = (Dollar) o;
        return value == dollar.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
