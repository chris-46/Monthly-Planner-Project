package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
//This class keeps each individual Day in an ArrayList, and functions to initiate printing of all events.\
//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.

public class Planner implements Writable {
    public ArrayList<Day> days; //This is a list of Day objects
    private final int month;
    //month is just used to determine the size of the list (e.g. June has 30 days => 30 Days in days).
    private final int year; //Current year used for display purposes, and for leap year calculations.
    private final int numDays;// Number of days user inputs for that month

    /*
    * REQUIRES: 1 <= month <= 12, year >= 0, numDays > 0
    * MODIFIES: this
    * EFFECTS: Initializes days, month, year, and daysNumber.
    * Doesn't account for different amount of days in months, just has 31 days to fill.
    */
    public Planner(int month,int year, int numDays) {
        this.month = month;
        this.year = year;
        this.numDays = numDays;
        days = new ArrayList<>();
        for (int i = 1;i <= numDays;i++) {
            days.add(new Day(i)); //Initialize all the Day objects within days with the appropriate date.
        }
    }

    /*
     * EFFECTS: Returns numDays
     * */
    public int getNumDays() {
        return numDays;
    }

    /*
     * EFFECTS: Returns month
     * */
    public int getMonth() {
        return month;
    }
    
    /*
     * EFFECTS: Returns year
     * */
    public int getYear() {
        return year;
    }

    @Override
    /*
     * EFFECTS: Puts Planner information into JSON
     * I got this format from the github link, modified it for Planner.
     * */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("month",month);
        json.put("year",year);
        json.put("numDays",numDays);
        json.put("events",eventsToJson());
        return json;
    }

    /*
     * EFFECTS: Puts an events information into JSON for each day.
     *  I got this format from the github link, modified it for Planner.
     */

    private JSONArray eventsToJson() {
        JSONArray jsonArr = new JSONArray();
        for (Day d: days) {
            for (EventItem e : d.eventItems) {
                jsonArr.put(e.toJson());
            }
        }
        return jsonArr;
    }
}
