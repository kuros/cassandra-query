package com.github.kuros.cassandra.query.metamodel;

import java.util.Map;

public interface MapAttribute<X, K, V> extends PluralAttribute<X, Map<K, V>, V> {
    Class<K> getKeyJavaType();
}
