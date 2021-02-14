import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MyCalendar {

    //regular events
    TreeMap<LocalDate, ArrayList<Event>> Events;

    //reccuring events
    Hashtable<DayOfWeek, ArrayList<ReccuringEvent>> reccuringEvents;

    public MyCalendar() {
        Events = new TreeMap<>();
        reccuringEvents = new Hashtable<>();

    }

    //fix so it is in the right order
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
                if (!d.isBefore(re.StartDate) && !d.isAfter(re.EndDate)) {
                    s += re.toString() + "\n";
                }
            }
        }


        return s;

    }

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

    public boolean addNewEventFromUser(String name, LocalTime start, LocalTime end, LocalDate day) {
        Event e = new Event(name, start, end, day);
        if (canAdd(e)) {
            addEvent(e);


            return true;
        } else {
            return false;
        }


    }

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


    public void addEvent(Event e) {
        if (Events.keySet().size() < 1) {


            ArrayList<Event> ar = new ArrayList<>();
            ar.add(e);
            Events.put(e.day, ar);
        } else if (!Events.keySet().contains(e.day)) {
            ArrayList<Event> ar = new ArrayList<>();
            ar.add(e);
            Events.put(e.day, ar);
        } else if (canAdd(e)) {
            this.Events.get(e.day).add(e);
        } else {
            return;
        }
    }

    public void addReccuringEvent(ReccuringEvent e) {

        for (DayOfWeek d : e.weekDayRep) {
            if (!reccuringEvents.containsKey(d)) {
                ArrayList<ReccuringEvent> ar = new ArrayList<>();
                ar.add(e);
                reccuringEvents.put(d, ar);

            } else {
                reccuringEvents.get(d).add(e);

            }
        }
    }

    public boolean canAdd(Event e) {
        Collection<ArrayList<ReccuringEvent>> a = reccuringEvents.values();

        Collection<DayOfWeek> d = reccuringEvents.keySet();

        ArrayList<Event> eventVals = Events.get(e.day);
        if (eventVals != null) {
            for (Event ev : eventVals) {
                if (e.timeInterval.overlapsWith(ev.timeInterval)) {
                    return false;
                }
            }
        }

        DayOfWeek eDayOfWeek = e.day.getDayOfWeek();
        ArrayList<ReccuringEvent> ReccuringEventVals = reccuringEvents.get(eDayOfWeek);
        if (ReccuringEventVals != null) {
            for (ReccuringEvent re : ReccuringEventVals) {
                if (e.timeInterval.overlapsWith(re.timeInterval) && d.contains(e.day.getDayOfWeek()) && e.day.isBefore(re.EndDate)) {
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
