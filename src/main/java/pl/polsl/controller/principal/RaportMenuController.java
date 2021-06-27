package pl.polsl.controller.principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.Oceny;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;
import pl.polsl.model.Subject;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.text.TabableView;
import java.io.IOException;
import java.awt.print.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaportMenuController {

    @FXML
    private Button highestAvg;
    @FXML
    private TableView<Przedmioty> tableSubjects;
    @FXML
    private TableColumn<Przedmioty, String> nameC;
    @FXML
    public void initialize()
    {
        displayTableSubjects();
        tableSubjects.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                highestAvg.setDisable(false);
            }
            else {
                highestAvg.setDisable(true);
            }
        });
    }

    private void displayTableSubjects()
    {
        tableSubjects.getItems().clear();
        Subject s= new Subject();
        List l=s.displaySubjects();
        nameC.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        for (Object p: l) {
            tableSubjects.getItems().add((Przedmioty) p);
        }
    }

    public void clickButtonAverageSubject(ActionEvent event) throws IOException {
        Student s = new Student();
        Przedmioty p = tableSubjects.getSelectionModel().getSelectedItem();
        List l=s.getGradeFromSubject(p.getID());

        Map<Uczniowie, Float> sum = new HashMap<>();
        Map<Uczniowie, Integer> size = new HashMap<>();

        for(Object o: l)
        {
            if((sum.keySet().isEmpty()) || !(sum.keySet().contains((Uczniowie) o)))
            {
                sum.put((Uczniowie) o, ((Oceny) o).getOcena());
                size.put((Uczniowie) o, 1);
            }
            else
            {
                int oldSize = size.get((Uczniowie) o);
                float oldGrade = sum.get((Uczniowie) o);

                size.remove((Uczniowie) o);
                sum.remove((Uczniowie) o);

                size.put((Uczniowie) o, oldSize+1);
                sum.put((Uczniowie) o, oldGrade + ((Oceny) o).getOcena());
            }
        }

        Map<Uczniowie, Float> avg = new HashMap<>();
        for(Uczniowie u :sum.keySet())
        {
            avg.put(u,sum.get(u)/size.get(u));
        }

        ArrayList list = new ArrayList();
        String string = "Średnia z przedmiotu: " + p.getNazwa();
        avg.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(m -> list.add(m)/*string += m.getKey().getImie() + " " + m.getKey().getNazwisko() +": " + m.getValue()*/);

        (new RaportPrinter()).printingCall(list);
    }

    public void clickButtonSelectStudent(ActionEvent event) throws IOException {
        Student s = new Student();
        List<Uczniowie> l=s.getAllStudents();

        ArrayList<String> textToPrint = new ArrayList<>();

        textToPrint.add("Lista studentów:");
        textToPrint.add("");
        for (Uczniowie u : l) {
            textToPrint.add(u.getImie() + " " + u.getNazwisko());
        }

        (new RaportPrinter()).printingCall(textToPrint);
    }


    public void clickButtonBack(ActionEvent event) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", Roles.PRINCIPAL);
        Main.setRoot("menu/adminMenuForm", params, WindowSize.principalMenuForm);
    }

    public void clickButtonAverageGrade(ActionEvent actionEvent) {
        Student s = new Student();
        List<Uczniowie> l=s.getAllStudents();
        Map<Uczniowie, Float> average = new HashMap<>();

        for(Uczniowie u :l)
        {
            List l2=s.getGradeFromStudent(u.ID);

            Map<Przedmioty, Float> sum = new HashMap<>();
            Map<Przedmioty, Integer> size = new HashMap<>();

            for(Object o: l2)
            {
                if((sum.keySet().isEmpty()) || !(sum.keySet().contains((Przedmioty) o)))
                {
                    sum.put((Przedmioty) o, ((Oceny) o).getOcena());
                    size.put((Przedmioty) o, 1);
                }
                else
                {
                    int oldSize = size.get((Przedmioty) o);
                    float oldGrade = sum.get((Przedmioty) o);

                    size.remove((Przedmioty) o);
                    sum.remove((Przedmioty) o);

                    size.put((Przedmioty) o, oldSize+1);
                    sum.put((Przedmioty) o, oldGrade + ((Oceny) o).getOcena());
                }
            }

            Map<Przedmioty, Float> avg = new HashMap<>();
            float allAvg=0;
            for(Przedmioty p :sum.keySet())
            {
                float a =sum.get(u)/size.get(u);
                avg.put(p,a);
                allAvg +=a;
            }

            average.put(u, allAvg/avg.keySet().size());
        }
        ArrayList list = new ArrayList();
        average.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(m -> list.add(m));
    }
}


class RaportPrinter implements Printable {

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

