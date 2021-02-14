import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event implements Comparable<Event> {
    String name;
    TimeInterval timeInterval;

    LocalDate day;


    /**
     * For non recurring events
     *
     * @param name
     * @param start
     * @param end
     * @param day
     */
    public Event(String name, LocalTime start, LocalTime end, LocalDate day) {

        this.timeInterval = new TimeInterval(start, end);
        this.name = name;
        this.day = day;
    }


    public int compareTo(Event that) {
        LocalDateTime startTime1 = LocalDateTime.of(this.day, this.timeInterval.start);
        LocalDateTime startTime2 = LocalDateTime.of(that.day, that.timeInterval.start);
        if (startTime1.compareTo(startTime2) != 0)
            return startTime1.compareTo(startTime2);
        return this.name.compareTo(that.name);
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        return "" + name +
                " " + day.format(formatter) +
                " " +
                " from " + timeInterval.start + "-" + timeInterval.end;
    }

    public static void main(String[] args) {
        LocalTime time1 = LocalTime.of(10, 43, 12);
        LocalTime time2 = LocalTime.of(10, 43, 13);

        LocalDate date = LocalDate.now();

        String s = "rave";


        Event e = new Event(s, time1, time2, date);
        System.out.println(e.toString());
    }
}
