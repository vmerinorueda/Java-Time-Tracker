package ui.ds.timetracker;

import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The Interval class is a class that represents a time interval of a task
public class Interval implements ObserverInterface, java.io.Serializable {

    private final Date dateStart;
    private Date dateEnd;
    private int duration;
    private final Task task;
    private int id;
    private static final int MILLISECONDS = 1000;
    private static final int HOUR_TO_SEC = 3600;
    private static final int HOUR_TO_MIN = 60;
    private static Logger logger = LoggerFactory.getLogger(Project.class);

    public final int getId() {
        return id;
    }

    public Interval(final Task task) {
        this.dateStart = new Date();
        this.task = task;
        this.id = task.getTasksIntervals().size() + 1;
        logger.debug("Interval " + this.id + " from task " + task.getName() + " created");

    }

    public final Date getDateStart() {
        return dateStart;
    }

    public final Date getDateEnd() {
        return dateEnd;
    }

    public final int getDuration() {
        return duration;
    }

    public final Task getTask() {
        return task;
    }

    // This method generates an instance to the unique Timer and add it
    // to the list of observers to be notified by each generated tick
    public final void startInterval() {
        TimerClock tc = TimerClock.getInstance();
        tc.addObserver(this);
        logger.debug("Interval " + this.id + " has been started. ");

    }

    // Remove this interval from the observer list
    public final void stopInterval() {
        this.dateEnd = new Date();

        TimerClock tc = TimerClock.getInstance();
        tc.deleteObserver(this);
        logger.debug("Interval " + this.id + " has been started. ");

    }

    // Each time the observable sends a notification with changes,
    // the following code is executed.
    // The dateNow variable contains the date on which the "tick" was generated.
    // With the start date of the interval and with the date that we
    // received from the observable we are increasing the duration
    @Override
    public final void update(final Observable o, final Object arg) {
       // logger.info("Interval " + this.id + " received notification from the observable\n");

        Date dateNow = ((TimerClock) arg).getHora();

        long date1 = dateNow.getTime();
        long date2 = getDateStart().getTime();
        long subtraction = date1 - date2;

        this.duration = (int) subtraction / MILLISECONDS;

        task.update(dateNow);

    }

    // /This method is called by PrinterVisitor in each notification of the
    // observable.
    // It converts the interval class into a class that can be visited and calls
    // the visitInterval method to paint the interval data.
    public final void acceptVisitor(final Visitor v) {
        v.visitInterval(this);
    }

    // Change duration format to hh:mm:ss
    public final String changeFormatDuration() {
        int seconds = this.getDuration();
        int hora = seconds / HOUR_TO_SEC;
        int min = (seconds - (HOUR_TO_SEC * hora)) / HOUR_TO_MIN;
        seconds = seconds - ((HOUR_TO_SEC * hora) + (HOUR_TO_MIN * min));
        return String.format(Locale.ENGLISH, "%02d", hora) + ":"
                + String.format(Locale.ENGLISH, "%02d", min) + ":"
                + String.format(Locale.ENGLISH, "%02d", seconds);
    }

    public final int recalculateDuration(final Date periodStartDate,
                                         final Date periodEndDate) {
        Date auxStartDate = getDateStart();
        Date auxEndDate = getDateEnd();

        // if report start date is after our start date,
        // take the report start date
        if (periodStartDate.after(getDateStart())) {
            auxStartDate = periodStartDate;
        }
        // if report end date is after our end date, take the report end date
        if (periodEndDate.before(getDateEnd())) {
            auxEndDate = periodEndDate;
        }
        long date1 = auxEndDate.getTime();
        long date2 = auxStartDate.getTime();
        long subtraction = date1 - date2;
        return (int) subtraction / MILLISECONDS;
    }
}
