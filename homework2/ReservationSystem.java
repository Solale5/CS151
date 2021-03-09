import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReservationSystem {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filename = args[0];

        try {
            File inFile = new File(filename);
            inFile.createNewFile();
            AirplaneSeats aps = new AirplaneSeats(inFile);
            aps.readFromFile();
            menu(sc, aps);


        } catch (IOException e) {
            System.out.println("exception occoured" + e);

        }


    }

    /**
     * menu method that appears at the start of execution
     *
     * @param sc the scanner that reads user input
     */
    public static void menu(Scanner sc, AirplaneSeats aps) {
        sopl("Add [P]assenger, Add [G]roup, [C]ancel, [A]vailibility list, [M]anifest list");
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("P") || input.equals("p")) {
                addPassenger(sc, aps);
                break;
            } else if (input.equals("g") || input.equals("G")) {
                addGroup(sc, aps);
                break;
            } else if (input.equals("A") || input.equals("a")) {
                availabilityList(sc, aps);
                break;
            } else if (input.equals("M") || input.equals("m")) {
                manifest(sc, aps);
                break;
            } else if (input.equals("C") || input.equals("c")) {
                cancel(sc, aps);
                break;
            } else if (input.equals("q") || input.equals("Q")) {
                aps.writeToFile();
                sopl("Good Bye");
                return;
            } else {
                aps.writeToFile();
                sopl("Good Bye");
                break;
            }
        }
    }

    private static void availabilityList(Scanner sc, AirplaneSeats aps) {
        sopl("enter your service class (First or Economy) ");

        String classF = sc.nextLine().toLowerCase();
        aps.getOpenSeatsList(classF);
        menu(sc, aps);
    }

    private static void manifest(Scanner sc, AirplaneSeats aps) {
        sopl("enter your service class (First or Economy) ");

        String classF = sc.nextLine().toLowerCase();
        aps.getManifest(classF);
        menu(sc, aps);
    }


    private static void addPassenger(Scanner sc, AirplaneSeats aps) {
        try {
            sopl("enter your full name");
            String name = sc.nextLine();

            sopl("enter your service class (First or Economy)");
            String theirClass = sc.nextLine();

            sopl("enter your seating preference ");
            sopl("W for window, M for middle, A for aisle, N for none");
            char seatingPref = sc.nextLine().toUpperCase().charAt(0);
            if (aps.searchForFirstOpenSeatWithPreference(new Reservation(name, theirClass, seatingPref))) {
                sopl("reserved");
                menu(sc, aps);
            } else {
                sopl("unable to add with this seat preference please try again");
                addPassenger(sc, aps);
            }

        } catch (Exception e) {
            sopl(e);
            menu(sc, aps);
        }


    }

    private static void addGroup(Scanner sc, AirplaneSeats aps) {
        try {
            sopl("enter group name");
            String groupname = sc.nextLine();
            if (aps.getGroupNames().contains(groupname)) {
                sopl("this group name is already being used choose another");
                groupname = sc.nextLine();
            }

            sopl("enter full names of passengers with no spaces in between commas ex Bruce Wayne,Tony Stark ");
            String theirnames = sc.nextLine();

            sopl("enter your service class (First or Economy) ");

            String classF = sc.nextLine().toLowerCase();
            if (aps.searchForConsecutiveSeats(new Reservation(groupname, theirnames, classF))) {
                sopl("reserved");
                menu(sc, aps);
            } else {
                sopl("unable to add due to lack of space on plane");
                addPassenger(sc, aps);
            }

        } catch (Exception e) {
            e.printStackTrace();
            menu(sc, aps);
        }


    }

    private static void cancel(Scanner sc, AirplaneSeats aps) {
        sopl("Cancel [I]ndividual or [G]roup?");
        String choice = sc.nextLine();
        if (choice.toUpperCase().equals("I")) {
            sopl("Enter the passengers first and last name");
            String name = sc.nextLine();
            if (aps.cancelRegular(name)) {
                sopl("canceled");
            } else {
                sopl("no reservation found for " + name);
            }
        } else if (choice.toUpperCase().equals("G")) {
            sopl("Enter the group name");
            String name = sc.nextLine();
            if (aps.cancelGroup(name)) {
                sopl("canceled");
            } else {
                sopl("no reservation found with " + name + " as the group name");
            }
        } else {
            menu(sc, aps);
        }
        menu(sc, aps);


    }

    //truncated print methods
    public static void sopl(Object x) {
        System.out.println(x);
    }

}
