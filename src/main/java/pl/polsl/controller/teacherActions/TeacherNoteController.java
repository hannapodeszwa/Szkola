package pl.polsl.controller.teacherActions;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uwagi;
import pl.polsl.model.SchoolClass;
import pl.polsl.model.Student;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherNoteController implements ParametrizedController {

    @FXML
    public AnchorPane table;
    public TableColumn<Text, Uwagi> columnDesc;
    public ComboBox<String> comboboxClass;
    public ComboBox<String> comboboxStudent;
    public Button buttonDelete;
    public Button buttonAdd;
    public Button buttonBack;

    Integer id;
    List<Klasy> classList;
    List<Uczniowie> studentList;
    List<Klasy> noteList;

    @Override
    public void receiveArguments(Map params) {
        id = (Integer) params.get("id");
    }

    public void initialize(){
        classList = (new SchoolClass()).displayClass();
        if(!classList.isEmpty()) {
            for (Klasy cl : classList) {
                comboboxClass.getItems().add(cl.getNumer());
            }
            comboboxClass.getSelectionModel().select(0);
        }

    }

    public void clickButtonDelete() {

    }

    public void clickButtonAdd() {
    }

    void setStudents(){

    }

    public void chengeComboboxClass() {
        Integer index = comboboxClass.getSelectionModel().getSelectedIndex();
        studentList = (new Student()).getStudentInClass(classList.get(index).getID());
        if(!studentList.isEmpty()){
            for (Uczniowie student : studentList) {
                comboboxStudent.getItems().add(student.getImie()+" " + student.getNazwisko());
            }
            comboboxStudent.getSelectionModel().select(0);
        }
    }

    public void chengeComboboxStudent() {
        Integer index = comboboxStudent.getSelectionModel().getSelectedIndex();

    }

    public void clickButtonBack() throws IOException {
        Map params = new HashMap<String, String>();
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm", params);
    }


}
