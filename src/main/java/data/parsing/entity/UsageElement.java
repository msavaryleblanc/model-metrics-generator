package main.java.data.parsing.entity;

import main.java.data.analysis.entity.DiagElementCSVEntry;
import org.w3c.dom.Node;

public class UsageElement extends PackagedElement {

    String associationType;


    public UsageElement(Node node) {
        super(node);


        DiagElementCSVEntry associationCSVEntry = new DiagElementCSVEntry();
        associationCSVEntry.setElementId(this.id);
        associationCSVEntry.setElementType("usage");
        this.diagElementCSVEntryList.add(associationCSVEntry);

    }


}
