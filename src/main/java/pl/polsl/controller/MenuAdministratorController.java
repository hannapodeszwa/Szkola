package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.Main;

import java.io.IOException;

public class MenuAdministratorController {
    private final Stage thisStage;

    public MenuAdministratorController() {
        thisStage = new Stage();
    }


    public void pressManageStudentsButton(ActionEvent event) throws IOException
    {
       Main.setRoot("manageStudentsForm");

    }
    public void pressManageTeacherButton(ActionEvent event) throws IOException
    {
        Main.setRoot("manageTeachersForm");
    }
    public void pressManageSubjectButton(ActionEvent event) throws IOException
    {
        Main.setRoot("manageSubjectsForm");
    }
    public void pressManageClassButton(ActionEvent event) throws IOException
    {
        Main.setRoot("manageClassForm");
    }
    public void pressCreateScheduleButton(ActionEvent event) throws IOException
    {
        Main.setRoot("createScheduleForm");
    }

    public void pressLogOutButton(ActionEvent event) throws IOException
    {
        Main.setRoot("signIn");
    }

}
