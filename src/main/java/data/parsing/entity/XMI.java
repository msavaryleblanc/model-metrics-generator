package main.java.data.parsing.entity;

public class XMI extends BaseNode {
	public Model model;

	public String version;
	public String xmi;
	public String xsi;

public XMI(org.w3c.dom.Node node){

	this.xmi = getAttributeFromNode(node, "xmlns:xmi");
	this.xsi = getAttributeFromNode(node, "xmlns:xsi");
	this.version = getAttributeFromNode(node, "xmi:version");


System.out.println(node.getChildNodes());
System.out.println(this.toString());
}


	//public String com.genmymodel.ecoreonline.graphic;
	//public String com.genmymodel.graphic.uml;
	//public String uml;


	@Override
	public String toString() {
		return "XMI{" +
				"Model=" + model +
				", version=" + version +
				", xmi='" + xmi + '\'' +
				", xsi='" + xsi + '\'' +
				'}';
	}
}
