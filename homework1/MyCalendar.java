import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;


public class MyCalendar {

    //regular events
    TreeMap<LocalDate, ArrayList<Event>> Events;

    //reccuring events
    TreeMap<DayOfWeek, ArrayList<ReccuringEvent>> reccuringEvents;

    public MyCalendar() {
        Events = new TreeMap<>();
        reccuringEvents = new TreeMap<>();

    }


    public void readFromFile() {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String[]> events = new ArrayList<>();

        try {
            File myObj = new File("events.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String title = myReader.nextLine();
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
    }

    public static String[] splitBySpace(String splitMe) {
        return splitMe.split(" ");
    }

    public void eventStringToObjectGuide(ArrayList<String> titles, ArrayList<String[]> events) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).length <= 3) {
                eventStringToObject(titles.get(i), events.get(i));
            } else {
                reccuringEventStringToObject(titles.get(i), events.get(i));

            }

        }

    }

    public void eventStringToObject(String titles, String[] events) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        LocalDate date = LocalDate.parse(events[0], formatter);
        LocalTime start = LocalTime.parse(events[1], timeFormatter);
        LocalTime end = LocalTime.parse(events[2], timeFormatter);

        Event e = new Event(titles, start, end, date);
//        this.addEvent(e);

        addEvent(e);

    }

    public void reccuringEventStringToObject(String titles, String[] events) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        String days = events[0];

        LocalTime startTime = LocalTime.parse(events[1], timeFormatter);
        LocalTime endTime = LocalTime.parse(events[2], timeFormatter);

        LocalDate startdate = LocalDate.parse(events[3], formatter);
        LocalDate enddate = LocalDate.parse(events[4], formatter);

        ReccuringEvent e = new ReccuringEvent(titles, days, startTime, endTime, startdate, enddate);


        addReccuringEvent(e);


    }

    public void addEvent(Event e) {

        if (Events.keySet() == null) {


            ArrayList<Event> ar = new ArrayList<>();
            ar.add(e);
            Events.put(e.day, ar);
        } else if (canAdd(e)) {
            ArrayList<Event> ar = new ArrayList<>();
            ar.add(e);
            Events.put(e.day, ar);
        } else {
            return;
        }
    }

    public void addReccuringEvent(ReccuringEvent e) {

        ArrayList<ReccuringEvent> ar = new ArrayList<>();
        ar.add(e);
        for (DayOfWeek day : e.weekDayRep) {
            reccuringEvents.put(day, ar);

        }

    }

    public boolean canAdd(Event e) {
        Collection<ArrayList<ReccuringEvent>> a = reccuringEvents.values();
        Collection<DayOfWeek> d = reccuringEvents.keySet();
        Collection<LocalDate> l = Events.keySet();

        if (l.contains(e.day)) {
            return false;
        }

        for (ArrayList<ReccuringEvent> ar : a) {
            for (ReccuringEvent r : ar) {
                if (e.timeInterval.overlapsWith(r.timeInterval) && d.contains(e.day.getDayOfWeek()) && e.day.isBefore(r.EndDate)) {
                    return false;
                }
            }

        }


        return true;
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
