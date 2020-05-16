package ui.ds.timetracker;

// The subtitle class generates a report element of type subtitle.
// This class is part of the visitor pattern,it is a visitable class
// that accepts to be visited(acceptVisitor).
// It will be visited to be printed in the project

public class SubTitle extends Element {

    private String subTitle;

    public SubTitle(final Report report, final String subTitlee) {
        super(report);
        this.subTitle = subTitlee;
    }

    public final void acceptVisitor(final FormatVisitor fv) {
        fv.visitSubtTitle(this);
    }

    public final String getSubTitle() {
        return subTitle;
    }

}
