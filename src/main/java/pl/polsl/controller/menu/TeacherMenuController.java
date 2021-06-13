package pl.polsl.controller.menu;

import javafx.event.ActionEvent;
import pl.polsl.Main;
import pl.polsl.controller.AddOrUpdateSubjectController;
import pl.polsl.controller.ParametrizedController;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class TeacherMenuController implements ParametrizedController {
    Integer loggedTeacherId;

    @Override
    public void receiveArguments(Map params){
        loggedTeacherId = (Integer) params.get("teacher");
        System.out.println("Logged as: " + loggedTeacherId);
    }

    public void pressManageStudentsButton(ActionEvent event) throws IOException
    {
        Main.setRoot("manageStudents");
    }
    public void pressManageTeacherButton(ActionEvent event) throws IOException
    {
        Main.setRoot("manageTeachers");
    }

    public void createScheduleButton(ActionEvent event) throws IOException
    {
        Main.setRoot("createSchedule");
    }

    public void viewGradesAction(ActionEvent event) throws IOException
    {
        Main.setRoot("teacherActions/writeGradesForm");
    }

    public void viewPresencesAction(ActionEvent event) throws IOException
    {
        Main.setRoot("teacherActions/writePresenceForm");
    }

    public void writeGradesAction(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, Integer>();

        params.put("teacher", loggedTeacherId);


        Main.setRoot("teacherActions/writeGradesForm", params);


    }

    public void writePresenceAction(ActionEvent event) throws IOException
    {
        Main.setRoot("teacherActions/writePresenceForm");
    }

    public void messagesAction(ActionEvent event) throws IOException
    {
        Main.setRoot("teacherActions/writeGradesForm");
    }

    public void logOutAction(ActionEvent event) throws IOException
    {
        Main.setRoot("signIn");
    }

}
