package io.realm.processor;

import java.lang.Override;
import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import io.realm.typed.annotations.RealmClass;

@SupportedAnnotationTypes({ "io.realm.typed.annotations.RealmClass" })
public class RealmProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element classElement : roundEnv.getElementsAnnotatedWith(RealmClass.class)) {
            // Check the annotation was applied to a Class
            if (!classElement.getKind().equals(ElementKind.CLASS)) {
                error("The RealmClass annotation can only be applied to classes");
                return false;
            }
            TypeElement typeElement = (TypeElement) classElement;

            // Get the package of the class
            Element enclosingElement = typeElement.getEnclosingElement();
            if (!enclosingElement.getKind().equals(ElementKind.PACKAGE)) {
                error("The RealmClass annotation does not support nested classes");
                return false;
            }
            PackageElement packageElement = (PackageElement) enclosingElement;

            // Get the getters and setters
            Set<ExecutableElement> accessors = new HashSet<ExecutableElement>();
            for (Element element : typeElement.getEnclosedElements()) {
                if (element.getKind().equals(ElementKind.METHOD)) {
                    ExecutableElement executableElement = (ExecutableElement) element;
                    String elementName = element.getSimpleName().toString();
                    if ((elementName.startsWith("set") ||
                            elementName.startsWith("get")) && !elementName.equals("getClass")) {
                        accessors.add(executableElement);
                    }
                }
            }

            System.out.println(accessors);
        }

        return true;
    }

    private void error(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }
}