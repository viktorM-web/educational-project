package org.viktor.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.viktor.util.HibernateTestUtil;
import org.viktor.util.TestDataImporter;

import java.lang.reflect.Proxy;

abstract class RepositoryTestBase {
    private static SessionFactory sessionFactory;
    protected static Session session;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
        session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));

    }

    @BeforeEach
    void beginTransaction() {
        session.beginTransaction();
    }

    @AfterEach
    void closeTransaction() {
        session.getTransaction().rollback();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

}
