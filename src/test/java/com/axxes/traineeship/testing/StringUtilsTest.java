package com.axxes.traineeship.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

class StringUtilsTest {

    private static final String QUICK_BROWN_FOX = "the quick brown fox jumps over the lazy dog";

    @Test
    void abbreviate() {
        assertThat(StringUtils.abbreviate(QUICK_BROWN_FOX, 10), is("the qui..."));
        assertThat(StringUtils.abbreviate(QUICK_BROWN_FOX, 20), is("the quick brown f..."));
    }

    @Test
    void abbreviate_short() {
        assertThat(StringUtils.abbreviate(QUICK_BROWN_FOX, 0), is(""));
        assertThat(StringUtils.abbreviate(QUICK_BROWN_FOX, 2), is("th"));
        assertThat(StringUtils.abbreviate(QUICK_BROWN_FOX, 3), is("the"));
        assertThat(StringUtils.abbreviate(QUICK_BROWN_FOX, 4), is("t..."));
    }

    @Test
    void abbreviate_null() {
        assertThat(StringUtils.abbreviate(null, 10), nullValue());
    }

    @Test
    void abbreviate_emptyString() {
        assertThat(StringUtils.abbreviate("", 10), is(""));
    }

}
