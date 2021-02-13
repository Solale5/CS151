import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReccuringEvent {
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

    public String toString() {
        return "ReccuringEvent{" + name.toUpperCase() +
                " StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                ", weekdays='" + weekdays + '\'' +
                ", weekDayRep=" + weekDayRep +
                ", timeInterval=" + timeInterval.start + "-" + timeInterval.end +
                '}';
    }
}
