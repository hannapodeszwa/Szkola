package pl.polsl.controller;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import pl.polsl.Main;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;
import pl.polsl.model.Subject;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddOrUpdateTeachersController implements ParametrizedController  {
    public TextField name;
    public TextField name2;
    public TextField surname;
    public TextField phone;

    public ListView subjectsList;
    @FXML
    private TableView<Przedmioty> subjectsTable;
    @FXML
    private TableColumn<Przedmioty, String> subjectName;
    @FXML
    private TableColumn<Przedmioty, Boolean> choose;

    private Nauczyciele toUpdate;

    public enum md {Add, Update}

    private md mode = md.Update;
    public void initialize(md mode1) {
        mode = mode1;
    }
    @FXML
    public void initialize() {

       // ListView<Przedmioty> subjectsList = new ListView<>();
        Subject s= new Subject();
        List l=s.displaySubjects();

        subjectName.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
       // choose.setCellValueFactory(new PropertyValueFactory<TableData,Boolean>("active"));

        for (Object p: l) {

            //subjectsList.getItems().add((Przedmioty)p);
            subjectsTable.getItems().add((Przedmioty) p);
        }


      /*  subjectsList.setCellFactory(CheckBoxListCell.forListView(new Callback<Przedmioty, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Przedmioty item) {
                return item.onProperty();
            }
        }));*/
        }

    @Override
    public void receiveArguments(Map params) {
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
