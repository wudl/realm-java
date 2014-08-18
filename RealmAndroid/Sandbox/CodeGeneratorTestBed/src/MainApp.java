import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

import io.realm.processor.RealmSourceCodeGenerator;

public class MainApp {

	public static void main(String[] args) {
		RealmSourceCodeGenerator rscg = new RealmSourceCodeGenerator();
		try
		{
			String qualifiedClassName = "io.realm.test.autogen.User";
			String fileName = System.getProperty("user.home")+"/"+
			                  qualifiedClassName.replace(".", "/")+".java";

			rscg.set_packageName("io.realm.test.autogen");
			rscg.set_className("User");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			rscg.setBufferedWriter(bw);
			rscg.generate();
			bw.flush();
			bw.close();
		}
		catch (Exception ex)
		{
			System.out.println("Unexpected exception:  "+ex.getMessage());
		}

	}
	
/*
	PackageElement packageElement = (PackageElement) enclosingElement;
	String qName = packageElement.getQualifiedName().toString();
	
	if (qName != null)
	{
		String qualifiedClassName = qName + "."+classElement.getSimpleName();
		qualifiedClassName = qualifiedClassName.replace(".", "/");
		
		codeGenerator.set_packageName(qName);
		codeGenerator.set_className(classElement.getSimpleName().toString());
		
        JavaFileObject jfo = processingEnv.getFiler().createSourceFile(classElement.getSimpleName());
        
        
        
        
        BufferedWriter bw = new BufferedWriter(jfo.openWriter());
        codeGenerator.setBufferedWriter(bw);
        
//        for (Element element : typeElement.getEnclosedElements()) {
//            if (element.getKind().equals(ElementKind.FIELD)) {
//                //ExecutableElement executableElement = (ExecutableElement) element;
//                String elementName = element.getSimpleName().toString();
//                //if ((elementName.startsWith("set") ||
//                //        elementName.startsWith("get")) && !elementName.equals("getClass")) {
//                //    accessors.add(executableElement);
//                //}
//                
//                codeGenerator.add_Field(elementName, element);
//                
//            }
//        }
*/
	
	
	

}
