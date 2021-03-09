import java.util.ArrayList;

public class Reservation {

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


    public char getServiceClass() {
        return serviceClass;
    }

    public String[] getNameArray() {
        return name;
    }

    public char getSeatPreference() {
        return seatPreference;
    }


    public String getGroupName() {
        return groupName;
    }

    public String[] getNames() {
        return names;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public static void sopl(Object x) {
        System.out.println(x);
    }
}

