import java.time.LocalTime;


/**
 * class responsible for creating time intervals
 *
 * @author Solomon Alemu *
 * @version 1.0 2/16/21
 */
public class TimeInterval {
    // start and end time IVs
    private LocalTime start, end;

    /**
     * constructs a time interval object with a LocalTime Start object and a LocalTime end object
     *
     * @param startT the start time of the interval
     * @param endT   the end time of the interval
     */
    public TimeInterval(LocalTime startT, LocalTime endT) {
        this.start = startT;
        this.end = endT;

    }


    /**
     * returns the end of the interval
     *
     * @return the end of this time interval as a localTime Object
     */
    public LocalTime getEnd() {
        return end;
    }

    /**
     * get the start of a time interval
     *
     * @return the start of this interval as a localTime Object
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * a method that compares two time intervals to see if they overlap
     *
     * @param that is the interval that this is being compared to
     * @return returns true if times overlap
     */
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


}
