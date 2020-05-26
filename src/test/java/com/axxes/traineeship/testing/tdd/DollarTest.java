package com.axxes.traineeship.testing.tdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DollarTest {

    @Test
    void addDollars() {
        Money result = new Dollar(5).plus(new Dollar(7));

        assertThat(result).isEqualTo(new Money(12, "USD"));
    }

    @Test
    void addMoreDollars() {
        Money result = new Dollar(3).plus(new Dollar(19));

        assertThat(result).isEqualTo(new Money(22, "USD"));
    }

}
