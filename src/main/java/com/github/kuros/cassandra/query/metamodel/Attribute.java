package com.github.kuros.cassandra.query.metamodel;

import java.lang.reflect.Member;

public interface Attribute<X, Y> {

    String getName();
    Class<X> getDeclaringJavaType();
    Class<Y> getJavaType();
    Member getJavaMember();
}
