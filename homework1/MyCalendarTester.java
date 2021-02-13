import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MyCalendarTester {


    public static void sop(Object x) {
        System.out.println(x);
    }

    public static void sopl(Object x) {
        System.out.print(x);
    }

    public static void main(String[] args) {
        System.out.println("Loading...");
        LocalDate cal = LocalDate.now();
        MyCalendar c = new MyCalendar();
        c.readFromFile();

        Scanner sc = new Scanner(System.in);
        menu(sc, cal, c);


    }

    public static void menu(Scanner sc, LocalDate cal, MyCalendar c) {
        sop("[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("v")) {
                view(sc, cal, c);
                break;
            } else if (input.equals("c")) {
                create(sc, cal, c);
            } else if (input.equals("g")) {
            } else if (input.equals("d")) {
            } else if (input.equals("q")) {
                return;
            } else {
                sop("thank you");
                break;
            }
        }
    }

    public static void create(Scanner sc, LocalDate cal, MyCalendar c) {
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
        }
    }

    public static void saveToFile() {

    }

    public static void loadFromFile() {
    }

    public static void view(Scanner sc, LocalDate cal, MyCalendar c) {
        sop("[D]ay view or [M]onth view ? ");

        while (sc.hasNextLine()) {

            boolean lastEntryWasMonth = false;
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
        return !test.isBefore(s.StartDate) && !test.isAfter(s.EndDate);

    }


    public static void dayView(Scanner sc, LocalDate cal, MyCalendar c) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
        System.out.println(" " + formatter.format(cal));
        LocalDate test = cal;

        Collection<LocalDate> dates = c.Events.keySet();
        Collection<DayOfWeek> weekDays = c.reccuringEvents.keySet();
        if (dates.contains(test)) {
            ArrayList<Event> events = c.Events.get(test);
            for (Event s : events) {
                System.out.println(s.name + " " + s.timeInterval.start + "-" + s.timeInterval.end);
            }
        }
        if (weekDays.contains(test.getDayOfWeek())) {
            ArrayList<ReccuringEvent> recEvents = c.reccuringEvents.get(test.getDayOfWeek());
            for (ReccuringEvent s : recEvents) {
                if (isInRange(test, s)) {
                    System.out.println(s.name + " " + s.timeInterval.start + "-" + s.timeInterval.end);
                }
            }
        }


        sop("[P]revious or [N]ext or [G]o back to the main menu");

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("p")) {
                System.out.println(" " + formatter.format(test));
                test = test.minusDays(1);
                if (dates.contains(test)) {
                    ArrayList<Event> events = c.Events.get(test);
                    for (Event s : events) {
                        System.out.println(s.name + " " + s.timeInterval.start + "-" + s.timeInterval.end);
                    }
                }
                if (weekDays.contains(test.getDayOfWeek())) {
                    ArrayList<ReccuringEvent> recEvents = c.reccuringEvents.get(test.getDayOfWeek());
                    for (ReccuringEvent s : recEvents) {
                        if (isInRange(test, s)) {
                            System.out.println(s.name + " " + s.timeInterval.start + "-" + s.timeInterval.end);
                        }
                    }
                }


                sop("[P]revious or [N]ext or [G]o back to the main menu");
            } else if (input.equals("n")) {
                test = test.plusDays(1);
                System.out.println(" " + formatter.format(test));
                if (dates.contains(test)) {
                    ArrayList<Event> events = c.Events.get(test);
                    for (Event s : events) {
                        System.out.println(s.name + " " + s.timeInterval.start + "-" + s.timeInterval.end);
                    }
                }
                if (weekDays.contains(test.getDayOfWeek())) {
                    ArrayList<ReccuringEvent> recEvents = c.reccuringEvents.get(test.getDayOfWeek());
                    for (ReccuringEvent s : recEvents) {
                        if (isInRange(test, s)) {
                            System.out.println(s.name + " " + s.timeInterval.start + "-" + s.timeInterval.end);
                        }
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

    public static void printCalendar(LocalDate todaydate, MyCalendar c) {
        //store local date
        int dayNum = todaydate.getDayOfMonth();
        Collection<LocalDate> dates = c.Events.keySet();
        Collection<DayOfWeek> weekDays = c.reccuringEvents.keySet();


        HashSet<Integer> dateNums = new HashSet<Integer>();
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
        sop(ht.get(first.getDayOfWeek() + ""));

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


            if (i == 8 - firstdayofmonthcode) {
                sopl(" " + i + "   ");
            } else if (i == dayNum || dateNums.contains(i) || weekDays.contains(tester.getDayOfWeek()) && isInRange(tester, c)) {
                sopl("[" + i + "]" + " ");
            } else if (i == 9 || i == 10) {
                sopl(i + "  ");
            } else if (i > 10) {
                sopl(i + "  ");

            } else {
                sopl(i + "   ");
            }


        }
        sop("\n" + "loading is not implemented");


    }

    private static boolean isInRange(LocalDate test, MyCalendar c) {
        Collection<ArrayList<ReccuringEvent>> re = c.reccuringEvents.values();
        for (ArrayList<ReccuringEvent> ar : re) {
            for (ReccuringEvent s : ar) {
                return !test.isBefore(s.StartDate) && !test.isAfter(s.EndDate);
            }
        }

        return false;


    }

}


