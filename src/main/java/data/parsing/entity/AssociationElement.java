package main.java.data.parsing.entity;

import main.java.data.analysis.entity.DiagElementCSVEntry;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class AssociationElement extends PackagedElement {

    String associationType;


    public AssociationElement(Node node) {
        super(node);

        //System.out.println(type);

        associationType = "regular";


        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodeItem = nodeList.item(i);

            String nodeName = nodeItem.getNodeName();

            if ("#text".equals(nodeName) || "xmi:Extension".equals(nodeName)) {
                continue;
            }

            if ("ownedEnd".equals(nodeItem.getNodeName())) {
                String aggregationValue = getAttributeFromNode(nodeItem, "aggregation");

                if (aggregationValue != null) {
                    associationType = aggregationValue;
                    break;
                }
            }

        }

        DiagElementCSVEntry associationCSVEntry = new DiagElementCSVEntry();
        associationCSVEntry.setElementId(this.id);
        associationCSVEntry.setElementType("association");
        associationCSVEntry.setType(associationType);
        this.diagElementCSVEntryList.add(associationCSVEntry);

    }


}
