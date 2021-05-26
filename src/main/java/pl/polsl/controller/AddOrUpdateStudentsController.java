package pl.polsl.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class AddOrUpdateStudentsController {
    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Modyfikowanie profilu ucznia");

    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Zmiany anulowano");

    }

}
