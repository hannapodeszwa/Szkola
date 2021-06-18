package pl.polsl.controller.teacherActions;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Uwagi;
import pl.polsl.model.NoteModel;
import pl.polsl.utils.Roles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherAddNewNote implements ParametrizedController {
    public TextField textField;
    public Button buttonCancel;
    public Button buttonAdd;
    public Label labelError;

    Integer id;
    Integer idStudent;
    Integer classCombobox;
    Integer studentCombobox;

    public void initialize(){
        labelError.setVisible(false);
    }

    @Override
    public void receiveArguments(Map params) {
        id = (Integer) params.get("id");
        idStudent = (Integer) params.get("idStudent");
        classCombobox = (Integer) params.get("classCombobox");
        studentCombobox = (Integer) params.get("studentCombobox");
    }

    Map<String, Object> params (){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("idStudent", idStudent);
        params.put("classCombobox", classCombobox);
        params.put("studentCombobox", studentCombobox);
        return params;
    }

    public void clickButtonCancel(ActionEvent actionEvent) throws IOException {
        Main.setRoot("teacherActions/teacherNoteForm", params());
    }

    public void clickButtonAdd(ActionEvent actionEvent) throws IOException {
        if(textField.getText()!="") {
            (new NoteModel()).persist(new Uwagi(textField.getText(), idStudent, id));
            Main.setRoot("teacherActions/teacherNoteForm", params());
        }
        else{
            labelError.setText("Nie uzupełniono opisu.");
            labelError.setVisible(true);
        }
    }


}
