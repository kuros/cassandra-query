package com.github.kuros.cassandra.query.metamodel.model;

import com.github.kuros.cassandra.query.annotation.StaticMetamodel;
import com.github.kuros.cassandra.query.metamodel.SingularAttribute;

@StaticMetamodel(Sample.class)
public class Sample_ {
    public static volatile SingularAttribute<Sample, String> id;
    public static volatile SingularAttribute<Sample, String> value;
}
