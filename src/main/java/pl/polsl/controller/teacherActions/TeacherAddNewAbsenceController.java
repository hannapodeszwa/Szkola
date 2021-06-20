package pl.polsl.controller.teacherActions;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.Grade;
import pl.polsl.model.LessonTimeModel;
import pl.polsl.model.Present;
import pl.polsl.model.Teacher;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TeacherAddNewAbsenceController implements ParametrizedController {



    private Integer loggedTeacherId;
    private Uczniowie student;


    @FXML
    private ComboBox<String> lessonComboBox;
    @FXML
    private ComboBox<String> excuseComboBox;
    @FXML
    private ComboBox<String> subjectComboBox;
    @FXML
    private Label labelStudent;

    @FXML
    private Label infoLabel;

    ObservableList<GodzinyLekcji> timesList;
    ObservableList<Przedmioty> subjectsList;

    @Override
    public void receiveArguments(Map<String, Object> params){
        loggedTeacherId = (Integer) params.get("teacherId");


        student = (Uczniowie) params.get("student");

        subjectsList = (FXCollections.observableArrayList((new Teacher()).checkTeacherSubjects(loggedTeacherId)));




        if(!subjectsList.isEmpty()){
            for (Przedmioty p : subjectsList){
                subjectComboBox.getItems().add(p.getNazwa());
            }
        }

        labelStudent.setText(student.getImie() + " " + student.getNazwisko());

        timesList = FXCollections.observableList((new LessonTimeModel()).getTime());

        if(!timesList.isEmpty()){
            for (GodzinyLekcji t : timesList){
                String start = t.getPoczatek().toString();
                String end = t.getKoniec().toString();
                lessonComboBox.getItems().add(start + "-" + end);
            }
        }
        lessonComboBox.getSelectionModel().select(0);
        excuseComboBox.getSelectionModel().select(0);
        subjectComboBox.getSelectionModel().select(0);

    }






    public void backAction() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherAbsenceForm", params, WindowSize.teacherGradeForm);
    }
    public void submitAction()
    {

            addNewAbsence();
            infoLabel.setText("Sukces!"+ System.lineSeparator() + "Lekcja: " + lessonComboBox.getValue().toString() + System.lineSeparator() + "Usprawiedliwiona: " + excuseComboBox.getValue().toString() + System.lineSeparator() );


    }

    private void addNewAbsence() {
        Nieobecnosci a = new Nieobecnosci();

        a.setData(new Date(System.currentTimeMillis()));
        a.setPrzedmiotyId(subjectsList.get(subjectComboBox.getSelectionModel().getSelectedIndex()).getID());
        a.setUczniowieId(student.getID());

        Integer tid = timesList.get(lessonComboBox.getSelectionModel().getSelectedIndex()).getID();
        a.setGodzina(tid);

        a.setCzyUsprawiedliwiona(excuseComboBox.getSelectionModel().getSelectedIndex());
        (new Present()).persist(a);
    }


}
