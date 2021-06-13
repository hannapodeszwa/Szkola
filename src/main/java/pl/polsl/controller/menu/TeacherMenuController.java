package pl.polsl.controller.menu;

import javafx.event.ActionEvent;
import pl.polsl.Main;

import java.io.IOException;

public class TeacherMenuController {

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
    }

    public void logOutAction(ActionEvent event) throws IOException
    {
        Main.setRoot("common/signIn");
    }

}
