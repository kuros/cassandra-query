package com.github.kuros.cassandra.query.metamodel;

import java.lang.reflect.Member;

public class SingularAttributeImpl<X, Y> extends AbstractAttributeImpl<X, Y> implements SingularAttribute<X, Y> {

    public SingularAttributeImpl(final String name, final Class<X> declaringJavaType, final Class<Y> javaType, final Member javaMember) {
        super(declaringJavaType, name, javaMember, javaType);
    }

}
