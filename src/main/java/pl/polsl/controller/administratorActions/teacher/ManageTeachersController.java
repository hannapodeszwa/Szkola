package pl.polsl.controller.administratorActions.teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageTeachersController {

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
        List l=t.getAllTeachers();

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
                    WindowSize.addOrUpdateTeacherForm.getWidth(),  WindowSize.addOrUpdateTeacherForm.getHeight());
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
       //delete from class
       if(!(classTutorList.isEmpty())) {
           for (Klasy k: classTutorList) {
               k.setIdWychowawcy(null); //delete this teacher from class
               (new SchoolClass()).update(k);
           }
       }

       //delete user
       Uzytkownicy userToDelete = (new UserModel()).getUserByIdAndRole(toDelete.getID(), Roles.TEACHER);
       if(userToDelete !=null)
       {
           (new UserModel()).delete(userToDelete);
       }


       deleteMessages(toDelete);
      deleteSchedule(toDelete);

       //usuwanie z kola
       //usuwanie z udzialu w konkursie

       (new Teacher()).delete(toDelete);
   }

    private void deleteMessages(Nauczyciele toDelete)
    {
        List<Wiadomosci> messagesList = (new MessageModel()).findByTeacher(toDelete);
        if(!(messagesList.isEmpty())) {
            for (Wiadomosci w: messagesList) {
                /*switch (w.getTyp())
                {
                    case (0):
                    case(2):
                        w.setOdbiorca(null);
                        (new MessageModel()).update(w);
                        break;
                    case(1):
                    case(3):
                        w.setNadawca(null);
                        (new MessageModel()).update(w);
                        break;
                }*/
            }
        }
    }

    private void deleteSchedule(Nauczyciele toDelete)
    {
        List<Rozklady> teacherScheduleList = (new ScheduleModel()).findByTeacher(toDelete);
        if(!(teacherScheduleList.isEmpty())) {
            for (Rozklady r: teacherScheduleList) {
                r.setIdNauczyciela(null);
                (new ScheduleModel()).update(r);
            }
        }
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
        Main.setRoot("menu/adminMenuForm",
                WindowSize.adminMenuForm.getWidth(), WindowSize.adminMenuForm.getHeight());
    }


}
