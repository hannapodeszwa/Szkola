package pl.polsl.controller.teacherActions;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Oceny;
import pl.polsl.model.Grade;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TeacherAddNewGradeController implements ParametrizedController {



    Integer loggedTeacherId;
    Integer studentId;
    Integer subjectId;

    @FXML
    private ComboBox gradeComboBox;
    @FXML
    private ComboBox valueComboBox;
    @FXML
    private Label labelSubject;
    @FXML
    private Label labelName;
    @FXML
    private Label labelSurname;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Label infoLabel;

    @Override
    public void receiveArguments(Map<String, Object> params){
        loggedTeacherId = (Integer) params.get("teacherId");
        studentId = (Integer) params.get("studentId");
        subjectId = (Integer) params.get("subjectId");

        labelSubject.setText((String) params.get("subject"));

        labelSurname.setText((String) params.get("surname"));

        labelName.setText((String) params.get("name"));
        gradeComboBox.getSelectionModel().select(0);
        valueComboBox.getSelectionModel().select(0);

    }



    public void initialize(){
        /* UnaryOperator<TextFormatter.Change> idFilter = change -> {
            String typedText = change.getControlNewText();
            if(!typedText.matches("-?([1-9][0-9]*)?")){
                return null;
            } else {
                return change;
            }
        }; */


        descriptionTextField.textProperty().addListener((observable -> {
            infoLabel.setText("");
        }));
    }


    public void backAction(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherGradeForm", params);
    }
    public void submitAction(ActionEvent event) throws IOException
    {
       //validate fields
        if(descriptionTextField.getText().isEmpty()){
            infoLabel.setText("Wypelnij  wszystkie pola tekstowe");
        } else {
            //    Integer subjectID = (new Subject()).getSubjectByName(subjectComboBox.getValue().toString()).getID();
            // (new Grade()).add(subjectID, Integer.parseInt(idTextField.getText().toString()),Integer.parseInt( gradeComboBox.getValue().toString()),Integer.parseInt(  valueComboBox.getValue().toString()), LocalDateTime.now(), descriptionTextField.getText());
            addNewGrade();
            infoLabel.setText("Sukces!"+ System.lineSeparator() + "Ocena: " + gradeComboBox.getValue().toString() + System.lineSeparator() + "Waga: " + valueComboBox.getValue().toString() + System.lineSeparator() + "Opis: " + descriptionTextField.getText() );
        }

    }

        private void addNewGrade() {
            Oceny o = new Oceny();

            o.setData(new Date(System.currentTimeMillis()));
            o.setIdPrzedmiotu(subjectId);
            o.setIdUcznia(studentId);
            o.setOcena((Float) gradeComboBox.getValue());
            o.setOpis(descriptionTextField.getText());
            o.setWaga((Float) valueComboBox.getValue());
            (new Grade()).persist(o);
        }


}
