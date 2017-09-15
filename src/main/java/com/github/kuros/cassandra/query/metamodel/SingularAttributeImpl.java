package com.github.kuros.cassandra.query.metamodel;

class SingularAttributeImpl<X, Y> extends AbstractAttributeImpl<X, Y> implements SingularAttribute<X, Y> {

    SingularAttributeImpl(final String name, final String columnName, final Class<Y> javaType, final ManagedType<X> declaringJavaType) {
        super(name, columnName, javaType, declaringJavaType);
    }

}
