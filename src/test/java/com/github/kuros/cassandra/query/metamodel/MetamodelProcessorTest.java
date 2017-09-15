package com.github.kuros.cassandra.query.metamodel;

import com.github.kuros.cassandra.query.metamodel.model.Sample;
import com.github.kuros.cassandra.query.metamodel.model.Sample_;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-github-kuros-cassandra-query.xml" })
public class MetamodelProcessorTest {

    @Test
    public void name() throws Exception {
        System.out.println(Sample_.value.getName());
        System.out.println(Sample_.value.getColumnName());
        System.out.println(Sample_.value.getJavaType());
        System.out.println(Sample_.value.getDeclaringType().getColumnName());
        System.out.println(Sample_.value.getDeclaringType().getJavaType());
        System.out.println(Sample_.value.getClass());
    }
}