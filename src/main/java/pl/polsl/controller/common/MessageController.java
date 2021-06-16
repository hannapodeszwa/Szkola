package pl.polsl.controller.common;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rodzice;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.ParentModel;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;
import pl.polsl.view.NotificationsInterface;

import java.io.IOException;
import java.util.*;

public class MessageController implements ParametrizedController, NotificationsInterface {

    private enum roles {
        teacher("nauczyciel"),
        student("uczen"),
        parent("rodzic");

        private final String value;
        roles(String value) {
            this.value = value;
        }
    }

    private String previousLocation;
    private String role;
    private Integer id;
    private Student studentModel;
    private ParentModel parentModel;
    private Teacher teacherModel;
    private UserModel userModel;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private Boolean receiverSet;
    private Boolean changeReceiver;

    private Set<String> suggestionsSet;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField receiverTextField;

    @FXML
    private TextField topicTextField;

    @FXML
    private TextArea messageTextArea;

    @FXML
    public void initialize() {
        receiverSet = false;
        changeReceiver = true;
        suggestionsSet = new HashSet<>();
        studentModel = new Student();
        parentModel = new ParentModel();
        teacherModel = new Teacher();
        userModel = new UserModel();
        receiverTextField.textProperty().addListener(observable -> {
            errorLabel.setText("");
            receiverSet = false;
        });
        topicTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        messageTextArea.textProperty().addListener(observable -> errorLabel.setText(""));
    }

    @Override
    public void receiveArguments(Map params) {
        previousLocation = (String) params.get("previousLocation");
        role = (String) params.get("role");
        id = (Integer) params.get("id");

        switch (role) {
            case "nauczyciel": {
                List<Uczniowie> students = studentModel.getAllStudents();
                List<Rodzice> parents = parentModel.getAllParents();
                for (Uczniowie student : students) {
                    suggestionsSet.add(student.getImie() + " " + student.getNazwisko() +
                            " [" + userModel.getLoginByIdAndRole(student.getID(), roles.student.value) + "]");
                }
                for (Rodzice parent : parents) {
                    suggestionsSet.add(parent.getImie() + " " + parent.getNazwisko() +
                            " [" + userModel.getLoginByIdAndRole(parent.getID(), roles.parent.value) + "]");
                }
                break;
            }
            case "uczen":
            case "rodzic": {
                List<Nauczyciele> teachers = teacherModel.getAllTeachers();
                for (Nauczyciele teacher : teachers) {
                    suggestionsSet.add(teacher.getImie() + " " + teacher.getNazwisko() +
                            " [" + userModel.getLoginByIdAndRole(teacher.getID(), roles.teacher.value) + "]");
                }
                break;
            }
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(receiverTextField, suggestionsSet);
        autoCompletionBinding.setOnAutoCompleted(event -> {
            receiverSet = true;
        });
    }

    public void cancelButtonAction() throws IOException {
        returnFromMessageWriter();
    }

    public void sendButtonAction() throws IOException {
        if (receiverTextField.getText().isEmpty())
            errorLabel.setText("Wprowadź odbiorcę");
        else if (topicTextField.getText().isEmpty())
            errorLabel.setText("Uzupełnij temat");
        else if (messageTextArea.getText().isEmpty())
            errorLabel.setText("Uzupełnij pole wiadomości");
        else {
            if(receiverSet){
                String[] receiver = receiverTextField.getText().split(" ");
                receiver[2] = receiver[2].replace("[", "");
                String login = receiver[2].replace("]", "");
                Uzytkownicy user = userModel.getUserByLogin(login);
            } else {
                errorLabel.setText("Wybierz kontakt z listy");
            }
        }
    }

    private void returnFromMessageWriter() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", previousLocation);
        parameters.put("role", role);
        parameters.put("id", id);
        Main.setRoot("common/messengerForm", parameters, 800.0, 450.0);
    }

}
