package com.axxes.traineeship.testing.mocking;

import com.axxes.traineeship.testing.integration.Person;
import com.axxes.traineeship.testing.integration.PersonDao;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SocialSecurityServiceTest2 {

    @Test
    void registerPerson() throws Exception {
        PersonDao personDao = mock(PersonDao.class);
        Person persistedPerson = new Person("123", "Niet Kevin", "Niet Sels");
        when(personDao.create(any())).thenReturn(persistedPerson);

//        SocialSecurityGenerator socialSecurityNumberGenerator = mock(SocialSecurityGenerator.class);
//        when(socialSecurityNumberGenerator.generate()).thenReturn("12354");

        SocialSecurityService socialSecurityService = new SocialSecurityService(personDao, () -> "12354");

        Person returnedPerson = socialSecurityService.registerPerson("Kevin", "Sels");

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personDao).create(personCaptor.capture());

        Person savedPerson = personCaptor.getValue();
        assertThat(savedPerson.getSocialSecurityNumber()).isEqualTo("12354");
        assertThat(savedPerson.getFirstName()).isEqualTo("Kevin");
        assertThat(savedPerson.getLastName()).isEqualTo("Sels");

        assertThat(returnedPerson).isSameAs(persistedPerson);

        verifyNoMoreInteractions(personDao);
    }

    @Test
    void registerPerson_exceptionOnCreate() throws Exception {
        PersonDao personDao = mock(PersonDao.class);
        when(personDao.create(any())).thenThrow(new RuntimeException("It didn't work"));

        SocialSecurityService socialSecurityService = new SocialSecurityService(personDao, () -> "12354");

        assertThatThrownBy(() -> socialSecurityService.registerPerson("Kevin", "Sels"))
                .isInstanceOf(RegisterPersonException.class)
                .hasRootCauseMessage("It didn't work");
    }

}
