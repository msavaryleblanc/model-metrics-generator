package main.java.data.analysis.entity;

public class SizePojo {

    int minX;
    int minY;
    int trueWith;
    int trueHeight;

    public SizePojo(int minX, int minY, int trueWith, int trueHeight) {
        this.minX = minX;
        this.minY = minY;
        this.trueWith = trueWith;
        this.trueHeight = trueHeight;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getTrueWith() {
        return trueWith;
    }

    public int getTrueHeight() {
        return trueHeight;
    }

    @Override
    public String toString() {
        return "SizePojo{" +
                "minX=" + minX +
                ", minY=" + minY +
                ", trueWith=" + trueWith +
                ", trueHeight=" + trueHeight +
                '}';
    }
}
