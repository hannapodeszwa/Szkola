package pl.polsl.controller.teacherActions;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uwagi;
import pl.polsl.model.NoteModel;
import pl.polsl.model.SchoolClass;
import pl.polsl.model.Student;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherNoteController implements ParametrizedController {

    @FXML
    public TableView table;
//    public TableColumn<Uwagi, Text> columnDesc;
    public TableColumn<Uwagi, String> columnDesc;
    public ComboBox<String> comboboxClass;
    public ComboBox<String> comboboxStudent;
    public Button buttonDelete;
    public Button buttonAdd;
    public Button buttonBack;

    Integer id;
    List<Klasy> classList;
    List<Uczniowie> studentList;
    ObservableList<Uwagi> noteList;

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
            setStudents(0);
            setNote(0);
        }

    }

    public void clickButtonDelete() {

    }

    public void clickButtonAdd() {
    }

    void setStudents(Integer index){
        studentList = (new Student()).getStudentInClass(classList.get(index).getID());
        if(!studentList.isEmpty()){
            for (Uczniowie student : studentList) {
                comboboxStudent.getItems().add(student.getImie()+" " + student.getNazwisko());
            }
            comboboxStudent.getSelectionModel().select(0);
        }
    }

    public void chengeComboboxClass() {
        studentList.clear();
        setStudents(comboboxClass.getSelectionModel().getSelectedIndex());
    }

    void setNote(Integer index){
        noteList = FXCollections.observableArrayList((new NoteModel()).getStudentNote(studentList.get(index).getID()));



        columnDesc.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getTresc();
            Text desc = new Text(tym);
            desc.setWrappingWidth(200);

            return new ReadOnlyStringWrapper(desc.toString());
        });
        //columnDesc.setCellValueFactory(new PropertyValueFactory<>("tresc"));

        table.setItems(noteList);
    }

    public void chengeComboboxStudent() {
        noteList.clear();
        Integer index = comboboxStudent.getSelectionModel().getSelectedIndex();
        setNote(index);

    }

    public void clickButtonBack() throws IOException {
        Map params = new HashMap<String, String>();
        params.put("id", id);
        Main.setRoot("menu/TeacherMenuForm", params);
    }


}
