package com.github.kuros.cassandra.query.builder.function;

import com.github.kuros.cassandra.query.metamodel.Attribute;

/**
 * Created by Kumar Rohit on 15/09/17.
 */
public class Count implements DbFunction {

    private DbFunction dbFunction;

    private Count(final DbFunction dbFunction) {
        this.dbFunction = dbFunction;
    }

    public static <X, Y> Count of(Attribute<X, Y> attribute) {
        return new Count(Value.of(attribute.getColumnName()));
    }

    public static Count all() {
        return new Count(Value.of("*"));
    }

    @Override
    public String toCql() {
        return " COUNT (" + dbFunction.toCql() + ") ";
    }
}
