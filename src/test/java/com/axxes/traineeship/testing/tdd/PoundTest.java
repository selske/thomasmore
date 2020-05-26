package com.axxes.traineeship.testing.tdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PoundTest {

    @Test
    void addPounds() {
        Money result = new Pound(5).plus(new Pound(7));

        assertThat(result.getValue()).isEqualTo(12);
        assertThat(result.getCurrency()).isEqualTo("GBP");
    }

    @Test
    void addMorePounds() {
        Money result = new Pound(3).plus(new Pound(19));

        assertThat(result.getValue()).isEqualTo(22);
        assertThat(result.getCurrency()).isEqualTo("GBP");
    }

}
