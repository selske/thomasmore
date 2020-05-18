package com.axxes.traineeship.testing.mocking.exercise;

public class Customer {

    private double discount;
    private String email;

    public Customer(double discount, String email) {
        this.discount = discount;
        this.email = email;
    }

    public double getDiscount() {
        return discount;
    }

    public String getEmail() {
        return email;
    }

}
