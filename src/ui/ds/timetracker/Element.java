package ui.ds.timetracker;

// The abstract class Element is the basis for generating an
// element of the report.This class is part of the visitor pattern,
//  it is a visitable class.It will be visited to be printed in the project

public abstract class Element {
    private Report report;

    public Element(final Report report) {
        this.report = report;
    }

    abstract void acceptVisitor(FormatVisitor fv);
}
