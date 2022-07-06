package main.java.data.parsing.entity;

import org.w3c.dom.Node;

public class BaseNode {

     String getAttributeFromNode(Node node, String name){
         try {
             return node.getAttributes().getNamedItem(name).getNodeValue();
         }
         catch (NullPointerException nullPointerException){
             //System.out.println("Couldn't find property " + name + " in node " + node.getNodeName());
             return null;
         }
    }

    int getIntAttributeFromNode(Node node, String name){
        try {
            return Integer.parseInt(node.getAttributes().getNamedItem(name).getNodeValue());
        }
        catch (NullPointerException nullPointerException){
            //System.out.println("Couldn't find property " + name + " in node " + node.getNodeName());
            return -1;
        }
    }
}
