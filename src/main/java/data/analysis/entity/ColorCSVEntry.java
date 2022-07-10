package main.java.data.analysis.entity;

public class ColorCSVEntry {

    String fileName;
    String diagramId;
    String projectId;
    String elementType;
    String color;
String url;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String toCSV() {
        return fileName + ';' +
                projectId + ';' +
                diagramId + ';' +
                elementType + ';' +
                color + ';' +
                url;
    }
}
