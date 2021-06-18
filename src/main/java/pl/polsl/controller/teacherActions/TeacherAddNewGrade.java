package pl.polsl.controller.teacherActions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Oceny;
import pl.polsl.model.Grade;
import pl.polsl.model.Subject;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class TeacherAddNewGrade implements ParametrizedController {
    Integer loggedTeacherId;
    Integer studentId;
    Integer subjectId;

    @Override
    public void receiveArguments(Map params){
        loggedTeacherId = (Integer) params.get("teacherId");
        studentId = (Integer) params.get("studentId");
        subjectId = (Integer) params.get("subjectId");

        subjectTextField.setText((String) params.get("subject"));
        subjectTextField.setEditable(false);

        surnameTextField.setText((String) params.get("surname"));
        surnameTextField.setEditable(false);

        nameTextField.setText((String) params.get("name"));
        nameTextField.setEditable(false);

        gradeComboBox.getSelectionModel().select(0);
        valueComboBox.getSelectionModel().select(0);

    }


    @FXML
    private ComboBox gradeComboBox;
    @FXML
    private ComboBox valueComboBox;
    @FXML
    private TextField subjectTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Label errorLabel;

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
            errorLabel.setText("");
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
            errorLabel.setText("Fill all text fields!");
        } else {
            //    Integer subjectID = (new Subject()).getSubjectByName(subjectComboBox.getValue().toString()).getID();
            // (new Grade()).add(subjectID, Integer.parseInt(idTextField.getText().toString()),Integer.parseInt( gradeComboBox.getValue().toString()),Integer.parseInt(  valueComboBox.getValue().toString()), LocalDateTime.now(), descriptionTextField.getText());
            addNewGrade();

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
