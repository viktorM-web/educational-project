package org.viktor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "cars")
@EqualsAndHashCode(exclude = "cars")
@Builder
@Entity
@Table(name = "car_category")
public class CarCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
    private BigDecimal dayPrice;

    @OneToMany(mappedBy = "carCategory", cascade = {PERSIST, MERGE, REFRESH}, orphanRemoval = true)
    private List<CarEntity> cars = new ArrayList<>();

    public void addCar(CarEntity car) {
        cars.add(car);
        car.setCarCategory(this);
    }
}
