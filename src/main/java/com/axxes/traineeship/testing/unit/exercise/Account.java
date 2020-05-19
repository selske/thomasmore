package com.axxes.traineeship.testing.unit.exercise;

import java.text.NumberFormat;

public class Account {

    private NumberFormat fmt = NumberFormat.getCurrencyInstance();

    private final long acctNumber;
    private float balance;
    public final String name;

    public Account(String owner, long account, float initial) {
        name = owner;
        acctNumber = account;
        balance = initial;
    }

    public void deposit(float amount) {
        if (amount < 0) {
            throw new InvalidOperationException("Unable to deposit a negative amount (" + amount + ")");
        } else {
            balance = balance + amount;
        }
    }

    public void withdraw(float amount, float fee) {
        if (isValidWithdrawal(amount, fee)) {
            amount += fee;
            balance = balance - amount;
        } else {
            throw new InvalidOperationException("Unable to withdraw " + (amount + fee));
        }
    }

    private boolean isValidWithdrawal(float amount, float fee) {
        return amount >= 0 && fee >= 0 && amount <= balance;
    }

    public void addInterest(float interestRate) {
        balance += balance * interestRate;
    }

    public float getBalance() {
        return balance;
    }

    public long getAccountNumber() {
        return acctNumber;
    }

    public String toString() {
        return (acctNumber + " - " + name + " - " + fmt.format(balance));
    }

}
