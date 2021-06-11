package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.entities.Klasy;
import pl.polsl.model.SchoolClass;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateClassController implements ParametrizedController{
    public TextField name;

    private Klasy toUpdate;

    public enum md {Add, Update}

    private md mode = md.Update;

    public void initialize(md mode1) {
        mode = mode1;
    }
    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add")
            mode = md.Add;
        else {
            mode = md.Update;
            toUpdate = (Klasy) params.get("class");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getNumer());
        }
    }

    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        if (mode == md.Add) {
            Klasy k = new Klasy();
            k.setNumer(name.getText());
            k.setIdPrzewodniczacego(1); //DO ZMIANY
            k.setIdWychowawcy(1); //DO ZMIANY

            (new SchoolClass()).persist(k);
        } else if (mode == md.Update) {
            toUpdate.setNumer(name.getText());
            (new SchoolClass()).update(toUpdate);
        }
        Main.setRoot("manageClassForm");

    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        Main.setRoot("manageClassForm");
    }



}
