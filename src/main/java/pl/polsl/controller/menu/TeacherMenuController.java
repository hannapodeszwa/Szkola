package pl.polsl.controller.menu;

import javafx.event.ActionEvent;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherMenuController implements ParametrizedController {

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
    public void viewPresencesAction(ActionEvent event) throws IOException
    {
    }

    public void writeGradesAction(ActionEvent event) throws IOException
    {
    }

    public void writePresenceAction(ActionEvent event) throws IOException
    {
    }

    public void messagesAction(ActionEvent event) throws IOException
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", "menu/teacherMenuForm");
        parameters.put("role", "nauczyciel");
        parameters.put("id", id);
        parameters.put("login", login);
        Main.setRoot("common/messengerForm", parameters, 800.0, 450.0);
    }

    public void logOutAction(ActionEvent event) throws IOException
    {
        Main.setRoot("common/signIn");
    }

}
