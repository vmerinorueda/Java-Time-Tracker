package ui.ds.timetracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;


// The abstract Report class is the basis for generating
// the two different types of reports there are, briefReport
// and detailedRreport. These reports are generated with the
// data extracted between two dates (period). To generate these
// reports we must insert the elements that compose it in a
// list to be able to treat them.

public abstract class Report {

    private final Collection<Element> elements = new ArrayList<>();
    private Project project; // root
    private Date dateStart;
    private Date dateEnd;
    private Taula table;


    public final Taula getTable() {
        return table;
    }

    public final void setTable(final Taula tablee) {
        this.table = tablee;
    }


    public Report(final Project projectt, final String dayStart,
                  final String start, final String dayEnd, final String end) {
        this.project = projectt;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
                Locale.ENGLISH);
        String datePlusHourStart = dayStart + " " + start;
        String datePlusHourEnd = dayEnd + " " + end;

        try {
            this.dateStart = sdf.parse(datePlusHourStart);
            this.dateEnd = sdf.parse(datePlusHourEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        generateReport();
        Logger logger = LoggerFactory.getLogger(Client.class);
        logger.info("Informe generado");
    }


    abstract void generateReport();

    public final Collection<Element> getElements() {
        return elements;
    }

    private void addElement(final Element element) {
        this.elements.add(element);
    }

    public final void generateHeader(final String headerTitle) {
        Title title = new Title(this, headerTitle);
        addElement(title);
    }

    public final void generateSubTitle(final String title) {
        SubTitle subtitle = new SubTitle(this, title);
        addElement(subtitle);
    }

    public final void generateSeparator() {
        Element separator = new Separator(this);
        addElement(separator);
    }

    public final void generatePeriod() {
        Element period = new Period(this, dateStart, dateEnd);
        addElement(period);
    }

    public final void generateTable(final Taula taula) {
        Element tablee = new Table(this, taula);
        addElement(tablee);
    }

    public final Project getProject() {
        return project;
    }

    public final Date getDateStart() {
        return dateStart;
    }

    public final Date getDateEnd() {
        return dateEnd;
    }

    public final void generateTableReport(final ArrayList<String> row) {
        this.table.afegeixFila(row);
    }

}
