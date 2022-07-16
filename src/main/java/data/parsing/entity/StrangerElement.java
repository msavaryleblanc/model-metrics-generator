package main.java.data.parsing.entity;

import main.java.data.analysis.entity.DiagElementCSVEntry;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StrangerElement extends PackagedElement {

    public List<OwnedAttribute> ownedAttributeList;
    //public List<OwnedOperation> ownedAttributeList;

    public StrangerElement(Node node) {
        super(node);

        this.ownedAttributeList = new ArrayList<>();


        DiagElementCSVEntry classCSVEntry = new DiagElementCSVEntry();
        classCSVEntry.setElementType(node.getAttributes().getNamedItem("xsi:type").getNodeValue());
        classCSVEntry.setName("-stranger-");

        this.diagElementCSVEntryList.add(classCSVEntry);

    }

}
