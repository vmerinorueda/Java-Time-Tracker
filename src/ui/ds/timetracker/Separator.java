package ui.ds.timetracker;

// The Separator class generates a report element of type Separator.
// This class is part of the visitor pattern,it is a visitable class
// that accepts to be visited(acceptVisitor).
// It will be visited to be printed in the project

public class Separator extends Element {

    public Separator(final Report report) {
        super(report);
    }

    public final void acceptVisitor(final FormatVisitor fv) {
        fv.visitSeparator(this);
    }
}
