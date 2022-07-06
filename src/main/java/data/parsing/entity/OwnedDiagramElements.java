package main.java.data.parsing.entity;

import org.w3c.dom.Node;

import java.util.List;

public class OwnedDiagramElements extends BaseNode{
	public Extension Extension;
	public String type;
	public String id;
	public String modelElement;
	public String diagramElementParent;
	public String anchors;
	public int x;
	public int y;
	public int width;
	public int height;
	public int minWidth;
	public int minHeight;
	public List<OwnedDiagramElements> ownedDiagramElements;
	public String targetSegments;
	public String attachedElement;
	public String sourceSegments;
	public String relativeNodes;
	public String sourceConnector;
	public String targetConnector;
	public String refElement;
	public String constraintPosition;
	public int deltaY;
	public int deltaX;
	public boolean showName;
	public String value;
	public boolean straight;
public String color;

	public OwnedDiagramElements(Node node){
		this.type = getAttributeFromNode(node, "xsi:type");
		this.id = getAttributeFromNode(node, "xmi:type");
		this.modelElement = getAttributeFromNode(node, "modelElement");
		this.diagramElementParent = getAttributeFromNode(node, "diagramElementParent");
		this.anchors = getAttributeFromNode(node, "anchors");
		this.x = getIntAttributeFromNode(node, "x");
		this.y = getIntAttributeFromNode(node, "y");
		this.width = getIntAttributeFromNode(node, "width");
		this.height = getIntAttributeFromNode(node, "height");
		this.minHeight = getIntAttributeFromNode(node, "minHeight");
		this.minWidth = getIntAttributeFromNode(node, "minWidth");
		this.color = getAttributeFromNode(node, "color");

	}



	@Override
	public String toString() {
		return "OwnedDiagramElements{" +
				"Extension=" + Extension +
				", type='" + type + '\'' +
				", id='" + id + '\'' +
				", modelElement='" + modelElement + '\'' +
				", diagramElementParent='" + diagramElementParent + '\'' +
				", anchors='" + anchors + '\'' +
				", x=" + x +
				", y=" + y +
				", width=" + width +
				", height=" + height +
				", minWidth=" + minWidth +
				", minHeight=" + minHeight +
				", ownedDiagramElements=" + ownedDiagramElements +
				", targetSegments='" + targetSegments + '\'' +
				", attachedElement='" + attachedElement + '\'' +
				", sourceSegments='" + sourceSegments + '\'' +
				", relativeNodes='" + relativeNodes + '\'' +
				", sourceConnector='" + sourceConnector + '\'' +
				", targetConnector='" + targetConnector + '\'' +
				", refElement='" + refElement + '\'' +
				", constraintPosition='" + constraintPosition + '\'' +
				", deltaY=" + deltaY +
				", deltaX=" + deltaX +
				", showName=" + showName +
				", value='" + value + '\'' +
				", straight=" + straight +
				'}';
	}
}
