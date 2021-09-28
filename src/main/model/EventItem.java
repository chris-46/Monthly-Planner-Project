package model;

import org.json.JSONObject;
import persistence.Writable;

//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.
/* This class is the EventItem itself, it is the basic object unit within a Day,
and it can have its description and completion changed.*/

public class EventItem implements Writable {
    private final String name;//This is the EventItem's name
    private String description; //This is the EventItem's description.
    private final int startDate;
    private final int endDate;
    private final String startTime;
    private final String endTime;
    private boolean completion;

    /*
     * REQUIRES: All startDate and endDate to make sense within the month
     * MODIFIES: this
     * EFFECTS: initializes all the aforementioned variables incl. completion set to false.
     */
    public EventItem(int startDate, int endDate, String startTime, String endTime, String description,String name) {
        this.startDate = startDate;
        this.endDate = endDate;
        //Note: startTime and endTime will not be checked for correctness,it is user's freedom to enter a format.
        this.startTime = startTime;
        this.endTime = endTime;
        this.completion = false;
        this.description = description;
        this.name = name;
    }

    /*
     * MODIFIES: this and Planner's days list.
     * EFFECTS: Edits an EventItem's description.
     */
    public void setEventItemDescription(String description) {
        this.description = description;
    }

    /*
     * MODIFIES: this and Planner's days list.
     * EFFECTS: Toggles an EventItem's completion.
     */
    public void toggleEventItemCompletion() {
        completion = !(completion);
    }

    /*
     * EFFECT: returns name
     * */
    public String getName() {
        return name;
    }

    /*
     * EFFECT: returns description
     * */
    public String getDescription() {
        return description;
    }


    /*
     * EFFECT:  returns startDate
     * */
    public int getStartDate() {
        return startDate;
    }

    /*
     * EFFECT: returns endDate
     * */
    public int getEndDate() {
        return endDate;
    }

    /*
     * EFFECT: returns startMinute
     * */
    public String getTimeSummary() {
        return startTime + " - " + endTime;
    }

    /*
    * EFFECT: returns completion
    * */
    public boolean getCompletion() {
        return completion;
    }

    /*
     * EFFECT: returns start time
     * */
    public String getStartTime() {
        return startTime;
    }

    /*
     * EFFECT: returns end time
     * */
    public String getEndTime() {
        return endTime;
    }

    @Override
    /*
    * EFFECTS: Puts an events information into JSON
    * I got this format from the github link, modified it for Planner.
    * */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("startDate",startDate);
        json.put("endDate",endDate);
        json.put("startTime",startTime);
        json.put("endTime",endTime);
        json.put("completion",completion);
        json.put("name",name);
        json.put("description",description);
        return json;
    }
}
