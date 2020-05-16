package ui.ds.timetracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// The Project class represents a set of tasks that is can be
//contained by another parent project
public class Project extends Activity {

    private static Logger logger = LoggerFactory.getLogger(Project.class);
	
    private final Collection<Activity> activityList = new ArrayList<>();

    public final Collection<Activity> getActivityList() {
        return activityList;
    }
    
    public Project(final String name) {
        super(name);
        logger.info("Project " + name + " created");
    }

    public Project(final String name, final Project project) {
        super(name, project);
        logger.info("Subproject " + name + " created");
    }

    @Override
    public final void addActivity(final Activity activity) {
        activityList.add(activity);
    }

    // This method is executed when a project receives a change
    // from a task or a sub-project.
    // Update project duration with summation of the tasks and sub-projects.
    public final void update(final Date dateEnd) {

        int duration = 0;
        this.setDateEnd(dateEnd);

        for (Activity p : activityList) {
            duration += p.getDuration();
            if (this.getDateStart() == null) {
                this.setDateStart(p.getDateStart());
            }
        }

        setDuration(duration);

        //If the project has a parent project it's updated.
        if (this.getProject() != null) {
            this.getProject().update(dateEnd);
        }
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
    // notification of the observable.It converts the project class into a
    // class that can be visited and calls the visitInterval
    // method to paint the project data.
    public final void acceptVisitor(final Visitor v) {
        v.visitProject(this);
    }

    public final void acceptVisitor(final TreeVisitor v) {
        v.visitProject(this);
    }
}
