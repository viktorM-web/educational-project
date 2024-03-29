package org.viktor.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;
import org.viktor.dto.CarFilterDto;
import org.viktor.entity.CarEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.viktor.entity.QCarCategoryEntity.carCategoryEntity;
import static org.viktor.entity.QCarEntity.carEntity;

@RequiredArgsConstructor
public class FilterCarRepositoryImpl implements FilterCarRepository {

    private final EntityManager entityManager;

    @Override
    public List<CarEntity> findAllByFilter(CarFilterDto filter) {
        Predicate predicate = QPredicate.builder()
                .add(filter.getCategory(), carCategoryEntity.category::eq)
                .add(filter.getMaxDayPrice(), carCategoryEntity.dayPrice::loe)
                .add(filter.getBrand(), carEntity.brand::eq)
                .add(filter.getModel(), carEntity.model::eq)
                .add(filter.getOlderYearIssue(), carEntity.yearIssue::goe)
                .add(filter.getColour(), carEntity.colour::eq)
                .add(filter.getMinSeatsQuantity(), carEntity.seatsQuantity::goe)
                .buildAnd();

        return new JPAQuery<CarEntity>(entityManager)
                .select(carEntity)
                .from(carEntity)
                .join(carEntity.carCategory, carCategoryEntity)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), entityManager.getEntityGraph("WithCarCategory"))
                .fetch();
    }
}
