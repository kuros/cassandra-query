package com.github.kuros.cassandra.query.metamodel;

public interface PluralAttribute<X, C, E> extends Attribute<X, C> {
    Class<E> getElementJavaType();
}
