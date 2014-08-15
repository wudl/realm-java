package io.realm.processor;

import java.io.BufferedWriter;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

public class RealmSourceCodeGenerator {
	
	private String _packageName;
	private String _className;
	private BufferedWriter bw;
	//private Set<ExecutableElement> accessors = new HashSet<ExecutableElement>();

	
	public void setBufferedWriter(BufferedWriter bw) {
		this.bw = bw;
	}


	public void set_packageName(String _packageName) {
		this._packageName = _packageName;
	}


	public void set_className(String _className) {
		this._className = _className;
	}

	public void add_Field(String _className, Element element) 
	{
	}

	public void add_Ignore() {
	}

	public boolean generate() throws Exception
	{
        bw.append("package ");
        bw.append(_packageName+".autogen");
        bw.append(";");
        bw.newLine();
        bw.newLine(); 
        bw.append("public class ");
        bw.append(_className);
        bw.append("{");
        bw.append("}");
        bw.flush();
        bw.close();
		return true;
	}

}
