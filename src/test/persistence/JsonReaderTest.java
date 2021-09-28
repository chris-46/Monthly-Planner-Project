package persistence;

import model.Day;
import model.EventItem;
import model.Planner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.

public class JsonReaderTest {
    @Test
    //I just modified this test from the JsonReaderTest class obtained in the github site.
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Planner p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    //I just modified this test from the JsonReaderTest class obtained in the github site.
    void testReaderEmptyPlanner() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlanner.json");
        try {
            Planner planner = reader.read();
            assertEquals(1, planner.getMonth());
            assertEquals(1, planner.getYear());
            assertEquals(1, planner.getNumDays());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
        //I just modified this test from the JsonReaderTest class obtained in the github site.
    void testReaderGeneralPlanner() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlanner.json");
        try {
            Planner planner = reader.read();
            assertEquals(2, planner.days.size());
            EventItem a = planner.days.get(0).eventItems.get(0);
            EventItem b = planner.days.get(1).eventItems.get(0);

            assertEquals(1,a.getStartDate());
            assertEquals(1,a.getEndDate());
            assertEquals(2,b.getStartDate());
            assertEquals(2,b.getEndDate());

            assertEquals("1",a.getStartTime());
            assertEquals("1",b.getStartTime());
            assertEquals("2",a.getEndTime());
            assertEquals("2",b.getEndTime());

            assertEquals("name1",a.getName());
            assertEquals("name2",b.getName());
            assertEquals("desc",a.getDescription());
            assertEquals("desc",b.getDescription());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    @Test
        //I just modified this test from the JsonReaderTest class obtained in the github site.

    void testReaderGeneralPlannerWithTrueEvents() {
        JsonReader reader = new JsonReader("./data/testReaderTrueEvent.json");
        try {
            Planner planner = reader.read();
            assertEquals(2, planner.days.size());
            EventItem a = planner.days.get(0).eventItems.get(0);
            EventItem b = planner.days.get(1).eventItems.get(0);

            assertEquals(1,a.getStartDate());
            assertEquals(1,a.getEndDate());
            assertEquals(2,b.getStartDate());
            assertEquals(2,b.getEndDate());

            assertEquals("1",a.getStartTime());
            assertEquals("1",b.getStartTime());
            assertEquals("2",a.getEndTime());
            assertEquals("2",b.getEndTime());

            assertEquals("name1",a.getName());
            assertEquals("name2",b.getName());
            assertEquals("desc",a.getDescription());
            assertEquals("desc",b.getDescription());
            assertTrue(a.getCompletion());
            assertTrue(a.getCompletion());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
        //I just modified this test from the JsonReaderTest class obtained in the github site.
    void testReaderInvalidEvents() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidEvent.json");
        try {
            Planner planner = reader.read();
            assertEquals(2,planner.days.size());
            assertTrue(planner.days.get(0).eventItems.isEmpty());
            assertTrue(planner.days.get(1).eventItems.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
