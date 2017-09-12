package com.github.kuros.cassandra.query.metamodel;

import java.lang.reflect.Member;

public class AbstractAttributeImpl<X, Y> implements Attribute<X, Y> {
    protected String name;
    protected Class<X> declaringJavaType;
    protected Class<Y> javaType;
    protected Member javaMember;

    public AbstractAttributeImpl(final Class<X> declaringJavaType, final String name, final Member javaMember, final Class<Y> javaType) {
        this.declaringJavaType = declaringJavaType;
        this.name = name;
        this.javaMember = javaMember;
        this.javaType = javaType;
    }

    public String getName() {
        return name;
    }

    public Class<X> getDeclaringJavaType() {
        return declaringJavaType;
    }

    public Class<Y> getJavaType() {
        return javaType;
    }

    public Member getJavaMember() {
        return javaMember;
    }
}
