package com.axxes.traineeship.testing.mocking;

import com.axxes.traineeship.testing.integration.Person;
import com.axxes.traineeship.testing.integration.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;

public class SocialSecurityService {

    private final PersonDao personDao;
    private SocialSecurityGenerator socialSecurityNumberGenerator;

    @Autowired
    public SocialSecurityService(final PersonDao personDao, SocialSecurityGenerator socialSecurityNumberGenerator) {
        this.personDao = personDao;
        this.socialSecurityNumberGenerator = socialSecurityNumberGenerator;
    }

    public Person registerPerson(final String firstName, final String lastName) throws RegisterPersonException {
        String socialSecurityNumber = socialSecurityNumberGenerator.generate();

        Person person = new Person(socialSecurityNumber, firstName, lastName);

        try {
            return personDao.create(person);
        } catch (Exception e) {
            throw new RegisterPersonException(e);
        }
    }

}
