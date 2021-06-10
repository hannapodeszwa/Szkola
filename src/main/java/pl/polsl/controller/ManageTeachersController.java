package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.polsl.Main;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageTeachersController implements ParametrizedController {
    @FXML
    private TableView<Nauczyciele> tableTeachers;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;

    @Override
    public void passArguments(Map params) {

    }

    @FXML
    public void initialize()
    {
        Teacher t = new Teacher();
        List l=t.displayTeachers();


        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        for (Object n: l) {
            tableTeachers.getItems().add((Nauczyciele) n);
        }
    }

    public void addTeacherButton(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("mode","add");
       Main.setRoot("addOrUpdateTeacherForm",params);
    }

    public void updateTeacherButton(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();

        params.put("teacher", tableTeachers.getSelectionModel().getSelectedItem());
        params.put("mode","update");
       Main.setRoot("addOrUpdateTeacherForm",params);
    }

    public void deleteTeacherButton(ActionEvent event) throws IOException
    {
        System.out.println("Usuwanie nauczyciela");
        Main.setRoot("deleteUserConfirmationForm");
    }
    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm");

    }
}
