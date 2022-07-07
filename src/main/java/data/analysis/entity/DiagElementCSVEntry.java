package main.java.data.analysis.entity;

public class DiagElementCSVEntry {

    String projectId ="";
    String diagramId ="";
    String elementId ="";
    String elementType ="";
    String name ="";
    String inClass ="";
    String type ="";
    String visibility ="";
    String format ="";
    String nameBeginning ="";

    public String getProjectId() {
        return projectId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(String diagramId) {
        this.diagramId = diagramId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInClass() {
        return inClass;
    }

    public void setInClass(String inClass) {
        this.inClass = inClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getNameBeginning() {
        return nameBeginning;
    }

    public void setNameBeginning(String nameBeginning) {
        this.nameBeginning = nameBeginning;
    }

    public String toCSV() {
        return projectId + ';' +
                diagramId + ';' +
                elementId + ';' +
                elementType + ';' +
                name + ';' +
                inClass + ';' +
                type + ';' +
                visibility + ';' +
                format+ ";"+
                nameBeginning;

    }

    @Override
    public String toString() {
        return "DiagElementCSVEntry{" +
                "projectId='" + projectId + '\'' +
                ", diagramId='" + diagramId + '\'' +
                ", elementId='" + elementId + '\'' +
                ", elementType='" + elementType + '\'' +
                ", name='" + name + '\'' +
                ", inClass='" + inClass + '\'' +
                ", type='" + type + '\'' +
                ", visibility='" + visibility + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
