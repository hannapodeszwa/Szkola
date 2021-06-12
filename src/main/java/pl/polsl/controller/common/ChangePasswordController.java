package pl.polsl.controller.common;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import pl.polsl.Main;

import java.io.IOException;

public class ChangePasswordController {

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private TextField confirmationCodeTextField;

    public void changePasswordAction(){

    }

    public void cancelButtonAction() throws IOException {
        Main.setRoot("common/signIn");
    }
}
