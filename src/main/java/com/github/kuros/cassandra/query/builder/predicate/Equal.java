package com.github.kuros.cassandra.query.builder.predicate;

import com.github.kuros.cassandra.query.metamodel.Attribute;

public final class Equal<T, V> implements DbPredicate {

    private final Attribute<T, V> function1;
    private final V value;

    private Equal(final Attribute<T, V> function1, final V value) {
        this.function1 = function1;
        this.value = value;
    }

    public static <T, V> Equal of(final Attribute<T, V> function1, final V value) {
        return new Equal(function1, value);
    }

    @Override
    public String toCql() {
        return function1.getColumnName() + " = '" + value.toString() + "' ";
    }

}
