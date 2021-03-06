import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class AirplaneSeats {

    private Reservation[][] firstClass;

    private Reservation[][] economy;

    private static HashMap<Integer, String> NumsToLetters;
    private static HashMap<Integer, String> firstClassNumsToLetters;

    File theFile;

    static {
        NumsToLetters = new HashMap<>();
        NumsToLetters.put(5, "A");
        NumsToLetters.put(4, "B");
        NumsToLetters.put(3, "C");
        NumsToLetters.put(2, "D");
        NumsToLetters.put(1, "E");
        NumsToLetters.put(0, "F");

        firstClassNumsToLetters = new HashMap<>();

        firstClassNumsToLetters.put(3, "A");
        firstClassNumsToLetters.put(2, "B");
        firstClassNumsToLetters.put(1, "C");
        firstClassNumsToLetters.put(0, "D");

    }

    public AirplaneSeats(File f) {
        firstClass = new Reservation[4][2];

        economy = new Reservation[6][20];

        theFile = f;
    }

    public AirplaneSeats() {
        firstClass = new Reservation[4][2];

        economy = new Reservation[6][3];


    }

    private void addNewReservationFromUser(Reservation r, int row, int col) {
        firstClass[row][col] = r;

    }

    private void addNewReservationFromUserEconomy(Reservation r, int row, int col) {
        economy[row][col] = r;

    }

    private void addGroupToFirstClass(TreeSet<Integer> ts, Reservation r) {

        ArrayList<Integer> a = new ArrayList<>(ts);
        int i = 0;
        for (int col = 0; col < firstClass[0].length; col++) {
            for (int row = 0; row < firstClass.length; row++) {

                if (firstClass[row][a.get(col)] == null && i < r.getReservations().size()) {
                    firstClass[row][a.get(col)] = r.getReservations().get(i);
                    i++;
                }

            }
        }

    }

    private void addGroupToEconomy(TreeSet<Integer> ts, Reservation r) {
        ArrayList<Integer> a = new ArrayList<>(ts);
        //testing purposes
        for (Integer i : a) {
            sopl(i);
        }
        int i = 0;
        for (int col = 0; col < a.size(); col++) {
            for (int row = 0; row < economy.length; row++) {

                if (economy[row][a.get(col)] == null && i < r.getReservations().size()) {
                    economy[row][a.get(col)] = r.getReservations().get(i);
                    i++;
                }

            }
        }
    }

    public int openSeatsInFirstClass() {
        int count = 0;
        for (int col = 0; col < firstClass[0].length; col++) {

            for (int row = 0; row < firstClass.length; row++) {
                if (firstClass[row][col] == null) {
                    count++;

                }

            }
        }
        return count;
    }

    public int openSeatsInEconomy() {
        int count = 0;
        for (int col = 0; col < economy[0].length; col++) {

            for (int row = 0; row < economy.length; row++) {
                if (economy[row][col] == null) {
                    count++;

                }

            }
        }
        return count;

    }

    public boolean searchForConsecutiveSeats(Reservation r) {
        //if the reservation is for first class execute this block of code
        if (r.getServiceClass() == 'F') {
            if (r.getReservations().size() <= openSeatsInFirstClass()) {
                //array to store the columns number of consecutive empty seats
                int[] emptySeatsInCol = new int[firstClass[0].length];

                for (int col = 0; col < firstClass[0].length; col++) {
                    int count = 0;
                    for (int row = 0; row < firstClass.length; row++) {

                        if (firstClass[row][col] == null) {
                            count++;
                            emptySeatsInCol[col] = count;
                        } else {
                            if (count > 0 && row != 3) {
                                count--;
                            }
                            emptySeatsInCol[col] = count;
                        }
                    }
                }
                //now emptySeatsInCol[] will have the number of consecutive empty seats in each col

                consecutiveEmptySeatsColumnsToIntValuesFC(emptySeatsInCol, r);
                return true;

            } else {
                return false;
            }
            //if the reservation is for economy execute this block of code
        } else {
            if (r.getReservations().size() <= openSeatsInEconomy()) {
                int[] emptySeatsInCol = new int[economy[0].length];
                for (int col = 0; col < economy[0].length; col++) {
                    int count = 0;
                    for (int row = 0; row < economy.length; row++) {

                        if (economy[row][col] == null) {
                            count++;
                            emptySeatsInCol[col] = count;
                        } else {
                            if (count > 0 && row != 5) {
                                count--;
                            }
                            emptySeatsInCol[col] = count;
                        }
                    }
                }
                //now emptySeatsInCol[] will have the number of consecutive empty seats in each col

                consecutiveEmptySeatsColumnsToIntValues(emptySeatsInCol, r);

                return true;
            } else {
                return false;
            }
        }
    }

    private int[] openConsecutiveSeatsArray(Reservation r) {
        int[] emptySeatsInCol;
        //if the reservation is for first class execute this block of code
        if (r.getServiceClass() == 'F') {

            //array to store the columns number of consecutive empty seats
            emptySeatsInCol = new int[firstClass[0].length];

            for (int col = 0; col < firstClass[0].length; col++) {
                int count = 0;
                for (int row = 0; row < firstClass.length; row++) {

                    if (firstClass[row][col] == null) {
                        count++;
                        emptySeatsInCol[col] = count;
                    } else {
                        if (count > 0 && row != 3) {
                            count--;
                        }
                        emptySeatsInCol[col] = count;
                    }
                }
            }
            //now emptySeatsInCol[] will have the number of consecutive empty seats in each col


            return emptySeatsInCol;


            //if the reservation is for economy execute this block of code
        } else {

            emptySeatsInCol = new int[economy[0].length];
            for (int col = 0; col < economy[0].length; col++) {
                int count = 0;
                for (int row = 0; row < economy.length; row++) {

                    if (economy[row][col] == null) {
                        count++;
                        emptySeatsInCol[col] = count;
                    } else {
                        if (count > 0 && row != 5) {
                            count--;
                        }
                        emptySeatsInCol[col] = count;
                    }
                }
            }
            //now emptySeatsInCol[] will have the number of consecutive empty seats in each col


            return emptySeatsInCol;

        }

    }

    private void consecutiveEmptySeatsColumnsToIntValuesFC(int[] emptySeatsInCol, Reservation r) {
        ArrayList<Integer> al = new ArrayList<>();
        TreeSet<Integer> columnIndexes = new TreeSet<>();
        for (Integer i : emptySeatsInCol) {
            al.add(i);
        }
        if (al.contains(r.getSize())) {

            columnIndexes.add(al.indexOf(r.getSize()));
            addGroupToEconomy(columnIndexes, r);

        } else {


            int peopleToBeSeated = r.getReservations().size();
            int largestNumberOfEmptySeats = emptySeatsInCol[0];
            int largestNumberOfEmptySeatscolIndex = 0;
            while (peopleToBeSeated > 0) {
                for (int i = 0; i < emptySeatsInCol.length; i++) {

                    if (emptySeatsInCol[i] > largestNumberOfEmptySeats && !columnIndexes.contains(i)) {
                        largestNumberOfEmptySeats = emptySeatsInCol[i];
                        largestNumberOfEmptySeatscolIndex = i;
                    }
                }
                int colNumber = largestNumberOfEmptySeatscolIndex;
                if ((peopleToBeSeated - largestNumberOfEmptySeats) == 0) {

                    columnIndexes.add(colNumber);
                    addGroupToFirstClass(columnIndexes, r);
                    break;
                } else {
                    columnIndexes.add(colNumber);
                    peopleToBeSeated = peopleToBeSeated - largestNumberOfEmptySeats;

                }
            }
            //call helper
            addGroupToFirstClass(columnIndexes, r);
        }
    }

    private void consecutiveEmptySeatsColumnsToIntValues(int[] emptySeatsInCol, Reservation r) {
        ArrayList<Integer> al = new ArrayList<>();
        TreeSet<Integer> columnIndexes = new TreeSet<>();
        for (Integer i : emptySeatsInCol) {
            al.add(i);
        }
        if (al.contains(r.getSize())) {

            columnIndexes.add(al.indexOf(r.getSize()));
            addGroupToEconomy(columnIndexes, r);

        } else if (al.contains(r.getSize() + 1)) {
            columnIndexes.add(al.indexOf(r.getSize() + 1));
            addGroupToEconomy(columnIndexes, r);
        } else if (al.contains(r.getSize() + 2)) {
            columnIndexes.add(al.indexOf(r.getSize() + 2));
            addGroupToEconomy(columnIndexes, r);
        } else if (al.contains(r.getSize() + 3)) {
            columnIndexes.add(al.indexOf(r.getSize() + 3));
            addGroupToEconomy(columnIndexes, r);
        } else if (al.contains(r.getSize() + 4)) {
            columnIndexes.add(al.indexOf(r.getSize() + 4));
            addGroupToEconomy(columnIndexes, r);
        } else {


            int peopleToBeSeated = r.getReservations().size();
            int largestNumberOfEmptySeats = emptySeatsInCol[0];
            int largestNumberOfEmptySeatscolIndex = 0;
            while (peopleToBeSeated > 0) {
                for (int i = 0; i < emptySeatsInCol.length; i++) {

                    if (emptySeatsInCol[i] > largestNumberOfEmptySeats && !columnIndexes.contains(i)) {
                        largestNumberOfEmptySeats = emptySeatsInCol[i];
                        largestNumberOfEmptySeatscolIndex = i;
                        columnIndexes.add(largestNumberOfEmptySeatscolIndex);

                    }
                }


                int colNumber = largestNumberOfEmptySeatscolIndex;
                if ((peopleToBeSeated - largestNumberOfEmptySeats) == 0) {

                    columnIndexes.add(colNumber);
                    addGroupToEconomy(columnIndexes, r);
                    break;
                } else {
                    //emptySeatsInCol = openConsecutiveSeatsArray(r);
                    peopleToBeSeated = peopleToBeSeated - largestNumberOfEmptySeats;

                    columnIndexes.add(colNumber);

                }
            }
            //call helper
            addGroupToEconomy(columnIndexes, r);
        }


    }


    /**
     * method for searching for open seats for single reservations that passes the reservation to a helper which adds them
     *
     * @param r a Reservation object that needs to be added
     * @return true if added, false if not
     */
    public boolean searchForFirstOpenSeatWithPreference(Reservation r) {
        // if the service class is First then check for seat preference
        if (r.getServiceClass() == 'F') {
            // if seat preference is window we have to search rows 0 and 3 bc those are window seat rows aka D and A
            if (r.getSeatPreference() == 'W') {
                for (int i = 0; i < firstClass[0].length; i++) {
                    if (firstClass[0][i] == null) {
                        addNewReservationFromUser(r, 0, i);
                        return true;
                    }
                }
                for (int i = 0; i < firstClass[3].length; i++) {
                    if (firstClass[3][i] == null) {
                        addNewReservationFromUser(r, 3, i);
                        return true;
                    }
                }
                return false;

            } else {
                // if your seat preference is not window we have to search for a seat that is in rows 1 and 2 aka C and B
                for (int i = 0; i < firstClass[1].length; i++) {
                    if (firstClass[1][i] == null) {
                        addNewReservationFromUser(r, 1, i);
                        return true;
                    }
                }
                for (int i = 0; i < firstClass[2].length; i++) {
                    if (firstClass[2][i] == null) {
                        addNewReservationFromUser(r, 2, i);
                        return true;
                    }
                }
                return false;
            }
        } else {
            // this else block is if you are an economy reservation
            //if you want a window seat we have to check for it in row 0 and 5 aka F and A
            if (r.getSeatPreference() == 'W') {
                for (int i = 0; i < economy[0].length; i++) {
                    if (economy[0][i] == null) {
                        addNewReservationFromUserEconomy(r, 0, i);
                        return true;
                    }
                }
                for (int i = 0; i < economy[5].length; i++) {
                    if (economy[5][i] == null) {
                        addNewReservationFromUserEconomy(r, 5, i);
                        return true;
                    }
                }
                //if you want a middle seat we have to check for it in row 1 and 4 aka E and B
            } else if (r.getSeatPreference() == 'M') {
                for (int i = 0; i < economy[1].length; i++) {
                    if (economy[1][i] == null) {
                        addNewReservationFromUserEconomy(r, 1, i);
                        return true;
                    }
                }

                for (int i = 0; i < economy[4].length; i++) {
                    if (economy[4][i] == null) {
                        addNewReservationFromUserEconomy(r, 4, i);
                        return true;
                    }
                }
                //if you want an aisle seat we have to check for it in row 2 and 3 aka D and C
            } else if (r.getSeatPreference() == 'A') {
                for (int i = 0; i < economy[2].length; i++) {
                    if (economy[2][i] == null) {
                        addNewReservationFromUserEconomy(r, 2, i);
                        return true;
                    }
                }

                for (int i = 0; i < economy[3].length; i++) {
                    if (economy[3][i] == null) {
                        addNewReservationFromUserEconomy(r, 3, i);
                        return true;
                    }
                }
                // case if you do not care where you sit
            } else {
                for (int col = 0; col < economy[0].length; col++) {

                    for (int row = 0; row < economy.length; row++) {

                        if (economy[row][col] == null) {
                            addNewReservationFromUserEconomy(r, row, col);
                            return true;
                        }
                    }

                }
            }

        }
        return false;
    }

    //tests
    public static void main(String[] args) {
        AirplaneSeats a = new AirplaneSeats();
        Reservation placeholder = new Reservation("test", "First", 'W');
        Reservation placeholder2 = new Reservation("test2", "First", 'W');
        a.firstClass[3][1] = placeholder;
        a.firstClass[0][1] = placeholder2;
        //create reservations
        Reservation sol = new Reservation("Solomon Alemu", "First", 'W');
        Reservation wish = new Reservation("Mark Wishom", "First", 'A');
        Reservation ed = new Reservation("eduardo sanchez", "First", 'M');
        Reservation brian = new Reservation("brian le", "First", 'W');
        Reservation noah = new Reservation("noah elena", "First", 'W');


        //add first class reservations to APS object and print if they worked
        sopl(a.searchForFirstOpenSeatWithPreference(sol));
        sopl(a.searchForFirstOpenSeatWithPreference(wish));
        sopl(a.searchForFirstOpenSeatWithPreference(ed));
        sopl(a.searchForFirstOpenSeatWithPreference(brian));
        sopl(a.searchForFirstOpenSeatWithPreference(noah));
        sopl("");
        //print out 2d array of seats
        for (int row = 0; row < a.firstClass.length; row++) {
            for (int col = 0; col < a.firstClass[row].length; col++) {
                if (a.firstClass[row][col] != null) {
                    sop(col + 1 + firstClassNumsToLetters.get(row) + " " + a.firstClass[row][col].getNameArray()[0] + " ");
                } else {
                    sop(col + 1 + firstClassNumsToLetters.get(row) + " empty ");
                }
            }
            sopl("");
        }
        sopl("");
        sopl("");
        sopl("");

        Reservation sol2 = new Reservation("Solomon Alemu", "Economy", 'W');
        Reservation wish2 = new Reservation("Mark Wishom", "Economy", 'A');
        Reservation ed2 = new Reservation("Eduardo sanchez", "Economy", 'M');
        Reservation brian2 = new Reservation("Brian le", "Economy", 'W');
        Reservation noah2 = new Reservation("Noah elena", "Economy", 'W');
        Reservation noah3 = new Reservation("noah elena", "Economy", 'W');


        //add first class reservations to APS object and print if they worked
        sopl(a.searchForFirstOpenSeatWithPreference(sol2));
        sopl(a.searchForFirstOpenSeatWithPreference(wish2));
        sopl(a.searchForFirstOpenSeatWithPreference(ed2));
        sopl(a.searchForFirstOpenSeatWithPreference(brian2));
        sopl(a.searchForFirstOpenSeatWithPreference(noah2));
        sopl(a.searchForFirstOpenSeatWithPreference(noah3));
        sopl("");
        Reservation squa = new Reservation("Old Heads", "Roberto Sanchez,Chris Burton,Jermaine g,Clarence Keys", "Economy");
        Reservation squa2 = new Reservation("Old Heads", "Qoberto Sanchez,Zhris Burton", "Economy");
        Reservation squa3 = new Reservation("Old Heads", "poberto Sanchez,ahris Burton,l a,t a,u a,i a", "Economy");
        a.searchForConsecutiveSeats(squa);
        a.searchForConsecutiveSeats(squa2);
        a.searchForConsecutiveSeats(squa3);
        //print out 2d array of seats
        for (int row = 0; row < a.economy.length; row++) {
            for (int col = 0; col < a.economy[row].length; col++) {
                if (a.economy[row][col] != null) {
                    //sop(col + 10 + NumsToLetters.get(row) + " " + a.economy[row][col].getNameArray()[0] + " ");
                    sop("{" + a.economy[row][col].getNameArray()[0].charAt(0) + "}");
                } else {
                    //sop(col + 10 + NumsToLetters.get(row) + " empty ");
                    sop("{ }");
                }
            }
            sopl("");
        }


    }


    public static void sopl(Object x) {
        System.out.println(x);
    }

    public static void sop(Object x) {
        System.out.print(x);
    }

}
