package pl.polsl.controller.teacherActions;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.Grade;
import pl.polsl.model.LessonTimeModel;
import pl.polsl.model.AbsenceModel;
import pl.polsl.model.Teacher;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TeacherAddNewAbsenceController implements ParametrizedController {



    private Integer loggedTeacherId;
    private Uczniowie student;
    private Przedmioty subject;
    private Integer lesson;

    @FXML
    private CheckBox excuseCheckBox;

    @FXML
    private Label labelStudent;
    @FXML
    private Label labelSubject;
    @FXML
    private Label labelLesson;

    @FXML
    private Label infoLabel;

    ObservableList<GodzinyLekcji> timesList;

    @Override
    public void receiveArguments(Map<String, Object> params){
        loggedTeacherId = (Integer) params.get("teacherId");


        student = (Uczniowie) params.get("student");
        subject = (Przedmioty) params.get("subject");
        lesson = (Integer) params.get("lesson");

        labelStudent.setText(student.getImie() + " " + student.getNazwisko());
        labelSubject.setText(subject.getNazwa());
        labelLesson.setText(lesson.toString());
        infoLabel.setText("");

        timesList = FXCollections.observableList((new LessonTimeModel()).getTime());



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
            String excuse = "";
            if(excuseCheckBox.isSelected()){
                excuse = "Tak";
            } else {
                excuse = "Nie";
            }
            infoLabel.setText("Sukces!"+ System.lineSeparator() + "Lekcja: " + lesson + System.lineSeparator() + "Usprawiedliwiona: " + excuse + System.lineSeparator() + "Przedmiot: " + subject.getNazwa());


    }

    private void addNewAbsence() {
        Nieobecnosci a = new Nieobecnosci();

        a.setData(new Date(System.currentTimeMillis()));
        a.setPrzedmiotyId(subject.getID());
        a.setUczniowieId(student.getID());
        a.setGodzina(lesson);

        if(excuseCheckBox.isSelected()){
            a.setCzyUsprawiedliwiona(1);
        } else {
            a.setCzyUsprawiedliwiona(0);
        }

        (new AbsenceModel()).persist(a);
    }


}
