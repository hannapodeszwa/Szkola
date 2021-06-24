package pl.polsl.controller.teacherActions;


import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Oceny;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Grade;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TeacherAddNewGradeController implements ParametrizedController {

    @FXML
    private ComboBox<Float> gradeComboBox;
    @FXML
    private ComboBox<Float> valueComboBox;
    @FXML
    private Label labelSubject;
    @FXML
    private Label labelStudent;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Label infoLabel;

    private Integer loggedTeacherId;
    private Uczniowie student;
    private Przedmioty subject;
    private Integer studentNumber;
    private Integer subjectNumber;
    private Integer classNumber;
    @Override
    public void receiveArguments(Map<String, Object> params){
        loggedTeacherId = (Integer) params.get("teacherId");


        student = (Uczniowie) params.get("student");
        subject = (Przedmioty) params.get("subject");
        classNumber = (Integer) params.get("classNumber");
        subjectNumber = (Integer) params.get("subjectNumber");
        studentNumber = (Integer) params.get("studentNumber");

        labelSubject.setText(subject.getNazwa());


        labelStudent.setText(student.getImie() + " " + student.getNazwisko());

        gradeComboBox.getSelectionModel().select(0);
        valueComboBox.getSelectionModel().select(0);

    }



    public void initialize(){


        descriptionTextField.textProperty().addListener((observable -> infoLabel.setText("")));
    }


    public void backAction() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        params.put("classNumber",classNumber);
        params.put("subjectNumber",subjectNumber);
        params.put("studentNumber",studentNumber);
        Main.setRoot("teacherActions/teacherGradeForm", params, WindowSize.teacherGradeForm);
    }
    public void submitAction()
    {
       //validate fields
        if(descriptionTextField.getText().isEmpty()){
            infoLabel.setText("Wypelnij  wszystkie pola tekstowe");
        } else {
            addNewGrade();
            infoLabel.setText("Sukces!"+ System.lineSeparator() + "Ocena: " + gradeComboBox.getValue().toString() + System.lineSeparator() + "Waga: " + valueComboBox.getValue().toString() + System.lineSeparator() + "Opis: " + descriptionTextField.getText() );
        }

    }

        private void addNewGrade() {
            Oceny o = new Oceny();

            o.setData(new Date(System.currentTimeMillis()));
            o.setIdPrzedmiotu(subject.getID());
            o.setIdUcznia(student.getID());
            o.setOcena(gradeComboBox.getValue());
            o.setOpis(descriptionTextField.getText());
            o.setWaga(valueComboBox.getValue());
            (new Grade()).persist(o);
        }


}
