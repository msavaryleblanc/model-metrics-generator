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
import java.util.List;

public class Parser {

    public static void parse(String input) throws IOException {
        ObjectMapper xmlMapper = new XmlMapper();
        //xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        XMI value = xmlMapper.readValue(input, XMI.class);

        System.out.println(value);

    }

    public static void main(String[] args) throws JsonProcessingException {

        List<InputCSVEntry> classDiagramProjects= new XmiFileListReader(new InputCSVReader()).getXmiWithClassDiagrams();
        //new InputCSVReader();

        OutputCSVWriter outputCSVWriter = new OutputCSVWriter();

        CustomParser customParser = new CustomParser();
        try {

            classDiagramProjects = classDiagramProjects.subList(0,15000);
            for(InputCSVEntry inputCSVEntry : classDiagramProjects){
                OutputCSVEntry outputCSVEntry = customParser.readXML(inputCSVEntry.getAttachedFile());
                outputCSVWriter.writeOutput(outputCSVEntry);
            }
        //customParser.readXML("3308-UML-48b69bbf-46f2-4cd7-b884-d2e6b7a759c4.xmi");
        //customParser.readXML("3306-UML-_otmYUONpEee1VcqWCkiVQg.xmi");
        //customParser.readXML("2704-UML-_VcWt4GzhEeiBgNYYgyFQHg.xmi");

            customParser.heatMapBuilder.normalize();
            //customParser.heatMapBuilder.printPixels();
            customParser.heatMapBuilder.printImage();
            //customParser.heatMapBuilder.printTestImage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
