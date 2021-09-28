//This class was base don the example given on the phase3 page (it is a modified form of the stackoverflow program).
// https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
//Royalty-free music was obtained from this URL [Clapping sound]: https://freesound.org/people/Bootstrap193/sounds/265998/
//Code for playing sound effects from here [Java: How to play .wav files ]: https://www.youtube.com/watch?v=QVrxiJyLTqU
//This class sets up and runs the GUI for a version of the EventPlanner app.

package ui;

import model.EventItem;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class PlannerGUI extends JFrame implements ActionListener {
    static final int MONTH = 11;
    static final int YEAR = 2020;
    static final int NUM_DAYS = 30;
    private JPanel topPanel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton addBtn;
    private JButton toggleBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private PlannerApp plannerApp;
    private File musicPath;


    /*
     *  MODIFIES: this
     * EFFECTS: This is the constructor to the GUI, it sets things up and calls a helper setup().
    * */
    public PlannerGUI() {
        super("Event Planner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1920, 1080));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
        setLayout(new BorderLayout());
        addBtn = new JButton("Add an Event");
        addBtn.setActionCommand("add");
        addBtn.addActionListener(this);
        toggleBtn = new JButton("Toggle Event Completion");
        toggleBtn.setActionCommand("toggle");
        toggleBtn.addActionListener(this);
        loadBtn = new JButton("Load");
        loadBtn.setActionCommand("load");
        loadBtn.addActionListener(this);
        saveBtn = new JButton("Save");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);
        try {
            plannerApp = new PlannerApp(MONTH,YEAR,NUM_DAYS);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file to run application.");
        }
        additionalSetup();
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: This is a method that helps setup the GUI.
     * */
    public void additionalSetup() {
        musicPath = new File("./data/265998__bootstrap193__multi-clap.wav");
        String msg = "Welcome to your Event Planner! \n"
                + "This version allows you to add events, edit their completions, and load and save your Planner. \n"
                + "This is the Planner for November 2020, which contains 30 days.";
        topPanel = new JPanel(new FlowLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea); //add text area to scrollPane
        topPanel.add(addBtn);
        topPanel.add(toggleBtn);
        topPanel.add(loadBtn);
        topPanel.add(saveBtn);
        add(BorderLayout.PAGE_START,topPanel);
        add(BorderLayout.CENTER,scrollPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        JOptionPane.showMessageDialog(null,msg);
        setResizable(false);
    }

    /*
    * EFFECTS: This is the hub for all possible actions accessible through the GUI.
    * Calls appropriate helper for toggle and add, but manages the rest for save and load.
    * */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            playSound(musicPath);
            plannerApp.loadPlanner();
            JOptionPane.showMessageDialog(null,"Your Planner has been loaded from planner.json");
            textArea.setText(plannerApp.allEventsToString());
        } else if (e.getActionCommand().equals("save")) {
            playSound(musicPath);
            plannerApp.savePlanner();
            JOptionPane.showMessageDialog(null,"Your recent changes have been saved.");
        } else if (e.getActionCommand().equals("add")) {
            playSound(musicPath);
            addAction();
            textArea.setText(plannerApp.allEventsToString());
        } else if (e.getActionCommand().equals("toggle")) {
            playSound(musicPath);
            toggleAction();
            textArea.setText(plannerApp.allEventsToString());
        }
    }

    /*
    * REQUIRES: Input is correct and valid.
    * MODIFIES: Planner and its content
    * EFFECTS: Toggles a specific event's completion by evoking PlannerApp's toggle method.
    * */
    public void toggleAction() {
        int date;
        int index;
        date = Integer.parseInt(JOptionPane.showInputDialog("Enter date of the event you want to toggle: "));
        index = Integer.parseInt(JOptionPane.showInputDialog("Enter index of the event you want to toggle: "));
        JOptionPane.showMessageDialog(null,plannerApp.toggleEvent(date,index));
    }

    /*
    * MODIFIES: this, the planner
    * EFFECTS: Gathers inputs to add a new event.
    * */
    public void addAction() {
        int startDate;
        int endDate;
        String description;
        String name;
        String startTime; //Start and end times not checked for correctness, it is user's responsibility.
        String endTime;
        String msg = "Note that you must input a date within [1," + NUM_DAYS + "]  with startDate <= endDate.";
        JOptionPane.showMessageDialog(null,msg);
        do {
            name = JOptionPane.showInputDialog("Enter task name: ");
            startDate = Integer.parseInt(JOptionPane.showInputDialog("Enter start date: "));
            endDate = Integer.parseInt(JOptionPane.showInputDialog("Enter end date: "));
            startTime = JOptionPane.showInputDialog("Enter the start time in the format of your choosing: ");
            endTime = JOptionPane.showInputDialog("Enter the end time in the format of your choosing: ");
            description = JOptionPane.showInputDialog("Enter description of the task:");
        } while (startDate < 0 || startDate > NUM_DAYS
            || endDate < 0 || endDate > NUM_DAYS || startDate > endDate);
        plannerApp.addEvent(new EventItem(startDate,endDate,startTime,endTime,description,name));
    }

    /*
    * EFFECTS: This plays the .wav sound file found in sound.
    * This was obtained from https://www.youtube.com/watch?v=QVrxiJyLTqU, modified very slightly.
    * */
    private static void playSound(File sound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new PlannerGUI();
    }
}
