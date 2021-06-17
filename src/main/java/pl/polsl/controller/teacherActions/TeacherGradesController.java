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
import java.util.Map;
import java.util.function.UnaryOperator;

public class TeacherGradesController implements ParametrizedController {
    Integer loggedTeacherId;

    @Override
    public void receiveArguments(Map params){
        loggedTeacherId = (Integer) params.get("id");
        System.out.println("Logged as: " + loggedTeacherId);


        ObservableList<String> subjects = FXCollections.observableArrayList( (new Teacher().checkTeacherSubjects(loggedTeacherId)));
        subjectComboBox.setItems(subjects);
        subjectComboBox.setVisibleRowCount(subjects.size());
        subjectComboBox.setValue(subjects.get(0));
    }

    @FXML
    private ComboBox subjectComboBox;
    @FXML
    private ComboBox gradeComboBox;
    @FXML
    private ComboBox valueComboBox;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Label errorLabel;

    public void initialize(){
        UnaryOperator<TextFormatter.Change> idFilter = change -> {
            String typedText = change.getControlNewText();
            if(!typedText.matches("-?([1-9][0-9]*)?")){
                return null;
            } else {
                return change;
            }
        };
        idTextField.setTextFormatter((new TextFormatter<Integer>(new IntegerStringConverter(), 0, idFilter)));

        surnameTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));

        nameTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));

        idTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));

        descriptionTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));
    }


    public void backAction(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/teacherMenuForm");
    }
    public void submitAction(ActionEvent event) throws IOException
    {
       //validate fields
        if(idTextField.getText().isEmpty() ||
                nameTextField.getText().isEmpty() ||
                surnameTextField.getText().isEmpty() ||
                descriptionTextField.getText().isEmpty()){
            errorLabel.setText("Fill all text fields!");
        } else {
            Integer subjectID = (new Subject()).getSubjectByName(subjectComboBox.getValue().toString()).getID();
           // (new Grade()).add(subjectID, Integer.parseInt(idTextField.getText().toString()),Integer.parseInt( gradeComboBox.getValue().toString()),Integer.parseInt(  valueComboBox.getValue().toString()), LocalDateTime.now(), descriptionTextField.getText());
            Oceny o = new Oceny();
            /* To be moved to separate method */
            o.setData(new Date(System.currentTimeMillis()));
            o.setIdPrzedmiotu(subjectID);
            o.setIdUcznia(Integer.parseInt(idTextField.getText().toString()));
            o.setOcena((Float) gradeComboBox.getValue());
            o.setOpis(descriptionTextField.getText());
            o.setWaga((Float) valueComboBox.getValue());
            (new Grade()).persist(o);

        }



    }
}
