package org.viktor.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.viktor.config.ApplicationConfiguration;
import org.viktor.util.TestDataImporter;

import javax.persistence.EntityManager;

abstract class RepositoryTestBase {

    private static SessionFactory sessionFactory;
    protected static EntityManager entityManager;
    protected static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        sessionFactory = context.getBean(SessionFactory.class);
        TestDataImporter.importData(sessionFactory);
        entityManager = context.getBean(EntityManager.class);
    }

    @BeforeEach
    void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void closeTransaction() {

        entityManager.getTransaction().rollback();
    }

    @AfterAll
    static void closeSessionFactory() {
        context.close();
    }
}
