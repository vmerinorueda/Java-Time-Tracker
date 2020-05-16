package ui.ds.timetracker;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

// The formatTXTclass is responsible for printing the
// report in TXT format. To generate the report, it must
// be done from this class where, following the visitor pattern,
// each time we visit an element, this generates a component
// of our html web file.

public class FormatTXT implements FormatVisitor {
    private final Report report;

    public FormatTXT(final Report reportt) {
        this.report = reportt;
        //Collection<Activity> activities = this.report.getProject().getActivityList();
    }

    // In this method we will generate each element that
    // is within our list of elements to generate the report

    public final void generate() {
        for (Element e : this.report.getElements()) {
            e.acceptVisitor(this);
        }
    }

    public final void visitTitle(final Title title) {
        System.out.println(title.getTitle());
    }

    public final void visitSubtTitle(final SubTitle subTitle) {
        System.out.println(subTitle.getSubTitle());
    }

    public final void visitTable(final Table tb) {

        Collection table = tb.getContent().getTaula();
        Iterator itRows = table.iterator();
        Iterator itCols;

        while (itRows.hasNext()) {
            itCols = ((Collection) itRows.next()).iterator();
            while (itCols.hasNext()) {
                System.out.print(itCols.next().toString() + " ");
            }
            System.out.println();
        }

    }

    public final void visitSeparator(final Separator separator) {
        System.out.println("-------------------------------------"
            + "---------------------------------------------------"
            + "--------------");
    }

    public final void visitPeriod(final Period period) {
        Date date = new Date();
        //period.acceptVisitor(this);
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss",
        //       Locale.ENGLISH);

        System.out.println("Data");
        System.out.println("Desde " + period.getDateStart());
        System.out.println("Fins a " + period.getDateEnd());
        System.out.println("Data de generació de l'informe: " + date);
    }

}
