package com.github.kuros.cassandra.query.metamodel;

public class MapAttributeImpl<X, K, V> extends AbstractAttributeImpl<X, V> implements MapAttribute<X, K, V> {

    private Class<K> keyJavaType;


    MapAttributeImpl(final String name, final String columnName, final Class<V> javaType, final ManagedType<X> declaringType, final Class<K> keyJavaType) {
        super(name, columnName, javaType, declaringType);
        this.keyJavaType = keyJavaType;
    }


    @Override
    public Class<K> getKeyJavaType() {
        return keyJavaType;
    }

}
