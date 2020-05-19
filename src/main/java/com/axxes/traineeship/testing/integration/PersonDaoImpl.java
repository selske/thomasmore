package com.axxes.traineeship.testing.integration;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class PersonDaoImpl implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public PersonDaoImpl(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Person create(final Person person) {
        jdbcTemplate.update("INSERT INTO PERSONS(SOCIAL_SECURITY_NUMBER,FIRST_NAME,LAST_NAME) VALUES(?,?,?)", person.getSocialSecurityNumber(), person.getFirstName(), person.getLastName());

        return person;
    }

    @Override
    public void deleteAll() {

    }

}
