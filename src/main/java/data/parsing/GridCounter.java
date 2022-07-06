package main.java.data.parsing;

import main.java.data.analysis.entity.SizePojo;
import main.java.data.parsing.entity.OwnedDiagramElements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class GridCounter {

    public static final Integer Y_MARGIN = 80;
    public static final Integer X_MARGIN = 80;

    public int countLines(List<OwnedDiagramElements> ownedDiagramElementsList){
        List<Integer> yList = new ArrayList<>();

        for(OwnedDiagramElements ownedDiagramElements : ownedDiagramElementsList){
            yList.add(ownedDiagramElements.y + ownedDiagramElements.height/2);
        }

        yList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        ListIterator<Integer> it = yList.listIterator();

        // Condition check whether there is element in List
        // using hasNext() which holds true till
        // there is single element in List

        int previousValue = -1;
        List<List<Integer>> clusters = new ArrayList<>();
        List<Integer> currentCluster = new ArrayList<>();

        while (it.hasNext()) {
            if(previousValue == -1){
                previousValue = it.next();
                currentCluster.add(previousValue);
            }
            else{
                int currentValue = it.next();
                if (currentValue >= previousValue + Y_MARGIN) {
                    clusters.add(currentCluster);
                    currentCluster = new ArrayList<>();
                    previousValue = currentValue;
                }
                currentCluster.add(currentValue);
            }
        }
        clusters.add(currentCluster);

        return clusters.size();
    }

    public int countColumns(List<OwnedDiagramElements> ownedDiagramElementsList){
        List<Integer> xList = new ArrayList<>();

        for(OwnedDiagramElements ownedDiagramElements : ownedDiagramElementsList){
            xList.add(ownedDiagramElements.x + ownedDiagramElements.width/2);
        }

        xList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        ListIterator<Integer> it = xList.listIterator();

        // Condition check whether there is element in List
        // using hasNext() which holds true till
        // there is single element in List

        int previousValue = -1;
        List<List<Integer>> clusters = new ArrayList<>();
        List<Integer> currentCluster = new ArrayList<>();

        while (it.hasNext()) {
            if(previousValue == -1){
                previousValue = it.next();
                currentCluster.add(previousValue);
            }
            else{
                int currentValue = it.next();
                if (currentValue >= previousValue + X_MARGIN) {
                    clusters.add(currentCluster);
                    currentCluster = new ArrayList<>();
                    previousValue = currentValue;
                }
                currentCluster.add(currentValue);
            }
        }
        clusters.add(currentCluster);

        return clusters.size();
    }


    public SizePojo getTrueSize(List<OwnedDiagramElements> ownedDiagramElementsList){

        int minY = Integer.MAX_VALUE;
        int maxYWithOffset = -1;
        int minX = Integer.MAX_VALUE;
        int maxXWithOffset = -1;

        for(OwnedDiagramElements ownedDiagramElements : ownedDiagramElementsList){
            if(ownedDiagramElements.y < minY){
                minY = ownedDiagramElements.y;
            }
            if(ownedDiagramElements.y + ownedDiagramElements.height > maxYWithOffset){
                maxYWithOffset = ownedDiagramElements.y + ownedDiagramElements.height;
            }
            if(ownedDiagramElements.x < minX){
                minX = ownedDiagramElements.x;
            }
            if(ownedDiagramElements.x + ownedDiagramElements.width > maxXWithOffset){
                maxXWithOffset = ownedDiagramElements.x + ownedDiagramElements.width;
            }
        }



        SizePojo sizePojo = new SizePojo(minX,minY,maxXWithOffset-minX,maxYWithOffset-minY);

        return sizePojo;
    }
}
