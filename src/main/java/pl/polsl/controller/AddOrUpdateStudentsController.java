package pl.polsl.controller;

import javafx.event.ActionEvent;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;

import java.io.IOException;

public class AddOrUpdateStudentsController {
    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Modyfikowanie profilu ucznia");
        Uczniowie u = new Uczniowie();
        u.setID(44);
        u.setImie("Adam");
        u.setNazwisko("Mickiewicz");
        u.setAdres("Litwa");
        u.setIdKlasy(1);
        (new Student()).update(u);

    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Zmiany anulowano");

    }

}
