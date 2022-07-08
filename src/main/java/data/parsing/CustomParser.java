package main.java.data.parsing;

import main.java.data.analysis.HeatMapBuilder;
import main.java.data.analysis.InterestingClassBuilder;
import main.java.data.analysis.entity.*;
import main.java.data.parsing.entity.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CustomParser {

    public static final String BASE_PATH = "D:\\boulot\\genmymodel-model-retriever\\xmi\\";

    HeatMapBuilder heatMapBuilder;
    HeatMapBuilder trueSizeHeatMapBuilder;
    HeatMapBuilder fitDiagramHeatpMapBuilder;
    InterestingClassBuilder interestingClassBuilder;
    InterestingClassBuilder allClassLocationBuilder;
    InterestingClassBuilder maxElementsBuilder;

    public CustomParser() {
        heatMapBuilder = new HeatMapBuilder();
        this.trueSizeHeatMapBuilder = new HeatMapBuilder();
        this.fitDiagramHeatpMapBuilder = new HeatMapBuilder();
        this.interestingClassBuilder = new InterestingClassBuilder();
        this.allClassLocationBuilder = new InterestingClassBuilder();
        this.maxElementsBuilder = new InterestingClassBuilder();
    }


    public List<OutputCSVEntry> readXML(InputDiagram inputDiagram) throws IOException, SAXException, ParserConfigurationException {

        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        List<OutputCSVEntry> parsedDiagrams = new ArrayList<>();

        try {
            dom = db.parse(new File(BASE_PATH + inputDiagram.getFileName()));

            NodeList contentsNodeList = dom.getElementsByTagName("contents");
            for (int i = 0; i < contentsNodeList.getLength(); i++) {
                Node contents = contentsNodeList.item(i);
                if ("com.genmymodel.graphic.uml:ClassDiagram".equals(contents.getAttributes().getNamedItem("xsi:type").getNodeValue())) {

                    OutputCSVEntry outputCSVEntry = new OutputCSVEntry();
                    outputCSVEntry.setFileName(inputDiagram.getFileName());
                    String projectId = inputDiagram.getFileName().split("-UML-")[1].replace(".xmi", "");
                    outputCSVEntry.setProjectId(projectId);

                    String diagramId = contents.getAttributes().getNamedItem("xmi:id").getNodeValue();

                    if (inputDiagram.getDiagramId() != null && !diagramId.equals(inputDiagram.getDiagramId())) {
                        System.out.print("Skipping diagram");
                        continue;
                    }


                    outputCSVEntry.setDiagramId(diagramId);
                    outputCSVEntry.setDiagramUrl("https://app.genmymodel.com/api/projects/" + outputCSVEntry.getProjectId() + "/diagrams/" + diagramId + "/jpeg");

                    //Get owned elements of this specific diagram
                    List<OwnedDiagramElements> ownedDiagramElementsList = getChildOwnedDiagramElements(contents);

                    Set<String> allOwnedElementsModelElements = new HashSet<>();

                    for (OwnedDiagramElements ownedDiagramElements : ownedDiagramElementsList) {


                        //Enable the retrieval of UML elements
                        allOwnedElementsModelElements.add(ownedDiagramElements.modelElement);

                        //Handle color identification
                        ColorCSVEntry colorCSVEntry = handleColorEntries(outputCSVEntry, ownedDiagramElements);
                        if (colorCSVEntry != null) {
                            colorCSVEntry.setUrl(outputCSVEntry.getDiagramUrl());
                            outputCSVEntry.getColorCSVEntryList().add(colorCSVEntry);
                        }
                    }

                    List<OwnedDiagramElements> classLikeElements = new ArrayList<>();
                    for (OwnedDiagramElements ownedDiagramElements : ownedDiagramElementsList) {
                        switch (ownedDiagramElements.type) {
                            case "com.genmymodel.graphic.uml:ClassWidget":
                            case "com.genmymodel.graphic.uml:InterfaceWidget":
                            case "com.genmymodel.graphic.uml:EnumerationWidget":
                            case "com.genmymodel.graphic.uml:DataTypeWidget":
                            case "com.genmymodel.graphic.uml:InstanceWidget":
                                //case "com.genmymodel.graphic.uml:PackageWidget":
                            case "com.genmymodel.graphic.uml:CommentWidget":
                                classLikeElements.add(ownedDiagramElements);
                                break;
                            default:
                                //Do nothing
                                break;
                        }
                    }


                    outputCSVEntry.setClassNb(Integer.toString(classLikeElements.size()));
                    //System.out.println("nb:"+outputCSVEntry.getClassNb());

                    List<PackagedElement> umlElements = getUMLElementsById(allOwnedElementsModelElements, dom, outputCSVEntry.getDiagramUrl());
                    List<DiagElementCSVEntry> diagElementCSVEntryList = new ArrayList<>();
                    PackagedElement elementWithMaxElements = null;
                    int maxElements = -1;
                    for (PackagedElement packagedElement : umlElements) {
                        if (packagedElement.getDiagElementCSVEntryList().size() > maxElements) {
                            maxElements = packagedElement.getDiagElementCSVEntryList().size();
                            elementWithMaxElements = packagedElement;
                        }
                        diagElementCSVEntryList.addAll(packagedElement.getDiagElementCSVEntryList());
                    }
                    for (DiagElementCSVEntry entry : diagElementCSVEntryList) {
                        entry.setProjectId(projectId);
                        entry.setDiagramId(diagramId);
                    }
                    outputCSVEntry.setDiagElementCSVEntryList(diagElementCSVEntryList);
                    outputCSVEntry.setMaxElementsInClass(Integer.toString(maxElements));

                    GridCounter gridCounter = new GridCounter();
                    int lines = gridCounter.countLines(classLikeElements);
                    int columns = gridCounter.countColumns(classLikeElements);

                    double gridCoeff = 1f * classLikeElements.size() / (lines * columns);

                    outputCSVEntry.setGridLines(Integer.toString(lines));
                    outputCSVEntry.setGirdColumns(Integer.toString(columns));
                    outputCSVEntry.setGridRatio(Double.toString(gridCoeff));

//System.out.println(outputCSVEntry.getDiagramUrl());


                    int width = -1;
                    int height = -1;

                    if (contents.getAttributes().getNamedItem("width") != null && contents.getAttributes().getNamedItem("height") != null) {
                        width = Integer.parseInt(contents.getAttributes().getNamedItem("width").getNodeValue());
                        height = Integer.parseInt(contents.getAttributes().getNamedItem("height").getNodeValue());

                        outputCSVEntry.setHeight(Integer.toString(height));
                        outputCSVEntry.setWidth(Integer.toString(width));
                        heatMapBuilder.parseClasses(width, height, classLikeElements);
                    }

                    SizePojo sizePojo = gridCounter.getTrueSize(classLikeElements);
                    if (sizePojo.getTrueWith() > 0) {
                        outputCSVEntry.setTrueWidth(Integer.toString(sizePojo.getTrueWith()));
                        outputCSVEntry.setTrueHeight(Integer.toString(sizePojo.getTrueHeight()));
                        trueSizeHeatMapBuilder.parseClassesRealWidth(sizePojo, width, height, classLikeElements);
                        fitDiagramHeatpMapBuilder.parseClassesFitDiagramInBox(sizePojo, width, height, classLikeElements);

                        //We filter out packages !!!
                        OwnedDiagramElements biggestArea = gridCounter.getBiggestAreaClass(classLikeElements);
                        interestingClassBuilder.parseClassFitDiagramInBox(sizePojo, biggestArea);

                        OwnedDiagramElements maxElementsClass = null;
                        for (OwnedDiagramElements ownedDiagramElements : classLikeElements) {
                            if (ownedDiagramElements.modelElement.equals(elementWithMaxElements.id)) {
                                System.out.println("gotcha");
                                maxElementsClass = ownedDiagramElements;
                                break;
                            }
                        }
                        if (maxElementsClass != null) {
                            maxElementsBuilder.parseClassFitDiagramInBox(sizePojo, maxElementsClass);
                        }

                        //For all
                        allClassLocationBuilder.parseClassesFitDiagramInBox(sizePojo, classLikeElements);
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
                    ownedDiagramElements.type.contains("PackageWidget") ||
                    ownedDiagramElements.type.contains("AssociationSegment") ||
                    ownedDiagramElements.type.contains("GeneralizationSegment") ||
                    ownedDiagramElements.type.contains("AssociationClassSegment") ||
                    ownedDiagramElements.type.contains("DependencySegment") ||
                    ownedDiagramElements.type.contains("UsageSegment") ||
                    ownedDiagramElements.type.contains("InterfaceRealizationSegment") ||
                    ownedDiagramElements.type.contains("InnerElementSegment") ||
                    ownedDiagramElements.type.contains("CommentWidget")
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
                        ownedDiagramElements.type.contains("StaticLabel") ||
                        ownedDiagramElements.type.contains("NodeWidget") ||
                        ownedDiagramElements.type.equals("uml:Node") ||
                        ownedDiagramElements.type.contains("InformationFlow") ||
                        ownedDiagramElements.type.contains("DeviceWidget") ||
                        ownedDiagramElements.type.contains("DeploymentNode") ||
                        ownedDiagramElements.type.contains("CommunicationPath")
                ) {
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
                //if ("com.genmymodel.graphic.uml:ClassWidget".equals(childNode.getAttributes().getNamedItem("xsi:type").getNodeValue())) {
                outputList.add(new OwnedDiagramElements(childNode));
                //}
            }
        }
        return outputList;
    }

    private List<PackagedElement> getUMLElementsById(Set<String> setOfId, Document dom, String url) {
        List<PackagedElement> output = new ArrayList<>();

        NodeList nodeList = dom.getElementsByTagName("packagedElement");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node element = nodeList.item(i);
            if (setOfId.contains(element.getAttributes().getNamedItem("xmi:id").getNodeValue())) {
                switch (element.getAttributes().getNamedItem("xsi:type").getNodeValue()) {
                    case "uml:Class":
                        output.add(new ClassElement(element));
                        break;
                    case "uml:Association":
                        output.add(new AssociationElement(element));
                        break;
                    case "uml:Dependency":
                        output.add(new DependencyElement(element));
                        break;
                    case "uml:Usage":
                        output.add(new UsageElement(element));
                        break;
                    case "uml:Enumeration":
                        output.add(new EnumElement(element));
                        break;
                    case "uml:EnumerationLiteral":
                        output.add(new EnumLiteralElement(element));
                        break;
                    case "uml:Interface":
                        output.add(new InterfaceElement(element));
                        break;
                    case "uml:InterfaceRealization":
                        output.add(new InterfaceRealizationElement(element));
                        break;
                    case "uml:InstanceSpecification":
                        output.add(new InstanceSpecificationElement(element));
                        break;
                    case "uml:Package":
                        output.add(new PackageElement(element));
                        break;
                    case "uml:DataType":
                        output.add(new DatatypeElement(element));
                        break;
                    case "uml:AssociationClass":
                        output.add(new AssociationClassElement(element));
                        break;
                    case "uml:UseCase":
                    case "uml:Actor":
                    case "uml:Component":
                    case "uml:Device":
                    case "uml:Artifact":
                    case "uml:ExecutionEnvironment":
                    case "uml:Activity":
                    case "uml:StateMachine":
                    case "uml:InformationFlow":
                    case "uml:Node":
                    case "uml:CommunicationPath":
                        //Do nothing
                        break;
                    default:
                        System.out.println("Didnt handle type " + element.getAttributes().getNamedItem("xsi:type").getNodeValue() + " - " + url);
                        break;
                }

            }
        }

        return output;
    }


}
