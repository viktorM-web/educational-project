package org.viktor.util;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.entity.Role;
import org.viktor.entity.UserEntity;

import java.math.BigDecimal;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var ivan = saveUser(session, "ivan@mail.ru");
        var petr = saveUser(session, "petr@mail.ru");

        var economy = saveCarCategory(session, "economy", 40.5);
        var premium = saveCarCategory(session, "premium", 60.7);
        var business = saveCarCategory(session, "business", 120.0);

        var polo = saveCar(session, economy, "Volkswagen", "Polo", 2020, "white", 5);
        var golf = saveCar(session, economy, "Volkswagen", "Golf", 2021, "red", 5);
        var mazda = saveCar(session, economy, "Mazda", "3", 2022, "black", 5);
        var bmw = saveCar(session, premium, "BMW", "530", 2020, "white", 5);
        var mercedes = saveCar(session, premium, "Mercedes", "E", 2021, "black", 5);
        var audi = saveCar(session, premium, "Audi", "A5", 2022, "blue", 5);
        var maybach = saveCar(session, business, "Mercedes", "Maybach", 2022, "black", 5);
        var rolls = saveCar(session, business, "Rolls-Royce", "Ghost", 2021, "black", 5);
        var bus = saveCar(session, business, "Mercedes", "V", 2020, "white", 6);

        session.getTransaction().commit();
    }

    private static CarEntity saveCar(Session session,
                                     CarCategoryEntity carCategory,
                                     String brand,
                                     String model,
                                     Integer yearIssue,
                                     String colour,
                                     Integer seatsQuantity) {
        CarEntity car = CarEntity.builder()
                .brand(brand)
                .model(model)
                .yearIssue(yearIssue)
                .colour(colour)
                .seatsQuantity(seatsQuantity)
                .image("")
                .carCategory(carCategory)
                .build();

        session.save(car);

        return car;
    }

    private static CarCategoryEntity saveCarCategory(Session session, String category, Double price) {
        CarCategoryEntity carCategory = CarCategoryEntity.builder()
                .category(category)
                .dayPrice(BigDecimal.valueOf(price))
                .build();

        session.save(carCategory);

        return carCategory;
    }

    private static UserEntity saveUser(Session session, String email) {
        UserEntity user = UserEntity.builder()
                .email(email)
                .password("123")
                .role(Role.CLIENT)
                .build();

        session.save(user);

        return user;
    }
}
