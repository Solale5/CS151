import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReservationSystem {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filename = args[0];

        try {
            File inFile = new File(filename);
            sopl(inFile.createNewFile());
            AirplaneSeats aps = new AirplaneSeats(inFile);
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
        sopl("Add [P]assenger ");
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("P") || input.equals("p")) {
                addPassenger(sc, aps);
                break;
            } else if (input.equals("C") || input.equals("c")) {

                break;
            } else if (input.equals("g") || input.equals("G")) {

                break;
            } else if (input.equals("e") || input.equals("E")) {

                break;
            } else if (input.equals("d") || input.equals("D")) {

                break;
            } else if (input.equals("q") || input.equals("Q")) {

                sopl("GoodBye");
                return;
            } else {
                sopl("Good Bye");
                break;
            }
        }
    }

    public static void addPassenger(Scanner sc, AirplaneSeats aps) {
        try {
            sopl("enter your full name");
            String name = sc.nextLine();

            sopl("enter your service class (First or Economy)");
            String theirClass = sc.nextLine();

            sopl("enter your seating preference ");
            sopl("W for window, M for middle, A for aisle, N for none");
            char seatingPref = sc.nextLine().charAt(0);
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

    public static void sopl(Object x) {
        System.out.println(x);
    }

}
