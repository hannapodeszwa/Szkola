package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Subject;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateSubjectController implements ParametrizedController {
    public TextField name;

    private Przedmioty toUpdate;

    public enum md {Add, Update}

    private md mode = md.Update;

    public void initialize(md mode1) {
        mode = mode1;
    }
    @Override
    public void passArguments(Map params) {
        if (params.get("mode") == "add")
            mode = md.Add;
        else {
            mode = md.Update;
            toUpdate = (Przedmioty) params.get("subject");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getNazwa());
        }
    }

    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        if (mode == md.Add) {
            Przedmioty p = new Przedmioty();
            p.setNazwa(name.getText());

            (new Subject()).persist(p);
        } else if (mode == md.Update) {
            toUpdate.setNazwa(name.getText());
            (new Subject()).update(toUpdate);
        }
        Main.setRoot("manageSubjectsForm");

    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        Main.setRoot("manageSubjectsForm");
    }
}
