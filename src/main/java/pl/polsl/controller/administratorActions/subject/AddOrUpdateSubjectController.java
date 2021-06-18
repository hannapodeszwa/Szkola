package pl.polsl.controller.administratorActions.subject;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Przedmioty;
import pl.polsl.model.Subject;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateSubjectController implements ParametrizedController {

    public TextField name;
    public Label title;
    public Button confirm;

    private Przedmioty toUpdate;
    public enum md {Add, Update}
    private md mode = md.Update;

    @FXML
    public void initialize()
    {
        name.textProperty().addListener(TextListener);
        disableButton();
    }

    private void disableButton()
    {
        if (name.getText().isEmpty())
            confirm.setDisable(true);
        else
            confirm.setDisable(false);
    }
    private ChangeListener TextListener = (observable, oldValue, newValue) -> {
        disableButton();
    };

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
                WindowSize.manageSubjectsForm.getWidth(), WindowSize.manageSubjectsForm.getHeight());
    }

    private void setNewValues(Przedmioty p)
    {
        p.setNazwa( (name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));
    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        Main.setRoot("administratorActions/subject/manageSubjectsForm",
                WindowSize.manageSubjectsForm.getWidth(), WindowSize.manageSubjectsForm.getHeight());
    }
}
