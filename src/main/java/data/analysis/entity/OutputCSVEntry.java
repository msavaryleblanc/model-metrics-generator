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
    String height = "-1";
    String trueWidth = "-1";
    String trueHeight = "-1";
    String diagramUrl;
    String maxElementsInClass;
    String nbIntersect;
    String nbLinks;
    String maxLinkForClass;
    String author;

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


    public String getMaxElementsInClass() {
        return maxElementsInClass;
    }

    public void setMaxElementsInClass(String maxElementsInClass) {
        this.maxElementsInClass = maxElementsInClass;
    }

    public String getNbIntersect() {
        return nbIntersect;
    }

    public void setNbIntersect(String nbIntersect) {
        this.nbIntersect = nbIntersect;
    }

    public String getNbLinks() {
        return nbLinks;
    }

    public void setNbLinks(String nbLinks) {
        this.nbLinks = nbLinks;
    }

    public String getMaxLinkForClass() {
        return maxLinkForClass;
    }

    public void setMaxLinkForClass(String maxLinkForClass) {
        this.maxLinkForClass = maxLinkForClass;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String toCSV() {
        return fileName + '\t' +
                projectId + '\t' +
                diagramId + '\t' +
                classNb + '\t' +
                gridLines + '\t' +
                girdColumns + '\t' +
                gridRatio + '\t' +
                width + '\t' +
                height + '\t' +
                trueWidth + '\t' +
                trueHeight + "\t" +
                maxElementsInClass + "\t" +
                nbIntersect + "\t" +
                nbLinks + "\t" +
                maxLinkForClass + "\t" +
                diagramUrl + "\t" +
                author;

    }

    @Override
    public String toString() {
        return "OutputCSVEntry{" +
                "colorCSVEntryList=" + colorCSVEntryList +
                ", diagElementCSVEntryList=" + diagElementCSVEntryList +
                ", fileName='" + fileName + '\'' +
                ", projectId='" + projectId + '\'' +
                ", diagramId='" + diagramId + '\'' +
                ", classNb='" + classNb + '\'' +
                ", gridLines='" + gridLines + '\'' +
                ", girdColumns='" + girdColumns + '\'' +
                ", gridRatio='" + gridRatio + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", trueWidth='" + trueWidth + '\'' +
                ", trueHeight='" + trueHeight + '\'' +
                ", diagramUrl='" + diagramUrl + '\'' +
                ", maxElementsInClass='" + maxElementsInClass + '\'' +
                ", nbIntersect='" + nbIntersect + '\'' +
                ", nbLinks='" + nbLinks + '\'' +
                ", maxLinkForClass='" + maxLinkForClass + '\'' +
                '}';
    }
}
