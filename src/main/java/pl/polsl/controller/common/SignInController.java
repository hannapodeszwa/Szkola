package pl.polsl.controller.common;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import pl.polsl.Main;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInController {

    private UserModel userModel;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorLabel;

    private Parent parent;
    private Scene scene;
    private Map params = new HashMap<String, String>();

    @FXML
    public void initialize() {
        userModel = new UserModel();
        loginTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));

        passwordTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));
    }

    public void signInAction() throws IOException {
        if (!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
            String login = loginTextField.getText();
            String password = passwordTextField.getText();
            Uzytkownicy user = userModel.getUserByLoginAndPassword(login, password);

            if (user != null) {
                switch (user.getDostep()) {
                    case Roles.STUDENT:
                        params = new HashMap<String, String>();
                        params.put("mode", "Student");
                        params.put("id", user.getID());
                        params.put("login", user.getLogin());
                        Main.setRoot("menu/studentMenuForm", params);
                        break;
                    case Roles.TEACHER:
                        params = new HashMap<String, String>();
                        params.put("id", user.getID());
                        params.put("login", user.getLogin());
                        Main.setRoot("menu/teacherMenuForm", params);
                        break;
                    case Roles.PARENT:
                        params = new HashMap<String, String>();
                        params.put("mode", "Parent");
                        params.put("id", user.getID());
                        params.put("login", user.getLogin());
                        Main.setRoot("menu/studentMenuForm", params);
                        break;
                    case Roles.ADMIN:
                        Main.setRoot("menu/adminMenuForm",
                                WindowSize.adminMenuForm.getWidth(), WindowSize.adminMenuForm.getHeight());
                        break;
                }
            } else errorLabel.setText("Błedny login lub hasło");

        } else errorLabel.setText("Wypełnij wszystkie pola");
    }

    public void resetPasswordAction() throws Exception {
        Main.setRoot("common/resetPasswordForm");
    }

    public void textChanged() {
        errorLabel.setText("");
    }
}
