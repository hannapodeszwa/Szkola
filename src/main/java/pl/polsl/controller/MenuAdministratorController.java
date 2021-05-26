package pl.polsl.controller;

import javafx.event.ActionEvent;
import pl.polsl.Main;

import java.io.IOException;

public class MenuAdministratorController {

    public void pressManageStudentsButton(ActionEvent event) throws IOException
    {
        Main.setRoot("manageStudentsForm");
    }
    public void pressManageTeacherButton(ActionEvent event) throws IOException
    {
        Main.setRoot("manageTeachersForm");
    }

    public void createScheduleButton(ActionEvent event) throws IOException
    {
        Main.setRoot("createScheduleForm");
    }

}
