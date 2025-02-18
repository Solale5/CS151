import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * class responsible for interacting with user and running the program
 *
 * @author Solomon Alemu * @version 1.0 2/16/21
 */
public class MyCalendarTester {

    /**
     * shorthand i use for printing an object on a new line
     *
     * @param x any object
     */
    public static void sop(Object x) {
        System.out.println(x);
    }

    /**
     * shorthand i use for printing an object on the same line
     *
     * @param x any object
     */
    public static void sopl(Object x) {
        System.out.print(x);
    }

    public static void main(String[] args) {
        System.out.println("Loading...");
        LocalDate cal = LocalDate.now();
        MyCalendar c = new MyCalendar();
        c.readFromFile();
        printCalendar(cal, c);

        Scanner sc = new Scanner(System.in);

        menu(sc, cal, c);


    }

    /**
     * menu method that appears at the start of execution
     *
     * @param sc  the scanner that reads user input
     * @param cal the current date
     * @param c   the current calendar object
     */
    public static void menu(Scanner sc, LocalDate cal, MyCalendar c) {
        sop("[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("v") || input.equals("V")) {
                view(sc, cal, c);
                break;
            } else if (input.equals("C") || input.equals("c")) {
                create(sc, cal, c);
                break;
            } else if (input.equals("g") || input.equals("G")) {
                goTo(sc, cal, c);
                break;
            } else if (input.equals("e") || input.equals("E")) {
                eventList(sc, cal, c);
                break;
            } else if (input.equals("d") || input.equals("D")) {
                delete(sc, cal, c);
                break;
            } else if (input.equals("q") || input.equals("Q")) {
                c.terminate();
                sop("GoodBye");
                return;
            } else {
                sop("Good Bye");
                break;
            }
        }
    }

    /**
     * user instructions for deleting an event
     *
     * @param sc  the scanner that reads user input
     * @param cal the current date
     * @param c   the current calendar object
     */
    public static void delete(Scanner sc, LocalDate cal, MyCalendar c) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
            sop("[S]elected  [A]ll   [DR] ");

            while (sc.hasNextLine()) {


                String input = sc.nextLine();
                if (input.equals("S") || input.equals("s")) {

                    sop("Enter Date:");
                    String date = sc.nextLine();
                    LocalDate dateObject = LocalDate.parse(date, formatter);
                    sop(c.getEventsOnGivenDay(dateObject));
                    sop("Enter the event name:");
                    String name = sc.nextLine();
                    if (c.deleteOneTimeEvent(dateObject, name)) {
                        c.readFromFile();
                        sop("deleted");
                    } else {
                        sop("no event found with that name and date");
                    }
                    menu(sc, cal, c);
                    break;


                } else if (input.equals("A") || input.equals("a")) {
                    sop("Enter Date:");
                    String date = sc.nextLine();
                    LocalDate dateObject2 = LocalDate.parse(date, formatter);
                    if (c.deleteAllEventsOnDate(dateObject2)) {
                        c.readFromFile();
                        sop("deleted");
                    } else {
                        sop("no event found with date");
                    }
                    menu(sc, cal, c);
                    break;


                } else if (input.equals("DR") || input.equals("dr")) {
                    sop("Enter the name of the recurring event:");
                    String name = sc.nextLine();
                    if (c.deleteRecurringEvent(name)) {
                        c.readFromFile();
                        sop("deleted");
                    } else {
                        sop("no event found with name");
                    }
                    menu(sc, cal, c);
                    break;
                } else {
                    return;
                }
            }


        } catch (Exception e) {
            sop("invalid format");
            
            menu(sc, cal, c);
        }
    }

    /**
     * prints out the list of all events in the calender
     *
     * @param sc  the scanner that reads user input
     * @param cal the current date
     * @param c   the current calendar object
     */
    public static void eventList(Scanner sc, LocalDate cal, MyCalendar c) {
        sop(c.listAllEvents());
        menu(sc, cal, c);
    }

    /**
     * goes to a given date in the calendar
     *
     * @param sc  the scanner that reads user input
     * @param cal the current date
     * @param c   the current calendar object
     */
    public static void goTo(Scanner sc, LocalDate cal, MyCalendar c) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
            sop("Enter a date to view its events M/D/YY");
            String date = sc.nextLine();
            LocalDate dateObject = LocalDate.parse(date, formatter);
            if (c.getEventsOnGivenDay(dateObject).length() < 2) {
                sop("No events scheduled");
            } else {
                sop(c.getEventsOnGivenDay(dateObject));
            }

            menu(sc, cal, c);

        } catch (Exception e) {
            sop("invalid format");
            menu(sc, cal, c);
        }
    }

    /**
     * menu for creating an event in the calendar
     *
     * @param sc  the scanner that reads user input
     * @param cal the current date
     * @param c   the current calendar object
     */
    public static void create(Scanner sc, LocalDate cal, MyCalendar c) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

            sop("Enter a Name");
            String name = sc.nextLine();
            if (c.getNames().contains(name)) {
                sop("the name entered is already being used");
                menu(sc, cal, c);


            }

            sop("Enter a date  M/D/YY");
            String date = sc.nextLine();
            LocalDate dateObject = LocalDate.parse(date, formatter);

            sop("Enter Start Time: 24 hour clock such as 06:00 for 6 AM and 15:30 for 3:30 PM");
            String startTime = sc.nextLine();
            LocalTime startTimeObject = LocalTime.parse(startTime, timeFormatter);

            sop("Enter End Time");
            String endTime = sc.nextLine();
            LocalTime endTimeObject = LocalTime.parse(endTime, timeFormatter);


            if (c.addNewEventFromUser(name, startTimeObject, endTimeObject, dateObject)) {
                sop("added");
                c.addToFile(name, date + " " + startTimeObject.toString() + " " + endTimeObject.toString());
                c.readFromFile();
            } else {
                sop("there is already an event scheduled for this time and date");
            }
            menu(sc, cal, c);

        } catch (Exception e) {
            sop("Invalid format");
            menu(sc, cal, c);

        }

    }

    /**
     * menu for viewing by month or by day
     *
     * @param sc  the scanner that reads user input
     * @param cal the current date
     * @param c   the current calendar object
     */
    public static void view(Scanner sc, LocalDate cal, MyCalendar c) {
        sop("[D]ay view or [M]onth view ? ");

        while (sc.hasNextLine()) {


            String input = sc.nextLine();
            if (input.equals("m")) {

                monthView(sc, cal, c);
                break;

            } else if (input.equals("d")) {
                dayView(sc, cal, c);
                break;


            } else if (input.equals("g")) {
                menu(sc, cal, c);
            } else {
                return;
            }
        }

    }

    /**
     * this method is called in the dayView method only, while looping over recurring events for a given day
     *
     * @param test Local date object to be tested to see if it is in the range of a recurring event s
     * @param s    A recurring event
     * @return returns true if the event is within the range of the local event
     */
    private static boolean isInRange(LocalDate test, ReccuringEvent s) {
        return !test.isBefore(s.getStartDate()) && !test.isAfter(s.getEndDate());

    }

    /**
     * menu for viewing by day
     *
     * @param sc  the scanner that reads user input
     * @param cal the current date
     * @param c   the current calendar object
     */
    public static void dayView(Scanner sc, LocalDate cal, MyCalendar c) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
        LocalDate test = cal;
        System.out.println(" " + formatter.format(test));


        Collection<LocalDate> dates = c.getEvents().keySet();
        Collection<DayOfWeek> weekDays = c.getReccuringEvents().keySet();
        if (dates.contains(test)) {
            ArrayList<Event> events = c.getEvents().get(test);
            for (Event s : events) {
                System.out.println(s.getName() + " " + s.getTimeInterval().getStart() + "-" + s.getTimeInterval().getEnd());
            }
        }

        if (weekDays.contains(test.getDayOfWeek())) {

            TreeSet<ReccuringEvent> ts = new TreeSet<>();
            ArrayList<ReccuringEvent> recEvents = c.getReccuringEvents().get(test.getDayOfWeek());
            ts.addAll(recEvents);

            for (ReccuringEvent s : ts) {
                System.out.println(s.getName() + " " + s.getTimeInterval().getStart() + "-" + s.getTimeInterval().getEnd());
            }
        }


        sop("[P]revious or [N]ext or [G]o back to the main menu");

        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if (input.equals("p")) {

                test = test.minusDays(1);
                System.out.println(" " + formatter.format(test));
                if (dates.contains(test)) {
                    ArrayList<Event> events = c.getEvents().get(test);
                    for (Event s : events) {
                        System.out.println(s.getName() + " " + s.getTimeInterval().getStart() + "-" + s.getTimeInterval().getEnd());
                    }


                }
                if (weekDays.contains(test.getDayOfWeek())) {
                    TreeSet<ReccuringEvent> ts = new TreeSet<>();
                    ArrayList<ReccuringEvent> recEvents = c.getReccuringEvents().get(test.getDayOfWeek());
                    for (ReccuringEvent s : recEvents) {
                        if (isInRange(test, s)) {
                            ts.add(s);
                        }
                    }
                    for (ReccuringEvent re : ts) {
                        System.out.println(re.getName() + " " + re.getTimeInterval().getStart() + "-" + re.getTimeInterval().getEnd());
                    }
                }


                sop("[P]revious or [N]ext or [G]o back to the main menu");
            } else if (input.equals("n")) {
                test = test.plusDays(1);
                System.out.println(" " + formatter.format(test));
                if (dates.contains(test)) {
                    ArrayList<Event> events = c.getEvents().get(test);
                    for (Event s : events) {
                        System.out.println(s.getName() + " " + s.getTimeInterval().getStart() + "-" + s.getTimeInterval().getEnd());
                    }
                }
                if (weekDays.contains(test.getDayOfWeek())) {
                    TreeSet<ReccuringEvent> ts = new TreeSet<>();
                    ArrayList<ReccuringEvent> recEvents = c.getReccuringEvents().get(test.getDayOfWeek());
                    for (ReccuringEvent s : recEvents) {
                        if (isInRange(test, s)) {
                            ts.add(s);
                        }
                    }
                    for (ReccuringEvent re : ts) {
                        System.out.println(re.getName() + " " + re.getTimeInterval().getStart() + "-" + re.getTimeInterval().getEnd());
                    }
                }

                sop("[P]revious or [N]ext or [G]o back to the main menu");
            } else if (input.equals("g")) {
                menu(sc, cal, c);
                break;

            } else {

                return;
            }

        }
    }

    /**
     * menu for viewing by month
     *
     * @param sc  the scanner that reads user input
     * @param cal the current date
     * @param c   the current calendar object
     */
    public static void monthView(Scanner sc, LocalDate cal, MyCalendar c) {
        printCalendar(cal, c);
        sop("[P]revious or [N]ext or [G]o back to the main menu");
        LocalDate test = cal;
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("p")) {
                test = test.minusMonths(1);
                printCalendar(test, c);
                sop("[P]revious or [N]ext or [G]o back to the main menu");

            } else if (input.equals("n")) {
                test = test.plusMonths(1);
                printCalendar(test, c);
                sop("[P]revious or [N]ext or [G]o back to the main menu");
            } else if (input.equals("g")) {
                menu(sc, cal, c);
                break;

            } else {

                return;
            }
        }
    }

    /**
     * @param todaydate the current date
     * @param c         the current calendar object
     */
    public static void printCalendar(LocalDate todaydate, MyCalendar c) {
        //store local date
        int dayNum = todaydate.getDayOfMonth();
        Collection<LocalDate> dates = c.getEvents().keySet();
        Collection<DayOfWeek> weekDays = c.getReccuringEvents().keySet();


        HashSet<Integer> dateNums = new HashSet<>();
        for (LocalDate d : dates) {
            if (d.getMonth().equals(todaydate.getMonth())) {
                dateNums.add(d.getDayOfMonth());
            }
        }


        Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
        ht.put("SUNDAY", 0);
        ht.put("MONDAY", 1);
        ht.put("TUESDAY", 2);
        ht.put("WEDNESDAY", 3);
        ht.put("THURSDAY", 4);
        ht.put("FRIDAY", 5);
        ht.put("SATURDAY", 6);

// gets first day of the month as a date variable
        LocalDate first = todaydate.withDayOfMonth(1);

//prints the first day of the month code ex 1 => Monday 2=> tuesday


//gets last day of the month

        int res = todaydate.lengthOfMonth();


        //gets the day of the week that falls on the first of the month (ex feb 2021 first day is monday) and returns it as a code
        int firstdayofmonthcode = ht.get(first.getDayOfWeek() + "");


        sop(todaydate.getMonth() + " " + todaydate.getDayOfMonth() + " " + todaydate.getYear());
        //gets date abbreviations
        DateFormatSymbols dfs = new DateFormatSymbols();
        // stores abbreviations in an array
        String[] days = dfs.getShortWeekdays();
        //prints days
        for (String string : days) {
            sopl(string + " ");
        }
        //counter for days
        int l = 0;
        sop("");
        //spaces array based on what the first day of the month is
        String[] spaces = {" ", "     ", "         ", "             ", "                 ", "                     ", "                         "};

        //loop responsible for printing the first row
        LocalDate testerone = todaydate.withDayOfMonth(1);
        for (int i = 0; i < 8 - firstdayofmonthcode; i++) {
            if (i != 0) {
                testerone = todaydate.withDayOfMonth(i);
            }
            if (i == 0) {
                sopl(spaces[firstdayofmonthcode] + "");
            } else if (i == dayNum || dateNums.contains(i) || weekDays.contains(testerone.getDayOfWeek()) && isInRange(testerone, c)) {
                l += 1;
                sopl("[" + i + "]" + " ");

            } else {
                l += 1;
                sopl(l + "   ");
            }


        }

        sop("");
        //loop responsible for printing the remaining rows
        for (int i = 8 - firstdayofmonthcode; i <= res; i++) {
            LocalDate tester = todaydate.withDayOfMonth(i);

            if (i - (8 - firstdayofmonthcode) == 7 || i - (8 - firstdayofmonthcode) == 14 || i - (8 - firstdayofmonthcode) == 21 || i - (8 - firstdayofmonthcode) == 28) {
                sop("");
            }
            if (i == dayNum || dateNums.contains(i) || weekDays.contains(tester.getDayOfWeek()) && isInRange(tester, c)) {
                sopl("[" + i + "]" + " ");
            } else if (i == 8 - firstdayofmonthcode) {
                sopl(" " + i + "   ");
            } else if (i == 9 || i == 10) {
                sopl(i + "  ");
            } else if (i > 10) {
                sopl(i + "  ");

            } else {
                sopl(i + "   ");
            }


        }
        sop("");


    }

    private static boolean isInRange(LocalDate test, MyCalendar c) {
        Collection<ArrayList<ReccuringEvent>> re = c.getReccuringEvents().values();
        for (ArrayList<ReccuringEvent> ar : re) {
            for (ReccuringEvent s : ar) {
                return !test.isBefore(s.getStartDate()) && !test.isAfter(s.getEndDate());
            }
        }

        return false;


    }

}


