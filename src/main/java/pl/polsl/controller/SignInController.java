package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.Main;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInController {

   public void studentButton(ActionEvent event) throws IOException
    {
        System.out.println("Logowanie ucznia");
        Main.setRoot("menu");
    }
   public void teacherButton(ActionEvent event) throws IOException
    {
        System.out.println("Logowanie nauczyciela");
        Main.setRoot("menuTeacher");
    }

    public void administratorButton(ActionEvent event) throws IOException
    {
        System.out.println("Logowanie administratora");
        Main.setRoot("menuAdministrator");
    }
}
