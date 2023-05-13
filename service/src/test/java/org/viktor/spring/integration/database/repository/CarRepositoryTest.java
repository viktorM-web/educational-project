package org.viktor.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.viktor.repository.CarCategoryRepository;
import org.viktor.repository.CarRepository;
import org.viktor.dto.CarFilterDto;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.spring.integration.IntegrationTestBase;
import org.viktor.util.EntityUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CarRepositoryTest extends IntegrationTestBase {

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;
    private final EntityManager entityManager;

    @Test
    void save() {
        CarEntity car = saveCar();

        assertThat(car.getId()).isNotNull();
    }

    @Test
    void delete() {
        CarEntity car = saveCar();
        entityManager.clear();

        CarEntity expectedCar = carRepository.findById(car.getId()).get();

        carRepository.delete(expectedCar);
        entityManager.flush();
        entityManager.clear();

        assertThat(carRepository.findById(expectedCar.getId())).isEmpty();
    }

    @Test
    void update() {
        CarEntity car = saveCar();
        entityManager.clear();

        CarEntity expectedCar = carRepository.findById(car.getId()).get();

        expectedCar.setVinCode("12545454er154trerg");
        carRepository.saveAndFlush(expectedCar);
        entityManager.clear();

        assertThat(carRepository.findById(expectedCar.getId()).get()).isEqualTo(expectedCar);
    }

    @Test
    void findById() {
        CarEntity car = saveCar();
        entityManager.clear();
        CarEntity expectedCar = carRepository.findById(car.getId()).get();
        entityManager.clear();

        assertThat(expectedCar.getVinCode()).isEqualTo("7777XXXX");
    }

    @Test
    void findAll() {
        List<CarEntity> expectedCar = carRepository.findAll();
        entityManager.clear();

        assertThat(expectedCar).hasSize(9);
    }

    @ParameterizedTest
    @MethodSource("carFilterDataProvider")
    void findAllByFilterQuerydsl(CarFilterDto filter, List<Integer> expectedResult) {
        List<CarEntity> actualResult = carRepository.findAllByFilter(filter);
        List<Integer> actualId = actualResult.stream().map(CarEntity::getId).collect(toList());

        assertThat(actualResult).hasSize(expectedResult.size());
        assertThat(actualId).containsAll(expectedResult);
    }

    public static Stream<Arguments> carFilterDataProvider() {
        return Stream.of(
                Arguments.of(build(null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null),
                        List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)),
                Arguments.of(build(
                                "Mercedes",
                                "V",
                                2020,
                                "white",
                                6,
                                "business",
                                BigDecimal.valueOf(130.00)),
                        List.of(9)),
                Arguments.of(build(
                                "Mercedes",
                                null,
                                2019,
                                null,
                                4,
                                null,
                                BigDecimal.valueOf(140.00)),
                        List.of(5, 7, 9)),
                Arguments.of(build(
                                null,
                                null,
                                2023,
                                null,
                                null,
                                null,
                                null),
                        List.of())
        );
    }

    private static CarFilterDto build(String brand,
                                      String model,
                                      Integer yearIssue,
                                      String colour,
                                      Integer seatsQuantity,
                                      String category,
                                      BigDecimal dayPrice) {
        return CarFilterDto.builder()
                .brand(brand)
                .model(model)
                .olderYearIssue(yearIssue)
                .colour(colour)
                .minSeatsQuantity(seatsQuantity)
                .category(category)
                .maxDayPrice(dayPrice)
                .build();
    }

    private CarEntity saveCar() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        carCategoryRepository.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);

        carRepository.save(car);
        return car;
    }
}
