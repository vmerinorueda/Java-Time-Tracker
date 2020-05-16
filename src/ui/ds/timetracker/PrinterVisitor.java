package ui.ds.timetracker;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;

//PrinterVisitor implements Visitor and his methods
// In each notice of the observable, this class will ask each type
// of activity(interval, task or project) to be visited, later if they
// accept to be visited, they will do their corresponding method.

public class PrinterVisitor implements Visitor {
    private final Activity activity;

    public PrinterVisitor(final Activity activityy) {
        this.activity = activityy;
        TimerClock tc = TimerClock.getInstance();
        tc.addObserver(this);
    }

    // Method that goes through the hierarchy of activities and call
    // the draw method for painting the tasks and projects
    public final void visitProject(final Project project) {
        SimpleDateFormat formatDate =
                new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);


        if (project.getDateStart() != null) {
            System.out.println(project.getName() + "\t   "
                    + formatDate.format(project.getDateStart()) + "\t "
                    + formatDate.format(project.getDateEnd()) + "\t  "
                    + project.changeFormatDuration());
        } else {
            System.out.println(project.getName()
                    + "\t\t\t\t\t\t\t\t\t\t\t\t\t  00:00:00");
        }

        for (Activity p : project.getActivityList()) {
            p.acceptVisitor(this);
        }

        if (project.getProject() == null) {
            System.out.println();
        }
    }

    //This method draws the tasks data.
    public final void visitTask(final Task task) {
        //Format date with this pattern
        SimpleDateFormat formatDate =
                new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
        if (task.getDateStart() != null) {
            System.out.println(task.getName() + "\t   "
                    + formatDate.format(task.getDateStart())
                    + "\t " + formatDate.format(task.getDateEnd()) + "\t  "
                    + task.changeFormatDuration());
        } else {
            System.out.println(task.getName()
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t  00:00:00");
        }
        for (Interval ti : task.getTasksIntervals()) {
            ti.acceptVisitor(this);
        }
    }

    // TODO We set it for future implementations, we don't need
    // TODO it at the moment as we don't need to draw the intervals
    public void visitInterval(final Interval interval) {

    }

    // Method that draws the serialized data.
    public final void draw(final Project root) {
        root.acceptVisitor(this);
    }

    // Each time the observable sends a notification with changes,
    // the following code is executed.This method will ask each type of
    // activity(interval, task or project) to be visited,
    @Override
    public final void update(final Observable o, final Object arg) {
        this.activity.acceptVisitor(this);
    }
}
