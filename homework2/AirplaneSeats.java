import java.io.*;
import java.util.*;

public class AirplaneSeats {

    private Reservation[][] firstClass;

    private Reservation[][] economy;

    private static HashMap<Integer, String> NumsToLetters;
    private static HashMap<Integer, String> firstClassNumsToLetters;
    private ArrayList<Reservation> reservationsInOrder;

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

    private HashSet<String> groupNames;

    public AirplaneSeats(File f) {
        firstClass = new Reservation[4][2];

        economy = new Reservation[6][20];

        theFile = f;
        groupNames = new HashSet<>();
        reservationsInOrder = new ArrayList<>();
    }

    public AirplaneSeats() {
        firstClass = new Reservation[4][2];

        economy = new Reservation[6][20];

        groupNames = new HashSet<>();
        reservationsInOrder = new ArrayList<>();
    }

    public HashSet<String> getGroupNames() {
        return groupNames;
    }

    public void readFromFile() {
        try {
            Scanner myReader = new Scanner(theFile);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                String[] objectInfo = line.split("\\|");
                sopl(line + "TEST");


                if (!objectInfo[2].equals("F") && !objectInfo[2].equals("E")) {
                    Reservation r = new Reservation(objectInfo[0], objectInfo[1], objectInfo[2].charAt(0));
                    searchForFirstOpenSeatWithPreference(r);


                } else if (objectInfo[2].equals("F") || objectInfo[2].equals("E")) {
                    sop("hi");
                    Reservation l = new Reservation(objectInfo[0], objectInfo[1], objectInfo[2]);
                    searchForConsecutiveSeats(l);
                    

                }

            }

            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            sopl("an error occurred");
        }
    }

    public void clearTheFile() {
        try {
            FileWriter fwOb = new FileWriter(theFile.getPath(), false);
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        try {
            clearTheFile();
            File myObj = new File(theFile.getPath());


            BufferedWriter out = new BufferedWriter(
                    new FileWriter(myObj, true));
            String firstclassString = "";
            String economyString = "";
            int i = 0;
            while (i < reservationsInOrder.size()) {

                //handles  group reservation
                if (reservationsInOrder.get(i).getGroupName() != null) {
                    economyString += "" + reservationsInOrder.get(i).getGroupName() + "|";
                    int j = 0;
                    while (j < reservationsInOrder.get(i).getSize()) {
                        economyString += reservationsInOrder.get(i).getReservations().get(j).getNameArray()[0] + " " + reservationsInOrder.get(i).getReservations().get(j).getNameArray()[1];
                        if (j < reservationsInOrder.get(i).getSize() - 1) {
                            economyString += ",";
                        }
                        j++;
                    }
                    economyString += "|";
                    economyString += reservationsInOrder.get(i).getServiceClass() + "\n";
                    //handles first class individual registration
                } else {
                    economyString += reservationsInOrder.get(i).getNameArray()[0] + " " + reservationsInOrder.get(i).getNameArray()[1] + "|" + reservationsInOrder.get(i).getServiceClass() + "|" + reservationsInOrder.get(i).getSeatPreference() + "\n";

                }

                i++;
            }

            out.write(economyString);
            out.close();
            sopl(firstclassString);
            sopl("");
            sopl(economyString);
        } catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    public void getOpenSeatsList(String sclass) {
        if (sclass.charAt(0) == 'f') {
            sopl("First Class:");
            for (int col = 0; col < firstClass[0].length; col++) {
                sop(col + 1 + ": ");
                for (int row = 0; row < firstClass.length; row++) {

                    if (firstClass[row][col] == null) {
                        sop(firstClassNumsToLetters.get(row) + ", ");

                    }
                }
                sopl("");
            }
        } else {
            sopl("");
            sopl("Economy:");
            for (int col = 0; col < economy[0].length; col++) {
                sop(col + 1 + ": ");
                for (int row = 0; row < economy.length; row++) {

                    if (economy[row][col] == null) {
                        sop(NumsToLetters.get(row) + ", ");

                    }
                }
                sopl("");
            }
        }


    }

    public void getManifest(String sclass) {
        if (sclass.charAt(0) == 'f') {
            sopl("First Class:");
            for (int col = 0; col < firstClass[0].length; col++) {

                for (int row = 0; row < firstClass.length; row++) {

                    if (firstClass[row][col] != null) {
                        sopl(col + 1 + firstClassNumsToLetters.get(row) + ": " + firstClass[row][col].getNameArray()[0] + " ");

                    }
                }

            }
        } else {
            sopl("Economy:");
            for (int col = 0; col < economy[0].length; col++) {

                for (int row = 0; row < economy.length; row++) {

                    if (economy[row][col] != null) {
                        sopl(col + 10 + NumsToLetters.get(row) + ": " + economy[row][col].getNameArray()[0] + " ");

                    }
                }

            }
        }


    }

    public boolean cancelRegular(String name) {

        for (int col = 0; col < firstClass[0].length; col++) {

            for (int row = 0; row < firstClass.length; row++) {
                if (firstClass[row][col] != null) {
                    String theCurrentName = firstClass[row][col].getNameArray()[0] + " " + firstClass[row][col].getNameArray()[1];
                    if (theCurrentName.toUpperCase().equals(name.toUpperCase())) {
                        firstClass[row][col] = null;
                        return true;

                    }
                }
            }

        }

        for (int col = 0; col < economy[0].length; col++) {

            for (int row = 0; row < economy.length; row++) {
                if (economy[row][col] != null) {
                    String theCurrentName = economy[row][col].getNameArray()[0] + " " + economy[row][col].getNameArray()[1];
                    if (theCurrentName.toUpperCase().equals(name.toUpperCase())) {
                        economy[row][col] = null;
                        return true;
                    }
                }
            }

        }

        ArrayList<Reservation> tbd = new ArrayList<>();
        for (Reservation r : reservationsInOrder) {
            if (r.getGroupName() != null && r.getGroupName().toUpperCase().equals(name.toUpperCase())) {
                tbd.add(r);
            }
        }
        reservationsInOrder.removeAll(tbd);
        return false;


    }

    public boolean cancelGroup(String name) {
        boolean found = false;
        for (int col = 0; col < firstClass[0].length; col++) {

            for (int row = 0; row < firstClass.length; row++) {
                if (firstClass[row][col] != null && firstClass[row][col].getGroupName() != null) {
                    String theCurrentName = firstClass[row][col].getGroupName();

                    if (theCurrentName.toUpperCase().equals(name.toUpperCase())) {
                        firstClass[row][col] = null;
                        found = true;
                        groupNames.remove(name);

                    }
                }
            }

        }

        for (int col = 0; col < economy[0].length; col++) {

            for (int row = 0; row < economy.length; row++) {
                if (economy[row][col] != null && economy[row][col].getGroupName() != null) {
                    String theCurrentName = economy[row][col].getGroupName();

                    if (theCurrentName.toUpperCase().equals(name.toUpperCase())) {
                        economy[row][col] = null;
                        found = true;
                        groupNames.remove(name);

                    }
                }
            }

        }
        ArrayList<Reservation> tbd = new ArrayList<>();
        for (Reservation r : reservationsInOrder) {
            if (r.getGroupName() != null && r.getGroupName().toUpperCase().equals(name.toUpperCase())) {
                tbd.add(r);
            }
        }
        reservationsInOrder.removeAll(tbd);
        return found;

    }


    private void addNewReservationFromUser(Reservation r, int row, int col) {
        firstClass[row][col] = r;
        reservationsInOrder.add(r);

    }

    private void addNewReservationFromUserEconomy(Reservation r, int row, int col) {
        economy[row][col] = r;
        reservationsInOrder.add(r);

    }

    private void addGroupToFirstClass(TreeSet<Integer> ts, Reservation r) {
        reservationsInOrder.add(r);
        ArrayList<Integer> a = new ArrayList<>(ts);

        int i = 0;
        for (int col = 0; col < a.size(); col++) {
            for (int row = 0; row < firstClass.length; row++) {

                if (firstClass[row][a.get(col)] == null && i < r.getReservations().size()) {

                    firstClass[row][a.get(col)] = r.getReservations().get(i);
                    i++;
                    groupNames.add(r.getGroupName());
                }

            }
        }

    }

    ////////////
    private void addGroupToEconomy(TreeSet<Integer> ts, Reservation r) {
        reservationsInOrder.add(r);
        ArrayList<Integer> a = new ArrayList<>(ts);

        int i = 0;
        for (int col = 0; col < a.size(); col++) {
            for (int row = 0; row < economy.length; row++) {

                if (economy[row][a.get(col)] == null && i < r.getReservations().size()) {
                    economy[row][a.get(col)] = r.getReservations().get(i);
                    i++;
                    groupNames.add(r.getGroupName());
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

    //fix to match the one for economy group reservations
    private void consecutiveEmptySeatsColumnsToIntValuesFC(int[] emptySeatsInCol, Reservation r) {
        ArrayList<Integer> al = new ArrayList<>();
        TreeSet<Integer> columnIndexes = new TreeSet<>();
        for (Integer i : emptySeatsInCol) {
            al.add(i);
        }
        if (al.contains(r.getSize())) {

            columnIndexes.add(al.indexOf(r.getSize()));
            addGroupToFirstClass(columnIndexes, r);

        } else if (al.contains(r.getSize() + 1)) {
            columnIndexes.add(al.indexOf(r.getSize() + 1));
            addGroupToFirstClass(columnIndexes, r);
        } else if (al.contains(r.getSize() + 2)) {
            columnIndexes.add(al.indexOf(r.getSize() + 2));
            addGroupToFirstClass(columnIndexes, r);
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
                    addGroupToFirstClass(columnIndexes, r);
                    break;
                } else {
                    //emptySeatsInCol = openConsecutiveSeatsArray(r);
                    peopleToBeSeated = peopleToBeSeated - largestNumberOfEmptySeats;

                    columnIndexes.add(colNumber);

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
        File test = new File("test.txt");
        AirplaneSeats a = new AirplaneSeats(test);
        a.readFromFile();
        for (Reservation r : a.reservationsInOrder) {
            sopl(r.getGroupName());
        }
//        Reservation placeholder = new Reservation("test e", "First", 'W');
//        Reservation placeholder2 = new Reservation("test e", "First", 'W');
//        a.firstClass[3][1] = placeholder;
//        a.firstClass[0][1] = placeholder2;
//        //create reservations
//        Reservation sol = new Reservation("Kobe Bryant", "First", 'W');
//        Reservation wish = new Reservation("Lebron James", "First", 'A');
//        Reservation ed = new Reservation("Michael Jordan", "First", 'M');
//        Reservation brian = new Reservation("Will Smith", "First", 'W');
//        Reservation noah = new Reservation("Chris Brown", "First", 'N');
//
//
//        //add first class reservations to APS object and print if they worked
//        sopl(a.searchForFirstOpenSeatWithPreference(sol));
//        sopl(a.searchForFirstOpenSeatWithPreference(wish));
//        sopl(a.searchForFirstOpenSeatWithPreference(ed));
//        sopl(a.searchForFirstOpenSeatWithPreference(brian));
//        sopl(a.searchForFirstOpenSeatWithPreference(noah));
//        sopl("");
//        //print out 2d array of seats
//        for (int row = 0; row < a.firstClass.length; row++) {
//            for (int col = 0; col < a.firstClass[row].length; col++) {
//                if (a.firstClass[row][col] != null) {
//                    sop(col + 1 + firstClassNumsToLetters.get(row) + " " + a.firstClass[row][col].getNameArray()[0] + " ");
//                } else {
//                    sop(col + 1 + firstClassNumsToLetters.get(row) + " empty ");
//                }
//            }
//            sopl("");
//        }
//        sopl("");
//        sopl("");
//        sopl("");
//
//        Reservation sol2 = new Reservation("Solomon Alemu", "Economy", 'W');
//        Reservation wish2 = new Reservation("Mark Wishom", "Economy", 'A');
//        Reservation ed2 = new Reservation("Eduardo sanchez", "Economy", 'M');
//        Reservation brian2 = new Reservation("Brian le", "Economy", 'W');
//        Reservation noah2 = new Reservation("Noah elena", "Economy", 'W');
//        Reservation noah3 = new Reservation("noah elena", "Economy", 'W');
//
//
//        //add first class reservations to APS object and print if they worked
//        sopl(a.searchForFirstOpenSeatWithPreference(sol2));
//        sopl(a.searchForFirstOpenSeatWithPreference(wish2));
//        sopl(a.searchForFirstOpenSeatWithPreference(ed2));
//        sopl(a.searchForFirstOpenSeatWithPreference(brian2));
//        sopl(a.searchForFirstOpenSeatWithPreference(noah2));
//        sopl(a.searchForFirstOpenSeatWithPreference(noah3));
//        sopl("");
//
//        Reservation squa = new Reservation("the Old Heads", "Roberto Sanchez,Chris Burton,Jermaine g,Clarence Keys", "Economy");
//        Reservation squa2 = new Reservation("Old Headz", "Qoberto Sanchez,Zhris Burton", "Economy");
//        Reservation squa3 = new Reservation("Old Heads", "poberto Sanchez,ahris Burton,l a,t a,u a,i a", "Economy");
//        a.searchForConsecutiveSeats(squa);
//        a.searchForConsecutiveSeats(squa2);
//        a.searchForConsecutiveSeats(squa3);


//        a.getOpenSeatsList("f");
//        a.getOpenSeatsList("e");
//        sopl("");

        a.getManifest("f");
        a.getManifest("e");


        sopl("");
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
        a.writeToFile();

    }


    public static void sopl(Object x) {
        System.out.println(x);
    }

    public static void sop(Object x) {
        System.out.print(x);
    }

}
