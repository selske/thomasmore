package com.axxes.traineeship.testing.unit.exercise;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.Offset.offset;

class AccountTest {

    @Test
    void deposit() {
        Account account = new Account("eigenaar", 12354, 0f);

        account.deposit(17.3f);

        assertThat(account.getBalance()).isEqualTo(17.3f);
    }

    @Test
    void deposit_negativeAmount() {
        Account account = new Account("eigenaar", 12354, 0f);

        assertThatThrownBy(() -> account.deposit(-13.3f))
                .isOfAnyClassIn(InvalidOperationException.class)
                .hasMessage("Unable to deposit a negative amount (-13.3)");

        assertThat(account.getBalance()).isEqualTo(0f);
    }

    @Test
    void withdraw() {
        Account account = new Account("eigenaar", 12354, 1300.1f);

        account.withdraw(17.3f, 0.5f);

//        assertThat(account.getBalance()).isEqualTo(1282.3f);
        assertThat(account.getBalance()).isCloseTo(1282.3f, offset(0.01f));
    }
    @Test
    void withdraw_moreThanBalance() {
        Account account = new Account("eigenaar", 12354, 1300.1f);

        assertThat("");
        assertThatThrownBy(() -> account.withdraw(1700.3f, 0.5f))
                .isOfAnyClassIn(InvalidOperationException.class)
                .hasMessage("Unable to withdraw 1700.8");

        assertThat(account.getBalance()).isEqualTo(1300.1f);
    }

    @Test
    void addInterest() {
        Account account = new Account("eigenaar", 12354, 1300.1f);

        account.addInterest(0.05f);

        assertThat(account.getBalance()).isCloseTo(1365.105f, offset(0.0001f));
    }

    @Test
    void getBalance() {
        Account account = new Account("eigenaar", 12354, 1300.1f);

        assertThat(account.getBalance()).isEqualTo(1300.1f);
    }

    @Test
    void getAccountNumber() {
        Account account = new Account("eigenaar", 12354, 1300.1f);

        assertThat(account.getAccountNumber()).isEqualTo(12354);
    }

    @Test
    void testToString() {
        Account account = new Account("eigenaar", 12354, 1300.1f);

        assertThat(account.toString()).isEqualTo("12354 - eigenaar - $1,300.10");
    }

}
