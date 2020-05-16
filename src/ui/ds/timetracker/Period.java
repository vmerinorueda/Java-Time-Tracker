package ui.ds.timetracker;

import java.util.Date;

// The Period class generates a report element of type period.
// This class is part of the visitor pattern,it is a visitable class
// that accepts to be visited(acceptVisitor).
// It will be visited to be printed in the project

public class Period extends Element {

    private Date dateStart;
    private Date dateEnd;

    public Period(final Report report, final Date dateStartt,
                  final Date dateEndd) {
        super(report);
        this.dateStart = dateStartt;
        this.dateEnd = dateEndd;
    }

    public final void acceptVisitor(final FormatVisitor fv) {
        fv.visitPeriod(this);
    }

    public final Date getDateStart() {
        return dateStart;
    }

    public final Date getDateEnd() {
        return dateEnd;
    }

}
