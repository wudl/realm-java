package io.realm.processor;

import java.io.BufferedWriter;
import java.lang.Override;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import io.realm.base.RealmClass;
import io.realm.base.Ignore;



@SupportedAnnotationTypes({"io.realm.base.RealmClass", "io.realm.base.Ignore"})
@SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_6)
public class RealmProcessor extends AbstractProcessor {
	
	RealmSourceCodeGenerator codeGenerator = new RealmSourceCodeGenerator();

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

	            try 
	            {
	            	PackageElement packageElement = (PackageElement) enclosingElement;
	            	String qName = packageElement.getQualifiedName().toString();
	            	
	            	if (qName != null)
	            	{
	            		String qualifiedClassName = qName + classElement.getSimpleName();
	            		codeGenerator.set_packageName(qName);
			            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(qualifiedClassName);
			            codeGenerator.setBufferedWriter(new BufferedWriter(jfo.openWriter()));
			            
			            for (Element element : typeElement.getEnclosedElements()) {
			                if (element.getKind().equals(ElementKind.FIELD)) {
			                    //ExecutableElement executableElement = (ExecutableElement) element;
			                    String elementName = element.getSimpleName().toString();
			                    //if ((elementName.startsWith("set") ||
			                    //        elementName.startsWith("get")) && !elementName.equals("getClass")) {
			                    //    accessors.add(executableElement);
			                    //}
			                    
			                    codeGenerator.add_Field(elementName, element);
			                    
			                }
			            }

			            
			            
			            
			            codeGenerator.generate();
	            	}
	            }
	            catch (Exception ex)
	            {
	            	error("Unable to write file: "+ex.getMessage());
	            }

	            //PackageElement packageElement = (PackageElement) enclosingElement;

	            // Get the getters and setters
	            /*

	            System.out.println(accessors);
	            */
	        }
	        
	        for (Element classElement : roundEnv.getElementsAnnotatedWith(Ignore.class)) {
	            // Check the annotation was applied to a Class
	            if (!classElement.getKind().equals(ElementKind.FIELD)) {
	                error("The Ignore annotation can only be applied to Fields");
	                return false;
	            }
	        }
	        return true;
	    }

	    private void error(String message) {
	        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
	    }
}
