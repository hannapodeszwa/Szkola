package pl.polsl.controller.menu;

import pl.polsl.Main;

import java.io.IOException;

public class StudentMenuController {

    public void logOutAction() throws IOException {
        Main.setRoot("common/signIn");
    }
}
