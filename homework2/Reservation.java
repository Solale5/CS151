import java.util.ArrayList;

/**
 * class responsible for creating reservation objects
 *
 * @author Solomon Alemu
 * @version 1.0 3/9/21
 */
public class Reservation {
    //constants
    static final char firstClass = 'F';
    static final char economy = 'E';
    // instance variables for individual reservations
    private char serviceClass;
    private String[] name;
    private char seatPreference;
    static final char window = 'W';
    static final char aisle = 'A';
    static final char middle = 'M';
    static final char none = 'N';


    // instance variables for group reservations
    private String groupName;
    private String[] names;
    private int size;
    private ArrayList<Reservation> reservations;

    /**
     * constructor for single person reservations
     *
     * @param theirName           the name of the individual
     * @param theirClass          the class of the person (first or economy)
     * @param theirSeatPreference the seat preference of the person
     */
    public Reservation(String theirName, String theirClass, char theirSeatPreference) {
        name = theirName.split(" ");
        groupName = null;
        serviceClass = theirClass.toUpperCase().charAt(0);
        if (serviceClass != firstClass && serviceClass != economy) {
            String error = serviceClass + " is not a valid class";
            throw new IllegalArgumentException(error);
        }
        seatPreference = theirSeatPreference;
        if (theirSeatPreference != window && theirSeatPreference != aisle && theirSeatPreference != middle && theirSeatPreference != none) {
            String error = serviceClass + " is not a valid seat preference, remember to make it capital";
            throw new IllegalArgumentException(error);
        }

    }

    /**
     * constructor for the group reservation feature
     *
     * @param theirGroupName is the name of the group
     * @param theirNames     are there names seperated by commas
     * @param theirClass     their service class (first or economy)
     */
    public Reservation(String theirGroupName, String theirNames, String theirClass) {
        groupName = theirGroupName;
        names = theirNames.split(",");
        size = names.length;
        serviceClass = theirClass.toUpperCase().charAt(0);
        if (serviceClass != firstClass && serviceClass != economy) {
            String error = serviceClass + " is not a valid class";
            throw new IllegalArgumentException(error);
        }
        reservations = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Reservation r = new Reservation(names[i], theirClass, 'N');
            r.groupName = theirGroupName;
            reservations.add(r);
        }


    }

    // main method
    public static void main(String[] args) {
        //single object test
        Reservation sol = new Reservation("Solomon Alemu", "First", 'W');

        sopl(sol.name[0]);
        sopl(sol.name[1]);
        sopl(sol.serviceClass);
        sopl(sol.seatPreference);

        sopl(" ");
        //group object test
        Reservation msb = new Reservation("MSB", "Solomon Alemu,Mark Wishom,Eduardo Sanchez", "First");
        sopl(msb.serviceClass);
        sopl(msb.groupName);
        sopl(msb.size);
        sopl(msb.reservations.get(0).name[0]);
        sopl(msb.reservations.get(1).name[0]);
        sopl(msb.reservations.get(2).name[0]);


    }

    /**
     * gets this service class
     *
     * @return a char
     */
    public char getServiceClass() {
        return serviceClass;
    }

    /**
     * gets the array containing the first and last name of a person at indexes 0 and 1
     *
     * @return a String[]
     */
    public String[] getNameArray() {
        return name;
    }

    /**
     * returns an individuals seat preference
     *
     * @return a char
     */
    public char getSeatPreference() {
        return seatPreference;
    }

    /**
     * gets the name of a group
     *
     * @return a String
     */
    public String getGroupName() {
        return groupName;
    }


    /**
     * returns size of group reservation
     *
     * @return an int
     */
    public int getSize() {
        return size;
    }

    /**
     * returns an arraylist of reservations for this group reservation
     *
     * @return
     */
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    //truncated print method
    public static void sopl(Object x) {
        System.out.println(x);
    }
}

