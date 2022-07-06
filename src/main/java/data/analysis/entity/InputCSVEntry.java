package main.java.data.analysis.entity;

public class InputCSVEntry {
    String projectName;
    String projectCreationDate;
    String projectId;
    String projectLastModificationDate;
    String projectXmiUrl;
    String projectType;
    String diagramKind;
    String diagramId;
    String jpegLink;
String attachedFile;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCreationDate() {
        return projectCreationDate;
    }

    public void setProjectCreationDate(String projectCreationDate) {
        this.projectCreationDate = projectCreationDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectLastModificationDate() {
        return projectLastModificationDate;
    }

    public void setProjectLastModificationDate(String projectLastModificationDate) {
        this.projectLastModificationDate = projectLastModificationDate;
    }

    public String getProjectXmiUrl() {
        return projectXmiUrl;
    }

    public void setProjectXmiUrl(String projectXmiUrl) {
        this.projectXmiUrl = projectXmiUrl;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDiagramKind() {
        return diagramKind;
    }

    public void setDiagramKind(String diagramKind) {
        this.diagramKind = diagramKind;
    }

    public String getJpegLink() {
        return jpegLink;
    }

    public void setJpegLink(String jpegLink) {
        this.jpegLink = jpegLink;
    }

    public String getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(String diagramId) {
        this.diagramId = diagramId;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    @Override
    public String toString() {
        return "InputCSVEntry{" +
                "projectName='" + projectName + '\'' +
                ", projectCreationDate='" + projectCreationDate + '\'' +
                ", projectId='" + projectId + '\'' +
                ", projectLastModificationDate='" + projectLastModificationDate + '\'' +
                ", projectXmiUrl='" + projectXmiUrl + '\'' +
                ", projectType='" + projectType + '\'' +
                ", diagramKind='" + diagramKind + '\'' +
                ", diagramId='" + diagramId + '\'' +
                ", jpegLink='" + jpegLink + '\'' +
                '}';
    }
}
