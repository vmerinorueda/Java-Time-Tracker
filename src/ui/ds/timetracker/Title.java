package ui.ds.timetracker;


// The Title class generates a report element of type Title.
// This class is part of the visitor pattern,it is a visitable class
// that accepts to be visited(acceptVisitor).
// It will be visited to be printed in the project

public class Title extends Element {


    private final String title;

    public Title(final Report report, final String titlee) {
        super(report);
        this.title = titlee;
    }

    public final void acceptVisitor(final FormatVisitor fv) {
        fv.visitTitle(this);
    }

    public final String getTitle() {
        return this.title;
    }

}
