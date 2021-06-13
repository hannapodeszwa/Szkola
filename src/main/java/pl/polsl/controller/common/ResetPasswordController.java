package pl.polsl.controller.common;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.entities.Rodzicielstwo;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.ParentModel;
import pl.polsl.model.ParenthoodModel;
import pl.polsl.model.UserModel;
import pl.polsl.model.VerificationCodesModel;
import pl.polsl.model.email.MailSenderModel;
import pl.polsl.model.email.VerificationCodesGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static pl.polsl.model.email.EmailMessages.*;

public class ResetPasswordController {

    private Boolean loginSet = false;
    private Boolean emailSet = false;
    private Boolean foundMail = false;
    private EmailAddressController emailValidator;
    private VerificationCodesModel verificationCodesModel;
    private UserModel userModel;
    private ParenthoodModel parenthoodModel;
    private ParentModel parentModel;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button sendVerificationMailButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ProgressIndicator sendingProgressBar;


    @FXML
    public void initialize() {
        verificationCodesModel = new VerificationCodesModel();
        userModel = new UserModel();
        parenthoodModel = new ParenthoodModel();
        parentModel = new ParentModel();
        emailValidator = new EmailAddressController();
        loginTextField.textProperty().addListener((observable, oldVal, newVal) -> {
            loginSet = !newVal.isEmpty();
            sendVerificationMailButton.setDisable(!loginSet || !emailSet);
            errorLabel.setText("");
        });

        emailTextField.textProperty().addListener((observable, oldVal, newVal) -> {
            emailSet = emailValidator.validate(newVal);
            sendVerificationMailButton.setDisable(!loginSet || !emailSet);
            errorLabel.setText("");
        });
    }

    public void sendVerificationEmailAction() {
        Uzytkownicy user = userModel.getUserByLogin(loginTextField.getText());
        if (user != null) {
            if (user.getEmail() != null && emailTextField.getText().equals(user.getEmail())) {
                prepareVerification(user);
            } else {
                List<Rodzicielstwo> ids = parenthoodModel.getParentsByChildID(user.getID());
                System.out.println(ids.get(0));
                for (Rodzicielstwo id : ids) {
                    String code = parentModel.getParentEmailByID(id.getIdRodzica());
                    if (code.equals(emailTextField.getText())) {
                        prepareVerification(user);
                        foundMail = true;
                        break;
                    }
                }
                if (!foundMail) {
                    errorLabel.setText("Login and email do not match.");
                }
            }
        } else {
            errorLabel.setText("Login does not exist.");
        }
    }

    public void cancelAction() throws IOException {
        Main.setRoot("common/signIn");
    }

    private void prepareVerification(Uzytkownicy user) {
        sendVerificationMailButton.setDisable(true);
        VerificationCodesGenerator verificationCodesGenerator = new VerificationCodesGenerator(6, ThreadLocalRandom.current());

        loginTextField.setDisable(true);
        emailTextField.setDisable(true);
        cancelButton.setDisable(true);
        sendVerificationMailButton.setDisable(true);
        sendingProgressBar.setVisible(true);

        new Thread(() -> {
            String code = verificationCodesGenerator.generateString();
            while (verificationCodesModel.getVerificationCode(code) != null)
                code = verificationCodesGenerator.generateString();
            verificationCodesModel.insertVerificationCode(code, loginTextField.getText(), emailTextField.getText());
            if (sendMail(code)) {
                Platform.runLater(() -> {
                    try {
                        Map<String, Object> params = new HashMap<>();
                        params.put("login", loginTextField.getText());
                        params.put("role", user.getDostep());
                        params.put("id", user.getID());
                        Main.setRoot("common/changePasswordForm", params);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                loginTextField.setDisable(false);
                emailTextField.setDisable(false);
                cancelButton.setDisable(false);
                sendVerificationMailButton.setDisable(false);
                sendingProgressBar.setVisible(false);
                verificationCodesModel.removeVerificationCodeByLogin(loginTextField.getText());
                Platform.runLater(() -> {
                    errorLabel.setText("Cannot send an email! Check your connection.");
                });
            }
        }).start();
    }

    private Boolean sendMail(String code) {
        MailSenderModel mailSenderModel = new MailSenderModel();
        mailSenderModel.setTopic(PASSWORD_RESET_CODE_TOPIC);
        mailSenderModel.setMessageText(PASSWORD_RESET_CODE_MESSAGE + code);
        return mailSenderModel.sendMail(emailTextField.getText());
    }
}
