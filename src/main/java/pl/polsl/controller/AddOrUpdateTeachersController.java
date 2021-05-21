package pl.polsl.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class AddOrUpdateTeachersController {
    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Modyfikowanie profilu nauczyciela");

    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Zmiany anulowano");

    }

}
