import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class Model {
    private ArrayList<String> words;
    private ArrayList<ChangeListener> listeners;

    public Model() {
        words = new ArrayList<>();
        listeners = new ArrayList<>();
    }


    public ArrayList<String> getWords() {
        return words;
    }

    public void AddWords(String word) {
        words.add(word);
        for (ChangeListener cl : listeners) {
            cl.stateChanged(new ChangeEvent(this));
        }
    }

    public void attach(ChangeListener c) {
        listeners.add(c);
    }

    public static void main(String[] args) {

    }
}
