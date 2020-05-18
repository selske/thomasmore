package com.axxes.traineeship.testing.mocking;

public class Person {

    private Long id;
    private String socialSecurityNumber;
    private String firstName;
    private String lastName;

    public Person(final String socialSecurityNumber, final String firstName, final String lastName) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(final String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((socialSecurityNumber == null) ? 0 : socialSecurityNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        if (socialSecurityNumber == null) {
            if (other.socialSecurityNumber != null) {
                return false;
            }
        } else if (!socialSecurityNumber.equals(other.socialSecurityNumber)) {
            return false;
        }
        return true;
    }
}
