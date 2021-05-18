package pl.polsl.controller;

import javafx.event.ActionEvent;
import pl.polsl.Main;

import java.io.IOException;

public class MenuAdministratorController {

    public void pressMenageUsersButton(ActionEvent event) throws IOException
    {
        System.out.println("Logowanie ucznia");
        Main.setRoot("manageUsers");
    }


}
