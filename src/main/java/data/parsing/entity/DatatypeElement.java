package main.java.data.parsing.entity;

import main.java.data.analysis.entity.DiagElementCSVEntry;
import org.w3c.dom.Node;

import java.util.Locale;

public class DatatypeElement extends PackagedElement {


    public DatatypeElement(Node node) {
        super(node);

        DiagElementCSVEntry classCSVEntry = new DiagElementCSVEntry();
        classCSVEntry.setElementId(this.id);
        classCSVEntry.setElementType("datatype");
        classCSVEntry.setName(this.name);
        classCSVEntry.setFormat(getFormatOfAttribute(classCSVEntry.getName()));
        classCSVEntry.setNameBeginning(getNameBeginning(classCSVEntry.getName()));

        this.diagElementCSVEntryList.add(classCSVEntry);


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
