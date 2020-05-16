package ui.ds.timetracker;

import java.text.ParseException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The DetailedReport class is responsible for generating this
// type of report, generating the elements that make up.
// To generate the different types of tables with their data,
// detailed report is responsible for activating the corresponding
// visitor pattern with the class TreeVisitor
public class DetailedReport extends Report {

    private static final String TITLE = "INFORME DETALLAT";
    private static Logger logger = LoggerFactory.getLogger(Project.class);


    public DetailedReport(final Project project, final String dayStart,
                          final String start, final String dayEnd,
                          final String end) throws ParseException {
        super(project, dayStart, start, dayEnd, end);
        logger.info("Detailed Report created");

    }


    public final void generateReport() {

        logger.info("Generating detailed report");

        Taula projectsTable = new Taula(0, 0);
        Taula subProjectsTable = new Taula(0, 0);
        Taula tasksTable = new Taula(0, 0);
        Taula intervalsTable = new Taula(0, 0);
        setTable(projectsTable);


        ArrayList<String> headerTable = new ArrayList<>();

        headerTable.add("Projecte");
        headerTable.add("Data d'inici");
        headerTable.add("Data final");
        headerTable.add("Temps total");

        projectsTable.afegeixFila(headerTable);
        subProjectsTable.afegeixFila(headerTable);

        ArrayList<String> headerTableTasks = new ArrayList<>();

        headerTableTasks.add("Projecte");
        headerTableTasks.add("Tasca");
        headerTableTasks.add("Data d'inici");
        headerTableTasks.add("Data final");
        headerTableTasks.add("Temps total");

        tasksTable.afegeixFila(headerTableTasks);

        ArrayList<String> headerTableIntervals = new ArrayList<>();

        headerTableIntervals.add("Projecte");
        headerTableIntervals.add("Tasca");
        headerTableIntervals.add("Interval");
        headerTableIntervals.add("Data inici");
        headerTableIntervals.add("Data final");
        headerTableIntervals.add("Temps total");

        intervalsTable.afegeixFila(headerTableIntervals);

        TreeVisitor treeV = new TreeVisitor(this);


        generateSeparator();
        generateHeader(TITLE);
        generateSeparator();
        generateSubTitle("Període");
        generatePeriod();

        generateSeparator();
        generateSubTitle("Projectes arrel");

        treeV.saveProjects(getProject());
        generateTable(projectsTable);

        generateSeparator();
        generateSubTitle("Subprojectes");


        setTable(subProjectsTable);
        treeV.visitProject(getProject());

        generateTable(subProjectsTable);
        generateSeparator();
        generateSubTitle("Tasques");

        setTable(tasksTable);
        treeV.saveTasks(getProject());

        generateTable(tasksTable);
        generateSeparator();
        generateSubTitle("Intervals");
        setTable(intervalsTable);
        treeV.saveIntervals(getProject());
        generateTable(intervalsTable);
        generateSeparator();
        logger.info("Detailed report generated");

    }
}
