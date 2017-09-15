package com.github.kuros.cassandra.query.metamodel;

public class ManagedType<X> implements Type<X> {

    private String columnName;
    private Class<X> javaType;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(final String columnName) {
        this.columnName = columnName;
    }

    @Override
    public Class<X> getJavaType() {
        return javaType;
    }

    public void setJavaType(final Class<X> javaType) {
        this.javaType = javaType;
    }
}
