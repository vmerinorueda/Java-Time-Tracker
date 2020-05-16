package ui.ds.timetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The Task class represents a set of
// intervals that is contained in a parent project
public class Task extends Activity {

    private final Collection<Interval> tasksIntervals = new ArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(Project.class);
    

    public Task(final String name, final Project project) {

        super(name);
        this.setProject(project);
        assert isValidTask() : "Error in the invariant task ";
        logger.debug("Task " + name + " created");
    }

    public final Collection<Interval> getTasksIntervals() {
        return tasksIntervals;
    }

    // This method starts the task creating an interval for that.
    public final void startTask() {
        Interval i = new Interval(this);
        this.addInterval(i);
        i.startInterval();
        logger.debug("Task " + this.getName() + " has been started. ");

    }

    //This method stops the task. The tasksIntervals collection is
    // converted to array and get the last element to stop this interval.
    // As there will only be one or none interval in execution always.
    public final void stopTask() {
        Interval i = lastInterval();
        i.stopInterval();
        logger.debug("Task " + this.getName() + " has been stoped. ");

    }

    private Interval lastInterval() {
        return (Interval) tasksIntervals.toArray()[tasksIntervals.size() - 1];
    }

    // This method is invoked when a task receives a change from an interval.
    // Update task duration with summation of the intervals.
    public final void update(final Date dateEnd) {

        logger.debug("Task " + this.getName() + "received interval notification");

        assert ((dateEnd != null))
                : "The final date can not be null";

        int duration = 0;

        setDateEnd(dateEnd);

        for (Interval ti : tasksIntervals) {
            duration += ti.getDuration();
        }
        setDuration(duration);
        this.getProject().update(dateEnd); // Call this task father

        assert ((dateEnd != null) || duration < 0)
                : "The final date can not be null or"
                + " duration must be a positive number";
    }

    public final void addActivity(final Activity c) {

        assert false : "Execution should never reach here";
        logger.debug("Unable to add the task.");

    }


    private void addInterval(final Interval interval) {

        if (getDateStart() == null) {
            setDateStart(interval.getDateStart());
        }

        tasksIntervals.add(interval);
        logger.debug("Interval" + interval.getId() + " added to task" + this.getName());

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

    // This method is called by PrinterVisitor in each
    // notification of the observable.
    // It converts the task class into a class that can be
    // visited and calls the visitInterval method to paint
    // the task data.
    public final void acceptVisitor(final Visitor v) {
        v.visitTask(this);
    }

    public final void acceptVisitor(final TreeVisitor v) {
        v.visitTask(this);
    }

    private boolean isValidTask() {
        return (this.getName() != null || this.getProject() != null);
    }


}
