package pl.polsl.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.polsl.Main;

import java.io.IOException;
import java.util.Map;

public class StudentMenuController implements ParametrizedController {

    private int id;
    public enum md {Parent, Student}
    private md mode;

    @FXML
    public Label labelTitle;
    public Button buttonGrades;
    public Button buttonAttendance;
    public Button buttonMessage;

    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "Parent") {
            mode = md.Parent;
            labelTitle.setText("Konto rodzica");
        }
        else {
            mode = md.Student;
            labelTitle.setText("Konto ucznia");
        }
    }

    public void clickButtonAttendance(ActionEvent event) throws IOException
    {
        Main.setRoot("manageTeachers");
    }




}
