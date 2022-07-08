package main.java.data.parsing;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericParser implements OneTaskParser {

    public Node goToFirstTagWithName(Document document, String tagName) {
        return goToFirstTagWithName(document.getFirstChild(), tagName);
    }

    public Node goToFirstTagWithName(Node node, String tagName) {
        if (stringEqualsUpperMode(node.getNodeName(),tagName)) {
            return node;
        } else {
            NodeList children = node.getChildNodes();
            for (int index = 0; index < children.getLength(); index++) {
                Node possibleNode = goToFirstTagWithName(children.item(index), tagName);
                if (possibleNode != null) {
                    return possibleNode;
                }
            }
            return null;
        }
    }

    public List<Node> getChildrenWithTagName(Node node, String tagName) {
        ArrayList<Node> nodeList = new ArrayList<>();
        NodeList children = node.getChildNodes();
        for (int index = 0; index < children.getLength(); index++) {
            Node currentNode = children.item(index);
            if (stringEqualsUpperMode(currentNode.getNodeName(),tagName)) {
                nodeList.add(currentNode);
            }
        }
        return nodeList;
    }

    public String getAttributeValue(Node node, String attributeName) {
        NamedNodeMap attributes = node.getAttributes();
        Node attribute = attributes.getNamedItem(attributeName);
        if (attribute!=null) {
            return attribute.getNodeValue();
        }
        else {
            return null;
        }
    }

    public boolean stringEqualsUpperMode (String string1, String string2) {
        return string1.toUpperCase().equals(string2.toUpperCase());
    }

    public void performTaskOnAllNodesWithTagName (Document document, String tagName, String xsiType, OneTaskOneTagInterface task) {
        performTaskOnAllNodesWithTagName(document.getFirstChild(),tagName,xsiType, task);
    }

    public void performTaskOnAllNodesWithTagName (Node node, String tagName, String xsiType, OneTaskOneTagInterface task) {
        if (stringEqualsUpperMode(node.getNodeName(),tagName)) {
            if (xsiType==null) {
                task.performOneTaskOnThisTag(node);
            } else {
                String type = getAttributeValue(node, "xsi:type");
                if (stringEqualsUpperMode(type,xsiType)) {
                    task.performOneTaskOnThisTag(node);
                }
            }
        }
        NodeList children = node.getChildNodes();
        for (int index = 0; index < children.getLength(); index++) {
            Node currentNode = children.item(index);
            performTaskOnAllNodesWithTagName(currentNode,tagName,xsiType, task);
        }
    }
}
