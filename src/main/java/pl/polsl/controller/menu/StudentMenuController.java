package pl.polsl.controller.menu;

import pl.polsl.Main;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;

import java.io.IOException;
import java.util.Map;

public class StudentMenuController implements ParametrizedController {

    private int id;

    public enum md {Parent, Student}
    private md mode;

    @FXML
    public Label labelTitle;
    public Button buttonGrades;
    public Button buttonPresence;
    public Button buttonMessages;

    @Override
    public void receiveArguments(Map params) {

        String a = (String)params.get("mode");
        if (a == "Parent") {
            mode = md.Parent;
        }
        else {
            mode = md.Student;
        }

    }

    @FXML
    public void initialize()
    {
        if(md.Parent == mode){
            labelTitle.setText("Konto rodzica");
        }
        else{
            labelTitle.setText("Konto ucznia");
        }
    }


    public void clickButtonPresence(ActionEvent event) throws IOException
    {
        Main.setRoot("studentActions/studentPresence");
    }




    public void logOutAction() throws IOException {
        Main.setRoot("common/signIn");
    }
}
