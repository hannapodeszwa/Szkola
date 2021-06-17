package pl.polsl.controller.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import pl.polsl.Main;
import pl.polsl.WindowSize;
import pl.polsl.model.ParentModel;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuAdministratorController {
    private final Stage thisStage;

    public MenuAdministratorController() {
        thisStage = new Stage();
    }


    public void pressManageStudentsButton(ActionEvent event) throws IOException
    {
       Main.setRoot("administratorActions/student/manageStudentsForm",
               WindowSize.manageStudentsForm.getWidth(), WindowSize.manageStudentsForm.getHeight());

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

    public void pressManageClassroomsButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/classroom/manageClassroomsForm",
                WindowSize.manageClassroomsForm);
    }

    public void pressLogOutButton(ActionEvent event) throws IOException
    {
        Main.setRoot("common/signIn");
    }

    public void pressDeleteUnused(ActionEvent actionEvent) {
        List toDelete = new ArrayList<>();
        toDelete.addAll((new Student()).getUnusedStudents());
        toDelete.addAll((new Teacher()).getUnusedTeachers());
        toDelete.addAll((new ParentModel()).getUnusedParents());
        toDelete.addAll((new UserModel()).getUnusedAccounts());
        if(toDelete.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Brak kont do usunięcia.", ButtonType.OK);
            alert.setHeaderText("");
            alert.setTitle("Czyszczenie kont");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Znaleziono " + toDelete.size() + " nieużywanych kont. Czy na pewno chcesz je usunąć?", ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Czyszczenie kont");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK){
                (new Student()).delete(toDelete);
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Nieużywane konta zostały usunięte.", ButtonType.OK);
                done.setHeaderText("");
                done.setTitle("Czyszczenie kont");
                done.showAndWait();
            }
        }
    }
}
