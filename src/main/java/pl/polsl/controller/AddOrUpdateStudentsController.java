package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateStudentsController implements ParametrizedController {

    public TextField poleImie;
    public TextField poleDrugieImie;
    public TextField poleAdres;
    public TextField poleNazwisko;
    //public ComboBox<Integer> poleKlasa;


    private Uczniowie modyfikowany;

    @Override
    public void passArguments(Map params) {
        if (params.get("tryb") == "dodawanie")
            tryb = tr.Dodaj;
        else {
            tryb = tr.Modyfikuj;
            modyfikowany = (Uczniowie) params.get("uczen");
        }

        if (modyfikowany != null) {
            poleImie.setText(modyfikowany.getImie());
            poleDrugieImie.setText(modyfikowany.getDrugieImie());
            poleNazwisko.setText(modyfikowany.getNazwisko());
            poleAdres.setText(modyfikowany.getAdres());
        }
    }

    public enum tr {Dodaj, Modyfikuj}

    private tr tryb = tr.Modyfikuj;

    public void Initalize(tr mode) {
        tryb = mode;
    }

    public void confirmChangesButton(ActionEvent event) throws IOException {

        if (tryb == tr.Dodaj) {
            System.out.println("Dodawanie nowego ucznia");
            Uczniowie u = new Uczniowie();
            //u.setID(44);
            u.setImie(poleImie.getText());
            u.setDrugieImie(poleDrugieImie.getText());
            u.setNazwisko(poleNazwisko.getText());
            u.setAdres(poleAdres.getText());
            u.setIdKlasy(1);
            (new Student()).persist(u);
        } else if (tryb == tr.Modyfikuj) {
            System.out.println("Modyfikowanie profilu ucznia");
            modyfikowany.setImie(poleImie.getText());
            modyfikowany.setDrugieImie(poleDrugieImie.getText());
            modyfikowany.setNazwisko(poleNazwisko.getText());
            modyfikowany.setAdres(poleAdres.getText());
            //modyfikowany.setIdKlasy(poleKlasa.getSelectionModel().getSelectedItem());
            (new Student()).update(modyfikowany);
        }
        Main.setRoot("manageStudentsForm");
    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        System.out.println("Zmiany anulowano");
        Main.setRoot("manageStudentsForm");
    }

}
