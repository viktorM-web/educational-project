package org.viktor.spring.integration;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.viktor.spring.integration.annotation.IT;

@IT
@Sql("classpath:sql/data.sql")
public abstract class IntegrationTestBase {

    public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.1");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }

}
