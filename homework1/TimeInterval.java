import java.time.LocalTime;

public class TimeInterval {

    private LocalTime start, end;

    public TimeInterval(LocalTime startT, LocalTime endT) {
        this.start = startT;
        this.end = endT;

    }

    public static void sop(Object x) {
        System.out.println(x);
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public LocalTime getEnd() {
        return end;
    }

    public LocalTime getStart() {
        return start;
    }

    public boolean overlapsWith(TimeInterval that) {
        LocalTime startTime1 = this.getStart();
        LocalTime endTime1 = this.getEnd();
        LocalTime startTime2 = that.getStart();
        LocalTime endTime2 = that.getEnd();

        if ((startTime1.isBefore(endTime2) && endTime1.isAfter(startTime2))
                || (startTime2.isBefore(endTime1) && endTime2.isAfter(startTime1))) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        LocalTime time1 = LocalTime.of(10, 43, 12);
        LocalTime time2 = LocalTime.of(10, 43, 13);
        LocalTime three = LocalTime.now();

        TimeInterval one = new TimeInterval(time1, time2);
        TimeInterval two = new TimeInterval(time1, time2);
        System.out.println(one.overlapsWith(two));
        sop(three);

    }
}
