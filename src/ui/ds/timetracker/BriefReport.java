package ui.ds.timetracker;

import java.text.ParseException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// The BriefReport class is responsible for generating this
// type of report, generating the elements that make up.
// To generate the different types of tables with their data,
// detailed report is responsible for activating the corresponding
// visitor pattern with the class TreeVisitor

public class BriefReport extends Report {

    private static final String TITLE = "INFORME BREU";
    private static Logger logger = LoggerFactory.getLogger(Project.class);


    public BriefReport(final Project project, final String dayStart,
                       final String start, final String dayEnd,
                       final String end) throws ParseException {
        super(project, dayStart, start, dayEnd, end);
        logger.info("Brief Report created");

    }

    public final void generateReport() {

        logger.info("Generating breif report");
        setTable(new Taula(0, 0));
        ArrayList<String> headerTable = new ArrayList<>();

        headerTable.add("Projecte");
        headerTable.add("Data d'inici");
        headerTable.add("Data final");
        headerTable.add("Temps total");

        getTable().afegeixFila(headerTable);

        TreeVisitor treeV = new TreeVisitor(this);
        treeV.visitProject(getProject());


        generateSeparator();
        generateHeader(TITLE);
        generateSeparator();
        generateSubTitle("Període");
        generatePeriod();
        generateSeparator();
        generateSubTitle("Projectes arrel");
        generateTable(getTable());
        generateSeparator();
        logger.info("Breif report generated");

    }

}
