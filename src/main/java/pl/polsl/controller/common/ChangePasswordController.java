package pl.polsl.controller.common;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Kodyweryfikacyjne;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;
import pl.polsl.model.VerificationCodesModel;
import pl.polsl.view.NotificationsInterface;

import java.io.IOException;
import java.util.Map;

public class ChangePasswordController implements ParametrizedController, NotificationsInterface {

    private VerificationCodesModel verificationCodesModel;
    private UserModel user;
    private String login;
    private Integer id;
    private String role;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private TextField confirmationCodeTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label providePasswordLabel;

    @Override
    public void receiveArguments(Map params) {
        login = (String) params.get("login");
        role = (String) params.get("role");
        id = (Integer) params.get("id");
        providePasswordLabel.setText("Provide a new password for user: " + login);
    }

    @FXML
    public void initialize() {
        user = new UserModel();
        verificationCodesModel = new VerificationCodesModel();
        newPasswordTextField.textProperty().addListener((observable) -> {
            errorLabel.setText("");
        });
        confirmPasswordTextField.textProperty().addListener((observable) -> {
            errorLabel.setText("");
        });
        confirmationCodeTextField.textProperty().addListener((observable) -> {
            errorLabel.setText("");
        });
    }

    public void changePasswordAction() throws IOException {
        if (newPasswordTextField.getText().isEmpty()
                || confirmPasswordTextField.getText().isEmpty()
                || confirmationCodeTextField.getText().isEmpty()) {
            errorLabel.setText("Fill all of required fields!");
        } else {
            String newPassword = newPasswordTextField.getText();
            String confirmedPassword = confirmPasswordTextField.getText();
            String confirmationCode = confirmationCodeTextField.getText();
            if (!newPassword.equals(confirmedPassword)) {
                errorLabel.setText("Passwords do not match!");
            } else {
                Kodyweryfikacyjne dbVerificationCode = verificationCodesModel.getVerificationCodeByLogin(login);
                if (dbVerificationCode.getKod().equals(confirmationCode)) {
                    user.updatePasswordByIdAndRole(role, id, newPassword);
                    verificationCodesModel.removeVerificationCodeByLogin(login);
                    showNotification("Zmiana hasła", "Pomyślnie zmieniono hasło", 2);
                    Main.setRoot("common/signIn");
                } else {
                    errorLabel.setText("Verification code does not match!");
                }
            }
        }
    }

    public void cancelButtonAction() throws IOException {
        Main.setRoot("common/signIn");
    }
}
