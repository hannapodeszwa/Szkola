package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pl.polsl.Main;
import pl.polsl.Window2;
import pl.polsl.entities.Przedmioty;
import pl.polsl.model.Subject;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateSubjectController extends Window2 implements ParametrizedController {
    double width = 400;
    double height = 280;
    @FXML
    private AnchorPane window;
    public TextField name;
    public Label title;

    private Przedmioty toUpdate;
    public enum md {Add, Update}
    private md mode = md.Update;

    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add") {
            mode = md.Add;
            title.setText("Dodawanie przedmiotu:");
        }
        else {
            mode = md.Update;
            toUpdate = (Przedmioty) params.get("subject");
            title.setText("Modyfikacja przedmiotu:");
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
        Main.setRoot("administratorActions/subject/manageSubjectsForm",
                manageSubjectsFormWidth, manageSubjectsFormHeight);

    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        Main.setRoot("administratorActions/subject/manageSubjectsForm",
                manageSubjectsFormWidth, manageSubjectsFormHeight);
    }
}
