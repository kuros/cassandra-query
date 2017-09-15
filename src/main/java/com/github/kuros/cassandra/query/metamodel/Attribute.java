package com.github.kuros.cassandra.query.metamodel;

import java.lang.reflect.Member;

public interface Attribute<X, Y> {

    String getName();
    String getColumnName();
    ManagedType<X> getDeclaringType();
    Class<Y> getJavaType();
}
