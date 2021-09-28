package model;
//This is the test class for Planner, Day, and EventItem Classes.
//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {
    EventItem eventItem;
    Planner monthPlanner;
    Day day;

    @BeforeEach
    public void init() {
        eventItem = new EventItem(1,1,"13","14","description","generic task name"); //there has to be at least one date.
        day = new Day(1);//there has to be at least one day.
        monthPlanner = new Planner(1,2020,31); //January 1, 2020 has 31 days. These values can be changed at any time.
    }

    //EventItem Class tests.
    @Test
    public void testEventItemConstructor() {
        EventItem newE = new EventItem(eventItem.getStartDate(),eventItem.getEndDate(), eventItem.getStartTime(), eventItem.getEndTime(), eventItem.getDescription(),eventItem.getName());
        assertFalse(newE == eventItem); //not the same object
        assertEquals(newE.getStartDate(), eventItem.getStartDate());
        assertEquals(newE.getEndDate(), eventItem.getEndDate());
        assertTrue(newE.getStartTime().equals(eventItem.getStartTime()));
        assertTrue(newE.getEndTime().equals(eventItem.getEndTime()));
        assertTrue(newE.getName().equals(eventItem.getName()));
        assertTrue(newE.getDescription().equals(eventItem.getDescription()));
    }

    @Test public void testSetDescription() {
        String test = "test";
        assertFalse(test.equals(eventItem.getDescription()));
        eventItem.setEventItemDescription(test);
        assertTrue(test.equals(eventItem.getDescription()));
    }

    @Test public void testToggleCompletion() {
        assertFalse(eventItem.getCompletion()); //By default is false
        eventItem.toggleEventItemCompletion();
        assertTrue(eventItem.getCompletion()); //Now it's true
        eventItem.toggleEventItemCompletion(); //Toggle it back to see if true becomes false as well
        assertFalse(eventItem.getCompletion()); //Now it should be false.

    }
    //Day Class tests.
    @Test
    public void testDayConstructor() {
        Day newDay = new Day(day.getDate());//there has to be at least one day.
        assertFalse(newDay == day);
        assertTrue(newDay.getDate() == day.getDate());
        assertFalse(newDay.eventItems == null); //The arraylist was created
    }

    @Test
    public void testPrintDayEventItemsNoPrint() {
        assertFalse(day.printDayEventItems()); //should return false as nothing was printed.
    }

    @Test
    public void testPrintDayEventItems1Item() {
        assertFalse(day.printDayEventItems()); //should return false as nothing was printed.
        day.addEventItem(1,1,"12","13","abc","task");
        assertTrue(day.printDayEventItems()); //should return true as something was printed.
    }

    @Test
    public void testPrintDayEventItems2Items() {
        assertFalse(day.printDayEventItems()); //should return false as nothing was printed.
        day.addEventItem(1,1,"12","13","abc","task");
        day.addEventItem(1,1,"12","13","abc","task");
        assertTrue(day.printDayEventItems()); //should return true as something was printed.
    }

    @Test
    public void testAddEventItemEmpty() {
        assertFalse(day.eventItems.contains(eventItem));
        day.eventItems.add(eventItem);
        assertTrue(day.eventItems.contains(eventItem));
    }
    @Test
    public void testAddEventItemHasContent() {
        assertFalse(day.eventItems.contains(eventItem));
        day.eventItems.add(eventItem);
        assertTrue(day.eventItems.contains(eventItem));
        EventItem b = new EventItem(1,1,"12","15","desc","task2");
        day.eventItems.add(b);
        assertTrue(day.eventItems.contains(b));
    }

    @Test
    public void testAddEventItem2() {
        assertFalse(day.eventItems.contains(eventItem));
        day.addEventItem(eventItem);
        assertTrue(day.eventItems.contains(eventItem));
    }
    @Test
    public void testRemoveEventItemNegativeIndex() {
        assertEquals(0,day.eventItems.size());
        try {
            day.removeEventItem(-1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(0,day.eventItems.size());//nothing happens
    }

    @Test
    public void testRemoveEventItemOutOfBounds() {
        assertEquals(0,day.eventItems.size());
        try {
            day.removeEventItem(day.eventItems.size() + 1);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            day.removeEventItem(day.eventItems.size());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(0,day.eventItems.size());//nothing happens
    }

    @Test
    public void testRemoveEventItemEmpty() { //exception is expected
        assertEquals(0,day.eventItems.size());
        try {
            day.removeEventItem(0);
        } catch (RuntimeException r) {
            System.out.println(r.getStackTrace());
            //This shouldn't happen, but it might happen on accident (user inputs a double instead of int).
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(0,day.eventItems.size());//nothing happens
    }

    @Test
    public void testRemoveEventItemHas1Content() {
        day.eventItems.add(eventItem);
        assertEquals(1,day.eventItems.size());
        try {
            day.removeEventItem(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(0,day.eventItems.size());//deleted
    }

    @Test
    public void testAddEventItemHas2Content() {
        day.eventItems.add(eventItem);
        day.eventItems.add(eventItem);
        assertEquals(2,day.eventItems.size());
        try {
            day.removeEventItem(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(1,day.eventItems.size());//one element left
        try {
            day.removeEventItem(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(0,day.eventItems.size());//one element left
    }

    @Test
    void testDayEventItemsToString() {
        String msg = "";
        //eventItem = new EventItem(1,1,"13","14","description","generic task name"); //there has to be at least one date.
        day.addEventItem(eventItem);
        day.addEventItem(eventItem);
        msg += ("Day " + 1 + "\n"); //date is 1.
        int i=1;
        for (EventItem e : day.eventItems) {
            msg += (
                    i + ". Name: " + e.getName()
                            + "\n   Start Date: " + e.getStartDate() + ", End date: " + e.getEndDate()
                            + "\n   Time:" + e.getTimeSummary() + "\n   Completion Status: " + e.getCompletion()
                            + "\n   Description:\n   " + e.getDescription() + "\n\n");
            i++;
        }
        assertEquals(msg,day.dayEventItemsToString());
    }

    @Test
    void testDayEventItemsNothing() {
        Day d = new Day(25); //empty day without events
        assertEquals("",d.dayEventItemsToString()); //nothing to string.
    }
    //Planner Class Tests
    @Test
    public void testPlannerConstructor() {
        Planner newPlanner = new Planner(monthPlanner.getMonth(),monthPlanner.getYear(),monthPlanner.getNumDays());
        assertFalse(newPlanner == monthPlanner);//not same object
        assertEquals(newPlanner.getMonth(),monthPlanner.getMonth());
        assertEquals(newPlanner.getYear(),monthPlanner.getYear());
        assertEquals(newPlanner.getNumDays(),monthPlanner.getNumDays());
        assertFalse(newPlanner.days == null);
    }



}