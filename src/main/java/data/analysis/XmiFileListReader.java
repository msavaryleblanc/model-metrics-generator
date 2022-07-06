package main.java.data.analysis;

import main.java.data.analysis.entity.InputCSVEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmiFileListReader {

    public List<String> getFilenames(){

        List<String> filenameList = new ArrayList<>();
        File folder = new File("D:\\boulot\\genmymodel-model-retriever\\xmi");
        File[] listOfFiles = folder.listFiles();

        for(File file : listOfFiles){
            filenameList.add(file.getName());
        }
        return filenameList;

    }
}
