package pl.polsl.controller.administratorActions;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.SchoolClass;
import pl.polsl.model.Student;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AddOrUpdateStudentsController implements ParametrizedController, CredentialsGenerator {

    public TextField poleImie;
    public TextField poleMail;
    public TextField poleDrugieImie;
    public TextField poleAdres;
    public TextField poleNazwisko;
    public ComboBox<String> poleKlasa;
    public Button buttonAccept;


    private Uczniowie modyfikowany;

    private ChangeListener TextListener = (observable, oldValue, newValue) -> {

        buttonAccept.setDisable(poleImie.getText().isEmpty() || poleNazwisko.getText().isEmpty() || poleMail.getText().isEmpty());
    };

    @FXML
    public void initialize()
    {
        SchoolClass c= new SchoolClass();
        List<Klasy> l = c.displayClass();
        for (Klasy el : l) {
            poleKlasa.getItems().add(el.getNumer());
        }

        poleImie.textProperty().addListener(TextListener);
        poleNazwisko.textProperty().addListener(TextListener);
        poleMail.textProperty().addListener(TextListener);

        buttonAccept.setDisable(poleImie.getText().isEmpty() || poleNazwisko.getText().isEmpty() || poleMail.getText().isEmpty());

    }

    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add")
            mode = md.Add;
        else {
            mode = md.Update;
            modyfikowany = (Uczniowie) params.get("student");
        }

        if (modyfikowany != null) {
            poleImie.setText(modyfikowany.getImie());
            poleDrugieImie.setText(modyfikowany.getDrugieImie());
            poleNazwisko.setText(modyfikowany.getNazwisko());
            poleAdres.setText(modyfikowany.getAdres());
            poleMail.setText(modyfikowany.getEmail());
            String classNumber = (new SchoolClass()).getClassNumber(modyfikowany.getIdKlasy());
            poleKlasa.getSelectionModel().select(classNumber);
        }
        else
            poleKlasa.getSelectionModel().selectFirst();

    }

    public enum md {Add, Update}

    private md mode = md.Update;

    public void confirmChangesButton(ActionEvent event) throws IOException {

        if (mode == md.Add) {
            System.out.println("Dodawanie nowego ucznia");
            Uczniowie u = new Uczniowie();
            u.setImie(poleImie.getText());
            u.setDrugieImie(poleDrugieImie.getText());
            u.setNazwisko(poleNazwisko.getText());
            u.setAdres(poleAdres.getText());
            u.setEmail(poleMail.getText());
            String classNumber = poleKlasa.getSelectionModel().getSelectedItem();
            u.setIdKlasy((new SchoolClass()).getClassId(classNumber));

            (new Student()).persist(u);

            Uzytkownicy usr = new Uzytkownicy();
            usr.setID(u.getID());
            usr.setHaslo(generatePassword());
            usr.setLogin(generateLogin(u.getImie(),u.getNazwisko()));
            usr.setDostep("uczen");
            (new UserModel()).persist(usr);

            sendCredentialsByEmail(usr.getLogin(), usr.getHaslo(), u.getEmail());


        } else if (mode == md.Update) {
            System.out.println("Modyfikowanie profilu ucznia");
            modyfikowany.setImie(poleImie.getText());
            modyfikowany.setDrugieImie(poleDrugieImie.getText());
            modyfikowany.setNazwisko(poleNazwisko.getText());
            modyfikowany.setAdres(poleAdres.getText());
            modyfikowany.setEmail(poleMail.getText());
            String classNumber = poleKlasa.getSelectionModel().getSelectedItem();
            modyfikowany.setIdKlasy((new SchoolClass()).getClassId(classNumber));
            (new Student()).update(modyfikowany);
        }
        Main.setRoot("administratorActions/student/manageStudentsForm");
    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        System.out.println("Zmiany anulowano");
        Main.setRoot("administratorActions/student/manageStudentsForm");
    }

}
