package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Present;
import pl.polsl.model.SchoolClass;
import pl.polsl.model.Student;

import java.util.List;
import java.util.Map;

public class studentPresenceController implements ParametrizedController {

    @FXML
    public TableView table;
    public TableColumn columnDate;
    public TableColumn columnHour;
    public TableColumn columnCheck;

    public enum md {Parent, Student}
    private StudentMenuController.md mode;

    @Override
    public void receiveArguments(Map params) {
        String a = (String)params.get("mode");
        if (a == "Parent") {
            mode = StudentMenuController.md.Parent;
        }
        else {
            mode = StudentMenuController.md.Student;
        }
    }


    public void initialize()
    {

        Present s= new Present();
        List<Nieobecnosci> l=s.displayPresent();

        columnDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        //columnHour.setCellValueFactory(new PropertyValueFactory<>("godzina"));
        columnCheck.setCellValueFactory(new PropertyValueFactory<>("czyUsprawiedliwiona"));



        for (Nieobecnosci p: l) {
            table.getItems().add(p);
        }


    }
}
