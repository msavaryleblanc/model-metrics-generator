package main.java.data.parsing.entity;

import main.java.data.analysis.entity.DiagElementCSVEntry;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AssociationClassElement extends PackagedElement {

    public List<OwnedAttribute> ownedAttributeList;
    //public List<OwnedOperation> ownedAttributeList;


    public AssociationClassElement(Node node) {
        super(node);

        this.ownedAttributeList = new ArrayList<>();


        DiagElementCSVEntry classCSVEntry = new DiagElementCSVEntry();
        classCSVEntry.setElementId(this.id);
        classCSVEntry.setElementType("association_class");
        classCSVEntry.setName(this.name);
        classCSVEntry.setFormat(getFormatOfAttribute(classCSVEntry.getName()));
        classCSVEntry.setNameBeginning(getNameBeginning(classCSVEntry.getName()));

        this.diagElementCSVEntryList.add(classCSVEntry);


        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodeItem = nodeList.item(i);

            String nodeName = nodeItem.getNodeName();

            if ("#text".equals(nodeName) || "xmi:Extension".equals(nodeName)) {
                continue;
            }

            //System.out.println("--- " + nodeItem.getNodeName());

            if ("ownedAttribute".equals(nodeItem.getNodeName())) {
                this.ownedAttributeList.add(new OwnedAttribute(nodeItem));
                DiagElementCSVEntry diagElementCSVEntry = new DiagElementCSVEntry();
                diagElementCSVEntry.setElementId(getAttributeFromNode(nodeItem, "xmi:id"));
                diagElementCSVEntry.setElementType("attribute");
                diagElementCSVEntry.setVisibility(getAttributeFromNode(nodeItem, "visibility"));
                diagElementCSVEntry.setName(getAttributeFromNode(nodeItem, "name"));
                diagElementCSVEntry.setInClass(this.id);
                diagElementCSVEntry.setFormat(getFormatOfAttribute(diagElementCSVEntry.getName()));
                diagElementCSVEntry.setNameBeginning(getNameBeginning(diagElementCSVEntry.getName()));
                this.diagElementCSVEntryList.add(diagElementCSVEntry);

            }

            if ("ownedOperation".equals(nodeItem.getNodeName())) {
                DiagElementCSVEntry diagElementCSVEntry = new DiagElementCSVEntry();
                diagElementCSVEntry.setElementId(getAttributeFromNode(nodeItem, "xmi:id"));
                diagElementCSVEntry.setElementType("operation");
                diagElementCSVEntry.setVisibility(getAttributeFromNode(nodeItem, "visibility"));
                diagElementCSVEntry.setName(getAttributeFromNode(nodeItem, "name"));
                diagElementCSVEntry.setInClass(this.id);
                diagElementCSVEntry.setFormat(getFormatOfAttribute(diagElementCSVEntry.getName()));
                diagElementCSVEntry.setNameBeginning(getNameBeginning(diagElementCSVEntry.getName()));
                this.diagElementCSVEntryList.add(diagElementCSVEntry);

            }


            if ("generalization".equals(nodeItem.getNodeName())) {
                DiagElementCSVEntry diagElementCSVEntry = new DiagElementCSVEntry();
                diagElementCSVEntry.setElementId(getAttributeFromNode(nodeItem, "xmi:id"));
                diagElementCSVEntry.setElementType("generalization");
                diagElementCSVEntry.setInClass(this.id);
                this.diagElementCSVEntryList.add(diagElementCSVEntry);
            }


        }

    }

    private String getNameBeginning(String name){
        if(name != null && name.length() > 0) {
            String firstLetter = name.substring(0, 1);
            return firstLetter.toUpperCase(Locale.ROOT).equals(firstLetter) ? "uppercase" : "lowercase";
        }
        return "undefined";
    }
    private String getFormatOfAttribute(String name) {
        if(name != null) {
            if (name.contains(" ")) {
                return "with_spaces";
            }
            if (name.contains("_")) {
                return "snake_case";
            }
            String shortened = name;

            if(name.length() > 1) {
                shortened = name.substring(1);
            }
            if(shortened.toLowerCase(Locale.ROOT).equals(shortened)){
                return "single_lower";
            }
            if (shortened.toUpperCase(Locale.ROOT).equals(shortened)) {
                return "single_upper";
            }
            return "camel_case";
        }
        return "undefined";
    }


}
