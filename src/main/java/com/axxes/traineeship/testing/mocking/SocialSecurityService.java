package com.axxes.traineeship.testing.mocking;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class SocialSecurityService {

    private final PersonDao personDao;

    @Autowired
    public SocialSecurityService(final PersonDao personDao) {
        this.personDao = personDao;
    }

    public Person registerPerson(final String firstName, final String lastName) throws RegisterPersonException {
        String socialSecurityNumber = UUID.randomUUID().toString();

        Person person = new Person(socialSecurityNumber, firstName, lastName);

        try {
            return personDao.create(person);
        } catch (Exception e) {
            throw new RegisterPersonException(e);
        }
    }

}
