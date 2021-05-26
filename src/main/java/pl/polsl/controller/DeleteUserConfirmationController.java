package pl.polsl.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class DeleteUserConfirmationController {
    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Usuwanie ucznia");

    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Zmiany anulowano");

    }

}
