package com.github.kuros.cassandra.query.metamodel;

public interface Type<X> {

    Class<X> getJavaType();
}
