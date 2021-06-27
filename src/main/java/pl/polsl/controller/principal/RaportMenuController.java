package pl.polsl.controller.principal;
import javafx.event.ActionEvent;
import pl.polsl.Main;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.ParentModel;
import pl.polsl.model.Student;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.io.IOException;
import java.awt.print.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaportMenuController {
    public void clickButtonAverageSubject(ActionEvent event) throws IOException {
        Student s = new Student();
        Przedmioty p;
       //List l=s.getGradeFromSubject(p);
    }

    public void clickButtonSelectStudent(ActionEvent event) throws IOException {
        Student s = new Student();
        List l=s.getAllStudents();

        (new StudentsPrinter()).printingCall(l);
       // (new HelloWorldPrinter()).printingCall();
    }


    public void clickButtonBack(ActionEvent event) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", Roles.PRINCIPAL);
        Main.setRoot("menu/adminMenuForm.fxml", params, WindowSize.principalMenuForm);
    }

    public void clickButtonAverageGrade(ActionEvent actionEvent) {
    }
}


class HelloWorldPrinter implements Printable {

    private String textToPrint;

    public String getTextToPrint() {
        return textToPrint;
    }

    public void setTextToPrint(String textToPrint) {
        this.textToPrint = textToPrint;
    }


    int[] pageBreaks;  // array of page break line positions.

    /* Synthesise some sample lines of text */
    String[] textLines;
    private void initTextLines() {
        if (textLines == null) {
            int numLines=200;
            textLines = new String[numLines];
            for (int i=0;i<numLines;i++) {
                textLines[i]= "This is line number " + i;
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws
            PrinterException {

        Font font = new Font("Serif", Font.PLAIN, 10);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        if (pageBreaks == null) {
            initTextLines();
            int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
            int numBreaks = (textLines.length-1)/linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b=0; b<numBreaks; b++) {
                pageBreaks[b] = (b+1)*linesPerPage;
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Draw each line that is on this page.
         * Increment 'y' position by lineHeight for each line.
         */
        int y = 0;
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end   = (pageIndex == pageBreaks.length)
                ? textLines.length : pageBreaks[pageIndex];
        for (int line=start; line<end; line++) {
            y += lineHeight;
            g.drawString(textLines[line], 0, y);
        }

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void printingCall() {try {
        String cn = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(cn);
        }
        catch (Exception cnf) {
        }

        PrinterJob job = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PageFormat pf = job.pageDialog(aset);


        //PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }
}
    class StudentsPrinter implements Printable {
        List <Uczniowie>students;


        public int print(Graphics g, PageFormat pf, int page) throws
                PrinterException {
            Font font = new Font("Serif", Font.PLAIN, 10);
            FontMetrics metrics = g.getFontMetrics(font);
            int lineHeight = metrics.getHeight();

            if (page > 0) { /* We have only one page, and 'page' is zero-based */
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());

            /* Now we perform our rendering */
            //g.drawString((students.size(), 100, 100);
            int y=10;
            for(Uczniowie u: students)
            {
                g.drawString(u.getImie() + " " + u.getNazwisko(), 100, y);
                y+=lineHeight;
            }

            /* tell the caller that this page is part of the printed document */
            return PAGE_EXISTS;
        }

        public void printingCall(List <Uczniowie>students) {
            this.students=students;
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(this);
            boolean ok = job.printDialog();
            if (ok) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    /* The job did not successfully complete */
                }
            }
        }

}


