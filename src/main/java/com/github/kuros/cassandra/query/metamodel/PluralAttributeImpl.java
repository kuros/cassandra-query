package com.github.kuros.cassandra.query.metamodel;

class PluralAttributeImpl<X, C, E> extends AbstractAttributeImpl<X, C> implements PluralAttribute<X, C, E> {

    private Class<E> elementJavaType;

    PluralAttributeImpl(final String name, final String columnName, final Class<C> javaType, final ManagedType<X> declaringJavaType, final Class<E> elementJavaType) {
        super(name, columnName, javaType, declaringJavaType);
        this.elementJavaType = elementJavaType;
    }

    @Override
    public Class<E> getElementJavaType() {
        return elementJavaType;
    }
}
