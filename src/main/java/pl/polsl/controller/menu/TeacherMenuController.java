package pl.polsl.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.utils.Roles;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherMenuController implements ParametrizedController {


    @FXML
    public Button buttonGrades;
    public Button buttonPresence;
    public Button messagesButton;
    public Button buttonLogout;
    public Button buttonSchedule;
    Map params = new HashMap<String, Integer>();
    
    private Integer id;
    private String login;

    @Override
    public void receiveArguments(Map params) {
        id = (Integer) params.get("id");
        login = (String) params.get("login");
    }

    public void viewGradesAction(ActionEvent event) throws IOException
    {
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
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", "menu/teacherMenuForm");
        parameters.put("role", Roles.TEACHER);
        parameters.put("id", id);
        parameters.put("login", login);
        Main.setRoot("common/messengerForm", parameters, 800.0, 450.0);
    }

    public void clickButtonSchedule() throws IOException {
        params.put("id", id);
        Main.setRoot("teacherActions/teacherScheduleForm", params);
    }

    public void clickButtonLogout() throws IOException {
        Main.setRoot("common/signIn");
    }
}
