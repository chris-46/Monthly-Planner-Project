package persistence;

import org.json.JSONObject;
//All JSON-related code is obtained from the CPSC 210 Phase 2 github site (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo), and modified to suit my project.


public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
