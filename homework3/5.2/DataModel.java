import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * A Subject class for the observer pattern.
 */
public class DataModel {
    /**
     * Constructs a DataModel object
     *
     * @param d the data to model
     */
    public DataModel(ArrayList<Double> d) {
        data = d;
        listeners = new ArrayList<ChangeListener>();
    }

    /**
     * Constructs a DataModel object
     *
     * @return the data in an ArrayList
     */
    public ArrayList<Double> getData() {
        return (ArrayList<Double>) (data.clone());
    }

    /**
     * Attach a listener to the Model
     *
     * @param c the listener
     */
    public void attach(ChangeListener c) {
        listeners.add(c);
    }

//    public void attach(MouseListener m) {
//        this.m = m;
//    }

    /**
     * Change the data in the model at a particular location
     *
     * @param location the index of the field to change
     * @param value    the new value
     */
    public void update(int location, double value) {
        data.set(location, value);
        for (ChangeListener l : listeners) {
            l.stateChanged(new ChangeEvent(this));
        }
    }


    ArrayList<Double> data;
    ArrayList<ChangeListener> listeners;
//    MouseListener m;

}
