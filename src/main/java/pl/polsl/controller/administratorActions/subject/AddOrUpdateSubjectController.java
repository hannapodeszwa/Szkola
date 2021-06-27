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
import java.util.HashMap;
import java.util.Map;

public class AddOrUpdateSubjectController implements ParametrizedController {

    public TextField name;
    public Label title;
    public Button confirm;

    private Przedmioty toUpdate;
    public enum md {Add, Update}
    private md procedure = md.Update;
    private String mode;

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
        mode = (String)params.get("mode");

        if (params.get("procedure") == "add") {
            procedure = md.Add;
            title.setText("Dodawanie przedmiotu");
        }
        else {
            procedure = md.Update;
            toUpdate = (Przedmioty) params.get("subject");
            title.setText("Modyfikacja przedmiotu");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getNazwa());
        }
    }

    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        if (procedure == md.Add) {
            Przedmioty p = new Przedmioty();
            p.setNazwa(name.getText());

            (new Subject()).persist(p);
        } else if (procedure == md.Update) {
            toUpdate.setNazwa(name.getText());
            (new Subject()).update(toUpdate);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/subject/manageSubjectsForm", params, WindowSize.manageSubjectsForm);
    }

    private void setNewValues(Przedmioty p)
    {
        p.setNazwa( (name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));
    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        Main.setRoot("administratorActions/subject/manageSubjectsForm", params, WindowSize.manageSubjectsForm);
    }
}
