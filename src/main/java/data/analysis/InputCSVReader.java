package main.java.data.analysis;

import main.java.data.analysis.entity.InputCSVEntry;
import main.java.data.parsing.entity.InputDiagram;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class InputCSVReader {

    List<InputDiagram> inputDiagrams = new ArrayList<>();

    public InputCSVReader() {
        readFile();
        System.out.println("Created " + inputDiagrams.size() + " entries");
    }

    private void readFile() {
        try {
            List<String> lines = lines = Files.readAllLines(Path.of("csv/csv_report_1.csv"));

            for (String line : lines) {
                InputDiagram inputDiagram = new InputDiagram();
                String[] splittedLine = line.split(";");

                inputDiagram.setFileName(splittedLine[0]);
                inputDiagram.setProjectId(splittedLine[1]);
                inputDiagram.setDiagramId(splittedLine[2]);


                inputDiagrams.add(inputDiagram);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<InputDiagram> getInputDiagrams() {
        return inputDiagrams;
    }
}
