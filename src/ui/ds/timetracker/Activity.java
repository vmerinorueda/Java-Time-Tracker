package ui.ds.timetracker;

import java.util.Date;
import java.util.Observable;

//The abstract class Activity will define projects and tasks
//because they have the same structure
abstract class Activity extends Observable implements java.io.Serializable {

    private Project project;
    private final String name;
    private Date dateStart;
    private Date dateEnd;
    private int duration;
    protected static final int HOUR_TO_SEC = 3600;
    protected static final int HOUR_TO_MIN = 60;

    Activity(final String namee) {
        this.name = namee;
    }

    Activity(final String namee, final Project projectt) {
        this.name = namee;
        this.project = projectt;
    }

    public String getName() {
        return name;
    }

    int getDuration() {
        return duration;
    }

    void setDuration(final int durationn) {
        this.duration = durationn;
    }

    public Date getDateStart() {
        return dateStart;
    }

    void setDateStart(final Date dateStartt) {
        this.dateStart = dateStartt;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    void setDateEnd(final Date dateEndd) {
        this.dateEnd = dateEndd;
    }

    public Project getProject() {
        return project;
    }

    void setProject(final Project projectt) {
        this.project = projectt;
    }

    abstract void addActivity(Activity p);

    abstract void update(Date dateEndd);

    abstract String changeFormatDuration();

    abstract void acceptVisitor(Visitor v);

    abstract void acceptVisitor(TreeVisitor v);
}
