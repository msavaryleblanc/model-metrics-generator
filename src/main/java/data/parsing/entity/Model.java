package main.java.data.parsing.entity;

import org.w3c.dom.Node;

import java.util.List;

public class Model extends BaseNode{

	public Extension Extension;
	public PackageImport packageImport;
	public List<PackagedElement> packagedElement;
	public String id;
	public String name;

	public Model(Node node){
		System.out.println(node);
		this.id = getAttributeFromNode(node, "xmi:id");
		this.name = getAttributeFromNode(node, "name");
		System.out.println(this.toString());

	}
	@Override
	public String toString() {
		return "Model{" +
				"Extension=" + Extension +
				", packageImport=" + packageImport +
				", packagedElement=" + packagedElement +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
