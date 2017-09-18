package com.github.kuros.cassandra.query.builder.predicate;

import com.github.kuros.cassandra.query.metamodel.Attribute;

public final class GreaterThan<T, V> implements DbPredicate {

    private final Attribute<T, V> function;
    private final V value;

    private GreaterThan(final Attribute<T, V> function, final V value) {
        this.function = function;
        this.value = value;
    }

    public static <T, V> GreaterThan of(final Attribute<T, V> function, final V value) {
        return new GreaterThan(function, value);
    }

    @Override
    public String toCql() {
        return function.getColumnName() + " > '" + value + "'";
    }
}
