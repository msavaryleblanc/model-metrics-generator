package main.java.data.analysis;

import main.java.data.analysis.entity.ColorCSVEntry;
import main.java.data.analysis.entity.OutputCSVEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutputCSVWriter {


    public void writeOutput(OutputCSVEntry entry) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("csv/csv_report.csv", true));
            writer.write(entry.toCSV() + "\n");
            writeOutputColor(entry.getColorCSVEntryList());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOutput(List<OutputCSVEntry> entries) {
        List<ColorCSVEntry> colorCSVEntryList = new ArrayList<>();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("csv/csv_report.csv", true));
            for(OutputCSVEntry entry : entries) {
                writer.write(entry.toCSV() + "\n");
                colorCSVEntryList.addAll(entry.getColorCSVEntryList());
            }
            writer.close();
            writeOutputColor(colorCSVEntryList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOutputColor(List<ColorCSVEntry> entries) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("csv/csv_report_color.csv", true));
            for(ColorCSVEntry entry : entries) {
                writer.write(entry.toCSV() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
