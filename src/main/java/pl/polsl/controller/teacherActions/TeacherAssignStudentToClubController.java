package pl.polsl.controller.teacherActions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.SchoolClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherAssignStudentToClubController implements ParametrizedController {
    Integer clubId;
    Integer loggedTeacherId;

    List<Klasy> classList;

    @FXML
    private ComboBox comboboxClass;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private TableColumn<Uczniowie, String> columnName;
    @FXML
    private TableColumn<Uczniowie, String> columnSurname;



    @Override
    public void receiveArguments(Map params) {
        loggedTeacherId = (Integer) params.get("id");
        clubId = (Integer) params.get("clubId");

        classList = (new SchoolClass()).displayClass();
        if(!classList.isEmpty()) {
            for (Klasy cl : classList) {
                comboboxClass.getItems().add(cl.getNumer());
            }
            comboboxClass.getSelectionModel().select(0);

        }
    }

    public void clickButtonBack(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherClubForm", params);
    }

    public void clickButtonAdd(ActionEvent event) throws IOException {

    }

    public void changeComboboxClass(ActionEvent event) throws  IOException {

    }
}
