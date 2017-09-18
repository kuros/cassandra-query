package com.github.kuros.cassandra.query.builder.function;

/**
 * Created by Kumar Rohit on 15/09/17.
 */
public class Value<T> implements DbFunction {

    private T value;

    private Value(final T value) {
        this.value = value;
    }

    public static <T> Value<T> of(final T value) {
        return new Value<>(value);
    }

    @Override
    public String toCql() {
        return value.toString();
    }
}
