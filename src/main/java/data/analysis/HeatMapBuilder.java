package main.java.data.analysis;

import main.java.data.analysis.entity.SizePojo;
import main.java.data.parsing.entity.OwnedDiagramElements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HeatMapBuilder {

    public static final int TARGET_WIDTH = 1000;
    public static final int TARGET_HEIGHT = 800;
    public static final int MAX_HEIGHT = 2500;
    float[][] pixels;
    float currentMax;

    public HeatMapBuilder() {
        this.pixels = new float[MAX_HEIGHT][TARGET_WIDTH];
        this.currentMax = 0f;
    }


    public void parseClasses(int width, int height, List<OwnedDiagramElements> ownedDiagramElementsList) {
        float ratio = TARGET_WIDTH * 1f / width;
        for (OwnedDiagramElements classElement : ownedDiagramElementsList) {
            addPixelsOfClass(ratio, classElement);
        }
    }

    public void parseClassesRealWidth(SizePojo sizePojo, int width, int height, List<OwnedDiagramElements> ownedDiagramElementsList) {
        float ratio = TARGET_WIDTH * 1f / sizePojo.getTrueWith();
        for (OwnedDiagramElements classElement : ownedDiagramElementsList) {
            addPixelsOfClassTrueWidth(sizePojo, ratio, classElement);
        }
    }

    public void parseClassesFitDiagramInBox(SizePojo sizePojo, int width, int height, List<OwnedDiagramElements> ownedDiagramElementsList) {
        //float ratioY = TARGET_WIDTH * 1f / sizePojo.getTrueWith();
        float ratioX = TARGET_WIDTH * 1f / sizePojo.getTrueWith();
        //float ratioX = TARGET_HEIGHT * 1f / sizePojo.getTrueHeight();
        float ratioY = TARGET_HEIGHT * 1f / sizePojo.getTrueHeight();

        for (OwnedDiagramElements classElement : ownedDiagramElementsList) {
            addPixelsOfClassFirDiagramInBox(sizePojo, ratioX, ratioY, classElement);
        }
    }

    private void addPixelsOfClassFirDiagramInBox(SizePojo sizePojo, float ratioX, float ratioY, OwnedDiagramElements classElement) {
        int x = Math.max(0,(int) ((classElement.x - sizePojo.getMinX()) * ratioX));
        int y = Math.max(0,(int) ((classElement.y - sizePojo.getMinY()) * ratioY));
        int w = (int) (classElement.width * ratioX);
        int h = (int) (classElement.height * ratioY);


        int heightConstraint = Math.min(y + h, TARGET_HEIGHT-1);

        for (int j = y; j < heightConstraint; j++) {
            for (int i = x; i < Math.min(x + w, TARGET_WIDTH-1); i++) {

                pixels[j][i] += 1;
            }
        }
    }

    private void addPixelsOfClassTrueWidth(SizePojo sizePojo, float ratio, OwnedDiagramElements classElement) {
        int x = Math.max(0,(int) ((classElement.x - sizePojo.getMinX()) * ratio));
        int y = Math.max(0,(int) ((classElement.y - sizePojo.getMinY()) * ratio));
        int w = (int) (classElement.width * ratio);
        int h = (int) (classElement.height * ratio);


        int heightConstraint = Math.min(y + h, MAX_HEIGHT);

        for (int j = y; j < heightConstraint; j++) {
            for (int i = x; i < Math.min(x + w, TARGET_WIDTH-1); i++) {

                pixels[j][i] += 1;
            }
        }
    }

    private void addPixelsOfClass(float ratio, OwnedDiagramElements classElement) {
        int x = Math.max(0,(int) (classElement.x * ratio));
        int y = Math.max(0,(int) (classElement.y * ratio));
        int w = (int) (classElement.width * ratio);
        int h = (int) (classElement.height * ratio);


        int heightConstraint = Math.min(y + h, MAX_HEIGHT);

        for (int j = y; j < heightConstraint; j++) {
            for (int i = x; i < Math.min(x + w, TARGET_WIDTH-1); i++) {

                pixels[j][i] += 1;
            }
        }
    }

    public void printPixels() {
        for (float[] pixel : pixels) {
            for (float v : pixel) {
                System.out.print(v+";");
            }
            System.out.print("\n");
        }
    }

    public void normalize() {
        float maxPixel = -1f;
        for (float[] pixel : pixels) {
            for (float v : pixel) {
                if (v > maxPixel) {
                    maxPixel = v;
                }
            }
        }

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                pixels[i][j] = pixels[i][j] / maxPixel;
            }
        }
    }


    public void printTestImage() {
        // Create the new image needed
        BufferedImage img = new BufferedImage(1000, 200, BufferedImage.TYPE_INT_RGB);

        for (int rc = 0; rc < 200; rc++) {
            for (int cc = 1; cc < 1000; cc++) {
                // Set the pixel colour of the image n.b. x = cc, y = rc
                img.setRGB(cc, rc, getColorFromPixelPower(0.001f*cc));
            }//for cols
        }//for rows

        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setVisible(true);
    }

    public void printImage(String imageName, boolean shouldDisplay) {
        // Create the new image needed
        BufferedImage img = new BufferedImage(TARGET_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int rc = 0; rc < MAX_HEIGHT; rc++) {
            for (int cc = 0; cc < TARGET_WIDTH; cc++) {
                // Set the pixel colour of the image n.b. x = cc, y = rc
                img.setRGB(cc, rc, getColorFromPixelPower(pixels[rc][cc]));
            }//for cols
        }//for rows

        if(shouldDisplay) {
            JFrame frame = new JFrame();
            frame.getContentPane().setLayout(new FlowLayout());
            frame.getContentPane().add(new JLabel(new ImageIcon(img)));
            frame.pack();
            frame.setVisible(true);
        }

        try {
            File outputfile = new File("heatmap/"+imageName+".jpg");
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printImageFit(String imageName, boolean shouldDisplay) {
        // Create the new image needed
        BufferedImage img = new BufferedImage(TARGET_WIDTH, TARGET_HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int rc = 0; rc < TARGET_HEIGHT; rc++) {
            for (int cc = 0; cc < TARGET_WIDTH; cc++) {
                // Set the pixel colour of the image n.b. x = cc, y = rc
                img.setRGB(cc, rc, getColorFromPixelPower(pixels[rc][cc]));
            }//for cols
        }//for rows

        if(shouldDisplay) {
            JFrame frame = new JFrame();
            frame.getContentPane().setLayout(new FlowLayout());
            frame.getContentPane().add(new JLabel(new ImageIcon(img)));
            frame.pack();
            frame.setVisible(true);
        }

        try {
            File outputfile = new File("heatmap/"+imageName+".jpg");
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getColorFromPixelPower(float pixelPower) {
        int r = 0;
        if (pixelPower > 0.5) {
            if (pixelPower >= 0.75) {
                r = 255;
            } else {
                r = (int) ((pixelPower - 0.5) * 4d * 255d);
            }
        }

        int g = 0;
        if (pixelPower < 0.25) {
            g = (int) (pixelPower * 4d * 255d);
        } else {
            if (pixelPower > 0.75) {
                g = (int) ((1- pixelPower) * 4d * 255d);
            } else {
                g = 255;
            }
        }

        int b = 0;
        if (pixelPower <= 0.25) {
            b = 255;
        } else {
            if (pixelPower < 0.5) {
                b = (int) ((0.5-pixelPower) * 4d * 255d);
            }
        }


        return getIntFromColor(r, g, b);
    }

    private int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }


}
