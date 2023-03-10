package org.viktor.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Predicate_ {
    private final List<Predicate> predicates = new ArrayList<>();

    public static Predicate_ builder() {
        return new Predicate_();
    }

    public <T> Predicate_ add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public Predicate[] build() {
        return predicates.toArray(Predicate[]::new);
    }
}
