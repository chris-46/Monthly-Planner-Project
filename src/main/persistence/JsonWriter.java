package persistence;

import model.Planner;
import org.json.JSONObject;


import java.io.*;

//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.
// I edited it to suit the needs of my project.
// Represents a writer that writes JSON representation of Planner to file

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    // This method served the same purpose as its original use.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    // This method served the same purpose as its original use.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Planner to file
    // This method served the same purpose as its original use.
    public void write(Planner planner) {
        JSONObject json = planner.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    // This method served the same purpose as its original use.
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    // This method served the same purpose as its original use.
    private void saveToFile(String json) {
        writer.print(json);
    }
}
