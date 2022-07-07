package main.java.data.analysis.entity;

import java.util.ArrayList;
import java.util.List;

public class OutputCSVEntry {

    List<ColorCSVEntry> colorCSVEntryList;
    List<DiagElementCSVEntry> diagElementCSVEntryList = new ArrayList<>();

    String fileName;
    String projectId;
    String diagramId;
    String classNb;
    String gridLines;
    String girdColumns;
    String gridRatio;
    String width = "-1";
    String height= "-1";
    String trueWidth= "-1";
    String trueHeight= "-1";
    String diagramUrl;


    public OutputCSVEntry() {
        this.colorCSVEntryList = new ArrayList<>();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getClassNb() {
        return classNb;
    }

    public void setClassNb(String classNb) {
        this.classNb = classNb;
    }

    public String getGridLines() {
        return gridLines;
    }

    public void setGridLines(String gridLines) {
        this.gridLines = gridLines;
    }

    public String getGirdColumns() {
        return girdColumns;
    }

    public void setGirdColumns(String girdColumns) {
        this.girdColumns = girdColumns;
    }

    public String getGridRatio() {
        return gridRatio;
    }

    public void setGridRatio(String gridRatio) {
        this.gridRatio = gridRatio;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTrueWidth() {
        return trueWidth;
    }

    public void setTrueWidth(String trueWidth) {
        this.trueWidth = trueWidth;
    }

    public String getTrueHeight() {
        return trueHeight;
    }

    public void setTrueHeight(String trueHeight) {
        this.trueHeight = trueHeight;
    }

    public String getDiagramUrl() {
        return diagramUrl;
    }

    public void setDiagramUrl(String diagramUrl) {
        this.diagramUrl = diagramUrl;
    }

    public List<ColorCSVEntry> getColorCSVEntryList() {
        return colorCSVEntryList;
    }

    public String getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(String diagramId) {
        this.diagramId = diagramId;
    }

    public List<DiagElementCSVEntry> getDiagElementCSVEntryList() {
        return diagElementCSVEntryList;
    }

    public void setDiagElementCSVEntryList(List<DiagElementCSVEntry> diagElementCSVEntryList) {
        this.diagElementCSVEntryList = diagElementCSVEntryList;
    }

    public String toCSV() {
        return fileName + ';' +
                projectId + ';' +
                diagramId + ';' +
                classNb + ';' +
                gridLines + ';' +
                girdColumns + ';' +
                gridRatio + ';' +
                width + ';' +
                height + ';' +
                trueWidth + ';' +
                trueHeight + ";"+
                diagramUrl;
    }
}
