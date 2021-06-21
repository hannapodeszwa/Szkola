package pl.polsl.controller.teacherActions;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.*;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.*;

public class TeacherGradesController implements ParametrizedController {

    @FXML
    private Button buttonAverage;
    @FXML
    private TableView<Oceny> table;
    @FXML
    private TableColumn<Oceny, String> columnDesc;
    @FXML
    private TableColumn<Oceny, String> columnDate;
    @FXML
    private TableColumn<Oceny, String> columnGrade;
    @FXML
    private TableColumn<Oceny, String> columnValue;
    @FXML
    private ComboBox<String> comboboxClass;
    @FXML
    private ComboBox<String> comboboxStudent;
    @FXML
    private ComboBox<String> comboboxSubject;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonBack;
    @FXML
    private Label labelAerage;

    Integer id;
    List<Klasy> classList;
    ObservableList<Uczniowie> studentList;
    ObservableList<Przedmioty> subjectsList;
    ObservableList<Oceny> gradeList;

    @Override
    public void receiveArguments(Map<String, Object> params) {
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
        Map<String, Object> params = new HashMap<>();

        params.put("teacherId", id);

        int index = comboboxStudent.getSelectionModel().getSelectedIndex();
        params.put("student", studentList.get(index));
        params.put("subject", subjectsList.get(index));
        Main.setRoot("teacherActions/teacherAddNewGradeForm", params, WindowSize.teacherAddNewGradeForm);
    }

    void setStudents(Integer index){
        studentList = FXCollections.observableArrayList((new Student()).getStudentInClass(classList.get(index).getID()));
        if(!studentList.isEmpty()){
            for (Uczniowie student : studentList) {
                comboboxStudent.getItems().add(student.getImie()+" " + student.getNazwisko());
            }
            comboboxStudent.getSelectionModel().select(0);
        }
        else{
            comboboxStudent.getSelectionModel().select(-1);
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
        StringBuilder result = new StringBuilder();
        String[] words = wraping.split(" ");
        List<Integer> sizewords = new ArrayList<>();

        for(String word : words){
            sizewords.add(getWitdh(word));
        }
        Integer act = 0;
        int i = 0;
        for(Integer size : sizewords) {

            if(size + act < width){
                result.append(words[i]).append(" ");
                act +=size+3;
            }
            else if (size + act >= width) {
                result.append("\n").append(words[i]).append(" ");
                act=size+3;
            }
            else {
                result.append("\n").append(words[i]).append("\n");
                act = 0;
            }
            i++;
        }


        return result.toString();
    }

    void setGrade() {
        labelAerage.setText("");
        if (!studentList.isEmpty()) {

            gradeList = FXCollections.observableArrayList((new Grade()).checkStudentGrades(studentList.get(comboboxStudent.getSelectionModel().getSelectedIndex()), subjectsList.get(comboboxSubject.getSelectionModel().getSelectedIndex())));


            columnDesc.setCellValueFactory(CellData -> {
                String tym = CellData.getValue().getOpis();
                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnDesc.getWidth()));
            });

            columnDate.setCellValueFactory(CellData -> {
                String tym = CellData.getValue().getData().toString();
                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnDate.getWidth()));
            });

            columnValue.setCellValueFactory(CellData -> {
                String tym = CellData.getValue().getWaga().toString();
                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnValue.getWidth()));
            });

            columnGrade.setCellValueFactory(CellData -> {
                String tym = CellData.getValue().getOcena().toString();
                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnGrade.getWidth()));
            });

            table.setItems(gradeList);
        }

        if(gradeList.isEmpty()){
            buttonAverage.setDisable(true);
        }
        else{
            buttonAverage.setDisable(false);
        }
    }
    public void changeComboboxStudent() {
        gradeList.clear();
        setGrade();

    }

    public void clickButtonAverage(ActionEvent actionEvent) {
        Float sum = 0.0f;
        Float weight = 0.0f;
        for(Oceny grade :gradeList){
            sum+=grade.getOcena() * grade.getWaga();
            weight+=grade.getWaga();
        }
        labelAerage.setText(String.valueOf(sum/weight));

    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("menu/teacherMenuForm", params, WindowSize.teacherMenuForm);
    }


}
