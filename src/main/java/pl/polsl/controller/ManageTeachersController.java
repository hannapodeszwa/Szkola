package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageTeachersController {
    @FXML
    private TableView<Nauczyciele> tableTeachers;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;


    @FXML
    public void initialize()
    {
        displayTableTeachers();
    }
    public void displayTableTeachers()
    {
        tableTeachers.getItems().clear();
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
       Main.setRoot("addOrUpdateTeacherForm",params, 470, 320);
    }

    public void updateTeacherButton(ActionEvent event) throws IOException
    {
        Nauczyciele toUpdate = tableTeachers.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modyfikacja nauczyciela");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz nauczyciela do modyfikacji.");
            alert.showAndWait();
        }
        else {
            Map params = new HashMap<String, String>();
            params.put("teacher", tableTeachers.getSelectionModel().getSelectedItem());
            params.put("mode", "update");
            Main.setRoot("addOrUpdateTeacherForm", params, 470, 320);
        }
    }

    public void deleteTeacherButton(ActionEvent event) throws IOException
    {
        Nauczyciele toDelete = tableTeachers.getSelectionModel().getSelectedItem();

        if(toDelete==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuwanie nauczyciela");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz nauczyciela do usunięcia.");
            alert.showAndWait();
        }
        else {
            List<Klasy> classTutorList = (new Teacher()).checkTutor(toDelete); //find class where this teacher is tutor

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie nauczyciela");
            String message = "Czy na pewno chcesz usunąć tego nauczyciela?";
            if(!(classTutorList.isEmpty()))
            {
                message += " To wychowawca klasy: \n";
                for (Klasy k: classTutorList) {
                    message += k.getNumer();
                    message += "\n";
                }
            }
            alert.setHeaderText(message);

            alert.setContentText(toDelete.getImie() + " " + toDelete.getNazwisko());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if(!(classTutorList.isEmpty())) {
                    for (Klasy k: classTutorList) {
                        k.setIdWychowawcy(null); //delete this teacher from class
                    }
                }
                (new Teacher()).delete(toDelete);
                displayTableTeachers();
            }
        }
    }
    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm");
    }
}
