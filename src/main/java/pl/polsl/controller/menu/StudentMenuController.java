package pl.polsl.controller.menu;

import pl.polsl.Main;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.polsl.WindowSize;
import pl.polsl.controller.ParametrizedController;

import java.util.HashMap;
import java.util.Map;

public class StudentMenuController implements ParametrizedController{

    private int id;
    public enum md {Parent, Student}
    private md mode;
    private Map params;

    @FXML
    public Label labelTitle;
    public Button buttonGrades;
    public Button buttonPresence;
    public Button buttonMessages;

    @Override
    public void receiveArguments(Map params) {

        mode = md.valueOf((String)params.get("mode"));
        id = (Integer) params.get("id");
        if(md.Parent == mode){
            labelTitle.setText("Konto rodzica");
        }
        else{
            labelTitle.setText("Konto ucznia");
        }

    }

    public void clickButtonGrades() throws IOException {
        params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentGradesForm",params,
                WindowSize.studentGradesForm.getWidth(), WindowSize.studentGradesForm.getHeight());
    }

    public void clickButtonPresence() throws IOException
    {
        params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentPresenceForm",params,
                WindowSize.studentPresenceForm.getWidth(), WindowSize.studentPresenceForm.getHeight());
    }




    public void logOutAction() throws IOException {
        Main.setRoot("common/signIn");
    }


    public void clickButtonSchedule() throws IOException {
        params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentScheduleForm");
    }

}
