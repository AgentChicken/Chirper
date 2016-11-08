import java.util.ArrayList;
import java.util.List;

/**
 * Created by MStev on 11/1/2016.
 */
abstract class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    void attach(Observer observer)
    {
        observers.add(observer);
    }

    void detach(Observer observer)
    {
        observers.remove(observer);
    }

    void notifyObservers()
    {
        for(Observer observer : observers)
        {
            observer.update(this);
        }
    }
}
