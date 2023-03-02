package org.viktor.entity;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
class EntityUtil {

    public static CarCategoryEntity buildCarCategory() {
        return CarCategoryEntity.builder()
                .category("S")
                .dayPrice(BigDecimal.valueOf(60.12))
                .build();
    }

    public static UserEntity buildUser() {
        return UserEntity.builder()
                .email("ivan@mail.ru")
                .password("123")
                .role(Role.CLIENT)
                .build();
    }

    public static CarEntity buildCar(CarCategoryEntity carCategory) {
        return CarEntity.builder()
                .brand("BMW")
                .model("530i")
                .yearIssue(2022)
                .colour("BLACK")
                .seatsQuantity(5)
                .image("")
                .carCategory(carCategory)
                .build();
    }

    public static ClientDataEntity buildClientData(UserEntity user) {
        return ClientDataEntity.builder()
                .user(user)
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthday(LocalDate.of(2000, 1, 2))
                .driverLicenceNo("AB1235456")
                .dateExpiry(LocalDate.of(2024, 1, 8))
                .driverExperience(4)
                .build();
    }

    public static OrderEntity buildOrder(UserEntity user, CarEntity car) {
        return OrderEntity.builder()
                .user(user)
                .car(car)
                .startDateUse(LocalDateTime.of(2023, 3, 1, 2, 0))
                .expirationDate(LocalDateTime.of(2023, 3, 3, 2, 0))
                .status(Status.ACCEPTED)
                .build();
    }

    public static ExtraPaymentEntity buildPayment(OrderEntity order) {
        return ExtraPaymentEntity.builder()
                .order(order)
                .description("speeding fine 2023.3.2 14:00 50.00$")
                .price(BigDecimal.valueOf(50.0).setScale(2))
                .build();
    }
}