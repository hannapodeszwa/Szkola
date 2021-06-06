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
      /*  try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherActions/manageStudentsForm.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("testowe okno");

        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
