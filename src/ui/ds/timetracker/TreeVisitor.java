package ui.ds.timetracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//the treeVisisit class is responsible for traversing
// our data tree every time it is visited and collecting
// the data corresponding to the period requested in tables
// to be written according to the requested format

public class TreeVisitor {

    private Report report;

    private static final int HOUR_TO_SEC = 3600;
    private static final int HOUR_TO_MIN = 60;
    private static Logger logger = LoggerFactory.getLogger(Project.class);

    public TreeVisitor(final Report report) {
        this.report = report;
    }

    private int getProjectDurationTree(final Project project) {
        int sum = 0;
        for (Activity a : project.getActivityList()) {
            if (a.getDateStart() != null
                    && this.isInsidePeriod(a.getDateStart(),
                    a.getDateEnd())) {
                if (a instanceof Task) {
                    sum += getTaskDurationTree((Task) a);
                } else {
                    sum += getProjectDurationTree((Project) a);
                }
            }

        }
        return sum;
    }

    // Checks if the period is between period given dates,
    // and returns true if it happens.
    private Boolean isInsidePeriod(final Date dateStart, final Date dateEnd) {
        SimpleDateFormat formatDate
                = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
                Locale.ENGLISH);
        // Default precondition, no intersect
        boolean insidePeriod = false;
        // if activity starts after or at the same
        // time that the report end time
        if (formatDate.format(report.getDateEnd()).compareTo(
                formatDate.format(dateStart)) <= 0
                || formatDate.format(report.getDateStart()).compareTo(
                formatDate.format(dateEnd)) > 0) {
            insidePeriod = false;
            // if activity starts before, or at the same report start time
        } else if (report.getDateEnd().compareTo(dateEnd) <= 0) {
            insidePeriod = true;
            // if activity is after the report start time
        } else if ((report.getDateEnd().compareTo(dateStart) < 0
                || report.getDateEnd().compareTo(dateEnd) >= 0)
                && report.getDateStart().compareTo(dateEnd) < 0) {
            insidePeriod = true;
            //if activity is between the report period
        } else if (report.getDateStart().compareTo(dateStart) < 0
                && report.getDateEnd().compareTo(dateEnd) > 0) {
            insidePeriod = true;
            // if activity is not before period
        } else if (report.getDateStart().compareTo(dateStart) >= 0
                && report.getDateStart().compareTo(dateEnd) < 0) {
            insidePeriod = true;
        }
        return insidePeriod;
    }

    // Method that sums the duration of the intervals in one task
    // so this task can know his duration in the report.
    private int getTaskDurationTree(final Task task) {
        int sum = 0;
        for (Interval i : task.getTasksIntervals()) {
            if (i.getDateStart() != null
                    && this.isInsidePeriod(i.getDateStart(),
                    i.getDateEnd())) {
                sum += i.recalculateDuration(report.getDateStart(),
                        report.getDateEnd());
            }
        }
        return sum;
    }


    // The "save" methods and saveProject save the data of the
    // tree according to its type
    public final void saveProjects(final Project project) {

        for (Activity p : project.getActivityList()) {
            if (p != null) {
                if (p instanceof Project) {
                    saveProject(p);
                }
            }
        }
    }

    public final void saveTasks(final Project project) {

        for (Activity p : project.getActivityList()) {
            if (p instanceof Task) {
                visitTask((Task) p);
            }
            if (p instanceof Project) {
                saveTasks((Project) p);
            }
        }
    }

    public final void saveIntervals(final Project project) {

        for (Activity p : project.getActivityList()) {
            if (p instanceof Task) {
                for (Interval i : ((Task) p).getTasksIntervals()) {
                    visitInterval(i);
                }
            }
            if (p instanceof Project) {
                saveIntervals((Project) p);
            }
        }
    }

    public final void visitProject(final Project project) {

        for (Activity a : project.getActivityList()) {
            if ((this.report instanceof DetailedReport)
                    && (a instanceof Project)) {
                saveProjects((Project) a);

            } else {
                saveProject(a);

            }
        }
    }




    //the methods visittask, visitinterval and saveproject
    // draw their respective data
    public final void visitTask(final Task task) {
        if (task.getDateStart() != null
                && this.isInsidePeriod(task.getDateStart(),
                task.getDateEnd())) {

            ArrayList<String> rowTable = fieldOfProjects(task);
            this.report.generateTableReport(rowTable);


        }
    }

    public final void visitInterval(final Interval interval) {

        if (interval.getDateStart() != null
                && this.isInsidePeriod(interval.getDateStart(),
                interval.getDateEnd())) {
            ArrayList<String> rowsTable = fieldOfIntervals(interval);
            this.report.generateTableReport(rowsTable);
        }
    }

    private void saveProject(final Activity a) {
        if (a.getDateStart() != null
                && this.isInsidePeriod(a.getDateStart(),
                a.getDateEnd())) {
            ArrayList<String> rowsTable = fieldOfProjects(a);
            this.report.generateTableReport(rowsTable);
        }
    }


    //the "fieldOf" methods verify that the data are within
    // the established period and if so, adjust it to this
    private ArrayList<String> fieldOfIntervals(final Interval i) {
        SimpleDateFormat formatDate
                = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS",
                Locale.ENGLISH);
        ArrayList<String> rowTable;

        rowTable = new ArrayList<>();
        rowTable.add(i.getTask().getProject().getName());
        rowTable.add(i.getTask().getName());
        rowTable.add(Integer.toString(i.getId()));
        rowTable.add(formatDate.format(i.getDateStart()));
        rowTable.add(formatDate.format(i.getDateEnd()));
        int duration = i.recalculateDuration(report.getDateStart(),
                report.getDateEnd());
        rowTable.add(changeFormatDuration(duration));

        return rowTable;
    }

    private ArrayList<String> fieldOfProjects(final Activity a) {
        SimpleDateFormat formatDate
                = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
                Locale.ENGLISH);
        ArrayList<String> rowTable;

        rowTable = new ArrayList<>();
        if (a instanceof Task) {
            rowTable.add(a.getProject().getName());
        }
        rowTable.add(a.getName());
        if (a.getDateStart().compareTo(report.getDateStart()) < 0) {
            rowTable.add(formatDate.format(report.getDateStart()));
        } else {
            rowTable.add(formatDate.format(a.getDateStart()));
        }
        if (a.getDateEnd().compareTo(report.getDateEnd()) < 0) {
            rowTable.add(formatDate.format(a.getDateEnd()));
        } else {
            rowTable.add(formatDate.format(report.getDateEnd()));
        }
        if (a instanceof Project) {
            a.setDuration(getProjectDurationTree((Project) a));
        }

        rowTable.add(a.changeFormatDuration());

        return rowTable;
    }




    // Change duration format to hh:mm:ss
    private String changeFormatDuration(final int duration) {
        int seconds = duration;
        int hora = seconds / HOUR_TO_SEC;
        int min = (seconds - (HOUR_TO_SEC * hora)) / HOUR_TO_MIN;
        seconds = seconds - ((HOUR_TO_SEC * hora) + (HOUR_TO_MIN * min));
        return String.format(Locale.ENGLISH, "%02d", hora) + ":"
                + String.format(Locale.ENGLISH, "%02d", min) + ":"
                + String.format(Locale.ENGLISH, "%02d", seconds);

    }

}
