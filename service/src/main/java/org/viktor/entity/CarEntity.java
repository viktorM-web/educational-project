package org.viktor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"carCategory", "orders"})
@EqualsAndHashCode(exclude = {"carCategory", "orders"})
@Builder
@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String vinCode;
    private String brand;
    private String model;
    private Integer yearIssue;
    private String colour;
    private Integer seatsQuantity;
    private String image;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CarCategoryEntity carCategory;

    @OneToMany(mappedBy = "car", cascade = {PERSIST, MERGE, REFRESH}, orphanRemoval = true)
    private Set<OrderEntity> orders = new HashSet<>();

    public void addCar(OrderEntity order) {
        orders.add(order);
        order.setCar(this);
    }
}
