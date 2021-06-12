package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.polsl.Main;
import pl.polsl.Window;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateTeachersController extends Window implements ParametrizedController  {
    double width = 470;
    double height = 320;
    @FXML
    private AnchorPane window;
    public TextField name;
    public TextField name2;
    public TextField surname;
    public TextField phone;
    public Label title;

    private Nauczyciele toUpdate;
    public enum md {Add, Update}
    private md mode = md.Update;

    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add") {
            mode = md.Add;
            title.setText("Dodawanie nauczyciela:");
        }
        else {
            mode = md.Update;
           toUpdate = (Nauczyciele) params.get("teacher");
            title.setText("Modyfikacja nauczyciela:");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getImie());
            name2.setText(toUpdate.getDrugieImie());
            surname.setText(toUpdate.getNazwisko());
        }
    }

    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        if (mode == md.Add) {
            Nauczyciele n = new Nauczyciele();
           setNewValues(n);
            (new Teacher()).persist(n);
        } else if (mode == md.Update) {
           setNewValues(toUpdate);
            (new Teacher()).update(toUpdate);
        }
        Main.setRoot("administratorActions/teacher/manageTeachersForm",
                manageTeachersFormWidth, manageTeachersFormHeight);
    }

    private void setNewValues(Nauczyciele n)
    {
        n.setImie(name.getText());
        n.setDrugieImie(name2.getText());
        n.setNazwisko(surname.getText());
        n.setNrKontaktowy(phone.getText());
    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/teacher/manageTeachersForm",
                manageTeachersFormWidth, manageTeachersFormHeight);
    }

}
