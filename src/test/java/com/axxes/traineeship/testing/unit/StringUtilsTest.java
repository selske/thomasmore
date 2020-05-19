package com.axxes.traineeship.testing.unit;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

class StringUtilsTest {

    @Test
    void abbreviate_null() {
        // given
        String input = null;

        // when
        String output = StringUtils.abbreviate(input, 13);

        // then
        Assertions.assertNull(output);
        // hamcrest
        MatcherAssert.assertThat(output, nullValue());
        // assertj
        assertThat(output).isNull();
    }

    @Test
    void abbreviate_tooLong() {
        // given
        String input = "This is the input";

        // when
        String output = StringUtils.abbreviate(input, 10);

        //
        Assertions.assertEquals(output, "This is...");
        MatcherAssert.assertThat(output, equalTo("This is..."));
        assertThat(output).isEqualTo("This is...");
    }

    @Test
    void abbreviate_veryShort() {
        // given
        String input = "This is the input";

        // when
        String output = StringUtils.abbreviate(input, 2);

        //
        Assertions.assertEquals(output, "Th");
        MatcherAssert.assertThat(output, equalTo("Th"));
        assertThat(output).isEqualTo("Th");
    }

    @Test
    void abbreviate_notTooLong() {
        // given
        String input = "This is";

        // when
        String output = StringUtils.abbreviate(input, 100);

        //
        Assertions.assertEquals(output, "This is");
        MatcherAssert.assertThat(output, equalTo("This is"));
        assertThat(output).isEqualTo("This is");
    }

}
