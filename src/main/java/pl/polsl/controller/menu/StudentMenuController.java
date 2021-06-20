package pl.polsl.controller.menu;

import pl.polsl.Main;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import java.util.HashMap;
import java.util.Map;

public class StudentMenuController implements ParametrizedController{

    public Button buttonClubs;
    public Button buttonCompetitions;
    private int id;
    public enum md {Parent, Student, Admin}
    private md mode;
    private String login;

    @FXML
    public Label labelTitle;
    public Button buttonGrades;
    public Button buttonPresence;
    public Button buttonMessages;
    public Button buttonSchedule;
    public Button buttonLogout;

    @Override
    public void receiveArguments(Map<String, Object> params) {

        mode = md.valueOf((String)params.get("mode"));
        id = (Integer) params.get("id");
        login = (String) params.get("login");
    }

    @FXML
    public void initialize()
    {
        if(md.Parent == mode){
            labelTitle.setText("Konto rodzica");
            buttonClubs.setVisible(true);
            buttonCompetitions.setVisible(true);
        }
        else{
            labelTitle.setText("Konto ucznia");
        }

    }

    public void clickButtonGrades() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentGradesForm",params,
                WindowSize.studentGradesForm);
    }

    public void clickButtonPresence() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentPresenceForm",params, WindowSize.studentPresenceForm);
    }


    public void buttonMessagesAction() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", "menu/studentMenuForm");
        parameters.put("role", Roles.STUDENT);
        parameters.put("id", id);
        parameters.put("login", login);
        Main.setRoot("common/messengerForm", parameters, 800.0, 450.0);
    }


    public void clickButtonSchedule() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentScheduleForm", params, WindowSize.studentScheduleForm);
    }

    public void clickButtonClubs() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentClubsForm", params, WindowSize.studentClubsForm);
    }

    public void clickButtonCompetitions() throws IOException {

        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("studentActions/studentCompetitionForm", params, WindowSize.studentCompetitionsForm);
    }



    public void clickButtonLogout() throws IOException {
        Main.setRoot("common/signIn");
    }
}
