package model;

import javax.swing.*;

import java.util.ArrayList;

/*This class keeps all EventItems in an ArrayList, and functions to add EventItems,
remove EventItems, and print that Day's EventItems.*/
//All JSON-related code can be found in the CPSC 210 Phase 2 Github page, and is modified to suit my project

public class Day {
    private final int date;
    public ArrayList<EventItem> eventItems;

    /*
     * REQUIRES: date within valid range of values w.r.t. the month and year.
     * MODIFIES: this
     * EFFECTS: Initializes date
     */
    public Day(int date) {
        this.date = date;
        eventItems = new ArrayList<EventItem>();
    }

    /*
     * EFFECTS: Prints all the events for this Day object.
     */
    public boolean printDayEventItems() {
        int i = 1;
        if (eventItems.size() > 0) {
            System.out.println("Day " + date);
        } else {
            return false; //Nothing was printed; no events that day.
        }
        for (EventItem e : eventItems) {
            System.out.print(
                    i + ". Name: " + e.getName()
                            + "\n   Start Date: " + e.getStartDate() + ", End date: " + e.getEndDate()
                            + "\n   Time:" + e.getTimeSummary() + "\n   Completion Status: " + e.getCompletion()
                            + "\n   Description:\n   " + e.getDescription() + "\n"
            );
            i++;
        }
        return true; //Something was printed: that day had at least an event.
    }

    /*
    * EFFECTS: Returns day's events as a string for printing.
    * */
    public String dayEventItemsToString() {
        int i = 1;
        String msg = "";
        if (eventItems.size() > 0) {
            msg += ("Day " + date + "\n");
        } else {
            return msg; //Return nothing.
        }
        for (EventItem e : eventItems) {
            msg += (
                    i + ". Name: " + e.getName()
                            + "\n   Start Date: " + e.getStartDate() + ", End date: " + e.getEndDate()
                            + "\n   Time:" + e.getTimeSummary() + "\n   Completion Status: " + e.getCompletion()
                            + "\n   Description:\n   " + e.getDescription() + "\n\n");
            i++;
        }
        return msg;
    }

    /*
     * MODIFIES: this and Planner's days list.
     * EFFECTS: Adds an EventItem to this Day object.
     */
    public void addEventItem(
            int startDate, int endDate, String startTime, String endTime, String description,String name) {
        eventItems.add(
                new EventItem(startDate, endDate, startTime, endTime, description,name)
        );
    }

    /*
     * MODIFIES: this and Planner's days list.
     * EFFECTS: Adds an EventItem to this Day object.
     */
    public void addEventItem(EventItem e) {
        eventItems.add(e);
    }

    /*

     * MODIFIES: this and Planner's days list.
     * EFFECTS: Removes an EventItem to this Day object.
     * Returns true if successful.
     * Note: Did not include checks for the parameter index, so used an exception to handle it instead. (Phase 4 task 2)
     */
    public void removeEventItem(int index) throws Exception {
        if (index >= eventItems.size() || index < 0) { //Index out of bounds.
            throw new Exception("Please input an index that satisfies 0 <= index < (number of events for chosen day)");
        } else {
            eventItems.remove(index);
        }
    }

    /*
    * EFFECTS: Returns date
    * */
    public int getDate() {
        return date;
    }

}
