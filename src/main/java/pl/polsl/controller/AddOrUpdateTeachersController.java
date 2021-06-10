package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateTeachersController implements ParametrizedController  {
    public TextField name;
    public TextField name2;
    public TextField surname;
    public TextField phone;
    private Nauczyciele toUpdate;

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
           toUpdate = (Nauczyciele) params.get("teacher");
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
            System.out.println("Dodawanie nowego nauczyciela");
            Nauczyciele n = new Nauczyciele();
            n.setImie(name.getText());
            n.setDrugieImie(name2.getText());
            n.setNazwisko(surname.getText());
            n.setNrKontaktowy(phone.getText());

            (new Teacher()).persist(n);
        } else if (mode == md.Update) {
            System.out.println("Modyfikowanie profilu nauczyciela");
            toUpdate.setImie(name.getText());
            toUpdate.setDrugieImie(name2.getText());
            toUpdate.setNazwisko(surname.getText());
            (new Teacher()).update(toUpdate);
        }
        System.out.println("Modyfikowanie profilu nauczyciela");
        Main.setRoot("manageTeachersForm");

    }

    public void discardChangesButton(ActionEvent event) throws IOException
    {
        System.out.println("Zmiany anulowano");
        Main.setRoot("manageTeachersForm");

    }

}
