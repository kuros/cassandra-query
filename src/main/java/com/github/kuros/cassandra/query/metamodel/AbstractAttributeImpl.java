package com.github.kuros.cassandra.query.metamodel;

import java.lang.reflect.Member;

public class AbstractAttributeImpl<X, Y> implements Attribute<X, Y> {
    private String name;
    private String columnName;
    protected ManagedType<X> declaringType;
    protected Class<Y> javaType;

    public AbstractAttributeImpl(final String name,
                                 final String columnName,
                                 final Class<Y> javaType,
                                 final ManagedType<X> declaringType) {
        this.declaringType = declaringType;
        this.name = name;
        this.columnName = columnName;
        this.javaType = javaType;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public ManagedType<X> getDeclaringType() {
        return declaringType;
    }

    @Override
    public Class<Y> getJavaType() {
        return javaType;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setColumnName(final String columnName) {
        this.columnName = columnName;
    }

    public void setDeclaringType(final ManagedType<X> declaringType) {
        this.declaringType = declaringType;
    }

    public void setJavaType(final Class<Y> javaType) {
        this.javaType = javaType;
    }
}
