Rental car booking progect
![image](https://github.com/viktorM-web/educational-project/assets/81855790/7f4972a1-d8ed-483e-9f0e-5434cacfdcb5)
The project was completed in accordance with the following requirements:

-The project must be built using Apache Maven (Gradle optional)
-The project must have two modules (common, service) and be assembled into a jar
-Need to use Spring Boot with Embedded Tomcat

-Use PostgreSQL as DBMS
-Using Hibernate for Entity Mapping
-At least 5 meaningful @Entities
-Relationships: 1-1, 1-n (n-n optional)
-Presence of Embedded entities (optional)
-Presence of an optimistic entity lock
-The presence of filter queries with pagination
(HQL, Criteria API, Querydsl)
-Using Hibernate Second Level Cache (Optional)
-The password must be stored encrypted

-The application should be transactional in the service layer (@Transactional)
-The presence of logging to the console (log4j, logback)
-Implement logging of the called service method with all parameters and return value via Spring AOP

-For endpoints path naming, consider API Design Best Practices
-Use Thymeleaf as the presentation layer
-Use @RestController when working with static (files, pictures, audio, video)
-Implement pages for authorization and registration of users
-Implement pagination page
-Implement internationalization
-Use Spring JSR-303 for validation

-Use Spring Security to authenticate and authorize users on the system
-The presence of at least two roles for the user (for example, USER and ADMIN)
-Availability of functionality that is available only to the administrator (ADMIN)
-At least 40% of the code should be covered by unit and integration tests (jacoco plugin)
