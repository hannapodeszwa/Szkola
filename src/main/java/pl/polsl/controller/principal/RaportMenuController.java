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
import java.util.ArrayList;
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
        List<Uczniowie> l=s.getAllStudents();

        ArrayList<String> textToPrint = new ArrayList<>();

        for (Uczniowie u : l) {
            textToPrint.add(u.getImie() + " " + u.getNazwisko());
        }

        (new HelloWorldPrinter()).printingCall(textToPrint);
    }


    public void clickButtonBack(ActionEvent event) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", Roles.PRINCIPAL);
        Main.setRoot("menu/adminMenuForm", params, WindowSize.principalMenuForm);
    }

    public void clickButtonAverageGrade(ActionEvent actionEvent) {
    }
}


class HelloWorldPrinter implements Printable {

    private ArrayList<String> textLines;

    public ArrayList<String> getTextToPrint() {
        return textLines;
    }

    public void setTextToPrint(ArrayList<String> textToPrint) {
        this.textLines = textToPrint;
    }

    private int[] pageBreaks;

    private int margin = 60;

    public int print(Graphics g, PageFormat pf, int pageIndex) throws
            PrinterException {

        Font font = new Font("Serif", Font.PLAIN, 10);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        if (pageBreaks == null) {
            int linesPerPage = (int)((pf.getImageableHeight() - margin * 2)/lineHeight);
            int numBreaks = (textLines.size()-1)/linesPerPage;
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

        int y = margin;
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end   = (pageIndex == pageBreaks.length)
                ? textLines.size() : pageBreaks[pageIndex];
        for (int line=start; line<end; line++) {
            y += lineHeight;
            g.drawString(textLines.get(line), 50, y);
        }

        return PAGE_EXISTS;
    }

    public void printingCall(ArrayList<String> text) {try {
        this.setTextToPrint(text);
        String cn = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(cn);
        }
        catch (Exception cnf) {
        }

        PrinterJob job = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

        Book book = new Book();
        book.append(this, job.defaultPage());
        job.setPageable(book);

        job.setPrintable(this);
        boolean ok = job.printDialog(aset);
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
            }
        }
    }
}

