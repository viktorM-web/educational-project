package org.viktor.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import org.viktor.dto.CarFilterDto;
import org.viktor.entity.CarCategoryEntity_;
import org.viktor.entity.CarEntity;
import org.viktor.entity.CarEntity_;

import java.util.List;

import static org.viktor.entity.QCarCategoryEntity.carCategoryEntity;
import static org.viktor.entity.QCarEntity.carEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDao {
    private static final CarDao INSTANCE = new CarDao();

    public List<CarEntity> findAllByFilterQuerydsl(Session session, CarFilterDto filter) {
        Predicate predicate = QPredicate.builder()
                .add(filter.getCategory(), carCategoryEntity.category::eq)
                .add(filter.getMaxDayPrice(), carCategoryEntity.dayPrice::loe)
                .add(filter.getBrand(), carEntity.brand::eq)
                .add(filter.getModel(), carEntity.model::eq)
                .add(filter.getOlderYearIssue(), carEntity.yearIssue::goe)
                .add(filter.getColour(), carEntity.colour::eq)
                .add(filter.getMinSeatsQuantity(), carEntity.seatsQuantity::goe)
                .buildAnd();

        return new JPAQuery<CarEntity>(session)
                .select(carEntity)
                .from(carEntity)
                .join(carEntity.carCategory, carCategoryEntity)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), session.getEntityGraph("WithCarCategory"))
                .fetch();
    }

    public List<CarEntity> findAllByFilterCriteriaApi(Session session, CarFilterDto filter) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(CarEntity.class);

        var car = criteria.from(CarEntity.class);
        var carCategory = car.join(CarEntity_.carCategory);

        javax.persistence.criteria.Predicate[] predicates = CriteriaPredicate.builder()
                .add(filter.getCategory(), it -> cb.equal(carCategory.get(CarCategoryEntity_.category), it))
                .add(filter.getMaxDayPrice(), it -> cb.le(carCategory.get(CarCategoryEntity_.dayPrice), it))
                .add(filter.getBrand(), it -> cb.equal(car.get(CarEntity_.brand), it))
                .add(filter.getModel(), it -> cb.equal(car.get(CarEntity_.model), it))
                .add(filter.getOlderYearIssue(), it -> cb.ge(car.get(CarEntity_.yearIssue), it))
                .add(filter.getColour(), it -> cb.equal(car.get(CarEntity_.colour), it))
                .add(filter.getMinSeatsQuantity(), it -> cb.ge(car.get(CarEntity_.seatsQuantity), it))
                .build();

        criteria.select(car)
                .where(predicates);

        return session.createQuery(criteria)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), session.getEntityGraph("WithCarCategory"))
                .list();
    }

    public static CarDao getInstance() {
        return INSTANCE;
    }
}
