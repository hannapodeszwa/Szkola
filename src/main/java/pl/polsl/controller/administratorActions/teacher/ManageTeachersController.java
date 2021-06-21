package pl.polsl.controller.administratorActions.teacher;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private Label name2;
    @FXML
    private Label email;
    @FXML
    private Label phone;

    @FXML
    private TextField searchT;
    @FXML
    private ComboBox searchC;

    private String login;

    private ObservableList<Rodzice> teachers;

    @FXML
    public void initialize()
    {
        displayTableTeachers();
        changeLabels();
        displayCategories();
        search();
    }

    private void search()
    {
        FilteredList<Nauczyciele> filter = new FilteredList(teachers, p -> true);
        tableTeachers.setItems(filter);
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
    private void displayCategories()
    {
        searchC.getItems().add("Imię");
        searchC.getItems().add("Nazwisko");
    }

    public void displayTableTeachers()
    {
        tableTeachers.getItems().clear();
        Teacher t = new Teacher();
        List l=t.getAllTeachers();
        teachers = FXCollections.observableArrayList(l);

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        for (Object n: l) {
            tableTeachers.getItems().add((Nauczyciele) n);
        }
    }

    private void changeLabels()
    {
        ObservableList<Nauczyciele> selectedTeacher = tableTeachers.getSelectionModel().getSelectedItems();
        selectedTeacher.addListener(new ListChangeListener<Nauczyciele>() {
            @Override
            public void onChanged(Change<? extends Nauczyciele> change) {
                String selectedName2 = selectedTeacher.get(0).getDrugieImie();
                String selectedEmail = selectedTeacher.get(0).getEmail();
                String selectedPhone= selectedTeacher.get(0).getNrKontaktowy();

                name2.setText(selectedName2);
                email.setText(selectedEmail);
                phone.setText(selectedPhone);
                }
        });
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
       login = userToDelete.getLogin();
       if(userToDelete !=null)
       {
           (new UserModel()).delete(userToDelete);
       }

        deleteMessages(toDelete);
        deleteSchedule(toDelete);
        deleteNote(toDelete);
        deleteCompetition(toDelete);
        deleteClub(toDelete);

       (new Teacher()).delete(toDelete);
   }

    private void deleteNote(Nauczyciele toDelete)
    {
        List<Uwagi> teacherNoteList = (new NoteModel()).findByTeacher(toDelete);
        if(!(teacherNoteList.isEmpty())) {
            for (Uwagi u: teacherNoteList) {
                u.setIdNauczyciela(null);
                (new NoteModel()).update(u);
            }
        }
    }

    private void deleteCompetition(Nauczyciele toDelete)
    {
        List<Udzialwkonkursie> teacherCompetitionList = (new CompetitionModel()).findByTeacher(toDelete);
        if(!(teacherCompetitionList.isEmpty())) {
            for (Udzialwkonkursie u: teacherCompetitionList) {
                u.setIdNauczyciela(null);
                (new CompetitionModel()).update(u);
            }
        }
    }

    private void deleteClub(Nauczyciele toDelete)
    {
        List<Kolanaukowe> teacherClubList = (new ClubModel()).findByTeacher(toDelete);
        if(!(teacherClubList.isEmpty())) {
            for (Kolanaukowe k: teacherClubList) {
                k.setIdNauczyciela(null);
                (new ClubModel()).update(k);
            }
        }
    }

    private void deleteMessages(Nauczyciele toDelete) {
        List<Wiadomosci> messagesList = (new MessageModel()).findByTeacher(login);
        if (!(messagesList.isEmpty())) {

            for (Wiadomosci w : messagesList) {
                if (w.getNadawca().equals(login)) {
                    w.setNadawca(null);
                    (new MessageModel()).update(w);
                } else {
                    w.setOdbiorca(null);
                    (new MessageModel()).update(w);
                }
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
