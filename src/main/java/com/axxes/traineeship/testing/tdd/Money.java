package com.axxes.traineeship.testing.tdd;

import java.util.Objects;

public class Money {

    private final int value;
    private final String currency;

    public Money(int value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money plus(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new UnsupportedOperationException();
        }
        return new Money(value + other.value, currency);
    }

    public String getCurrency() {
        return currency;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value &&
               Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

}
