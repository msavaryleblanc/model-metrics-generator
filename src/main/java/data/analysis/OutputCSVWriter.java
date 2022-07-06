package main.java.data.analysis;

import main.java.data.analysis.entity.OutputCSVEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputCSVWriter {


    public void writeOutput(OutputCSVEntry entry) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("csv_report.csv", true));
            writer.write(entry.toCSV() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
