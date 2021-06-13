package pl.polsl.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import pl.polsl.Main;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInController {

    private String login;
    private String password;

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


           if(true/*db verification*/){
               String role = rolesComboBox.getValue().toString();
               switch(role){
                   case "Student":
                       //check in DB if account is Student
                       if(true){
                           Main.setRoot("menu/studentMenuForm");
                       } else {
                           errorLabel.setText("Permissions incorrect");
                       }
                       break;

                   case "Teacher":
                       //check in DB if account is Teacher
                       if(true){

                           Map params = new HashMap<String, Integer>();

                           params.put("teacher", 1); //TODO: change to signed in teacher ID



                           Main.setRoot("menu/teacherMenuForm", params);
                       } else {
                           errorLabel.setText("Permissions incorrect");
                       }
                       break;
                   case "Parent":
                       //check in DB if account is Parent

                       if(true){
                           Main.setRoot("menu/studentMenuForm"); //pass Student/Parent to form
                       } else {
                           errorLabel.setText("Permissions incorrect");
                       }
                       break;
                   case "Administrator":
                       //check in DB if account is Administrator
                       if(true){
                           Main.setRoot("menu/adminMenuForm");
                       } else {
                           errorLabel.setText("Permissions incorrect");
                       }
                       break;
                   default:
               }
           } else {
               errorLabel.setText("Wrong login or password");
           }

       } else {
           errorLabel.setText("Fill all of the fields");
       }
    }

    public void textChanged(){
        errorLabel.setText("");
    }
}
