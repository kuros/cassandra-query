package com.github.kuros.cassandra.query.builder.predicate;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public final class And implements DbPredicate {

    private final List<DbPredicate> predicates;

    private And(final DbPredicate... predicates) {
        this.predicates = Lists.newArrayList(predicates);
    }

    public static And of(final DbPredicate... predicates) {
        return new And(predicates);
    }

    public static And of(final List<DbPredicate> predicates) {
        return new And(predicates.toArray(new DbPredicate[predicates.size()]));
    }

    public And add(final DbPredicate dbPredicate) {
        predicates.add(dbPredicate);
        return this;
    }

    @Override
    public String toCql() {
        return predicates
                .stream()
                .map(DbPredicate::toCql)
                .collect(Collectors.joining(" AND ", "(", ")"));

    }
}
