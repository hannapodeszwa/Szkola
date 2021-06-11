package pl.polsl.controller.common;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import pl.polsl.Main;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.SigningModel;

import java.io.IOException;

public class SignInController {

    private String login;
    private String password;
    private SigningModel signingModel;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox rolesComboBox;

    private Parent parent;
    private Scene scene;

    @FXML
    public void initialize() {
        signingModel = new SigningModel();
        loginTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));

        passwordTextField.textProperty().addListener((observable -> {
            errorLabel.setText("");
        }));
    }

    public void signInClick() throws IOException {
       if(!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()){
           login = loginTextField.getText();
           password = passwordTextField.getText();
           Uzytkownicy user = signingModel.getUser(login, password);

           if(user != null){
               switch(user.getDostep()){
                   case "uczen":
                       Main.setRoot("menu/studentMenuForm");
                       break;
                   case  "nauczyciel":
                       Main.setRoot("menu/teacherMenuForm");
                       break;
                   case "rodzic":
                       //Main.setRoot("/view/menu/studentMenuForm");
                       break;
               }
           } else errorLabel.setText("Wrong login or password");

       } else errorLabel.setText("Fill all of the fields");
    }

    public void textChanged(){
        errorLabel.setText("");
    }
}
