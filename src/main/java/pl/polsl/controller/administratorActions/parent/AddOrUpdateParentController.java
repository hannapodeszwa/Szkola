package pl.polsl.controller.administratorActions.parent;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.administratorActions.CredentialsGenerator;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddOrUpdateParentController implements ParametrizedController, CredentialsGenerator {
    public TextField name;
    public TextField name2;
    public TextField surname;
    public TextField email;
    public TextField phone;
    public TextField adress;
    public Label title;

    @FXML
    private TableView<Uczniowie> tableStudents;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;
    @FXML
    private TableColumn<Uczniowie, String> classC;
    @FXML
    private TableColumn<ParentChoice, CheckBox> chooseC;


    private Rodzice toUpdate;
    public enum md {Add, Update}
    private AddOrUpdateParentController.md mode = AddOrUpdateParentController.md.Update;

    @FXML
    public void initialize()
    {
        displayStudents();
    }

    private void displayStudents()
    {
        tableStudents.setEditable(true);
        tableStudents.getItems().clear();
        Student s = new Student();
        List l=s.displayStudents();

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        classC.setCellValueFactory(CellData -> {
            Integer idKlasy = CellData.getValue().getIdKlasy();
            String numerKlasy = (new SchoolClass()).getClassNumber(idKlasy);
            return new ReadOnlyStringWrapper(numerKlasy);
        });
        chooseC.setCellValueFactory(new PropertyValueFactory<ParentChoice, CheckBox>("choose"));


        for (Object u: l) {
            tableStudents.getItems().add((Uczniowie) u);
        }
    }


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
            adress.setText(toUpdate.getAdres());
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

    private void setNewValues(Rodzice r)
    {
        r.setImie(name.getText());
        r.setDrugieImie(name2.getText());
        r.setNazwisko(surname.getText());
       // r.setNrKontaktowy(phone.getText());
        r.setEmail(email.getText());
        r.setAdres(adress.getText());
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
