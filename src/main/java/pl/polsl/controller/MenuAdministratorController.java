package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import pl.polsl.Main;
import pl.polsl.Window2;
import pl.polsl.WindowSize;

import java.io.IOException;

public class MenuAdministratorController extends Window2 {
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
        Main.setRoot("administratorActions/teacher/manageTeachersForm",
                WindowSize.manageTeachersForm.getWidth(), WindowSize.manageTeachersForm.getHeight());
    }
    public void pressManageSubjectButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/subject/manageSubjectsForm",
                WindowSize.manageSubjectsForm.getWidth(), WindowSize.manageSubjectsForm.getHeight());
    }
    public void pressManageClassButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/class/manageClassForm",
                WindowSize.manageClassForm.getWidth(), WindowSize.manageClassForm.getHeight());
    }
    public void pressCreateScheduleButton(ActionEvent event) throws IOException
    {
        Main.setRoot("createScheduleForm");
    }
    public void pressManageParentButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/parent/manageParentsForm",
                WindowSize.manageParentsForm.getWidth(), WindowSize.manageParentsForm.getHeight());
    }

    public void pressLogOutButton(ActionEvent event) throws IOException
    {
        Main.setRoot("common/signIn");
    }

}
