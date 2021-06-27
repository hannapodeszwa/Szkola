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

        mode = (String) params.get("mode");
        if (Roles.ADMIN.equals(mode)) {
            labelTitle.setText("Konto administratora");
            buttonRaport.setVisible(false);
            buttonLogout.setLayoutY(406);
        } else {
            labelTitle.setText("Konto dyrektora");
        }
    }


    public void pressManageStudentsButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/student/manageStudentsForm", params, WindowSize.manageStudentsForm);

    }

    public void pressManageTeacherButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/teacher/manageTeachersForm", params, WindowSize.manageTeachersForm);
    }

    public void pressManageSubjectButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/subject/manageSubjectsForm", params, WindowSize.manageSubjectsForm);
    }

    public void pressManageClassButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/class/manageClassForm", params, WindowSize.manageClassForm);
    }

    public void pressCreateScheduleButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        //params.put("id", id);
        Main.setRoot("studentActions/studentScheduleForm", params, WindowSize.manageScheduleForm);
    }

    public void pressManageParentButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/parent/manageParentsForm", params, WindowSize.manageParentsForm);
    }

    public void pressManageClassroomsButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/classroom/manageClassroomsForm", params, WindowSize.manageClassroomsForm);
    }

    public void pressLessonHoursButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/manageLessonHoursForm", params, WindowSize.manageLessonHoursForm);
    }

    public void pressButtonRaport() throws IOException {
        Main.setRoot("principal", WindowSize.raportMenuForm);
    }

    public void pressLogOutButton() throws IOException {
        Main.setRoot("common/signIn", WindowSize.signIn);
    }

    public void pressDeleteUnusedButton() {
        List toDelete = new ArrayList<>();
        toDelete.addAll((new Student()).getUnusedStudents());
        toDelete.addAll((new Teacher()).getUnusedTeachers());
        toDelete.addAll((new ParentModel()).getUnusedParents());
        toDelete.addAll((new UserModel()).getUnusedAccounts());
        if (toDelete.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Brak kont do usunięcia.", ButtonType.OK);
            alert.setHeaderText("");
            alert.setTitle("Czyszczenie kont");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Znaleziono " + toDelete.size() + " nieużywanych kont. Czy na pewno chcesz je usunąć?", ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Czyszczenie kont");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                (new Student()).delete(toDelete);
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Nieużywane konta zostały usunięte.", ButtonType.OK);
                done.setHeaderText("");
                done.setTitle("Czyszczenie kont");
                done.showAndWait();
            }
        }
    }


}
