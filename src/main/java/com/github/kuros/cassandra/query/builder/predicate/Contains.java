package com.github.kuros.cassandra.query.builder.predicate;

import com.github.kuros.cassandra.query.metamodel.Attribute;

public final class Contains<T, V> implements DbPredicate {

    private String keyword;
    private final Attribute<T, V> function1;
    private final V value;

    private Contains(final String keyword, final Attribute<T, V> function1, final V value) {
        this.function1 = function1;
        this.value = value;
        this.keyword = keyword;
    }

    public static <T, V> Contains of(final Attribute<T, V> function1, final V value) {
        return new Contains("CONTAINS", function1, value);
    }

    public static <T, V> Contains key(final Attribute<T, V> function1, final V value) {
        return new Contains("CONTAINS KEY", function1, value);
    }

    @Override
    public String toCql() {
        return function1.getColumnName() + " " + keyword +  "'" + value.toString() + "'";
    }

}
