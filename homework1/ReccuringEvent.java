import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * class responsible for creating events that occur over multiple days
 *
 * @author Solomon Alemu *
 * @version 1.0 2/16/21
 */
public class ReccuringEvent implements Comparable<ReccuringEvent> {
    // IVs for reccuring
    private String name;
    private LocalDate StartDate;
    private LocalDate EndDate;

    private String wkdys;
    private String weekdays = "MTWRFAS";
    private ArrayList<DayOfWeek> weekDayRep;
    private TimeInterval timeInterval;

    /**
     * @param name
     * @param wkdays
     * @param start
     * @param end
     * @param StartDay
     * @param EndDay
     */
    public ReccuringEvent(String name, String wkdays, LocalTime start, LocalTime end, LocalDate StartDay, LocalDate EndDay) {
        weekDayRep = new ArrayList<>();
        this.timeInterval = new TimeInterval(start, end);
        this.name = name;
        this.wkdys = wkdays;
        this.StartDate = StartDay;
        this.EndDate = EndDay;
        char[] a = wkdays.toUpperCase().toCharArray();
        for (char d : a) {
            this.weekDayRep.add(DayOfWeek.of(weekdays.indexOf(d) + 1));
        }
    }

    /**
     * gets the days of the week that this event occurs
     *
     * @return an array of all the days an event takes place
     */
    public ArrayList<DayOfWeek> getWeekDayRep() {
        return weekDayRep;
    }


    /**
     * gets this time interval
     *
     * @return time interval of this event
     */
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    /**
     * returns the event name
     *
     * @return a string
     */
    public String getName() {
        return name;
    }

    /**
     * gets the start date
     *
     * @return a LocalDate object
     */
    public LocalDate getStartDate() {
        return StartDate;
    }

    /**
     * gets the end date
     *
     * @return a LocalDate object
     */
    public LocalDate getEndDate() {
        return EndDate;
    }

    /**
     * returns a string of the first characters that represent the weekdays that an event takes place
     *
     * @return a string of the first characters that represent the weekdays that an event takes place
     */
    public String getWeekDayCharacters() {
        return wkdys;
    }

    /**
     * compares two recurring events
     *
     * @param that event that this is being compared to
     * @return -1,0, or 1 depending chronological order
     */
    public int compareTo(ReccuringEvent that) {
        LocalDateTime startTime1 = LocalDateTime.of(this.StartDate, this.timeInterval.getStart());
        LocalDateTime startTime2 = LocalDateTime.of(that.StartDate, that.timeInterval.getStart());
        if (startTime1.compareTo(startTime2) != 0) {
            return startTime1.compareTo(startTime2);
        } else if (this.weekDayRep.get(0).compareTo(weekDayRep.get(0)) != 0) {
            return this.weekDayRep.get(0).compareTo(weekDayRep.get(0));
        } else {
            return this.name.compareTo(that.name);
        }

    }

    /**
     * returns event information
     *
     * @return a String
     */
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        return "" + name +
                " " + StartDate.format(formatter) +
                " to " + EndDate.format(formatter) +
                ", on these weekdays " +
                " " + weekDayRep +
                " from " + timeInterval.getStart() + "-" + timeInterval.getEnd();
    }
}
