package pl.polsl.controller.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    public enum md {Parent, Student, Admin}
    private md mode;
    private Map params = new HashMap<String, String>();;

    @FXML
    public Label labelTitle;
    public Button buttonGrades;
    public Button buttonPresence;
    public Button buttonMessages;
    public Button buttonSchedule;
    public Button buttonLogout;

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
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentGradesForm",params,
                WindowSize.studentGradesForm);
    }

    public void clickButtonPresence() throws IOException
    {
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentPresenceForm",params, WindowSize.studentPresenceForm);
    }


    public void clickButtonMessages() throws IOException {
        params.put("previousLocation", "menu/studentMenuForm");
        params.put("role", "uczen");
        params.put("id", id);
        Main.setRoot("common/messengerForm", params, 800.0, 450.0);
    }


    public void clickButtonSchedule() throws IOException {

        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentScheduleForm", params, WindowSize.studentScheduleForm);
    }


    public void clickButtonLogout() throws IOException {
        Main.setRoot("common/signIn");
    }
}
