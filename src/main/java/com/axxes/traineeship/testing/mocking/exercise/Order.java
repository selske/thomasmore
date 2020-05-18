package com.axxes.traineeship.testing.mocking.exercise;

import com.axxes.traineeship.testing.mocking.exercise.Customer;

public class Order {

    private Customer customer;
    private double total;

    public Order(Customer customer, double total) {
        this.customer = customer;
        this.total = total;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getTotal() {
        return this.total;
    }

}
