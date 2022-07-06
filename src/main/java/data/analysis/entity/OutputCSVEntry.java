package main.java.data.analysis.entity;

public class OutputCSVEntry {

    String fileName;
    String projectId;
    String classNb;
    String gridLines;
    String girdColumns;
    String gridRatio;
    String width;
    String height;


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


    public String toCSV() {
        return fileName + ';' +
                projectId + ';' +
                classNb + ';' +
                gridLines + ';' +
                girdColumns + ';' +
                gridRatio + ';' +
                width + ';' +
                height;
    }
}
