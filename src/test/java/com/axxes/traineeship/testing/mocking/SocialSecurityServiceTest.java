package com.axxes.traineeship.testing.mocking;

import com.axxes.traineeship.testing.integration.Person;
import com.axxes.traineeship.testing.integration.PersonDao;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class SocialSecurityServiceTest {

    PersonDao personDaoMock = mock(PersonDao.class);
    SocialSecurityService socialSecurityService = new SocialSecurityService(personDaoMock);

    @BeforeEach
    void setPersonDaoMock() {
        when(personDaoMock.create(Mockito.any(Person.class)))
                .thenAnswer((Answer<Person>) invocation -> (Person) invocation.getArguments()[0]);

    }

    @Test
    void registerPerson() throws RegisterPersonException {
        doThrow(new IllegalAccessError()).when(personDaoMock).deleteAll();

        Person person = socialSecurityService.registerPerson("Kevin", "Sels");

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personDaoMock).create(personCaptor.capture());



        Person value = person;
//        Person value = personCaptor.getValue();
        System.out.println("value = " + value);
        assertThat(value.getSocialSecurityNumber(), is(Matchers.any(String.class)));
        assertThat(value.getFirstName(), equalTo("Kevin"));

        verifyNoMoreInteractions(personDaoMock);
    }

}
