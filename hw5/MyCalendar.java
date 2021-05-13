import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * class responsible for creating the calendar data structure
 *
 * @author Solomon Alemu
 * @version 1.0 2/16/21
 */
public class MyCalendar {
    ArrayList<ChangeListener> cl;


    LocalDate dl;


    //regular events
    private TreeMap<LocalDate, ArrayList<Event>> Events;

    //reccuring events
    private Hashtable<DayOfWeek, ArrayList<ReccuringEvent>> reccuringEvents;

    // set that holds names of all events to reference when an event is created so there are no events with the same name (helps with deletion)
    private HashSet<String> names;

    /**
     * creates a new calendar object
     */
    public MyCalendar() {
        dl = LocalDate.now();
        cl = new ArrayList<>();
        Events = new TreeMap<>();
        reccuringEvents = new Hashtable<>();
        names = new HashSet<>();

    }

    public void setDay(LocalDate day) {
        dl = day;
        notifyL();

    }

    public void nextDay() {
        dl = dl.plusDays(1);
        notifyL();
    }

    public void previousDay() {
        dl = dl.minusDays(1);
        notifyL();
    }

    public void nextMonth() {
        dl = dl.plusDays(1);
        notifyL();
    }

    public void previousMonth() {
        dl = dl.minusDays(1);
        notifyL();
    }

    public void notifyL() {
        for (ChangeListener c : cl) {
            c.stateChanged(new ChangeEvent(this));
        }
    }

    public void attach(ChangeListener tCL) {
        this.cl.add(tCL);

        notifyL();

    }

    /**
     * returns a string of events that  take place on a given day
     *
     * @param d the day that the event information will be about
     * @return a string of events on that day
     */
    public String getEventsOnGivenDay(LocalDate d) {
        String s = "";


        if (Events.containsKey(d)) {

            ArrayList<Event> evs = Events.get(d);
            for (Event e : evs) {
                s += e.toString() + "\n";
            }
        }

        if (reccuringEvents.containsKey(d.getDayOfWeek())) {
            ArrayList<ReccuringEvent> evs = reccuringEvents.get(d.getDayOfWeek());
            for (ReccuringEvent re : evs) {
                if (!d.isBefore(re.getStartDate()) && !d.isAfter(re.getEndDate())) {
                    s += re.toString() + "\n";
                }
            }
        }


        return s;


    }

    public ArrayList<String> getEventsOnGivenDayAsList(LocalDate d) {
        ArrayList<String> s = new ArrayList<>();


        if (Events.containsKey(d)) {

            ArrayList<Event> evs = Events.get(d);
            for (Event e : evs) {
                s.add(e.toString());
            }
        }

        if (reccuringEvents.containsKey(d.getDayOfWeek())) {
            ArrayList<ReccuringEvent> evs = reccuringEvents.get(d.getDayOfWeek());
            for (ReccuringEvent re : evs) {
                if (!d.isBefore(re.getStartDate()) && !d.isAfter(re.getEndDate())) {
                    s.add(re.toString());
                }
            }
        }


        return s;


    }

    /**
     * a set of all the event names that are currently being used, useful for making sure two events to not have the same name
     *
     * @return HashSet<String>
     */
    public HashSet<String> getNames() {
        return names;
    }

    /**
     * a hashset filled with all the reccuring events in the calendar
     *
     * @return HashSet<ReccuringEvent>
     */
    public HashSet<ReccuringEvent> bucketOfRecurringEvents() {
        HashSet<ReccuringEvent> hs = new HashSet<>();
        Collection<ArrayList<ReccuringEvent>> recEvents = reccuringEvents.values();
        for (ArrayList<ReccuringEvent> ar : recEvents) {
            hs.addAll(ar);
        }
        return hs;
    }

    /**
     * accessor method for getting the recurring event hashtable
     *
     * @return Hashtable<DayOfWeek, ArrayList < ReccuringEvent>>
     */
    public Hashtable<DayOfWeek, ArrayList<ReccuringEvent>> getReccuringEvents() {
        return reccuringEvents;
    }

    /**
     * returns tree map of regular events
     *
     * @return TreeMap<LocalDate, ArrayList < Event>>
     */
    public TreeMap<LocalDate, ArrayList<Event>> getEvents() {
        return Events;
    }


