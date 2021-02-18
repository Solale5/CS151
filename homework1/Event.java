import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * class responsible for creating events
 *
 * @author Solomon Alemu *
 * @version 1.0 2/16/21
 */
public class Event implements Comparable<Event> {
    private String name;
    private TimeInterval timeInterval;
    private LocalDate day;


    /**
     * constructor for non recurring events
     *
     * @param name  is the name
     * @param start is the start time so that a time interval can be created
     * @param end   is the ending time
     * @param day   is the day of the one time event
     */
    public Event(String name, LocalTime start, LocalTime end, LocalDate day) {

        this.timeInterval = new TimeInterval(start, end);
        this.name = name;
        this.day = day;

    }

    /**
     * method for retrieving this events name
     *
     * @return the events name
     */
    public String getName() {
        return name;
    }

    /**
     * method for getting this events day of occurrence
     *
     * @return this date
     */
    public LocalDate getDay() {
        return day;
    }

    /**
     * gets this events time interval
     *
     * @return the time interval
     */
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    /**
     * a method that compares two time events
     *
     * @param that is the event that this is being compared to
     * @return returns  -1, 0, or 1 depending on the specified order of when the events take place
     */
    public int compareTo(Event that) {
        LocalDateTime startTime1 = LocalDateTime.of(this.day, this.timeInterval.getStart());
        LocalDateTime startTime2 = LocalDateTime.of(that.day, that.timeInterval.getStart());
        if (startTime1.compareTo(startTime2) != 0)
            return startTime1.compareTo(startTime2);
        return this.name.compareTo(that.name);
    }

    /**
     * formats the event
     *
     * @return a string of event info
     */
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        return "" + name +
                " " + day.format(formatter) +
                " " +
                " from " + timeInterval.getStart() + "-" + timeInterval.getEnd();
    }

    public static void main(String[] args) {

    }
}
