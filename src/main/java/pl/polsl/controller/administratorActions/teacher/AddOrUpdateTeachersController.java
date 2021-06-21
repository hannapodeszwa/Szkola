package pl.polsl.controller.administratorActions.teacher;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.administratorActions.CredentialsGenerator;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateTeachersController  implements ParametrizedController, CredentialsGenerator {

    public TextField name;
    public TextField name2;
    public TextField surname;
    public TextField email;
    public TextField phone;
    public Label title;
    public Button confirm;

    private Nauczyciele toUpdate;
    public enum md {Add, Update}
    private md mode = md.Update;

    @FXML
    public void initialize()
    {
        name.textProperty().addListener(TextListener);
        surname.textProperty().addListener(TextListener);
        email.textProperty().addListener(TextListener);
        disableButton();
    }

    private void disableButton()
    {
        if (name.getText().isEmpty() || surname.getText().isEmpty() || email.getText().isEmpty())
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
            email.setText(toUpdate.getEmail());
            phone.setText(toUpdate.getNrKontaktowy());
        }
    }

    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        if (mode == md.Add) {
            Uzytkownicy u = new Uzytkownicy();
            Nauczyciele n = new Nauczyciele();
            setNewValues(n);

            (new Teacher()).persist(n);
            setNewValues(u, n.getImie(), n.getNazwisko(), n.getID());
            (new UserModel()).persist(u);

        } else if (mode == md.Update) {
           setNewValues(toUpdate);
            (new Teacher()).update(toUpdate);
        }
        Main.setRoot("administratorActions/teacher/manageTeachersForm",
                WindowSize.manageTeachersForm.getWidth(), WindowSize.manageTeachersForm.getHeight());
    }

    private void setNewValues(Nauczyciele n)
    {
        n.setImie((name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));
        n.setDrugieImie((name2.getText().length() >= 45 ? name2.getText().substring(0,45) : name2.getText()));
        n.setNazwisko((surname.getText().length() >= 45 ? surname.getText().substring(0,45) : surname.getText()));
        n.setNrKontaktowy((phone.getText().length() >= 45 ? phone.getText().substring(0,45) : phone.getText()));
        n.setEmail((email.getText().length() >= 45 ? email.getText().substring(0,45) : email.getText()));
    }

    private void setNewValues(Uzytkownicy u, String name, String surname, Integer id)
    {
       u.setLogin(generateLogin(name,surname));
       u.setHaslo(generatePassword());
       u.setDostep(Roles.TEACHER);
       u.setID(id);
    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/teacher/manageTeachersForm",
                WindowSize.manageTeachersForm.getWidth(), WindowSize.manageTeachersForm.getHeight());
    }

}
