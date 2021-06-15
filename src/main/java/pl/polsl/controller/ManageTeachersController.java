package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pl.polsl.Main;
import pl.polsl.Window2;
import pl.polsl.WindowSize;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageTeachersController extends Window2 {
    double width = 600; //DO ZMIANY
    double height = 600;
    @FXML
    private AnchorPane window;
    @FXML
    private TableView<Nauczyciele> tableTeachers;
    @FXML
    private TableColumn<Nauczyciele, String> nameC;
    @FXML
    private TableColumn<Nauczyciele, String> surnameC;


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
       Main.setRoot("administratorActions/teacher/addOrUpdateTeacherForm",params,
               WindowSize.addOrUpdateTeacherForm.getWidth(),  WindowSize.addOrUpdateTeacherForm.getHeight());
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
            Main.setRoot("administratorActions/teacher/addOrUpdateTeacherForm", params,
                    addOrUpdateTeacherFormWidth, addOrUpdateTeacherFormHeight);
        }
    }

    public void deleteTeacherButton(ActionEvent event) throws IOException
    {
        Nauczyciele toDelete = tableTeachers.getSelectionModel().getSelectedItem();

        if(toDelete==null)
        {
          chooseTeacherAlert();
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

                deleteTeacher(toDelete, classTutorList);
                displayTableTeachers();
            }
        }
    }

   private void deleteTeacher(Nauczyciele toDelete, List <Klasy> classTutorList)
   {
       if(!(classTutorList.isEmpty())) {
           for (Klasy k: classTutorList) {
               k.setIdWychowawcy(null); //delete this teacher from class
           }
       }

       //USUWANIE UZYTKOWNIKA
       Uzytkownicy userToDelete = (new UserModel()).getUserByIdAndRole(toDelete.getID(), "nauczyciel");
       if(userToDelete !=null)
       {
           (new UserModel()).delete(userToDelete);
       }

       //UsUwANIE WIADOMOSCI


       //do rozkladu zmienic na nulle

       (new Teacher()).delete(toDelete);
   }

    private void chooseTeacherAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuwanie nauczyciela");
        alert.setHeaderText(null);
        alert.setContentText("Wybierz nauczyciela do usunięcia.");
        alert.showAndWait();
    }

    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm");
    }


}
