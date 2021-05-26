package pl.polsl.controller;

import javafx.event.ActionEvent;
import pl.polsl.Main;

import java.io.IOException;

public class ManageTeachersController {
    public void addTeacherButton(ActionEvent event) throws IOException
    {
       Main.setRoot("addOrUpdateTeacher");
    }

    public void updateTeacherButton(ActionEvent event) throws IOException
    {
       Main.setRoot("addOrUpdateTeacher");
    }

    public void deleteTeacherButton(ActionEvent event) throws IOException
    {
        System.out.println("Usuwanie nauczyciela");
        Main.setRoot("deleteUserConfirmation");
    }
}
