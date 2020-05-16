package ui.ds.timetracker;

import java.util.Observable;
import java.util.Observer;

interface ObserverInterface extends Observer {
    void update(Observable o, Object arg);
}
