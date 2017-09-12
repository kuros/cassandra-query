package com.github.kuros.cassandra.query.metamodel;

import java.lang.reflect.Member;

public class PluralAttributeImpl<X, C, E> extends AbstractAttributeImpl<X, C> implements PluralAttribute<X, C, E> {

    private Class<E> elementJavaType;

    public PluralAttributeImpl(final Class<X> declaringJavaType, final String name, final Member javaMember, final Class<C> javaType, final Class<E> elementJavaType) {
        super(declaringJavaType, name, javaMember, javaType);
        this.elementJavaType = elementJavaType;
    }

    @Override
    public Class<E> getElementJavaType() {
        return elementJavaType;
    }
}
