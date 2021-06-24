package pl.polsl.controller.administratorActions.student;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.*;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentsController {

    @FXML
    private Button buttonDeleteStudent;
    @FXML
    private Button buttonUpdateStudent;
    @FXML
    private ComboBox searchC;
    @FXML
    private TextField searchT;
    @FXML
    private TableView<Uczniowie> tableStudents;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;
    @FXML
    private TableColumn<Uczniowie, String> classC;


    private ObservableList<Uczniowie> students;



    @FXML
    public void initialize()
    {
        Student s= new Student();
        List<Uczniowie> l=s.getAllStudents();

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        classC.setCellValueFactory(CellData -> {
            Integer idKlasy = CellData.getValue().getIdKlasy();
            String numerKlasy = (new SchoolClass()).getClassNumber(idKlasy);
            return new ReadOnlyStringWrapper(numerKlasy);
        });

        for (Uczniowie u: l) {
            tableStudents.getItems().add(u);
        }
        students = FXCollections.observableArrayList(l);

        buttonDeleteStudent.setDisable(true);
        buttonUpdateStudent.setDisable(true);

        tableStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                buttonDeleteStudent.setDisable(false);
                buttonUpdateStudent.setDisable(false);
            }
            else {
                buttonDeleteStudent.setDisable(true);
                buttonUpdateStudent.setDisable(true);
            }
        });

        search();
    }


    private void search()
    {
        FilteredList<Uczniowie> filter = new FilteredList(students, p -> true);
        tableStudents.setItems(filter);
        searchC.setValue("Nazwisko");

        searchT.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (searchC.getValue().toString())
            {
                case "Imię":
                    filter.setPredicate(p -> p.getImie().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "Nazwisko":
                    filter.setPredicate(p -> p.getNazwisko().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
                    break;
            }
        });
    }



    public void addStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Dodawanie ucznia");
        Map params = new HashMap<String, String>();

        params.put("mode","add");
        Main.setRoot("administratorActions/student/addOrUpdateStudentForm",params, WindowSize.addOrUpdateStudentForm.getWidth(), WindowSize.addOrUpdateStudentForm.getHeight());
    }

    public void updateStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Modyfikowanie ucznia");

        Map params = new HashMap<String, String>();

        params.put("student", tableStudents.getSelectionModel().getSelectedItem());
        params.put("mode","update");
        //AddOrUpdateStudentsController
        Main.setRoot("administratorActions/student/addOrUpdateStudentForm",params, WindowSize.addOrUpdateStudentForm.getWidth(), WindowSize.addOrUpdateStudentForm.getHeight());
    }

    public void deleteStudentsButton(ActionEvent event) throws IOException
    {

        Uczniowie u = tableStudents.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz usunąć z listy uczniów " + u.getImie() + " " + (u.getDrugieImie() != null ? u.getDrugieImie() + " " : "") + u.getNazwisko() + "?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {

            List<Object> toUpdate = new ArrayList<>();
            List<Object> toDelete = new ArrayList<>();

            toDelete.add(u);
            Uzytkownicy usr = (new UserModel()).getUserByIdAndRole(u.getID(), Roles.STUDENT);
            toDelete.add(usr);

            //(new ParenthoodModel()).getParentsByChildID(u.getID());
            //toDelete.addAll()

            Klasy k = (new SchoolClass()).getClassByLeader(u.getID());
            if (k != null) {
                k.setIdPrzewodniczacego(null);
                toUpdate.add(k);
            }

            List<Rodzicielstwo> r = (new ParenthoodModel()).findByStudent(u);
            if (r != null) {
                toDelete.addAll(r);
            }

            (new Student()).applyChanges(toUpdate,null,toDelete);

            tableStudents.getItems().remove(tableStudents.getSelectionModel().getSelectedItem());
        }

        System.out.println("Usuwanie ucznia");
    }


    public void goBackButtonClick(ActionEvent event) throws IOException {
        System.out.println("Powrot");
        Main.setRoot("menu/adminMenuForm", WindowSize.adminMenuForm.getWidth(), WindowSize.adminMenuForm.getHeight());
    }
}
