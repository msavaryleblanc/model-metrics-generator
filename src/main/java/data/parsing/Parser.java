package main.java.data.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import main.java.data.analysis.InputCSVReader;
import main.java.data.analysis.OutputCSVWriter;
import main.java.data.analysis.XmiFileListReader;
import main.java.data.analysis.entity.InputCSVEntry;
import main.java.data.analysis.entity.OutputCSVEntry;
import main.java.data.parsing.entity.Contents;
import main.java.data.parsing.entity.InputDiagram;
import main.java.data.parsing.entity.XMI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static void main(String[] args) throws JsonProcessingException {

        List<String> files = new XmiFileListReader().getFilenames();
        files = files.subList(0, 1000);
        List<InputDiagram> inputDiagrams = new ArrayList<>();
        for (String file : files) {
            InputDiagram inputDiagram = new InputDiagram();
            inputDiagram.setFileName(file);
            inputDiagrams.add(inputDiagram);
        }
        parseFiles(inputDiagrams, "", true, true);



        generateHeatmapsForAllNumbers();

    }


    private static void parseFiles(List<InputDiagram> fileNames, String heatprefix, boolean shouldDisplay, boolean shouldWrite) {
        //new InputCSVReader();

        OutputCSVWriter outputCSVWriter = new OutputCSVWriter();

        CustomParser customParser = new CustomParser();
        try {

            List<OutputCSVEntry> outputCSVEntryList = new ArrayList<>();

            for (InputDiagram inputDiagram : fileNames) {
                outputCSVEntryList.addAll(customParser.readXML(inputDiagram));
            }

            if (shouldWrite) {
                System.out.println("About to write " + outputCSVEntryList.size() + " entries");
                outputCSVWriter.writeOutput(outputCSVEntryList);
            }

            /*List<OutputCSVEntry> outputCSVEntryList = customParser.readXML("3631-UML-_Hr0oEMUuEeeWu-SLkciAbg.xmi");
            outputCSVWriter.writeOutput(outputCSVEntryList);*/


            customParser.fitDiagramHeatpMapBuilder.normalize();
            customParser.fitDiagramHeatpMapBuilder.printImageFit(heatprefix + "fitBoxHeat", shouldDisplay);

            customParser.interestingClassBuilder.normalize();
            customParser.interestingClassBuilder.printImageFit(heatprefix + "biggestClass", shouldDisplay);

            customParser.maxElementsBuilder.normalize();
            customParser.maxElementsBuilder.printImageFit(heatprefix + "maxElements", shouldDisplay);


            customParser.allClassLocationBuilder.normalize();
            customParser.allClassLocationBuilder.printImageFit(heatprefix + "all", shouldDisplay);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateHeatmapsForAllNumbers() {
        for (int i = 1; i < 60; i++) {
            List<InputDiagram> files = getFilenameWithNbClass(i);
            //files = files.subList(40,41);
            System.out.println("There are " + files.size() + " files for " + i);
            parseFiles(files, i + "-", false, false);
        }

    }


    private static List<InputDiagram> getFilenameWithNbClass(int numberOfClasses) {

        List<InputDiagram> output = new ArrayList<>();

        try {

            List<String> lines = Files.readAllLines(Path.of("csv/csv_report.csv"));

            for (String line : lines) {
                String[] splittedLine = line.split(";");
                String fileName = splittedLine[0];
                String diagramId = splittedLine[2];
                String nbClasses = splittedLine[3];


                if (Integer.parseInt(nbClasses) == numberOfClasses) {
                    InputDiagram inputDiagram = new InputDiagram();
                    inputDiagram.setFileName(fileName);
                    inputDiagram.setDiagramId(diagramId);
                    output.add(inputDiagram);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;

    }

}
