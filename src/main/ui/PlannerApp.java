package ui;

import model.EventItem;
import model.Planner;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
//This class creates all appropriate instances of each Class to start a working program.
//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.

public class PlannerApp {
    private static final String JSON_STORE = "./data/planner.json"; //This is the json filename.
    private Planner monthPlanner;
    private Scanner input;
    private Scanner sentenceInput;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    boolean keepGoing = true;

    //EFFECTS: Runs the Planner Application
    //I got this format from the github link, modified it for Planner.

    public PlannerApp() throws FileNotFoundException {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runPlannerApp();
    }

    //EFFECTS: Sets up a Planner app for the GUI.
    public PlannerApp(int month,int year, int days) throws FileNotFoundException {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        monthPlanner = new Planner(month,year,days);
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    //Note: The structure of runPlannerApp is based on runTeller() and processCommand() from the Teller Project.
    //The structure was simple and made sense, so I didn't see the point in modifying it too much for my own project.
    private void runPlannerApp() {
        String command;
        int month;
        int year;
        int numDays;
        System.out.println("Welcome to your monthly planner!");
        do {
            System.out.println("Please input the month for the planner:");
            input = new Scanner(System.in);
            sentenceInput = new Scanner(System.in).useDelimiter("###");
            month = input.nextInt();
            System.out.println("Please input the year for the planner:");
            year = input.nextInt();
            System.out.println("Please input number of days for that month/year combination:");
            numDays = input.nextInt();
            System.out.println();
        } while (month < 0 || month > 12 || year < 0 || numDays < 1);
        monthPlanner = new Planner(month, year,numDays);
        while (keepGoing) {
            System.out.println("Please input a command: ");
            displayMenu();
            command = (input.next()).toLowerCase();
            processCommand(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            System.out.println("You have selected: Add an event");
            addEvent();
        } else if (command.equals("r")) {
            System.out.println("You have selected: Remove an event");
            removeEvent();
        } else if (command.equals("e")) {
            System.out.println("You have selected: Edit an event's description");
            editEvent();
        } else if (command.equals("t")) {
            toggleEvent();
        } else if (command.equals("p")) {
            listAllEvents();
        } else if (command.equals("s")) {
            savePlanner();
        } else if (command.equals("l")) {
            System.out.println("You have selected: Load Planner from file.");
            loadPlanner();
        } else if (command.equals("q")) {
            quit();
        } else {
            System.out.println("Command invalid. \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Planner from file
    //I got this format from the github link, modified it for Planner.
    public void loadPlanner() {
        try {
            monthPlanner = jsonReader.read();
            System.out.println("Loaded the planner from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the Planner to file
    //I got this format from the github link, modified it for Planner.
    public void savePlanner() {
        System.out.println("You have selected: Save Planner to file.");
        try {
            jsonWriter.open();
            jsonWriter.write(monthPlanner);
            jsonWriter.close();
            System.out.println("Saved your planner to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    /*
    * MODIFIES: this, Planner, Day
    * EFFECTS: Adds an event to the planner
    */

    private void addEvent() {
        int startDate;
        int endDate;
        String description;
        String name;
        String startTime; //Start and end times not checked for correctness, it is user's responsibility.
        String endTime;
        do {
            System.out.println("Enter task name: ");
            name = sentenceInput.nextLine();
            System.out.println("Enter start date: ");
            startDate = input.nextInt();
            System.out.println("Enter end date:");
            endDate = input.nextInt();
            System.out.println("Enter the start time in the format of your choosing: ");
            startTime = input.next();
            System.out.println("Enter the end time in the format of your choosing: ");
            endTime = input.next();
            System.out.println("Enter description of the task:");
            description = sentenceInput.nextLine();
        } while (startDate < 0 || startDate > monthPlanner.getNumDays()
                || endDate < 0 || endDate > monthPlanner.getNumDays() || startDate > endDate);
        monthPlanner.days.get(startDate - 1).addEventItem(startDate, endDate, startTime, endTime, description,name);
    }

    /*
    * REQUIRES: Proper EventItem setup for insert into list.
    * MODIFIES: The planner
    * EFFECTS: Inserts e into the appropriate location.
    * */
    public void addEvent(EventItem e) {
        monthPlanner.days.get(e.getStartDate() - 1).addEventItem(e);
    }

    /*
     * MODIFIES: this, Planner, Day
     * EFFECTS: Removes an event from the planner
     */
    private void removeEvent() {
        int index;
        printAllEvents(1);//Print all events
        System.out.println("What is the date of the event you want to remove?");
        int date = input.nextInt();
        if (date > monthPlanner.getNumDays() || date < 0) {
            System.out.println("Invalid date.");
            return;
        }
        monthPlanner.days.get(date - 1).printDayEventItems();
        System.out.println("What is the index number of the event you want to remove?");
        index = input.nextInt();

        try {
            monthPlanner.days.get(date - 1).removeEventItem(index - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Event has been removed.");
    }

    /*
     * MODIFIES: this, Planner, Day
     * EFFECTS: Edits an event description in the planner
     */
    private void editEvent() {
        int date;
        int index;
        printAllEvents(1);//Print all events
        System.out.println("What is the date of the event you want to edit?");
        date = input.nextInt();
        if (date > monthPlanner.getNumDays() || date < 0) {
            System.out.println("Invalid date.");
            return;
        }
        monthPlanner.days.get(date - 1).printDayEventItems();
        System.out.println("What is the index number of the event you want to edit?");
        index = input.nextInt();
        if (index < 0 || index > monthPlanner.days.get(date - 1).eventItems.size()) {
            System.out.println("Invalid index.");
            return;
        }
        System.out.println(
                "Current description: \n" + monthPlanner.days.get(date - 1).eventItems.get(index - 1).getDescription()
        );
        System.out.println("Enter the new description: ");
        String description = sentenceInput.nextLine();
        monthPlanner.days.get(date - 1).eventItems.get(index - 1).setEventItemDescription(description);
        System.out.println(monthPlanner.days.get(date - 1).eventItems.get(index - 1).getName() + "has been edited.");
    }

    /*
     * MODIFIES: this, Planner, Day
     * EFFECTS: Toggles an event's completion in the planner
     */
    private void toggleEvent() {
        System.out.println("You have selected: Toggle an event");
        int date;
        int index;
        printAllEvents(1);//Print all events
        System.out.println("What is the date of the event you want to toggle completion?");
        date = input.nextInt();

        if (date > monthPlanner.getNumDays() || date < 0) {
            System.out.println("Invalid date.");
            return;
        }
        monthPlanner.days.get(date - 1).printDayEventItems();
        System.out.println("\nWhat is the index number of the event you want to edit?");
        index = input.nextInt();

        if (index < 0 || index > monthPlanner.days.get(date - 1).eventItems.size()) {
            System.out.println("Invalid index.");
            return;
        }
        monthPlanner.days.get(date - 1).eventItems.get(index - 1).toggleEventItemCompletion();
        System.out.println(
                "Completion of " + monthPlanner.days.get(date - 1).eventItems.get(index - 1).getName()
                + " has been set to " + monthPlanner.days.get(date - 1).eventItems.get(index - 1).getCompletion());
    }

    /*
     * MODIFIES: this, Planner, Day
     * EFFECTS: Toggles an event's completion in the planner, to be used from GUI.
     * Returns string message used for popup message.
     */
    public String toggleEvent(int date, int index) {
        monthPlanner.days.get(date - 1).eventItems.get(index - 1).toggleEventItemCompletion();
        return ("Completion of " + monthPlanner.days.get(date - 1).eventItems.get(index - 1).getName()
                + " has been set to " + monthPlanner.days.get(date - 1).eventItems.get(index - 1).getCompletion());
    }

    /*
     * EFFECTS: Lists all events
     */
    private void listAllEvents() {
        System.out.println("You have selected: Print all events");
        int currentDate;
        do {
            System.out.println("Enter the current date: ");
            currentDate = input.nextInt();
            System.out.println();
        } while (currentDate < 0 || currentDate > monthPlanner.getNumDays());
        printAllEvents(currentDate);
    }

    /*
     * REQUIRES: 1 <= currentDate (the index) <= daysNumber
     * EFFECTS: Prints all the events for the month.
     */
    private void printAllEvents(int currentDate) {
        System.out.println("Monthly Planner for Month " + monthPlanner.getMonth() + ", Year " + monthPlanner.getYear());
        System.out.println("Upcoming Events: ");
        for (int i = currentDate - 1;i < monthPlanner.getNumDays(); i++) {
            monthPlanner.days.get(i).printDayEventItems();
        }

        System.out.println("\nPast Events: ");
        for (int j = 0;j < currentDate - 1; j++) {
            monthPlanner.days.get(j).printDayEventItems();
        }
        System.out.println();
    }

    /*
     * EFFECTS: Returns all the events for the month in a String, invoking proper helpers.
     */
    public String allEventsToString() {
        String msg = "";
        msg += ("Monthly Planner for the " + monthPlanner.getNumDays()
                + "-day Month " + monthPlanner.getMonth() + ", Year " + monthPlanner.getYear() + "\n");
        for (int i = 0;i < monthPlanner.getNumDays(); i++) {
            msg += monthPlanner.days.get(i).dayEventItemsToString();
        }
        return msg;
    }

    /*
     * EFFECTS: Exits the program.
     */
    private void quit() {
        System.out.println("You have selected: quit");
        System.out.println("Thank you for using Month Planner. Goodbye!");
        keepGoing = false;
    }

    // EFFECTS: displays usage menu to user.
    private void displayMenu() {
        System.out.println("\nSelect Action:");
        System.out.println("\ta -> Add an event");
        System.out.println("\tr -> Remove an event");
        System.out.println("\te -> Edit an event's description");
        System.out.println("\tt -> Toggle event completion");
        System.out.println("\tp -> Print all events");
        System.out.println("\ts -> Save planner to file");
        System.out.println("\tl -> Load planner from file");
        System.out.println("\tq -> quit");
    }
}
