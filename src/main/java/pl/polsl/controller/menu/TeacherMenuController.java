package pl.polsl.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class TeacherMenuController implements ParametrizedController {

    @FXML
    public Button buttonGrades;
    public Button buttonPresence;
    public Button messagesButton;
    public Button buttonSchedule;
    public Button buttonLogout;

    Map params = new HashMap<String, Integer>();
    Integer id;

    @Override
    public void receiveArguments(Map params){
        id = (Integer) params.get("id");
        System.out.println("Logged as: " + id);
    }


    public void clickButtonGrades() throws IOException
    {
        params.put("id", id);
        Main.setRoot("teacherActions/teacherGradesForm", params);
    }


    public void clickButtonPresence() throws IOException {
        params.put("id", id);
        Main.setRoot("teacherActions/teacherPresenceForm", params);
    }

    public void clickButtonMessages() throws IOException {
        params.put("previousLocation", "menu/TeacherMenuForm");
        params.put("role", "nauczyciel");
        params.put("id", id);
        Main.setRoot("common/messengerForm", params, 800.0, 450.0);
    }

    public void clickButtonSchedule() throws IOException {
        params.put("teacher", id);
        Main.setRoot("teacherActions/teacherScheduleForm", params);
    }

    public void clickButtonLogout() throws IOException {
        Main.setRoot("common/signIn");
    }
}
