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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@NamedEntityGraph(
        name = "WithCarCategory",
        attributeNodes = {
                @NamedAttributeNode("carCategory"),
                @NamedAttributeNode("orders")
        }
)
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
    private List<OrderEntity> orders = new ArrayList<>();


    public void addCar(OrderEntity order) {
        orders.add(order);
        order.setCar(this);
    }
}