    /**
     * returns a string of all events in the calendar
     *
     * @return a String
     */
    public String listAllEvents() {
        String events = "Events:  \n";

        if (Events.values() != null) {
            Collection<ArrayList<Event>> eventVals = Events.values();
            for (ArrayList<Event> ae : eventVals) {
                for (Event e : ae) {
                    events += e.toString() + " \n";

                }
            }
        }


        String recurring = " \n  Recurring Events:  \n";
        if (reccuringEvents.values() != null) {
            TreeSet<ReccuringEvent> ts = new TreeSet<>();
            Collection<ArrayList<ReccuringEvent>> eventVals = reccuringEvents.values();
            for (ArrayList<ReccuringEvent> ae : eventVals) {
                ts.addAll(ae);
            }

            for (ReccuringEvent s : ts) {
                recurring += s.toString() + " \n";
            }
        }


        return events + recurring;


    }

    /**
     * reads events from events.txt  and passes them off to be parsed into event objects
     */
    public void readFromFile() {
        Events.clear();
        reccuringEvents.clear();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String[]> events = new ArrayList<>();

        try {
            File myObj = new File("events.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String title = myReader.nextLine();
                names.add(title);
                String eventInfo = myReader.nextLine();
                String[] eventsInString = splitBySpace(eventInfo);

                titles.add(title);
                events.add(eventsInString);

            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


//        for (int i = 0; i < titles.size(); i++) {
//            System.out.println(titles.get(i) + "___________" + Arrays.deepToString(events.get(i)));
//
//        }
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");

        //helper to convert strings to objects
        eventStringToObjectGuide(titles, events);
        System.out.println("Loading Complete");
        notifyL();
    }

    /**
     * splits a string by its spaces and returns an array of strings (used as a helper method)
     *
     * @param splitMe a string to be split
     * @return a String[]
     */
    public static String[] splitBySpace(String splitMe) {
        return splitMe.split(" ");
    }


    private void eventStringToObjectGuide(ArrayList<String> titles, ArrayList<String[]> events) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).length <= 3) {
                eventStringToObject(titles.get(i), events.get(i));
            } else {
                reccuringEventStringToObject(titles.get(i), events.get(i));

            }

        }

    }


    private void eventStringToObject(String titles, String[] events) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = LocalDate.parse(events[0], formatter);
        LocalTime start = LocalTime.parse(events[1], timeFormatter);
        LocalTime end = LocalTime.parse(events[2], timeFormatter);

        Event e = new Event(titles, start, end, date);


        addEvent(e);

    }

    private void reccuringEventStringToObject(String titles, String[] events) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String days = events[0];

        LocalTime startTime = LocalTime.parse(events[1], timeFormatter);
        LocalTime endTime = LocalTime.parse(events[2], timeFormatter);

        LocalDate startdate = LocalDate.parse(events[3], formatter);
        LocalDate enddate = LocalDate.parse(events[4], formatter);

        ReccuringEvent e = new ReccuringEvent(titles, days, startTime, endTime, startdate, enddate);


        addReccuringEvent(e);


    }

    /**
     * called by MyCalendarTester
     *
     * @param name  the name of the event
     * @param start the start time of the event
     * @param end   end time of event
     * @param day   the day the event takes place
     * @return if the event could be added or not
     */
    public boolean addNewEventFromUser(String name, LocalTime start, LocalTime end, LocalDate day) {
        if (names.contains(name)) {
            System.out.println("That name is already being used");
            return false;
        }
        Event e = new Event(name, start, end, day);
        if (canAdd(e)) {
            addEvent(e);


            return true;
        } else {
            return false;
        }


    }

    /**
     * a method called in MyCalendarTester that is called under the condition that the user entered a valid event to be added to the calendar in
     * the add event from user method , under this conditon we add the string of details to our events.txt
     *
     * @param name
     * @param eventInfo
     */
    public void addToFile(String name, String eventInfo) {
        try {

            File myObj = new File("events.txt");

            BufferedWriter out = new BufferedWriter(
                    new FileWriter(myObj, true));

            out.write(name);
            out.newLine();
            out.write(eventInfo);
            out.newLine();
            out.close();
        } catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }


    private void addEvent(Event e) {

        if (Events.keySet().size() < 1) {


            ArrayList<Event> ar = new ArrayList<>();
            ar.add(e);
            Events.put(e.getDay(), ar);
        } else if (!Events.keySet().contains(e.getDay())) {
            ArrayList<Event> ar = new ArrayList<>();
            ar.add(e);
            Events.put(e.getDay(), ar);
        } else if (canAdd(e)) {
            this.Events.get(e.getDay()).add(e);
        } else {
            return;
        }
    }

    private void addReccuringEvent(ReccuringEvent e) {

        for (DayOfWeek d : e.getWeekDayRep()) {
            if (!reccuringEvents.containsKey(d)) {
                ArrayList<ReccuringEvent> ar = new ArrayList<>();
                ar.add(e);
                reccuringEvents.put(d, ar);

            } else {
                reccuringEvents.get(d).add(e);

            }
        }
    }

    private boolean canAdd(Event e) {
        Collection<ArrayList<ReccuringEvent>> a = reccuringEvents.values();

        Collection<DayOfWeek> d = reccuringEvents.keySet();

        ArrayList<Event> eventVals = Events.get(e.getDay());
        if (eventVals != null) {
            for (Event ev : eventVals) {
                if (e.getTimeInterval().overlapsWith(ev.getTimeInterval())) {
                    return false;
                }
            }
        }

        DayOfWeek eDayOfWeek = e.getDay().getDayOfWeek();
        ArrayList<ReccuringEvent> ReccuringEventVals = reccuringEvents.get(eDayOfWeek);
        if (ReccuringEventVals != null) {
            for (ReccuringEvent re : ReccuringEventVals) {
                if (e.getTimeInterval().overlapsWith(re.getTimeInterval()) && d.contains(e.getDay().getDayOfWeek()) && e.getDay().isBefore(re.getEndDate())) {
                    return false;
                }
            }

        }


        return true;
    }

    /**
     * a method for deleting all one time events on a given date
     *
     * @param d
     * @return returns true if successful and false if not
     */
    public boolean deleteAllEventsOnDate(LocalDate d) {
        if (Events.containsKey(d)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
            ArrayList<Event> evs = Events.get(d);
            HashSet<String> stringsToBeDeletedFromFile = new HashSet<>();
            for (Event e : evs) {

                stringsToBeDeletedFromFile.add(e.getName());
                String eventInfo = e.getDay().format(formatter) + " " + e.getTimeInterval().getStart() + " " + e.getTimeInterval().getEnd();
                stringsToBeDeletedFromFile.add(eventInfo);

            }
            removeAllFromFile(stringsToBeDeletedFromFile);
            return true;
        }
        return false;


    }

    private static void removeAllFromFile(HashSet<String> stringsToBeDeletedFromFile) {
        try {

            File inputFile = new File("events.txt");
            File tempFile = new File("myTempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (stringsToBeDeletedFromFile.contains(trimmedLine)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch (IOException e) {
            System.out.println("exception occoured" + e);

        }
    }

    /**
     * method for deleting a specific one time event on a given date with a given name
     *
     * @param d    the day of the event to be deleted
     * @param name the name of the event to be deleted
     * @return true if deleted , false if not
     */
    public boolean deleteOneTimeEvent(LocalDate d, String name) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        ArrayList<Event> evs = Events.get(d);
        for (Event e : evs) {
            if (e.getName().equals(name)) {
                String eventInfo = e.getDay().format(formatter) + " " + e.getTimeInterval().getStart().toString() + " " + e.getTimeInterval().getEnd().toString();
                removeFromFile(name, eventInfo);


                return true;
            }
        }
        return false;

    }

    private static void removeFromFile(String name, String eventInfo) {
        try {

            File inputFile = new File("events.txt");
            File tempFile = new File("myTempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(name) || trimmedLine.equals(eventInfo)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch (IOException e) {
            System.out.println("exception occoured" + e);

        }
    }

    /**
     * deletes a reoccurring event of a given name
     *
     * @param name the name of the event
     * @return true if deleted
     */
    public boolean deleteRecurringEvent(String name) {

        if (names.contains(name)) {
            HashSet<ReccuringEvent> hs = bucketOfRecurringEvents();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");

            HashSet<String> toBeDeleted = new HashSet<>();
            for (ReccuringEvent re : hs) {
                if (re.getName().equals(name)) {
                    toBeDeleted.add(re.getName());
                    String info = re.getWeekDayCharacters() + " " + re.getTimeInterval().getStart() + " " + re.getTimeInterval().getEnd() + " " + re.getStartDate().format(formatter) + " " + re.getEndDate().format(formatter);
                    toBeDeleted.add(info);


                }
            }
            removeAllFromFile(toBeDeleted);
            return true;
        }
        return false;

    }


    /**
     * method for creating the output.txt when the program exits
     * precondition: the user enters q for quit
     * postcondition: output.txt is created
     */
    public void terminate() {
        try {

            File inputFile = new File("events.txt");
            File tempFile = new File("output.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {

                String trimmedLine = currentLine.trim();

                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

        } catch (IOException e) {
            System.out.println("exception occoured" + e);

        }

    }

    public static void main(String[] args) {
        System.out.println("start");
        MyCalendar c = new MyCalendar();

        c.readFromFile();
        System.out.println(c.Events);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(c.reccuringEvents);

    }

}
