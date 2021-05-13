import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SimpleCalendarTester {

    public static void main(String[] args) {
        //set up frame
        JFrame jf = new JFrame("Solomon's Calendar");
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setBackground(Color.black);
//create MyCalendar object
        MyCalendar cal = new MyCalendar();
        //Load from text file
        cal.readFromFile();
//create view and attach to model
        CalendarVisualizer cv = new CalendarVisualizer(cal.dl, cal);
        cal.attach(cv);

        //create second view and attach to model
        DayVisualizer dv = new DayVisualizer(cal.dl, cal);
        cal.attach(dv);


        jf.setSize(1500, 800);
        //next button
        JButton next = new JButton(">");

        next.addActionListener(e -> cal.nextDay());
        //previous button
        JButton prev = new JButton("<");

        prev.addActionListener(e -> cal.previousDay());


        //create button
        Button create = new Button("Create Event");
        Button quit = new Button("Quit");
        quit.addActionListener(e -> {
            System.exit(0);

        });
        create.addActionListener(e -> {
            JFrame jf2 = new JFrame("Solomon's Calendar");
            jf2.setSize(800, 200);
            String year = String.valueOf(cal.dl.getYear());
            String formattedYear = year.substring(2, 4);
            String date = cal.dl.getMonth().getValue() + "/" + cal.dl.getDayOfMonth() + "/" + formattedYear;
            jf2.setLocationRelativeTo(null);
//            jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel j1 = new JLabel();
            JLabel j2 = new JLabel("Name");
            JLabel j3 = new JLabel("start time (military time)");
            JLabel j4 = new JLabel("End time  (military time)");

            JLabel jta = new JLabel(date);
            JTextField jta2 = new JTextField("");
            JTextField jta3 = new JTextField("");
            JTextField jta4 = new JTextField("");
            jf2.add(j1);
            jf2.add(j2);
            jf2.add(j3);
            jf2.add(j4);
            jf2.add(jta);
            jf2.add(jta2);
            jf2.add(jta3);
            jf2.add(jta4);
            jf2.setLayout(new GridLayout(0, 4));
            Button confirm = new Button("Save Event");
            confirm.addActionListener(e1 -> {


                System.out.println(date);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
                // jf2.dispose();

                LocalDate dateObject = LocalDate.parse(date, formatter);


                if (jta3.getText().length() > 1 && jta4.getText().length() > 1) {
                    LocalTime startTimeObject = LocalTime.parse(jta3.getText(), timeFormatter);
                    LocalTime endTimeObject = LocalTime.parse(jta4.getText(), timeFormatter);
                    if (cal.addNewEventFromUser(jta2.getText(), startTimeObject, endTimeObject, dateObject)) {

                        cal.addToFile(jta2.getText(), date + " " + startTimeObject.toString() + " " + endTimeObject.toString());

                        cal.readFromFile();
                        jf2.dispose();
                    } else {
                        if (cal.getNames().contains(jta2.getText())) {
                            j1.setText("Pick different name");
                        } else {
                            j1.setText("there is already an event scheduled for this time and date");
                        }


                    }
                } else {
                    j1.setText("enter all fields");
                }

            });
            jf2.add(confirm);
            jf2.setVisible(true);

        });


        //append buttons
        JPanel buttonHolder = new JPanel();
        buttonHolder.setLayout(new BorderLayout());
        buttonHolder.add(next, BorderLayout.EAST);

        buttonHolder.add(create, BorderLayout.NORTH);


        JPanel quitHolder = new JPanel();
        quitHolder.setLayout(new BorderLayout());
        quitHolder.add(prev, BorderLayout.EAST);

        buttonHolder.add(quitHolder, BorderLayout.CENTER);

        buttonHolder.add(quit, BorderLayout.WEST);


        //calendar and text display
        JPanel calHolder = new JPanel();
        calHolder.add(cv);
        calHolder.add(dv);
        calHolder.setLayout(new GridLayout());

        jf.setLayout(new BorderLayout());
        jf.add(buttonHolder, BorderLayout.NORTH);
        jf.add(calHolder, BorderLayout.CENTER);


        jf.setVisible(true);


    }
}
