package com.github.kuros.cassandra.query.annotation;

import com.github.kuros.cassandra.query.exception.MetaModelDeclarationException;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.github.kuros.cassandra.query.annotation.StaticMetamodel")
public class MetamodelProcessor extends AbstractProcessor {

    private static final List<Modifier> MODIFIERS = new ArrayList<>();

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        System.out.println("********");

        final Set<? extends Element> allElements = roundEnv.getElementsAnnotatedWith(StaticMetamodel.class);

        for (Element element : allElements) {
            if (element instanceof TypeElement) {
                final TypeElement typeElement = (TypeElement) element;
                List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
                for (Element enclosedElement : enclosedElements) {
                    if (enclosedElement instanceof VariableElement) {
                        final VariableElement variableElement = (VariableElement) enclosedElement;

                        if (!variableElement.getModifiers().containsAll(MODIFIERS) || variableElement.getModifiers().size() != 3) {
                            throw new MetaModelDeclarationException("variable should be declared public static volatile only");
                        }

                        System.out.println("********"+variableElement.getSimpleName());

                    }
                }

            }

        }
        return false;
    }

    static {
        MODIFIERS.add(Modifier.PUBLIC);
        MODIFIERS.add(Modifier.STATIC);
        MODIFIERS.add(Modifier.VOLATILE);
    }
}
