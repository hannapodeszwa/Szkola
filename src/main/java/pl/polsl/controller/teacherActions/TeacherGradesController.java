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
import javafx.scene.text.Text;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.*;

public class TeacherGradesController implements ParametrizedController {

    @FXML
    public TableView<Oceny> table;
//    public TableColumn<Uwagi, Text> columnDesc;
    public TableColumn<Oceny, String> columnDesc;
    public TableColumn<Oceny, String> columnDate;
    public TableColumn<Oceny, String> columnGrade;
    public TableColumn<Oceny, String> columnValue;
    public ComboBox<String> comboboxClass;
    public ComboBox<String> comboboxStudent;
    public ComboBox<String> comboboxSubject;
    public Button buttonDelete;
    public Button buttonAdd;
    public Button buttonBack;

    Integer id;
    List<Klasy> classList;
    ObservableList<Uczniowie> studentList;
    ObservableList<Przedmioty> subjectsList;
    ObservableList<Oceny> gradeList;

    @Override
    public void receiveArguments(Map params) {
        id = (Integer) params.get("id");
        classList = (new Teacher()).checkTeacherClasses(id);
        subjectsList = (FXCollections.observableArrayList((new Teacher()).checkTeacherSubjects(id)));

        if(!subjectsList.isEmpty()) {
            for (Przedmioty sl : subjectsList) {
                comboboxSubject.getItems().add(sl.getNazwa());
            }
            comboboxSubject.getSelectionModel().select(0);
        }

        if(!classList.isEmpty()) {
            for (Klasy cl : classList) {
                comboboxClass.getItems().add(cl.getNumer());
            }
            comboboxClass.getSelectionModel().select(0);
            setStudents(0);
            setGrade();
        }

    }

    public void clickButtonDelete() {
        Oceny o = table.getSelectionModel().getSelectedItem();
        (new Grade()).delete(o);
    }

    public void clickButtonAdd() throws IOException{
        Map params = new HashMap<String, String>();
        params.put("teacherId", id);

        Integer studentId = studentList.get(comboboxStudent.getSelectionModel().getSelectedIndex()).getID();
        params.put("studentId", studentId);

        Integer subjectId = subjectsList.get(comboboxSubject.getSelectionModel().getSelectedIndex()).getID();
        params.put("subjectId", subjectId);

        String surname = studentList.get(comboboxStudent.getSelectionModel().getSelectedIndex()).getNazwisko();
        params.put("surname", surname);

        String name = studentList.get(comboboxStudent.getSelectionModel().getSelectedIndex()).getImie();
        params.put("name", name);

        String subject = subjectsList.get(comboboxSubject.getSelectionModel().getSelectedIndex()).getNazwa();
        params.put("subject",subject);

        Main.setRoot("teacherActions/teacherAddNewGradeForm", params);
    }

    void setStudents(Integer index){
        studentList = FXCollections.observableArrayList((new Student()).getStudentInClass(classList.get(index).getID()));
        if(!studentList.isEmpty()){
            for (Uczniowie student : studentList) {
                comboboxStudent.getItems().add(student.getImie()+" " + student.getNazwisko());
            }
            comboboxStudent.getSelectionModel().select(0);
        }
    }

    public void changeComboboxClass() {
        studentList.clear();
        comboboxStudent.getItems().clear();
        setStudents(comboboxClass.getSelectionModel().getSelectedIndex());
    }

    public void changeComboboxSubject() {
        gradeList.clear();
        setGrade();
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

    void setGrade(){
        gradeList = FXCollections.observableArrayList((new Grade()).checkStudentGrades(studentList.get(comboboxStudent.getSelectionModel().getSelectedIndex()), subjectsList.get(comboboxSubject.getSelectionModel().getSelectedIndex())));


        columnDesc.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getOpis();
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnDesc.getWidth()));
        });

        columnDate.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getData().toString();
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnDate.getWidth()));
        });

        columnValue.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getWaga().toString().toString();
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnValue.getWidth()));
        });

        columnGrade.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getOcena().toString();
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnGrade.getWidth()));
        });

        table.setItems(gradeList);
    }

    public void changeComboboxStudent() {
        gradeList.clear();
        setGrade();

    }

    public void clickButtonBack() throws IOException {
        Map params = new HashMap<String, String>();
        params.put("id", id);
        Main.setRoot("menu/teacherMenuForm", params);
    }


}
