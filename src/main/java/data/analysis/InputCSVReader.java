package main.java.data.analysis;

import main.java.data.analysis.entity.InputCSVEntry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class InputCSVReader {

    HashMap<String, List<InputCSVEntry>> idMap = new HashMap<>();
    List<InputCSVEntry> allEntries = new ArrayList<>();
int couldnotfind = 0;

    public InputCSVReader() {
        readFile();
        System.out.println(idMap);
    }

    private void readFile() {
        try {
            List<String> lines = lines = Files.readAllLines(Path.of("D:\\boulot\\genmymodel-model-retriever\\file-download.csv"));
            lines.remove(0);
            for (String line : lines) {
                InputCSVEntry inputCSVEntry = new InputCSVEntry();
                String[] splittedLine = line.split(",");
                if(splittedLine.length > 8) {
                    inputCSVEntry.setProjectName(splittedLine[0]);
                    inputCSVEntry.setProjectCreationDate(splittedLine[2]);
                    inputCSVEntry.setProjectId(splittedLine[3]);
                    inputCSVEntry.setProjectLastModificationDate(splittedLine[4]);
                    inputCSVEntry.setProjectXmiUrl(splittedLine[5]);
                    inputCSVEntry.setProjectType(splittedLine[6]);
                    inputCSVEntry.setDiagramKind(splittedLine[11]);
                    inputCSVEntry.setDiagramId(splittedLine[9]);
                    inputCSVEntry.setJpegLink(splittedLine[12]);

                    allEntries.add(inputCSVEntry);

                    idMap.computeIfAbsent(inputCSVEntry.getProjectId(), new Function<String, List<InputCSVEntry>>() {
                        @Override
                        public List<InputCSVEntry> apply(String s) {
                            return new ArrayList<>();
                        }
                    });

                    idMap.get(inputCSVEntry.getProjectId()).add(inputCSVEntry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean doesProjectContainsClassDiagram(String projectId) {
        if(idMap.get(projectId) == null){
            couldnotfind++;
            System.out.println("Could not find project with id " + projectId);
            return false;
        }
        for (InputCSVEntry entry : idMap.get(projectId)) {
            if ("ClassDiagram".equals(entry.getDiagramKind())) {
                return true;
            }
        }
        return false;
    }
}
