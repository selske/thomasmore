package com.axxes.traineeship.testing.integration;

import com.axxes.traineeship.testing.mocking.Person;
import com.axxes.traineeship.testing.mocking.PersonDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class PersonDaoImplTest {

    private DataSource dataSource;

    @BeforeEach
    void setupDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUsername("");
        dataSource.setPassword("");
        dataSource.setDriverClassName(org.h2.Driver.class.getName());

        new JdbcTemplate(dataSource).execute(
                "create table if not exists\n" +
                "PERSONS(\n" +
                "    SOCIAL_SECURITY_NUMBER varchar(100) primary key,\n" +
                "    FIRST_NAME varchar(100),\n" +
                "    LAST_NAME varchar(100)\n" +
                ")"
        );

        this.dataSource = dataSource;
    }

    @BeforeEach
//    @AfterEach
    void cleanup() {
        new JdbcTemplate(dataSource).update("delete from PERSONS");
    }

    @Test
    void testCreate() {
        Person person = new Person("s1", "Kevin", "Sels");

        Person output = new PersonDaoImpl(dataSource).create(person);

        assertThat(output.getFirstName(), is("Kevin"));

        List<Map<String, Object>> result = new JdbcTemplate(dataSource).queryForList("select * from PERSONS where SOCIAL_SECURITY_NUMBER = ?", "s1");
        assertThat(result, hasSize(1));
        assertThat(result.get(0).get("FIRST_NAME"), is("Kevin"));
        assertThat(result.get(0).get("LAST_NAME"), is("Sels"));
    }

}
