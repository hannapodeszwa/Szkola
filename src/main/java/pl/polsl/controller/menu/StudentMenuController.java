package pl.polsl.controller.menu;

import pl.polsl.Main;
import java.io.IOException;
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
    private String a;
    private Map params;
    private String login;

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
        login = (String) params.get("login");
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


    public void buttonMessagesAction() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", "menu/studentMenuForm");
        parameters.put("role", "uczen");
        parameters.put("id", id);
        parameters.put("login", login);
        Main.setRoot("common/messengerForm", parameters, 800.0, 450.0);
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
