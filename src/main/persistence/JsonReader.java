package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.EventItem;
import model.Planner;
import org.json. *;

// Represents a reader that reads Planner from JSON data stored in file
//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    // This method served the same purpose as its original use.
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Planner from file and returns it;
    // throws IOException if an error occurs reading data from file
    // This method served the same purpose as its original use.
    public Planner read() throws IOException {
        String jsonData = readFile(source); //Take json file, put it into a string.
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject); //Parse the json file which is now in string form
    }

    // EFFECTS: reads source file as string and returns it
    // This method served the same purpose as its original use.
    private String readFile(String source) throws IOException { //This takes the file, puts it as a string, returns it!
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Planner from JSON object and returns it
    // I modified this to work with my planner, but obtained from the github link.
    private Planner parseWorkRoom(JSONObject jsonObject) {
        int month = jsonObject.getInt("month");
        int year = jsonObject.getInt("year");
        int numDays = jsonObject.getInt("numDays");
        Planner p = new Planner(month,year,numDays);
        addEvents(p, jsonObject);
        return p;
    }

    // MODIFIES: p
    // EFFECTS: Parses EventItems from JSON object and adds them to Planner
    // This method used to function to add Thingies, I modified it to add EventItems.
    private void addEvents(Planner p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(p,nextEvent);
        }
    }

    // MODIFIES: p
    // EFFECTS: Parses EventItems from JSON object and adds it to Planner
    // This method used to function to add Thingies, I modified it to add EventItems.
    private boolean addEvent(Planner p, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int startDate = jsonObject.getInt("startDate");
        int endDate = jsonObject.getInt("endDate");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        boolean completion = jsonObject.getBoolean("completion");
        if (startDate < 1 || startDate > p.getNumDays()
                || endDate < 0 || endDate > p.getNumDays() || startDate > endDate) {
            return false;
        }
        EventItem e = new EventItem(startDate,endDate,startTime,endTime,description,name);
        if (completion) {
            e.toggleEventItemCompletion();
        }
        p.days.get(startDate - 1).addEventItem(e);

        return true;
    }
}