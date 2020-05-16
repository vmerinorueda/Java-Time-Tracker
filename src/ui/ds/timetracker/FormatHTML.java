package ui.ds.timetracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


 // The formatHTML class is responsible for printing the
 // report in HTML format. To generate the report, it must
 // be done from this class where, following the visitor pattern,
 // each time we visit an element, this generates a component
 // of our html web file.
public class FormatHTML implements FormatVisitor {
    private final Report report;
    private String file;
    private String archive;
    private PaginaWeb web;
     private static Logger logger = LoggerFactory.getLogger(Project.class);


    private SimpleDateFormat formatDate
            = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
            Locale.ENGLISH);


    public FormatHTML(final Report reportt) {
        this.report = reportt;
        this.web = new PaginaWeb();
    }

    // In this method we will generate each element that
    // is within our list of elements to generate the report

    public final void generate() {
        for (Element e : this.report.getElements()) {
            e.acceptVisitor(this);
        }
        printReport();
    }

    public final void visitTitle(final Title title) {
        web.afegeixHeader(title.getTitle(), 1, true);
    }

    public final void visitSubtTitle(final SubTitle subTitle) {
        web.afegeixHeader(subTitle.getSubTitle(), 2, false);
    }

    public final void visitTable(final Table table) {
        Taula taula = table.getContent();

        web.afegeixTaula(taula.getTaula(), true, false);

        try {
            archive = web.getPaginaWeb().toString();
            file = "index.html";

            FileWriter fw = new FileWriter(new File(file));
            fw.write(archive);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void visitSeparator(final Separator separator) {

        web.afegeixLiniaSeparacio();
    }

    public final void visitPeriod(final Period period) {
        Date date = new Date();
        Taula periodTable;
        periodTable = new Taula(0, 0);
        ArrayList<String> filaTaula = new ArrayList<>();
        filaTaula.add(" ");
        filaTaula.add("Data");
        periodTable.afegeixFila(filaTaula);

        filaTaula = new ArrayList<>();
        filaTaula.add("Desde");
        filaTaula.add(formatDate.format((period.getDateStart())));
        periodTable.afegeixFila(filaTaula);

        filaTaula = new ArrayList<>();
        filaTaula.add("Fins a");
        filaTaula.add(formatDate.format((period.getDateEnd())));
        periodTable.afegeixFila(filaTaula);

        filaTaula = new ArrayList<>();
        filaTaula.add("Data de generació de l'informe");
        filaTaula.add(formatDate.format((date)));
        periodTable.afegeixFila(filaTaula);
        web.afegeixTaula(periodTable.getTaula(), true, true);

    }

    private void printReport() {
        try {
            archive = web.getPaginaWeb().toString();
            file = "index.html";
            web.escriuPagina();

            FileWriter fw = new FileWriter(new File(file));
            fw.write(archive);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

