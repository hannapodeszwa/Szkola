package pl.polsl.controller.teacherActions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherAssignStudentToClubController implements ParametrizedController {
    Integer loggedTeacherId;

    @FXML
    private ComboBox comboboxClass;
    private TableView table;

    @Override
    public void receiveArguments(Map params) {
        loggedTeacherId = (Integer) params.get("id");
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
