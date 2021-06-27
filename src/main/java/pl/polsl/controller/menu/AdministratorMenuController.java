package pl.polsl.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.model.ParentModel;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdministratorMenuController implements ParametrizedController {

    @FXML
    private Button buttonLogout;
    @FXML
    private Button buttonRaport;
    @FXML
    private Label labelTitle;

    private String mode;

    @Override
    public void receiveArguments(Map<String, Object> params) {

        mode = (String)params.get("mode");
        if(Roles.ADMIN.equals(mode)){
            labelTitle.setText("Konto administratora");
            buttonRaport.setVisible(false);
            buttonLogout.setLayoutY(406);
        }
        else{
            labelTitle.setText("Konto dyrektora");
        }
    }


    public void pressManageStudentsButton() throws IOException
    {
       Main.setRoot("administratorActions/student/manageStudentsForm",
               WindowSize.manageStudentsForm.getWidth(), WindowSize.manageStudentsForm.getHeight());

    }
    public void pressManageTeacherButton() throws IOException
    {
        Main.setRoot("administratorActions/teacher/manageTeachersForm",
                WindowSize.manageTeachersForm.getWidth(), WindowSize.manageTeachersForm.getHeight());
    }
    public void pressManageSubjectButton() throws IOException
    {
        Main.setRoot("administratorActions/subject/manageSubjectsForm",
                WindowSize.manageSubjectsForm.getWidth(), WindowSize.manageSubjectsForm.getHeight());
    }
    public void pressManageClassButton() throws IOException
    {
        Main.setRoot("administratorActions/class/manageClassForm",
                WindowSize.manageClassForm.getWidth(), WindowSize.manageClassForm.getHeight());
    }
    public void pressCreateScheduleButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", Roles.ADMIN);
        //params.put("id", id);
        Main.setRoot("studentActions/studentScheduleForm", params, WindowSize.manageScheduleForm);
    }
    public void pressManageParentButton() throws IOException
    {
        Main.setRoot("administratorActions/parent/manageParentsForm",
                WindowSize.manageParentsForm.getWidth(), WindowSize.manageParentsForm.getHeight());
    }

    public void pressManageClassroomsButton() throws IOException
    {
        Main.setRoot("administratorActions/classroom/manageClassroomsForm",
                WindowSize.manageClassroomsForm);
    }

    public void pressLessonHoursButton() throws IOException
    {
        Main.setRoot("administratorActions/manageLessonHoursForm",
                WindowSize.manageLessonHoursForm);
    }

    public void pressLogOutButton() throws IOException
    {
        Main.setRoot("common/signIn", WindowSize.signIn);
    }

    public void pressDeleteUnusedButton() {
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

    public void pressButtonRaport(ActionEvent actionEvent) {
    }
}
