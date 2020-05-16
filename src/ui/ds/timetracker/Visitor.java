package ui.ds.timetracker;


import java.util.Observable;

// FormatVisitor is an interface that corresponds to
// the visitor pattern, it will give the methods to the
// classes that implement it to visit the visitable objects

interface Visitor extends ObserverInterface {

    void visitProject(Project project);

    void visitTask(Task task);

    void visitInterval(Interval interval);

    void update(Observable o, Object arg);

}
