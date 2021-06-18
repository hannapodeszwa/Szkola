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
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.ArrayList;
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
        classList = (new Teacher()).checkTeacherClasses(id);
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
        comboboxStudent.getItems().clear();
        setStudents(comboboxClass.getSelectionModel().getSelectedIndex());
    }

    public Integer getWitdh(String text){
        return (int)(new Text(text)).getBoundsInLocal().getWidth();
    }

    public String wrapString(String wraping,Integer wid){
        Integer width = wid-10;
        if(getWitdh(wraping) < width) {
            return wraping;
        }
        String result = "";
        String[] words = wraping.split(" ");
        List<Integer> sizewords = new ArrayList<Integer>();

        for(String word : words){
            sizewords.add(getWitdh(word));
        }
        Integer act = 0;
        Integer i = 0;
        for(Integer size : sizewords) {

            if(size + act < width){
                result += words[i] + " ";
                act +=size+3;
            }
            else if (size + act >= width) {
                result += "\n" + words[i]+" ";
                act=size+3;
            }
            else {
                result += "\n" + words[i] + "\n";
                act = 0;
            }
            i++;
        }


        return result;
    }

    void setNote(Integer index){
        noteList = FXCollections.observableArrayList((new NoteModel()).getStudentNote(studentList.get(index).getID()));


        columnDesc.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getTresc();
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnDesc.getWidth()));
        });

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
