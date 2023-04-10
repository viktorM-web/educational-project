package org.viktor.util;

import lombok.experimental.UtilityClass;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.entity.ClientDataEntity;
import org.viktor.entity.ExtraPaymentEntity;
import org.viktor.entity.OrderEntity;
import org.viktor.entity.Role;
import org.viktor.entity.Status;
import org.viktor.entity.UserEntity;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TestDataImporter {

    public static void importData(EntityManager entityManager) {

        var ivan = saveUser(entityManager, "ivan@mail.ru");
        var petr = saveUser(entityManager, "petr@mail.ru");

        var ivanData = saveClientData(entityManager, ivan, "Ivan", "Ivanov",
                LocalDate.of(2000, 1, 3), "123456AB",
                LocalDate.of(2030, 1, 1), 3);

        var economy = saveCarCategory(entityManager, "economy", 40.5);
        var premium = saveCarCategory(entityManager, "premium", 60.7);
        var business = saveCarCategory(entityManager, "business", 120.0);

        var polo = saveCar(entityManager, economy, "11111AA", "Volkswagen", "Polo", 2020, "white", 5);
        var golf = saveCar(entityManager, economy, "22222BB", "Volkswagen", "Golf", 2021, "red", 5);
        var mazda = saveCar(entityManager, economy, "33333SS", "Mazda", "3", 2022, "black", 5);
        var bmw = saveCar(entityManager, premium, "35689QQ", "BMW", "530", 2020, "white", 5);
        var mercedes = saveCar(entityManager, premium, "QW89564", "Mercedes", "E", 2021, "black", 5);
        var audi = saveCar(entityManager, premium, "RT48968", "Audi", "A5", 2022, "blue", 5);
        var maybach = saveCar(entityManager, business, "LK25674", "Mercedes", "Maybach", 2022, "black", 5);
        var rolls = saveCar(entityManager, business, "PO65324", "Rolls-Royce", "Ghost", 2021, "black", 5);
        var bus = saveCar(entityManager, business, "UY45873", "Mercedes", "V", 2020, "white", 6);

        var order1 = saveOrder(entityManager, ivan, bus, LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 2, 1, 10, 0), Status.ACCEPTED);
        var order2 = saveOrder(entityManager, ivan, bus, LocalDateTime.of(2023, 1, 1, 10, 0),
                LocalDateTime.of(2023, 2, 1, 10, 0), Status.ACCEPTED);

        var payment = savePayment(entityManager, order2, "speeding fine 2023.1.4 14:00 50.00$", BigDecimal.valueOf(50.0).setScale(2));
    }

    private static ExtraPaymentEntity savePayment(EntityManager entityManager,
                                                  OrderEntity order,
                                                  String description,
                                                  BigDecimal price) {
        ExtraPaymentEntity payment = ExtraPaymentEntity.builder()
                .order(order)
                .description(description)
                .price(price)
                .build();

        entityManager.persist(payment);

        return payment;
    }

    private static OrderEntity saveOrder(EntityManager entityManager,
                                         UserEntity user,
                                         CarEntity car,
                                         LocalDateTime startDateUse,
                                         LocalDateTime expirationDate,
                                         Status status) {
        OrderEntity order = OrderEntity.builder()
                .user(user)
                .car(car)
                .startDateUse(startDateUse)
                .expirationDate(expirationDate)
                .status(status)
                .build();

        entityManager.persist(order);

        return order;
    }

    private static CarEntity saveCar(EntityManager entityManager,
                                     CarCategoryEntity carCategory,
                                     String vinCode,
                                     String brand,
                                     String model,
                                     Integer yearIssue,
                                     String colour,
                                     Integer seatsQuantity) {
        CarEntity car = CarEntity.builder()
                .brand(brand)
                .vinCode(vinCode)
                .model(model)
                .yearIssue(yearIssue)
                .colour(colour)
                .seatsQuantity(seatsQuantity)
                .image("")
                .carCategory(carCategory)
                .build();

        entityManager.persist(car);

        return car;
    }

    private static CarCategoryEntity saveCarCategory(EntityManager entityManager,
                                                     String category, Double price) {
        CarCategoryEntity carCategory = CarCategoryEntity.builder()
                .category(category)
                .dayPrice(BigDecimal.valueOf(price))
                .build();

        entityManager.persist(carCategory);

        return carCategory;
    }

    private static UserEntity saveUser(EntityManager entityManager, String email) {
        UserEntity user = UserEntity.builder()
                .email(email)
                .password("123")
                .role(Role.CLIENT)
                .build();

        entityManager.persist(user);

        return user;
    }

    private static ClientDataEntity saveClientData(EntityManager entityManager,
                                                   UserEntity user,
                                                   String firstname,
                                                   String lastname,
                                                   LocalDate birthday,
                                                   String driverLicenceNo,
                                                   LocalDate dateExpiry,
                                                   Integer driverExperience) {
        ClientDataEntity clientData = ClientDataEntity.builder()
                .user(user)
                .firstname(firstname)
                .lastname(lastname)
                .birthday(birthday)
                .driverLicenceNo(driverLicenceNo)
                .dateExpiry(dateExpiry)
                .driverExperience(driverExperience)
                .build();

        entityManager.persist(clientData);

        return clientData;
    }
}
