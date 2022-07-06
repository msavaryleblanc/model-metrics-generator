package main.java.data.analysis;

import main.java.data.analysis.entity.InputCSVEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmiFileListReader {

    InputCSVReader inputCSVReader;

    public XmiFileListReader(InputCSVReader inputCSVReader){
        this.inputCSVReader = inputCSVReader;
    }

    public List<InputCSVEntry> getXmiWithClassDiagrams(){

        List<InputCSVEntry> projectsWithClassDiagrams = new ArrayList<>();
        File folder = new File("D:\\boulot\\genmymodel-model-retriever\\xmi");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            System.out.println(file.getName());
            if(file.getName().contains("-UML-")){
                String[] splittedName = file.getName().split("-UML-");
                String projectId = splittedName[1].replace(".xmi", "");
                if(inputCSVReader.doesProjectContainsClassDiagram(projectId)){
                    for(InputCSVEntry entry : inputCSVReader.idMap.get(projectId)){
                        entry.setAttachedFile(file.getName());
                        projectsWithClassDiagrams.add(entry);
                    }
                }
            }
        }
        System.out.println("We have " + listOfFiles.length + " files");
        System.out.println(projectsWithClassDiagrams.size() +" files contain class diagrams");
System.out.println("COuld not find : " + inputCSVReader.couldnotfind);
        return projectsWithClassDiagrams;
    }
}
