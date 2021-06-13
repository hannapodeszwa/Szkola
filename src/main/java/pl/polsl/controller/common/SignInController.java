package pl.polsl.controller.common;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import pl.polsl.Main;
import pl.polsl.controller.menu.StudentMenuController;
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
    private Map params;

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
       if(!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()){
           String login = loginTextField.getText();
           String password = passwordTextField.getText();
           Uzytkownicy user = userModel.getUserByLoginAndPassword(login, password);

           if(user != null){
               switch(user.getDostep()){
                   case "uczen":
                       params = new HashMap<String, String>();
                       params.put("mode", "Student");
                       params.put("id", user.getID());
                       Main.setRoot("menu/studentMenuForm",params);
                       break;
                   case  "nauczyciel":
                       Main.setRoot("menu/teacherMenuForm");
                       break;
                   case "rodzic":
                       params = new HashMap<String, String>();
                       params.put("mode", "Parent");
                       params.put("id", user.getID());
                       Main.setRoot("menu/studentMenuForm",params);
                       break;
                   case "admin":
                       Main.setRoot("menu/adminMenuForm");
                       break;
               }
           } else errorLabel.setText("Wrong login or password");

       } else errorLabel.setText("Fill all of the fields");
    }

    public void resetPasswordAction() throws Exception {
        Main.setRoot("common/resetPasswordForm");
    }

    public void textChanged(){
        errorLabel.setText("");
    }
}
