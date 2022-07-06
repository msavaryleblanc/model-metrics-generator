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
import main.java.data.parsing.entity.XMI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static void main(String[] args) throws JsonProcessingException {

        //List<String> files = new XmiFileListReader().getFilenames();
        //files = files.subList(0, 20000);
        //parseFiles(files, "", true, true);

        generateHeatmapsForAllNumbers();

    }

    private static void parseFiles(List<String> fileNames, String heatprefix, boolean shouldDisplay, boolean shouldWrite) {
        //new InputCSVReader();

        OutputCSVWriter outputCSVWriter = new OutputCSVWriter();

        CustomParser customParser = new CustomParser();
        try {

            List<OutputCSVEntry> outputCSVEntryList = new ArrayList<>();

            for (String filename : fileNames) {
                outputCSVEntryList.addAll(customParser.readXML(filename));
            }

            if(shouldWrite) {
                System.out.println("About to write " + outputCSVEntryList.size() + " entries");
                outputCSVWriter.writeOutput(outputCSVEntryList);
            }

            /*List<OutputCSVEntry> outputCSVEntryList = customParser.readXML("3631-UML-_Hr0oEMUuEeeWu-SLkciAbg.xmi");
            outputCSVWriter.writeOutput(outputCSVEntryList);
*/

            //customParser.readXML("3306-UML-_otmYUONpEee1VcqWCkiVQg.xmi");
            //customParser.readXML("2704-UML-_VcWt4GzhEeiBgNYYgyFQHg.xmi");

            /*customParser.heatMapBuilder.normalize();
            //customParser.heatMapBuilder.printPixels();
            customParser.heatMapBuilder.printImage(heatprefix+"scaledHeat", shouldDisplay);
            //customParser.heatMapBuilder.printTestImage();

            customParser.trueSizeHeatMapBuilder.normalize();
            customParser.trueSizeHeatMapBuilder.printImage(heatprefix+"trueWidthHeat", shouldDisplay);
*/
            customParser.fitDiagramHeatpMapBuilder.normalize();
            customParser.fitDiagramHeatpMapBuilder.printImageFit(heatprefix + "fitBoxHeat", shouldDisplay);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateHeatmapsForAllNumbers() {
        for (int i = 60; i < 120; i++) {
            List<String> files = getFilenameWithNbClass(i);
            System.out.println("There are " + files.size() + " files for " + i);
            parseFiles(files, i + "-", false, false);
        }

    }


    private static List<String> getFilenameWithNbClass(int numberOfClasses) {

        List<String> output = new ArrayList<>();

        try {

            List<String> lines = Files.readAllLines(Path.of("csv/csv_report.csv"));

            for (String line : lines) {
                String[] splittedLine = line.split(";");
                String fileName = splittedLine[0];
                String nbClasses = splittedLine[3];

                if (Integer.parseInt(nbClasses) == numberOfClasses) {
                    output.add(fileName);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;

    }

}
