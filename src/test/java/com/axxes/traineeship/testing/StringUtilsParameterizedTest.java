package com.axxes.traineeship.testing;

import com.axxes.traineeship.testing.unit.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class StringUtilsParameterizedTest {

    public static final String QUICK_BROWN_FOX = "the quick brown fox jumps over the lazy dog";

    @ParameterizedTest(name = "Abbreviating \"{0}\" to {1} characters should give \"{2}\"")
    @CsvSource(delimiter = '|', value = {
            "                         | 5  | ",
            " ''                      | 5  | ''",
            " " + QUICK_BROWN_FOX + " | 0  | ''",
            " " + QUICK_BROWN_FOX + " | 2  | th ",
            " " + QUICK_BROWN_FOX + " | 3  | the ",
            " " + QUICK_BROWN_FOX + " | 10 | the qui... ",
            " " + QUICK_BROWN_FOX + " | 20 | the quick brown f... ",
    })
    void csvSource(String input, int length, String expectedValue) {
        assertThat(StringUtils.abbreviate(input, length), is(expectedValue));
    }

    @ParameterizedTest
    @MethodSource("abbreviateInput")
    void methodSource(Input input) {
        assertThat(StringUtils.abbreviate(input.getInput(), input.getLength()), is(input.getExpectedValue()));
    }

    private static Stream<Input> abbreviateInput() {
        return Stream.of(
                new Input(null, 5, null),
                new Input("", 5, ""),
                new Input(QUICK_BROWN_FOX, 0, ""),
                new Input(QUICK_BROWN_FOX, 2, "th"),
                new Input(QUICK_BROWN_FOX, 3, "the"),
                new Input(QUICK_BROWN_FOX, 10, "the qui..."),
                new Input(QUICK_BROWN_FOX, 20, "the quick brown f...")
        );
    }

    private static class Input {

        private final String input;
        private final int length;
        private final String expectedValue;

        Input(String input, int length, String expectedValue) {
            this.input = input;
            this.length = length;
            this.expectedValue = expectedValue;
        }

        public String getInput() {
            return input;
        }

        public int getLength() {
            return length;
        }

        public String getExpectedValue() {
            return expectedValue;
        }

    }



}
