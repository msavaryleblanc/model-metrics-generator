package main.java.data.parsing;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SegmentIntersectionCounter extends GenericParser {


    public SegmentIntersectionCounter() {
    }

    @Override
    public void parse(Document document) {
        Node planeNode = this.goToFirstTagWithName(document, "plane");
        System.out.println(planeNode + " : " + planeNode.getChildNodes().getLength());

        this.performTaskOnAllNodesWithTagName(document, "contents", "com.genmymodel.graphic.uml:ClassDiagram", new OneTaskOneTagInterface() {
            @Override
            public void performOneTaskOnThisTag(Node node) {
                HashMap<String, Point> points = getAllSegmentPoints(node);
                ArrayList<Line2D> segments = getSegments(node, points);
                drawSegments(segments);
                //drawPoints(node);
                System.out.println("number of intersections : " + computeIntersections(segments));
            }
        });


    }

    public void drawSegments(ArrayList<Line2D> segments) {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new GridLayout(1, 1));
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (int index = 0; index < segments.size(); index++) {
                    Line2D segment = segments.get(index);
                    g.setColor(Color.BLACK);
                    g.drawLine((int) segment.getP1().getX(), (int) segment.getP1().getY(), (int) segment.getP2().getX(), (int) segment.getP2().getY());
                }
            }
        };
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 800));
        frame.setLocation(new Point(0, 0));


    }

    public class PointClass {
        String xsiType;
        Color color;
        String tagName = "ownedDiagramElements";

        public PointClass(Color color, String xsiType) {
            this.color = color;
            this.xsiType = xsiType;
        }
    }

    private void drawPoints(Node node) {
        ArrayList<PointClass> pointClasses = new ArrayList<>();
        pointClasses.add(new PointClass(Color.BLACK, "com.genmymodel.graphic.uml:AssociationSegment"));
        pointClasses.add(new PointClass(Color.RED, "com.genmymodel.ecoreonline.graphic:WaypointWidget"));
        pointClasses.add(new PointClass(Color.YELLOW, "com.genmymodel.graphic.uml:ClassWidget"));
        pointClasses.add(new PointClass(Color.GREEN, "com.genmymodel.ecoreonline.graphic:AnchorWidget"));
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new GridLayout(1, 1));
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (PointClass pointClass : pointClasses) {
                    performTaskOnAllNodesWithTagName(node, "ownedDiagramElements", pointClass.xsiType, new OneTaskOneTagInterface() {
                        @Override
                        public void performOneTaskOnThisTag(Node node) {
                            String xString = getAttributeValue(node, "x");
                            String yString = getAttributeValue(node, "y");
                            String widthString = getAttributeValue(node, "width");
                            String heightString = getAttributeValue(node, "height");
                            if (xString != null && yString != null) {
                                int x = Integer.parseInt(xString);
                                int y = Integer.parseInt(yString);
                                int width = 5;
                                int height = 5;
                                if (widthString != null) {
                                    width = Integer.parseInt(widthString);
                                }
                                if (heightString != null) {
                                    height = Integer.parseInt(heightString);
                                }
                                g.setColor(pointClass.color);
                                g.drawRect(x, y, width, height);
                            }
                        }

                    });
                }
            }
        };
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 800));
        frame.setLocation(new Point(0, 0));


    }

    public int computeIntersections(ArrayList<Line2D> segments) {
        int counter = 0;
        for (int index1 = 0; index1 < segments.size(); index1++) {
            for (int index2 = index1 + 1; index2 < segments.size(); index2++) {
                Line2D segment1 = segments.get(index1);
                Line2D segment2 = segments.get(index2);
                // Check if endpoints are equal (if they are, it is not an intersection)
                if (!(segment1.getP1().equals(segment2.getP1()) ||
                        segment1.getP1().equals(segment2.getP2()) ||
                        segment1.getP2().equals(segment2.getP1()) ||
                        segment1.getP2().equals(segment2.getP2())) ) {
                    // let's check if intersection there is
                    if (doIntersect(segment1.getP1(), segment1.getP2(), segment2.getP1(), segment2.getP2())) {
                        counter++;
                        //System.out.println(" ******* INTERSECTION ON *******");
                        //System.out.println("" + segment1.getP1().getX() + " , " + segment1.getP1().getY() + "   /   " + segment1.getP2().getX() + " , " + segment1.getP2().getY());
                        //System.out.println("" + segment2.getP1().getX() + " , " + segment2.getP1().getY() + "   /   " + segment2.getP2().getX() + " , " + segment2.getP2().getY());
                    }
                }
            }
        }
        return counter;
    }

    // To construct segments, we have to use associationSegment nodes
    // they link segment points (anchor or waypoint) through their attributes sourceConnector and targetConnector

    public ArrayList<Line2D> getSegments(Node node, HashMap<String, Point> points) {
        ArrayList<Line2D> lines = new ArrayList<>();
        this.performTaskOnAllNodesWithTagName(node, "ownedDiagramElements", "com.genmymodel.graphic.uml:AssociationSegment", new OneTaskOneTagInterface() {
            @Override
            public void performOneTaskOnThisTag(Node node) {
                String sourceId = getAttributeValue(node, "sourceConnector");
                String targetId = getAttributeValue(node, "targetConnector");
                Point source = points.get(sourceId);
                Point target = points.get(targetId);
                if (source != null && target != null) {
                    Line2D line = new Line2D.Float();
                    line.setLine(source.x, source.y, target.x, target.y);
                    lines.add(line);
                    //System.out.println(" ======= ONE SEGMENT FOUND =========");
                    //System.out.println("" + source.x + " , " + source.y + "   /   " + target.x + " , " + target.y);
                }
            }
        });
        return lines;
    }

    // Gather segment points
    // These points can be anchors or waypoints.

    public HashMap<String, Point> getAllSegmentPoints(Node node) {
        HashMap<String, Point> points = new HashMap<>();
        this.performTaskOnAllNodesWithTagName(node, "ownedDiagramElements", null, new OneTaskOneTagInterface() {
            @Override
            public void performOneTaskOnThisTag(Node node) {
                String xsiType = getAttributeValue(node, "xsi:type");
                if (stringEqualsUpperMode(xsiType, "com.genmymodel.ecoreonline.graphic:WaypointWidget") ||
                        stringEqualsUpperMode(xsiType, "com.genmymodel.ecoreonline.graphic:AnchorWidget")) {
                    String xString = getAttributeValue(node, "x");
                    String yString = getAttributeValue(node, "y");
                    if (xString != null && yString != null) {
                        int x = Integer.parseInt(xString);
                        int y = Integer.parseInt(yString);
                        Point point = new Point(x, y);
                        String id = getAttributeValue(node, "xmi:id");
                        points.put(id, point);
                    }
                }
            }
        });
        return points;
    }

    // ********************
    //
    // INTERSECTION PART
    //
    // This code is contributed by Princi Singh


    // Given three collinear points p, q, r, the function checks if
    // point q lies on line segment 'pr'
    boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;

        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    int orientation(Point p, Point q, Point r) {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        int val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0; // collinear

        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    Point transform(Point2D p1) {
        return new Point((int) (p1.getX()), (int) (p1.getY()));
    }

    ;

    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    boolean doIntersect(Point2D p1, Point2D q1, Point2D p2, Point2D q2) {
        return doIntersect(transform(p1), transform(q1), transform(p2), transform(q2));
    }

    boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are collinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are collinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are collinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }


}
