package org.viktor.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.viktor.util.HibernateUtil;

import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "org.viktor")
public class ApplicationConfiguration {

    @Bean
    SessionFactory sessionFactory(){
        return HibernateUtil.buildSessionFactory();
    }

    @Bean
    EntityManager entityManager(){
        return (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args) -> method.invoke(sessionFactory().getCurrentSession(), args));
    }
}
