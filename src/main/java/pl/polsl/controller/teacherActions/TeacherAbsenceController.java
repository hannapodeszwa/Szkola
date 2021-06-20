package pl.polsl.controller.teacherActions;


import javafx.beans.property.ReadOnlyStringWrapper;
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
import pl.polsl.utils.WindowSize;

import java.io.IOException;

import java.util.*;

public class TeacherAbsenceController implements ParametrizedController {

    @FXML
    public TableView<Nieobecnosci> table;
    public TableColumn<Nieobecnosci, String> columnExcuse;
    public TableColumn<Nieobecnosci, String> columnDate;
    public TableColumn<Nieobecnosci, String> columnLessonStart;
    public TableColumn<Nieobecnosci, String> columnLessonEnd;
    public TableColumn<Nieobecnosci, String> columnSubject;
    public ComboBox<String> comboboxClass;
    public ComboBox<String> comboboxStudent;
    public ComboBox<String> comboboxSubject;
    public Button buttonDelete;
    public Button buttonAdd;
    public Button buttonBack;

    Integer id;
    List<Klasy> classList;
    ObservableList<Uczniowie> studentList;

    ObservableList<Nieobecnosci> absenceList;

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
            setAbsence(0);
        }

    }

    public void clickButtonDelete() {
        Nieobecnosci n = table.getSelectionModel().getSelectedItem();
        (new Present()).delete(n);

        Integer index = comboboxStudent.getSelectionModel().getSelectedIndex();
        absenceList.clear();
        setAbsence(index);

    }

    public void clickButtonExcuse() {
        Nieobecnosci n = table.getSelectionModel().getSelectedItem();
        n.setCzyUsprawiedliwiona(1);
        (new Present()).update(n);

        Integer index = comboboxStudent.getSelectionModel().getSelectedIndex();
        absenceList.clear();
        setAbsence(index);
    }

    public void clickButtonAdd() throws IOException{
        Map params = new HashMap<String, Object>();

        params.put("teacherId", id);

        Integer studentIndex = comboboxStudent.getSelectionModel().getSelectedIndex();


        params.put("student", studentList.get(studentIndex));

        Main.setRoot("teacherActions/teacherAddNewAbsenceForm", params, WindowSize.teacherAddNewGradeForm);
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
        List<Integer> sizewords = new ArrayList<Integer>();

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

    public void setAbsence(Integer index ){
        absenceList = FXCollections.observableArrayList((new Present()).displayPresent(studentList.get(index).getID()));


        columnLessonEnd.setCellValueFactory(CellData -> {
            String tym = (new LessonTimeModel()).getById(CellData.getValue().getGodzina()).getKoniec().toString();
            return new ReadOnlyStringWrapper(wrapString(tym, (int) columnLessonEnd.getWidth()));
        });

        columnSubject.setCellValueFactory(CellData -> {
            String tym = (new Subject()).getSubjectName(CellData.getValue().getPrzedmiotyId());
            return new ReadOnlyStringWrapper(wrapString(tym, (int) columnSubject.getWidth()));
        });

        columnDate.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getData().toString();
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnDate.getWidth()));
        });

       columnExcuse.setCellValueFactory(CellData -> {
            Integer pom = CellData.getValue().getCzyUsprawiedliwiona();
            String tym = "";
            if(pom.equals(0)){
                tym = "Nie";
            } else {
                tym = "Tak";
            }
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnExcuse.getWidth()));
        });

        columnLessonStart.setCellValueFactory(CellData -> {
            String tym = (new LessonTimeModel()).getById(CellData.getValue().getGodzina()).getPoczatek().toString();

            return new ReadOnlyStringWrapper(wrapString(tym, (int) columnLessonStart.getWidth()));
        });

        table.setItems(absenceList);
    }

    public void changeComboboxStudent() {
        Integer index = comboboxStudent.getSelectionModel().getSelectedIndex();

        absenceList.clear();

        if(!studentList.isEmpty()){
            setAbsence(index);
        }
    }

    public void clickButtonBack() throws IOException {
        Map params = new HashMap<String, String>();
        params.put("id", id);
        Main.setRoot("menu/teacherMenuForm", params, WindowSize.teacherMenuForm);
    }


}
