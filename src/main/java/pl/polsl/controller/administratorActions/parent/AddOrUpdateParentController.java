package pl.polsl.controller.administratorActions.parent;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import javax.management.remote.rmi._RMIConnection_Stub;
import java.io.IOException;
import java.util.ArrayList;
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
    public Button confirm;

    @FXML
    private TableView<ParenthoodModel> tableStudents;
    @FXML
    private TableColumn<ParenthoodModel, String> nameC;
    @FXML
    private TableColumn<ParenthoodModel, String> surnameC;
    @FXML
    private TableColumn<ParenthoodModel, String> classC;
    @FXML
    private TableColumn<ParenthoodModel, CheckBox> chooseC;


    private Rodzice toUpdate;
    public enum md {Add, Update}
    private AddOrUpdateParentController.md mode = AddOrUpdateParentController.md.Update;
    private   ObservableList<ParenthoodModel> parentchoodList = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        displayStudents();
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

    private void displayStudents()
    {
        tableStudents.setEditable(true);
        tableStudents.getItems().clear();
        Student s = new Student();
        List <Uczniowie> l=s.getAllStudents();
       // List <ParentChoice>parentchoodList = new ArrayList<>();


        for (Uczniowie u : l) {
            parentchoodList.add(new ParenthoodModel(u));
        }

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        classC.setCellValueFactory(CellData -> {
            Integer idKlasy = CellData.getValue().getIdKlasy();
            String numerKlasy = (new SchoolClass()).getClassNumber(idKlasy);
            return new ReadOnlyStringWrapper(numerKlasy);
        });
        chooseC.setCellValueFactory(new PropertyValueFactory<ParenthoodModel, CheckBox>("choose"));

       tableStudents.setItems(parentchoodList);
    }


    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add") {
            mode = md.Add;
            title.setText("Dodawanie rodzica:");
        }
        else {
            mode = md.Update;
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
            checkChildren();
        }
    }

    private void checkChildren()
    {

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
            addChildren(r);

        } else if (mode == md.Update) {
            setNewValues(toUpdate);
            (new ParentModel()).update(toUpdate);
            addChildren(toUpdate);
        }
        Main.setRoot("administratorActions/parent/manageParentsForm",
                WindowSize.manageParentsForm.getWidth(), WindowSize.manageParentsForm.getHeight());
    }

    private void deleteChildren()
    {
        ParenthoodModel p = new ParenthoodModel((new Uczniowie()));
        List<Rodzicielstwo> l = p.findByParent(toUpdate);

        for(Rodzicielstwo r: l)
        {
            p.delete(r);
        }
    }

    private void addChildren(Rodzice parent)
    {
        if(mode == md.Update)
        {
            deleteChildren();
        }

        for(ParenthoodModel ph : parentchoodList )
        {
            if(ph.choose.isSelected()==true)
            {
                Rodzicielstwo r = new Rodzicielstwo();
                r.setIdRodzica(parent.getID());
                r.setIdUcznia(ph.ID);
                ph.persist(r);
            }
        }
    }

    private void setNewValues(Rodzice r)
    {
        r.setImie((name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));
        r.setDrugieImie((name2.getText().length() >= 45 ? name2.getText().substring(0,45) : name2.getText()));
        r.setNazwisko((surname.getText().length() >= 45 ? surname.getText().substring(0,45) : surname.getText()));
        r.setNrKontaktowy((phone.getText().length() >= 20 ? phone.getText().substring(0,20) : phone.getText()));
        r.setEmail((email.getText().length() >= 45 ? email.getText().substring(0,45) : email.getText()));
        r.setAdres((adress.getText().length() >= 45 ? adress.getText().substring(0,45) : adress.getText()));
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
