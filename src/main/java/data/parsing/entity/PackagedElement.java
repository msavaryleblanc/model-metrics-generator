package main.java.data.parsing.entity;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class PackagedElement extends BaseNode{

	//public List<Include> include;
	public String type;
	public String id;
	public String name;
	//public Extension Extension;
	//public List<OwnedEnd> ownedEnd;
	//public String memberEnd;
	//public String navigableOwnedEnd;
	//public List<ExtensionPoint> extensionPoint;
	//public Extend extend;
	//public List<Edge> edge;
	//public List<Group> group;
	//public List<Node> node;
public List<OwnedAttribute> ownedAttributeList;

	public PackagedElement(org.w3c.dom.Node node){
		this.type = getAttributeFromNode(node, "xsi:type");
		this.id = getAttributeFromNode(node, "xmi:id");
		this.name = getAttributeFromNode(node, "name");

this.ownedAttributeList = new ArrayList<>();

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeItem = nodeList.item(i);
			if("ownedAttribute".equals(nodeItem.getNodeName())){
				this.ownedAttributeList.add(new OwnedAttribute(nodeItem));
			}
		}
	}
	/*
	<packagedElement xsi:type="uml:Class" xmi:id="_US2RYE8PEDa7wqv_e_UwiQ" name="ConverterPreprocessor">
      <xmi:Extension extender="http://www.eclipse.org/emf/2002/Ecore">
        <eAnnotations xmi:id="_US2RYE8PEDa7wqv_e_UwiQ0" source="genmymodel">
          <details xmi:id="_US2RYE8PEDa7wqv_e_UwiQ00" key="uuid" value="_US2RYE8PEDa7wqv_e_UwiQ"/>
        </eAnnotations>
      </xmi:Extension>
      <ownedAttribute xmi:id="_BdvXIE8QEDa7wqv_e_UwiQ" name="Convert(Image)" visibility="public" type="_HafpcU8QEDa7wqv_e_UwiQ">
        <xmi:Extension extender="http://www.eclipse.org/emf/2002/Ecore">
          <eAnnotations xmi:id="_BdvXIE8QEDa7wqv_e_UwiQ0" source="genmymodel">
            <details xmi:id="_BdvXIE8QEDa7wqv_e_UwiQ00" key="uuid" value="_BdvXIE8QEDa7wqv_e_UwiQ"/>
          </eAnnotations>
        </xmi:Extension>
      </ownedAttribute>
      <ownedAttribute xmi:id="_uEAs4E8QEDa7wqv_e_UwiQ" name="" visibility="private" type="_2e9f8E8QEDa7wqv_e_UwiQ">
        <xmi:Extension extender="http://www.eclipse.org/emf/2002/Ecore">
          <eAnnotations xmi:id="_uEAs4E8QEDa7wqv_e_UwiQ0" source="genmymodel">
            <details xmi:id="_uEAs4E8QEDa7wqv_e_UwiQ00" key="uuid" value="_uEAs4E8QEDa7wqv_e_UwiQ"/>
          </eAnnotations>
        </xmi:Extension>
      </ownedAttribute>
      <ownedAttribute xmi:id="_5t_AsE8QEDa7wqv_e_UwiQ" name="IncreaseTreshold()" visibility="public" type="_fknrIE8QEDa7wqv_e_UwiQ">
        <xmi:Extension extender="http://www.eclipse.org/emf/2002/Ecore">
			*/

	@Override
	public String toString() {
		return "PackagedElement{" +
				"type='" + type + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", ownedAttributeList=" + ownedAttributeList +
				"} " + super.toString();
	}
}
