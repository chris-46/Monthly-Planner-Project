package persistence;

import model.Day;
import model.EventItem;
import model.Planner;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.
public class JsonWriterTest {

    @Test
    //This was based on the test in the given github page, just modified.
    void testWriterInvalidFile() {
        try {
            Planner planner = new Planner(1,1,30);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    //Empty planner without events
    //This was based on the test in the given github page, just modified.
    void testWriterMinPlanner() {
        try {
            Planner planner = new Planner(1,1,1);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlanner.json");
            writer.open();
            writer.write(planner);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlanner.json");
            planner = reader.read();
            assertEquals(1, planner.getMonth());
            assertEquals(1, planner.getYear());
            assertEquals(1, planner.getNumDays());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    //This was based on the test in the given github page, just modified.
    void testWriterGeneralPlanner() {
        try {
            Planner planner = new Planner(1,1,2);
            planner.days.get(0).addEventItem(1,1,"1","2","desc","name1");
            planner.days.get(1).addEventItem(2,2,"1","2","desc","name2");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlanner.json");
            writer.open();
            writer.write(planner);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlanner.json");
            planner = reader.read();
            List<Day> e = planner.days;
            assertEquals(2, e.size());
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
            fail("Exception should not have been thrown");
        }
    }
}
