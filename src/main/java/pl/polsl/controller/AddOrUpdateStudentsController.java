package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateStudentsController implements ParametrizedController {

    public TextField poleImie;
    public TextField poleDrugieImie;
    public TextField poleAdres;
    public TextField poleNazwisko;
    public ComboBox poleKlasa;

    @Override
    public void passArguments(Map<String, String> params) {
        if (params.get("tryb") == "dodawanie")
            tryb = tr.Dodaj;
        else
            tryb = tr.Modyfikuj;

        poleImie.setText(params.getOrDefault("imie", "Błądzisław"));
        poleNazwisko.setText(params.getOrDefault("nazwisko", "Errorkiewicz"));
        poleAdres.setText(params.getOrDefault("adres", "Błędowo"));

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
            u.setID(44);
            u.setImie("Adam");
            u.setNazwisko("Mickiewicz");
            u.setAdres("Litwa");
            u.setIdKlasy(1);
            (new Student()).persist(u);
        } else if (tryb == tr.Modyfikuj) {
            System.out.println("Modyfikowanie profilu ucznia");
            Uczniowie u = new Uczniowie();
            u.setID(44);
            u.setImie("Adam");
            u.setNazwisko("Mickiewicz");
            u.setAdres("Litwa");
            u.setIdKlasy(1);
            (new Student()).update(u);
        }

    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        System.out.println("Zmiany anulowano");

    }

}
