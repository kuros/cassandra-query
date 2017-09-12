package com.github.kuros.cassandra.query.metamodel;

import java.lang.reflect.Member;
import java.util.Map;

public class MapAttributeImpl<X, K, V> extends PluralAttributeImpl<X, Map<K, V>, V> implements MapAttribute<X, K, V> {

    private Class<K> keyJavaType;

    public MapAttributeImpl(final Class<X> declaringJavaType, final String name, final Member javaMember, final Class<Map<K, V>> javaType, final Class<V> elementJavaType, final Class<K> keyJavaType) {
        super(declaringJavaType, name, javaMember, javaType, elementJavaType);
        this.keyJavaType = keyJavaType;
    }

    @Override
    public Class<K> getKeyJavaType() {
        return keyJavaType;
    }
}
