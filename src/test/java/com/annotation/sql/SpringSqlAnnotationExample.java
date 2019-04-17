package com.annotation.sql;

import java.util.List;

import javax.sql.DataSource;

import static org.junit.Assert.*;

import org.hsqldb.TransactionManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Sql({"classpath:drop_schema.sql", "classpath:schema.sql", "classpath:data.sql"})
public class SpringSqlAnnotationExample {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void printTestName() {
        System.out.println(testName.getMethodName());
    }

    @Test
    public void printRows() {
        List empNames = jdbcTemplate.queryForList("select name from employee", String.class);
        assertEquals(2, empNames.size());
        System.out.println(empNames);
    }

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setName("empty-sql-scripts-without-tx-mgr-test-db")//
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }
}