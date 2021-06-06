package pl.polsl.controller;

import pl.polsl.Main;

import java.io.IOException;

public class ViewGradesController {
    public void backAction() throws IOException {
        Main.setRoot("menu/teacherMenuForm");
    }
}
