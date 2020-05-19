package com.axxes.traineeship.testing;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.axxes.traineeship.testing.MatcherTest.TraineeTypeSafeMatcher.traineeWithFirstName;
import static java.util.Arrays.asList;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.assertj.core.groups.Tuple.tuple;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MatcherTest {

    @Test
    void customMatcher() {
        Collection<Trainee> trainees = asList(
                new Trainee("Anthony", "Torfs"),
                new Trainee("Bart", "Biemans"),
                new Trainee("Bart", "Wezenbeek"),
                new Trainee("Bavo", "De Waele"),
                new Trainee("Bram", "Van Dyck"),
                new Trainee("Cel", "Pynenborg"),
                new Trainee("Stijn", "Defour"),
                new Trainee("Tommy", "Zhou"),
                new Trainee("Yassine", "Bouzeya")
        );

//        assertEquals(8, trainees.size());
        // hamcrest
        assertThat(trainees, hasSize(9));

        assertThat(trainees, hasItem(traineeWithFirstName("Bart").andLastName("Wezenbeek")));


        Assertions.assertThat(trainees)
                .anySatisfy(t -> assertSoftly(softly -> {
                    softly.assertThat(t.getFirstName()).isEqualTo("Tommy");
                    softly.assertThat(t.getLastName()).isEqualTo("Zhou");
                }));

        Assertions.assertThat(trainees)
                .extracting(Trainee::getFirstName, Trainee::getLastName)
                .contains(
                        tuple("Tommy", "Zhou")
                );
    }

    private static final class Trainee {

        private final String firstName;
        private final String lastName;

        private Trainee(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        String getFirstName() {
            return firstName;
        }

        String getLastName() {
            return lastName;
        }

        @Override
        public String toString() {
            return "Trainee{" +
                   "firstName='" + firstName + '\'' +
                   ", lastName='" + lastName + '\'' +
                   '}';
        }

    }

    static class TraineeTypeSafeMatcher extends TypeSafeMatcher<Trainee> {

        private final String firstName;
        private String lastName;

        TraineeTypeSafeMatcher(String firstName) {
            this.firstName = firstName;
        }

        @Override
        protected boolean matchesSafely(Trainee trainee) {
            return trainee.getFirstName().equals(firstName) && (lastName == null || trainee.getLastName().equals(lastName));
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("A trainee with firstname ").appendValue(firstName);
            if (lastName != null) {
                description.appendText(" and lastname ").appendValue(lastName);
            }
        }

        static TraineeTypeSafeMatcher traineeWithFirstName(String firstName) {
            return new TraineeTypeSafeMatcher(firstName);
        }

        TraineeTypeSafeMatcher andLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

    }

}
