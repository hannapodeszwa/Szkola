package pl.polsl.controller.common;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import static pl.polsl.model.email.EmailMessages.PASSWORD_RESET_CODE_MESSAGE;
import static pl.polsl.model.email.EmailMessages.PASSWORD_RESET_CODE_TOPIC;

public class ResetPasswordController {

    private Boolean loginSet = false;
    private Boolean emailSet = false;
    private Boolean foundMail = false;
    private volatile Boolean switchScreens = false;
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

    public void sendVerificationEmailAction() throws IOException {
        Uzytkownicy user = userModel.getUserByLogin(loginTextField.getText());
        if (user != null) {
            if (user.getEmail() != null) {
                prepareVerification();
            } else {
                List<Rodzicielstwo> ids = parenthoodModel.getParentsByChildID(user.getID());
                System.out.println(ids.get(0));

                for (Rodzicielstwo id : ids) {
                    String code = parentModel.getParentEmailByID(id.getIdRodzica());
                    if (code.equals(emailTextField.getText())) {
                        prepareVerification();
                        foundMail = true;
                        break;
                    }
                }

                if(!foundMail){
                    errorLabel.setText("Login and/or email do not match.");
                } else {
                    CompletableFuture.runAsync(() -> {
                        while (!switchScreens) {}
                        try {
                            Main.setRoot("common/changePasswordForm");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    public void cancelAction() throws IOException {
        Main.setRoot("common/signIn");
    }

    private void prepareVerification() {
        sendVerificationMailButton.setDisable(true);
        VerificationCodesGenerator verificationCodesGenerator = new VerificationCodesGenerator(6, ThreadLocalRandom.current());

        loginTextField.setDisable(true);
        emailTextField.setDisable(true);
        cancelButton.setDisable(true);
        sendVerificationMailButton.setDisable(true);
        sendingProgressBar.setVisible(true);

        CompletableFuture.runAsync(() -> {
            String code = verificationCodesGenerator.generateString();
            while (verificationCodesModel.getVerificationCode(code) != null)
                code = verificationCodesGenerator.generateString();
            verificationCodesModel.insertVerificationCode(code, loginTextField.getText(), emailTextField.getText());
            sendMail(code);
            switchScreens = true;
        });
    }

    private void sendMail(String code) {
        MailSenderModel mailSenderModel = new MailSenderModel();
        mailSenderModel.setTopic(PASSWORD_RESET_CODE_TOPIC);
        mailSenderModel.setMessageText(PASSWORD_RESET_CODE_MESSAGE + code);
        mailSenderModel.sendMail(emailTextField.getText());
    }
}
