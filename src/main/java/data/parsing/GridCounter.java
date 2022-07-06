package main.java.data.parsing;

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

        System.out.println(clusters);

        for(Integer y : yList){
            System.out.println(y);
        }

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

        System.out.println(clusters);

        for(Integer y : xList){
            System.out.println(y);
        }

        return clusters.size();
    }
}
