package pl.polsl.controller.administratorActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentsController {

    @FXML
    public Button buttonDeleteStudent;
    @FXML
    public Button buttonUpdateStudent;
    @FXML
    private TableView<Uczniowie> tableStudents;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;
    @FXML
    private TableColumn<Uczniowie, String> classC;




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


    }




    public void addStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Dodawanie ucznia");
        Map params = new HashMap<String, String>();

        params.put("mode","add");
        Main.setRoot("administratorActions/student/addOrUpdateStudentForm",params);
    }

    public void updateStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Modyfikowanie ucznia");

        Map params = new HashMap<String, String>();

        params.put("student", tableStudents.getSelectionModel().getSelectedItem());
        params.put("mode","update");
        //AddOrUpdateStudentsController
        Main.setRoot("administratorActions/student/addOrUpdateStudentForm",params);
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
            Uzytkownicy usr = (new UserModel()).getUserByIdAndRole(u.getID(),"uczen");
            toDelete.add(usr);

            //(new ParenthoodModel()).getParentsByChildID(u.getID());
            //toDelete.addAll()

            Klasy k = (new SchoolClass()).getClassByLeader(u.getID());
            k.setIdPrzewodniczacego(null);
            toUpdate.add(k);

            (new Student()).applyChanges(toUpdate,null,toDelete);

            tableStudents.getItems().remove(tableStudents.getSelectionModel().getSelectedItem());
        }

        System.out.println("Usuwanie ucznia");
    }


    public void goBackButtonClick(ActionEvent event) throws IOException {
        System.out.println("Powrot");
        Main.setRoot("menu/adminMenuForm");
    }
}
