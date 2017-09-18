package com.github.kuros.cassandra.query.builder.predicate;

import com.github.kuros.cassandra.query.metamodel.Attribute;

public final class GreaterThanOrEq<T, V> implements DbPredicate {

    private final Attribute<T, V> function;
    private final V value;

    private GreaterThanOrEq(final Attribute<T, V> function, final V value) {
        this.function = function;
        this.value = value;
    }

    public static <T, V> GreaterThanOrEq of(final Attribute<T, V> function, final V value) {
        return new GreaterThanOrEq(function, value);
    }


    @Override
    public String toCql() {
        return function.getColumnName() + " >= '" + value + "'";
    }

}
