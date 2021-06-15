package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.Main;
import pl.polsl.Window;

import java.io.IOException;

public class MenuAdministratorController extends Window {
    private final Stage thisStage;

    public MenuAdministratorController() {
        thisStage = new Stage();
    }


    public void pressManageStudentsButton(ActionEvent event) throws IOException
    {
       Main.setRoot("administratorActions/student/manageStudentsForm");

    }
    public void pressManageTeacherButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/teacher/manageTeachersForm", manageTeachersFormWidth, manageTeachersFormHeight );
    }
    public void pressManageSubjectButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/subject/manageSubjectsForm", manageSubjectsFormWidth, manageSubjectsFormHeight);
    }
    public void pressManageClassButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/class/manageClassForm", manageClassFormWidth, manageClassFormHeight);
    }
    public void pressCreateScheduleButton(ActionEvent event) throws IOException
    {
        Main.setRoot("createScheduleForm");
    }

    public void pressLogOutButton(ActionEvent event) throws IOException
    {
        Main.setRoot("common/signIn");
    }

}
