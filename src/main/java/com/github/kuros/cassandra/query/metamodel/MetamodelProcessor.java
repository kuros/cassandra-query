package com.github.kuros.cassandra.query.metamodel;

import com.github.kuros.cassandra.query.annotation.StaticMetamodel;
import com.github.kuros.cassandra.query.exception.StaticMetaModelException;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class MetamodelProcessor implements BeanFactoryPostProcessor {

    private static final String INCLUDE_PATHS = "kuros.query.include.path";
    private static final String INCLUDE_SPECIFIC_CLASSES = "kuros.query.include.specific.classes";


    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        final List<Class<?>> attributeClasses  = new ArrayList<>();
        attributeClasses.addAll(scanClasses(getInclusionPaths(configurableListableBeanFactory)));
        attributeClasses.addAll(getSpecificClasses(configurableListableBeanFactory));

        for (Class<?> attributeClass : attributeClasses) {
            initializeAttributeVariable(attributeClass);

        }
    }

    private void initializeAttributeVariable(final Class<?> attributeClass) {
        final StaticMetamodel annotation = attributeClass.getAnnotation(StaticMetamodel.class);
        final Class<?> referencedClass = annotation.value();

        final Field[] fields = attributeClass.getFields();
        for (Field field : fields) {
            initalize(field, referencedClass);
        }
    }

    private void initalize(final Field field, final Class<?> referencedClass) {
        final String name = field.getName();
        final String columnName = getColumnName(referencedClass, name);
        final ManagedType<?> managedType = getDeclaringType(referencedClass);

        singularAttribute(field, referencedClass, name, columnName, managedType);
        pluralAttribute(field, referencedClass, name, columnName, managedType);
        mapAttribute(field, referencedClass, name, columnName, managedType);
    }

    private void pluralAttribute(final Field field, final Class<?> referencedClass, final String name, final String columnName, final ManagedType<?> managedType) {
        if (field.getType() == PluralAttribute.class) {
            final Type[] genericJavaType = getGenericJavaType(field);
            Assert.isTrue(genericJavaType.length == 3, "PluralAttribute<X, C, E> not declared properly");

            try {
                field.set(null, new PluralAttributeImpl<>(name, columnName, (Class<?>) genericJavaType[1], managedType, (Class<?>) genericJavaType[2]));
            } catch (IllegalAccessException e) {
                throw new StaticMetaModelException(String.format("Not able to set value for field %s (%s), field should be public static volatile",
                        name, referencedClass));
            }

        }
    }

    private void mapAttribute(final Field field, final Class<?> referencedClass, final String name, final String columnName, final ManagedType<?> managedType) {
        if (field.getType() == PluralAttribute.class) {
            final Type[] genericJavaType = getGenericJavaType(field);
            Assert.isTrue(genericJavaType.length == 3, "MapAttribute<X, K, V> not declared properly");

            try {
                field.set(null, new MapAttributeImpl<>(name, columnName, Map.class, managedType, (Class<?>) genericJavaType[1]));
            } catch (IllegalAccessException e) {
                throw new StaticMetaModelException(String.format("Not able to set value for field %s (%s), field should be public static volatile",
                        name, referencedClass));
            }

        }
    }

    private void singularAttribute(final Field field, final Class<?> referencedClass, final String name, final String columnName, final ManagedType<?> managedType) {
        if (field.getType() == Attribute.class || field.getType() == SingularAttribute.class) {
            final Type[] genericJavaType = getGenericJavaType(field);
            Assert.isTrue(genericJavaType.length == 2, "Attribute<X, Y> not declared properly");
            try {
                field.set(null, new SingularAttributeImpl<>(name, columnName, (Class<?>) genericJavaType[1], managedType));
            } catch (IllegalAccessException e) {
                throw new StaticMetaModelException(String.format("Not able to set value for field %s (%s), field should be public static volatile",
                        name, referencedClass));
            }
        }
    }

    private Type[] getGenericJavaType(final Field field) {
        final Type genericType = field.getGenericType();
        try {
            final ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) genericType;
            return parameterizedType.getActualTypeArguments();
        } catch (Exception e) {
            throw new StaticMetaModelException("Attribute generic type not declared correctly");
        }
    }

    private <T> ManagedType<T> getDeclaringType(final Class<T> referencedClass) {
        final ManagedType<T> managedType = new ManagedType<>();

        final Table table = referencedClass.getAnnotation(Table.class);
        if (table == null) {
            throw new StaticMetaModelException(String.format("Class %s must be annotated with @Table", referencedClass));
        }

        managedType.setColumnName(table.value().isEmpty() ? referencedClass.getSimpleName() : table.value());
        managedType.setJavaType(referencedClass);

        return managedType;
    }

    private String getColumnName(final Class<?> referencedClass, final String name) {
        String columnName = null;
        try {
            final Annotation[] declaredAnnotations = referencedClass.getDeclaredField(name).getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                if (annotation instanceof PrimaryKey) {
                    columnName = ((PrimaryKey) annotation).value();
                } else if (annotation instanceof PrimaryKeyColumn) {
                    columnName = ((PrimaryKeyColumn) annotation).name();
                } else if (annotation instanceof Column) {
                    columnName = ((Column) annotation).value();
                }
            }
        } catch (NoSuchFieldException e) {
            throw new StaticMetaModelException(String.format("field %s not found in %s", name, referencedClass.getName()));
        }

        return (columnName == null) ? null : (columnName.isEmpty() ? name : columnName);
    }

    private List<Class<?>> scanClasses(final List<String> inclusionPaths) {
        final List<Class<?>> results = new ArrayList<>();
        for (final String basePackage : inclusionPaths) {
            final Reflections refs = new Reflections(basePackage);
            final Set<Class<?>> typesAnnotatedWith = refs.getTypesAnnotatedWith(StaticMetamodel.class);
            results.addAll(typesAnnotatedWith);
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private List<String> getInclusionPaths(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
        return getBeanListValues(configurableListableBeanFactory, INCLUDE_PATHS);
    }

    @SuppressWarnings("unchecked")
    private List<Class<?>> getSpecificClasses(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
        List<Class<?>> result = new ArrayList<>();
        final List<String> beanListValues = getBeanListValues(configurableListableBeanFactory, INCLUDE_SPECIFIC_CLASSES);

        for (String className : beanListValues) {
            final Class<?> aClass;
            try {
                aClass = Class.forName(className);
                result.add(aClass);
            } catch (final ClassNotFoundException e) {
                throw new StaticMetaModelException(e);
            }
        }
        return result;
    }


    private List<String> getBeanListValues(final ConfigurableListableBeanFactory configurableListableBeanFactory, final String includePaths) {
        try {
            if (configurableListableBeanFactory.containsBean(includePaths)) {
                return (List<String>) configurableListableBeanFactory.getBean(includePaths);
            }
        } catch (final Exception e) {
            return new ArrayList<>();
        }

        return new ArrayList<>();
    }
}
