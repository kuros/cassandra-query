package com.github.kuros.cassandra.query.builder;

import com.github.kuros.cassandra.query.metamodel.Attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder<T> {

    private final Class<T> entity;
    private List<Attribute<T, ?>> selectAttributes;

    public QueryBuilder(final Class<T> entity) {
        this.entity = entity;

        this.selectAttributes = new ArrayList<>();
    }

    public static <T> QueryBuilder from(final Class<T> entity) {
        return new QueryBuilder(entity);
    }

    public QueryBuilder select(Attribute<T, ?>... attributes) {
        selectAttributes.addAll(Arrays.asList(attributes));
        return this;
    }
}
