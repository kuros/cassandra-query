package com.github.kuros.cassandra.query.builder.predicate;

import com.github.kuros.cassandra.query.metamodel.Attribute;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public final class In<T, V> implements DbPredicate {

    private final Attribute<T, V> function;
    private final List<V> values;

    private In(final Attribute<T, V> function, final V... values) {
        this.function = function;
        this.values = Lists.newArrayList(values);
    }

    public static <T, V> In of(final Attribute<T, V> function, final V... values) {
        return new In(function, values);
    }

    @Override
    public String toCql() {
        final String in = values
                .stream()
                .map(V::toString).collect(Collectors.joining(", ", " ( ", " ) "));
        return function.getColumnName() + " IN " + in;
    }

}
