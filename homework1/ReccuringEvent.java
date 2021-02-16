import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReccuringEvent implements Comparable<ReccuringEvent> {
    // IVs for reccuring
    private String name;
    private LocalDate StartDate;
    private LocalDate EndDate;

    private String wkdys;
    String weekdays = "MTWRFAS";
    ArrayList<DayOfWeek> weekDayRep;
    TimeInterval timeInterval;

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

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public String getWeekDayCharacters() {
        return wkdys;
    }

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
