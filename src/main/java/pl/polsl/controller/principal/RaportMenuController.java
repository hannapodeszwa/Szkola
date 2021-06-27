package pl.polsl.controller.principal;
import javafx.event.ActionEvent;
import pl.polsl.Main;
import pl.polsl.entities.Przedmioty;
import pl.polsl.model.ParentModel;
import pl.polsl.model.Student;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

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
        List l=s.getGradeFromSubject(p);
    }

    public void clickButtonSelectStudent(ActionEvent event) throws IOException {
        Student s = new Student();
        List l=s.getAllStudents();

        (new HelloWorldPrinter()).printingCall();
    }


    public void clickButtonBack(ActionEvent event) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", Roles.PRINCIPAL);
        Main.setRoot("menu/adminMenuForm.fxml", params, WindowSize.principalMenuForm);
    }

}


class HelloWorldPrinter implements Printable {

    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        g.drawString("Hello world!", 100, 100);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void printingCall() {
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


