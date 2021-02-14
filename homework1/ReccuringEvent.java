import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReccuringEvent implements Comparable<ReccuringEvent> {
    // IVs for reccuring
    String name;
    LocalDate StartDate;
    LocalDate EndDate;

    String weekdays = "MTWRFAS";
    ArrayList<DayOfWeek> weekDayRep;
    TimeInterval timeInterval;

    public ReccuringEvent(String name, String wkdays, LocalTime start, LocalTime end, LocalDate StartDay, LocalDate EndDay) {
        weekDayRep = new ArrayList<>();
        this.timeInterval = new TimeInterval(start, end);
        this.name = name;
        this.StartDate = StartDay;
        this.EndDate = EndDay;
        char[] a = wkdays.toUpperCase().toCharArray();
        for (char d : a) {
            this.weekDayRep.add(DayOfWeek.of(weekdays.indexOf(d) + 1));
        }
    }

    public int compareTo(ReccuringEvent that) {
        LocalDateTime startTime1 = LocalDateTime.of(this.StartDate, this.timeInterval.start);
        LocalDateTime startTime2 = LocalDateTime.of(that.StartDate, that.timeInterval.start);
        if (startTime1.compareTo(startTime2) != 0) {
            return startTime1.compareTo(startTime2);
        } else if (this.weekDayRep.get(0).compareTo(weekDayRep.get(0)) != 0) {
            return this.weekDayRep.get(0).compareTo(weekDayRep.get(0));
        } else {
            return this.name.compareTo(that.name);
        }

    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        return "" + name +
                " " + StartDate.format(formatter) +
                " to " + EndDate.format(formatter) +
                ", on these weekdays " +
                " " + weekDayRep +
                " from " + timeInterval.start + "-" + timeInterval.end;
    }
}
