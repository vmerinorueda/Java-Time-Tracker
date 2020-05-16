package ui.ds.timetracker;

import java.util.Date;
import java.util.Observable;


// The TimerClock class is the Observable within observer
// change to the Observers.change to the Observers.
public final class TimerClock extends Observable {

    private Date hora;

    private static TimerClock tc = null;

    private TimerClock() {
        this.hora = new Date();
    }

    public Date getHora() {
        return hora;
    }

    // The Timer instance is created, there should only be a
    // single timer that controls the time.
    public static TimerClock getInstance() {

        if (tc == null) {
            tc = new TimerClock();
        }
        return tc;
    }

    // Method called by the clock every time the refresh time passes,
    // it notifies that there has been a change to the observers
    public void tick() {
        this.hora = new Date();
        setChanged();
        notifyObservers(this);
    }


}
