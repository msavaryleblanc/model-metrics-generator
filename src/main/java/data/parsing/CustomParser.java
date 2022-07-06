package main.java.data.parsing;

import main.java.data.analysis.HeatMapBuilder;
import main.java.data.analysis.entity.ColorCSVEntry;
import main.java.data.analysis.entity.InputCSVEntry;
import main.java.data.analysis.entity.OutputCSVEntry;
import main.java.data.analysis.entity.SizePojo;
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
import java.util.Locale;

public class CustomParser {

    public static final String BASE_PATH = "D:\\boulot\\genmymodel-model-retriever\\xmi\\";

    HeatMapBuilder heatMapBuilder;
    HeatMapBuilder trueSizeHeatMapBuilder;
    HeatMapBuilder fitDiagramHeatpMapBuilder;

    public CustomParser() {
        heatMapBuilder = new HeatMapBuilder();
        this.trueSizeHeatMapBuilder = new HeatMapBuilder();
        this.fitDiagramHeatpMapBuilder = new HeatMapBuilder();
    }


    public List<OutputCSVEntry> readXML(String fileName) throws IOException, SAXException, ParserConfigurationException {

        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        List<OutputCSVEntry> parsedDiagrams = new ArrayList<>();

        try {
            dom = db.parse(new File(BASE_PATH + fileName));

            NodeList contentsNodeList = dom.getElementsByTagName("contents");
            for (int i = 0; i < contentsNodeList.getLength(); i++) {
                Node contents = contentsNodeList.item(i);
                if ("com.genmymodel.graphic.uml:ClassDiagram".equals(contents.getAttributes().getNamedItem("xsi:type").getNodeValue())) {

                    OutputCSVEntry outputCSVEntry = new OutputCSVEntry();
                    outputCSVEntry.setFileName(fileName);
                    String projectId = fileName.split("-UML-")[1].replace(".xmi", "");
                    outputCSVEntry.setProjectId(projectId);

                    String diagramId = contents.getAttributes().getNamedItem("xmi:id").getNodeValue();
                    outputCSVEntry.setDiagramId(diagramId);
                    outputCSVEntry.setDiagramUrl("https://app.genmymodel.com/api/projects/" + outputCSVEntry.getProjectId() + "/diagrams/" + diagramId + "/jpeg");

                    //Get owned elements of this specific diagram
                    List<OwnedDiagramElements> ownedDiagramElementsList = getChildOwnedDiagramElements(contents);


                    //Handle color identification
                    for (OwnedDiagramElements ownedDiagramElements : ownedDiagramElementsList) {
                        ColorCSVEntry colorCSVEntry = handleColorEntries(outputCSVEntry, ownedDiagramElements);
                        if(colorCSVEntry != null){
                            outputCSVEntry.getColorCSVEntryList().add(colorCSVEntry);
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


                    if (contents.getAttributes().getNamedItem("width") != null && contents.getAttributes().getNamedItem("height") != null) {
                        width = Integer.parseInt(contents.getAttributes().getNamedItem("width").getNodeValue());
                        height = Integer.parseInt(contents.getAttributes().getNamedItem("height").getNodeValue());

                        outputCSVEntry.setHeight(Integer.toString(height));
                        outputCSVEntry.setWidth(Integer.toString(width));
                        heatMapBuilder.parseClasses(width, height, ownedDiagramElementsList);
                    }

                    SizePojo sizePojo = gridCounter.getTrueSize(ownedDiagramElementsList);
                    if (sizePojo.getTrueWith() > 0) {
                        outputCSVEntry.setTrueWidth(Integer.toString(sizePojo.getTrueWith()));
                        outputCSVEntry.setTrueHeight(Integer.toString(sizePojo.getTrueHeight()));
                        trueSizeHeatMapBuilder.parseClassesRealWidth(sizePojo, width, height, ownedDiagramElementsList);
                        fitDiagramHeatpMapBuilder.parseClassesFitDiagramInBox(sizePojo, width, height, ownedDiagramElementsList);
                    }

                    parsedDiagrams.add(outputCSVEntry);

                }
            }

            return parsedDiagrams;


        } catch (Exception e) {
            e.printStackTrace();
            return parsedDiagrams;
        }
    }

    private ColorCSVEntry handleColorEntries(OutputCSVEntry outputCSVEntry, OwnedDiagramElements ownedDiagramElements) {
        //Handling color
        if (ownedDiagramElements.color != null) {
            if (ownedDiagramElements.type.contains("ClassWidget") ||
                    ownedDiagramElements.type.contains("InterfaceWidget") ||
                    ownedDiagramElements.type.contains("EnumerationWidget") ||
                    ownedDiagramElements.type.contains("DataTypeWidget") ||
                    ownedDiagramElements.type.contains("InstanceWidget") ||
                    ownedDiagramElements.type.contains("PackageWidget") //||
            ) {
                ColorCSVEntry colorCSVEntry = new ColorCSVEntry();
                colorCSVEntry.setColor(ownedDiagramElements.color.toUpperCase(Locale.ROOT));
                colorCSVEntry.setElementType(ownedDiagramElements.type);
                colorCSVEntry.setFileName(outputCSVEntry.getFileName());
                colorCSVEntry.setProjectId(outputCSVEntry.getProjectId());
                return colorCSVEntry;

            } else {
                if (ownedDiagramElements.type.contains("ExecutionSpecificationWidget") ||
                        ownedDiagramElements.type.contains("GuardWidget") ||
                        ownedDiagramElements.type.contains("OpaqueActionWidget") ||
                        ownedDiagramElements.type.contains("DecisionNodeWidget") ||
                        ownedDiagramElements.type.contains("MergeNodeWidget") ||
                        ownedDiagramElements.type.contains("LifelineWidget") ||
                        ownedDiagramElements.type.contains("ActorWidget") ||
                        ownedDiagramElements.type.contains("WaypointWidget") ||
                        ownedDiagramElements.type.contains("ActivityFinalNodeWidget") ||
                        ownedDiagramElements.type.contains("AnchorWidget") ||
                        ownedDiagramElements.type.contains("UseCaseWidget") ||
                        ownedDiagramElements.type.contains("SubjectWidget") ||
                        ownedDiagramElements.type.contains("StaticLabel")) {
                    //Do nothing
                } else {
                    System.out.println("Found colored element ! " + ownedDiagramElements.type + " " + outputCSVEntry.getDiagramUrl());
                }
            }
        }
        return null;
    }

    private List<OwnedDiagramElements> getChildOwnedDiagramElements(Node node) {
        NodeList childNodes = node.getChildNodes();
        List<OwnedDiagramElements> outputList = new ArrayList<>();
        if (childNodes.getLength() == 0) {
            return outputList;
        }
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            outputList.addAll(getChildOwnedDiagramElements(childNode));
            if ("ownedDiagramElements".equals(childNode.getNodeName())) {
                if ("com.genmymodel.graphic.uml:ClassWidget".equals(childNode.getAttributes().getNamedItem("xsi:type").getNodeValue())) {
                    outputList.add(new OwnedDiagramElements(childNode));
                }
            }
        }
        return outputList;
    }


}
