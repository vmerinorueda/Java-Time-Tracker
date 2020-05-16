package ui.ds.timetracker;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

    private static final int REFRESH_TIME = 2000;
    private static Logger logger = LoggerFactory.getLogger(Project.class);

    public static void main(String[] args) {
        Project root = new Project("Root");
        Project project1 = new Project("P1");
        Project project2 = new Project("P2");
        root.addActivity(project1);
        root.addActivity(project2);
        Project project12 = new Project("P1.2", project1);
        project1.addActivity(project12);
        Task task1 = new Task("T1", project1);
        Task task2 = new Task("T2", project1);
        project1.addActivity(task1);
        project1.addActivity(task2);
        Task task3 = new Task("T3", project2);
        Task task4 = new Task("T4", project12);
        project2.addActivity(task3);
        project12.addActivity(task4);
        System.out.println("The application output will update "
                + "with a periodicity of " + REFRESH_TIME + " ms.");

        //new PrinterVisitor(root);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        try {
            //testA1(task3, task2);
            //testA2(task3, task2, task1);
            testA3(task1, task2, task3, task4);

        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.warn(" Error " + e + " when creating the test ");
        }

        try {

            calendar.add(Calendar.SECOND, 4);
            Date start= calendar.getTime();
            calendar.add(Calendar.SECOND, 10);
            Date end = calendar.getTime();
            SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss:SSS",
                    Locale.ENGLISH);

            FormatVisitor formatHTML = new FormatHTML(new DetailedReport(root,
                    "15-05-2020", formatDate.format(start),
                    "15-05-2020", formatDate.format(end)));
            formatHTML.generate();
            
            FormatVisitor formatTXT = new FormatTXT(new DetailedReport(root,
                    "15-05-2020", formatDate.format(start),
                    "15-05-2020", formatDate.format(end)));
            formatTXT.generate();
            
        } catch (ParseException e) {
            e.printStackTrace();
            logger.warn(" Error " + e + " when generating the date format ");
        }

        serializableMethod(project1);
    }

    // We created an instance of the Timer, in charge of
    // notifying the changes of the observable.
    // create a TickGenerator that will contain a thread parallel
    // to the main thread which will call to the Timer class with each
    // refresh time (first parameter)
    // We perform a test starting and stopping tasks.
    public static void testA1(Task task3, Task task2)
            throws InterruptedException {
        System.out.println(" Nom \t   Temps inici \t\t\t"
                + " Temps final\t\tDurada (hh:mm:ss)");
        System.out.println("-----+---------------------+---------"
                + "------------+-------------------");

        TimerClock tc = TimerClock.getInstance();
        new TickGenerator(REFRESH_TIME, tc);

        task3.startTask();
        Thread.sleep(3100);
        task3.stopTask();
        Thread.sleep(7000);
        task2.startTask();
        Thread.sleep(10100);
        task2.stopTask();
        task3.startTask();
        Thread.sleep(2100);
        task3.stopTask();
    }

    // We perform the same action as with the testA1 method but
    // generating a different test starting and stopping tasks.
    private static void testA2(Task task3, Task task2, Task task1)
            throws InterruptedException {
        System.out.println("Nom \tTemps inici"
                + " \t\tTemps final \t\tDurada (hh:mm:ss)");
        System.out.println("-----+---------------------+-----------"
                + "----------+-------------------");

        TimerClock tc = TimerClock.getInstance();
        new TickGenerator(REFRESH_TIME, tc);

        task3.startTask();
        Thread.sleep(4050);
        task2.startTask();
        Thread.sleep(2050);
        task3.stopTask();
        Thread.sleep(2050);
        task1.startTask();
        Thread.sleep(4050);
        task1.stopTask();
        Thread.sleep(2050);
        task2.stopTask();
        Thread.sleep(4000);
        task3.startTask();
        Thread.sleep(2000);
        task3.stopTask();
    }

    private static void testA3(Task task1, Task task2, Task task3, Task task4)
            throws InterruptedException {

        TimerClock tc = TimerClock.getInstance();
        new TickGenerator(REFRESH_TIME, tc);

        task1.startTask();
        task4.startTask();
        Thread.sleep(4000);
        task1.stopTask();
        task2.startTask();
        Thread.sleep(6000);
        task2.stopTask();
        task4.stopTask();
        task3.startTask();
        Thread.sleep(4000);
        task3.stopTask();
        task2.startTask();
        Thread.sleep(2000);
        task3.startTask();
        Thread.sleep(4000);
        task2.stopTask();
        task3.stopTask();
    }

    // This method is responsible for making the system
    // serializable by saving the data in a .ser file.
    private static void serializableMethod(Project root) {
        try {
            File dir = new File("data");
            boolean isDirCreated = dir.exists();
            if (!isDirCreated) {
                isDirCreated = dir.mkdir();
            }
            if (isDirCreated) {
                FileOutputStream fos =
                        new FileOutputStream("./data/" + "object" + ".ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(root);
                oos.close();
                fos.close();

               logger.info("Serialized data is saved in /data/"
                        + "object" + ".ser");
            }

        } catch (IOException i) {
            i.printStackTrace();
           logger.warn(" Error " + i + " when serializing the data ");
        }
    }

    // This method is responsible for deserialize the data in the .ser file
    private static Project deserializableMethod() {
        Project loadRoot = null;
        try {
            File dir = new File("data");
            boolean isDirCreated = dir.exists();
            if (isDirCreated) {
                FileInputStream fis =
                        new FileInputStream("./data/" + "object" + ".ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                loadRoot = (Project) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (IOException i) {
            i.printStackTrace();
            logger.warn(" Error " + i + " when deserializing the data ");

        } catch (ClassNotFoundException c) {
            c.printStackTrace();

        }

        return loadRoot;
    }

}
