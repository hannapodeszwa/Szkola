package pl.polsl.controller.teacherActions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.Map;

public class WriteGradesController implements ParametrizedController {
    Integer loggedTeacherId;

    @Override
    public void receiveArguments(Map params){
        loggedTeacherId = (Integer) params.get("teacher");
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
    private  ComboBox valueComboBox;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize(){
        surnameTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));

        nameTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));

        idTextField.textProperty().addListener((observable -> {
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

        //throw error if sth wrong
    }
}
