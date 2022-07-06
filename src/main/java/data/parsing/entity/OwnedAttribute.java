package main.java.data.parsing.entity;

import org.w3c.dom.Node;

public class OwnedAttribute extends BaseNode{

    String id;
    String name;
    String visibility;
    String typeId;

    public OwnedAttribute(Node node){
        this.id = getAttributeFromNode(node, "xmi:id");
        this.name = getAttributeFromNode(node, "name");
        this.visibility = getAttributeFromNode(node, "visibility");
        this.typeId = getAttributeFromNode(node, "type");
    }

    @Override
    public String toString() {
        return "OwnedAttribute{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", visibility='" + visibility + '\'' +
                ", typeId='" + typeId + '\'' +
                "} " + super.toString();
    }
}
