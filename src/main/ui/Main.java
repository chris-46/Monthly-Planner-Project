package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new PlannerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file to run application.");
        }
    }
}
