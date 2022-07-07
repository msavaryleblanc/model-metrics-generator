package main.java.data.parsing.entity;

import main.java.data.analysis.entity.DiagElementCSVEntry;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DependencyElement extends PackagedElement {

    String associationType;


    public DependencyElement(Node node) {
        super(node);


        DiagElementCSVEntry associationCSVEntry = new DiagElementCSVEntry();
        associationCSVEntry.setElementId(this.id);
        associationCSVEntry.setElementType("dependency");
        this.diagElementCSVEntryList.add(associationCSVEntry);

    }


}
