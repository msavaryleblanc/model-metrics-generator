package main.java.data.parsing;

import main.java.data.analysis.HeatMapBuilder;
import main.java.data.analysis.entity.OutputCSVEntry;
import main.java.data.parsing.entity.OwnedDiagramElements;
import main.java.data.parsing.entity.PackagedElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomParser {

public static final String BASE_PATH = "";

    HeatMapBuilder heatMapBuilder;

    public CustomParser(){
        heatMapBuilder = new HeatMapBuilder();
    }


    public OutputCSVEntry readXML(String fileName) throws IOException, SAXException, ParserConfigurationException {

        OutputCSVEntry outputCSVEntry = new OutputCSVEntry();
        outputCSVEntry.setFileName(fileName);

        //rolev = new ArrayList<String>();
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // use the factory to take an instance of the document builder
        DocumentBuilder db = dbf.newDocumentBuilder();
        // parse using the builder to get the DOM mapping of the
        // XML file


        try {
            dom = db.parse(new File(BASE_PATH + fileName));

            NodeList nodeList = dom.getElementsByTagName("packagedElement");

            SegmentIntersectionCounter segmentIntersectionCounter = new SegmentIntersectionCounter();
            segmentIntersectionCounter.parse(dom);


            //for (int i = 0; i < nodeList.getLength(); i++) {
            //System.out.println(new PackagedElement(nodeList.item(i)));
            //}

            List<OwnedDiagramElements> ownedDiagramElementsList = new ArrayList<>();

            nodeList = dom.getElementsByTagName("ownedDiagramElements");
            for (int i = 0; i < nodeList.getLength(); i++) {
                OwnedDiagramElements ownedDiagramElements = new OwnedDiagramElements(nodeList.item(i));
                if (ownedDiagramElements.type.equals("com.genmymodel.graphic.uml:ClassWidget")) {
                    //System.out.println(new OwnedDiagramElements(nodeList.item(i)));
                    ownedDiagramElementsList.add(new OwnedDiagramElements(nodeList.item(i)));
                }
            }

            outputCSVEntry.setClassNb(Integer.toString(ownedDiagramElementsList.size()));


            GridCounter gridCounter = new GridCounter();
            int lines = gridCounter.countLines(ownedDiagramElementsList);
            int columns = gridCounter.countColumns(ownedDiagramElementsList);

            double gridCoeff = 1f * ownedDiagramElementsList.size() / (lines * columns);

            outputCSVEntry.setGridLines(Integer.toString(lines));
            outputCSVEntry.setGirdColumns(Integer.toString(columns));
            outputCSVEntry.setGridRatio(Double.toString(gridCoeff));


            int width = -1;
            int height = -1;

            nodeList = dom.getElementsByTagName("contents");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println(node);
                System.out.println(nodeList.getLength());
                if (node.getAttributes().getNamedItem("width") != null && node.getAttributes().getNamedItem("height") != null) {
                    width = Integer.parseInt(node.getAttributes().getNamedItem("width").getNodeValue());
                    height = Integer.parseInt(node.getAttributes().getNamedItem("height").getNodeValue());
                    break;
                    //System.out.println(width + " - " + height);
                }
            }

            if (width != -1) {
                outputCSVEntry.setHeight(Integer.toString(height));
                outputCSVEntry.setWidth(Integer.toString(width));

                heatMapBuilder.parseClasses(width, height, ownedDiagramElementsList);
            }

            return outputCSVEntry;
        }
        catch(SAXParseException e){
            e.printStackTrace();
            return new OutputCSVEntry();
        }
    }


}
