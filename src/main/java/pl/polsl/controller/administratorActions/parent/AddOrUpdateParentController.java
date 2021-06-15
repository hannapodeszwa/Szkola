package pl.polsl.controller.administratorActions.parent;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.Window2;
import pl.polsl.WindowSize;
import pl.polsl.controller.AddOrUpdateTeachersController;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateParentController extends Window2 implements ParametrizedController {
    public TextField name;
    public TextField name2;
    public TextField surname;
    public TextField email;
    public TextField phone;
    public TextField login;
    public TextField password;
    public Label title;

    private Nauczyciele toUpdate;
    public enum md {Add, Update}
    private AddOrUpdateTeachersController.md mode = AddOrUpdateTeachersController.md.Update;

    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add") {
            mode = AddOrUpdateTeachersController.md.Add;
            title.setText("Dodawanie nauczyciela:");
        }
        else {
            mode = AddOrUpdateTeachersController.md.Update;
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
        if (mode == AddOrUpdateTeachersController.md.Add) {
            Uzytkownicy u = new Uzytkownicy();
            Nauczyciele n = new Nauczyciele();
            setNewValues(n);

            (new Teacher()).persist(n);
            setNewValues(u, n.getID());
            (new UserModel()).persist(u);

        } else if (mode == AddOrUpdateTeachersController.md.Update) {
            setNewValues(toUpdate);
            (new Teacher()).update(toUpdate);
        }
        Main.setRoot("administratorActions/teacher/manageTeachersForm",
                WindowSize.manageTeachersForm.getWidth(), WindowSize.manageTeachersForm.getHeight());
    }

    private void setNewValues(Nauczyciele n)
    {
        n.setImie(name.getText());
        n.setDrugieImie(name2.getText());
        n.setNazwisko(surname.getText());
        n.setNrKontaktowy(phone.getText());
        n.setEmail(email.getText());
    }

    private void setNewValues(Uzytkownicy u, Integer id)
    {
        u.setLogin(login.getText());
        u.setHaslo(password.getText());
        u.setDostep("nauczyciel");
        u.setID(id);
    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/teacher/manageTeachersForm",
                manageTeachersFormWidth, manageTeachersFormHeight);
    }

}
