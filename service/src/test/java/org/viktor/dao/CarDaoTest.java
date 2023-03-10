package org.viktor.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.viktor.dto.CarFilterDto;
import org.viktor.entity.CarEntity;
import org.viktor.util.HibernateTestUtil;
import org.viktor.util.TestDataImporter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class CarDaoTest {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final CarDao carDao = CarDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @ParameterizedTest
    @MethodSource("carFilterDataProvider")
    void findAllByFilterCriteriaApi(CarFilterDto filter, int expectedResult) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<CarEntity> actualResult = carDao.findAllByFilterCriteriaApi(session, filter);

            assertThat(actualResult).hasSize(expectedResult);

            session.getTransaction().rollback();
        }
    }

    @ParameterizedTest
    @MethodSource("carFilterDataProvider")
    void findAllByFilterQuerydsl(CarFilterDto filter, int expectedResult) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<CarEntity> actualResult = carDao.findAllByFilterQuerydsl(session, filter);

            assertThat(actualResult).hasSize(expectedResult);

            session.getTransaction().rollback();
        }
    }

    public static Stream<Arguments> carFilterDataProvider() {
        return Stream.of(
                Arguments.of(build(null, null, null, null,
                        null, null, null), 9),
                Arguments.of(build("Mercedes", "V", 2020, "white",
                        6, "business", BigDecimal.valueOf(130.00)), 1),
                Arguments.of(build("Mercedes", null, 2019, null,
                        4, null, BigDecimal.valueOf(140.00)), 3)
        );
    }

    private static CarFilterDto build(String brand, String model, Integer yearIssue,
                                      String colour, Integer seatsQuantity, String category,
                                      BigDecimal dayPrice) {
        return CarFilterDto.builder()
                .brand(brand)
                .model(model)
                .yearIssue(yearIssue)
                .colour(colour)
                .seatsQuantity(seatsQuantity)
                .category(category)
                .dayPrice(dayPrice)
                .build();
    }
}
