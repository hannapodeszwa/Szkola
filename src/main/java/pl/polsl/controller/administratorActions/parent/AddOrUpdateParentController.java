package pl.polsl.controller.administratorActions.parent;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.administratorActions.CredentialsGenerator;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rodzice;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.ParentModel;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateParentController implements ParametrizedController, CredentialsGenerator {
    public TextField name;
    public TextField name2;
    public TextField surname;
    public TextField email;
    public TextField phone;
    public TextField login;
    public TextField password;
    public Label title;

    private Rodzice toUpdate;
    public enum md {Add, Update}
    private AddOrUpdateParentController.md mode = AddOrUpdateParentController.md.Update;

    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add") {
            mode = AddOrUpdateParentController.md.Add;
            title.setText("Dodawanie rodzica:");
        }
        else {
            mode = AddOrUpdateParentController.md.Update;
            toUpdate = (Rodzice) params.get("parent");
            title.setText("Modyfikacja rodzica:");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getImie());
            name2.setText(toUpdate.getDrugieImie());
            surname.setText(toUpdate.getNazwisko());
            email.setText(toUpdate.getEmail());
            phone.setText(toUpdate.getNrKontaktowy().toString());
        }
    }

    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        if (mode == AddOrUpdateParentController.md.Add) {
            Uzytkownicy u = new Uzytkownicy();
            Rodzice r = new Rodzice();
            setNewValues(r);

            (new ParentModel()).persist(r);
            setNewValues(u, r.getImie(), r.getNazwisko(), r.getID());
            (new UserModel()).persist(u);

        } else if (mode == AddOrUpdateParentController.md.Update) {
            setNewValues(toUpdate);
            (new ParentModel()).update(toUpdate);
        }
        Main.setRoot("administratorActions/parent/manageParentsForm",
                WindowSize.manageParentsForm.getWidth(), WindowSize.manageParentsForm.getHeight());
    }

    private void setNewValues(Rodzice n)
    {
        n.setImie(name.getText());
        n.setDrugieImie(name2.getText());
        n.setNazwisko(surname.getText());
       // n.setNrKontaktowy(phone.getText());
        n.setEmail(email.getText());
    }

    private void setNewValues(Uzytkownicy u, String name, String surname, Integer id)
    {
        u.setLogin(generateLogin(name,surname));
        u.setHaslo(generatePassword());
        u.setDostep("rodzic");
        u.setID(id);
    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        Main.setRoot("administratorActions/parent/manageParentsForm",
                WindowSize.manageParentsForm.getWidth(), WindowSize.manageParentsForm.getHeight());
    }

}
