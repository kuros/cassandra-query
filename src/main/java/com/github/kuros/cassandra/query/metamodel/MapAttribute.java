package com.github.kuros.cassandra.query.metamodel;

import java.util.Map;

public interface MapAttribute<X, K, V> extends Attribute<X, V> {
    Class<K> getKeyJavaType();
}
