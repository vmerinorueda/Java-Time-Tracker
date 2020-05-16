package ui.ds.timetracker;

// The Table class generates a report element of type Table.
// This class is part of the visitor pattern,it is a visitable class
// that accepts to be visited(acceptVisitor).
// It will be visited to be printed in the project

public class Table extends Element {

    private Taula content;

    public Table(final Report report, final Taula table) {

        super(report);
        this.content = table;
    }

    public final Taula getContent() {
        return this.content;
    }

    public final void acceptVisitor(final FormatVisitor fv) {
        fv.visitTable(this);
    }


}
