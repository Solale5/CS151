import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View implements ChangeListener {
    private Model model;

    private JTextArea display;

    public View(Model a) {
        model = a;

        JFrame jf = new JFrame("View");
        jf.setLayout(new FlowLayout());
        Button button = new Button("add");
        JTextField textField = new JTextField(10);
        display = new JTextArea();

        jf.setSize(500, 500);
        jf.add(button);
        //CONTROLLER
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                a.AddWords(textField.getText());

            }
        });

        jf.add(textField);
        jf.add(display);


        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    public void stateChanged(ChangeEvent e) {
        ArrayList<String> words = model.getWords();
        String update = "";
        for (String s : words) {
            update += s + " " + "\n";
        }
        display.setText(update);

    }
}
