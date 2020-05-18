package com.axxes.traineeship.testing.mocking.exercise;

public class Invoice {

    private final Customer customer;
    private final double total;

    public Invoice(Customer customer, double total) {
        this.customer = customer;
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotal() {
        return total;
    }

}
