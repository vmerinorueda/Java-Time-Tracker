package ui.ds.timetracker;

//FormatVisitor is an interface that specifies the methods
// that the classes that implement it must have (FormatHTML and FormatTXT)
public interface FormatVisitor {

    void visitTitle(Title title);

    void visitSubtTitle(SubTitle subTitle);

    void visitTable(Table table);

    void visitSeparator(Separator separator);

    void visitPeriod(Period period);

    // In this method we will generate each element that
    // is within our list of elements to generate the report

    void generate();
}
