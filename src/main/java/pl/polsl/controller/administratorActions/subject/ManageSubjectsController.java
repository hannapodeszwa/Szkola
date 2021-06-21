package pl.polsl.controller.administratorActions.subject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.utils.WindowSize;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageSubjectsController{
    @FXML
    private TableView<Przedmioty> tableSubjects;
    @FXML
    private TableColumn<Przedmioty, String> nameC;

    @FXML
    public void initialize()
    {
        displayTableSubjects();
    }

    private void displayTableSubjects()
    {
        tableSubjects.getItems().clear();
        Subject s= new Subject();
        List l=s.displaySubjects();
        nameC.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        for (Object p: l) {
            tableSubjects.getItems().add((Przedmioty) p);
        }
    }

    public void addSubjectButton(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("mode","add");
        Main.setRoot("administratorActions/subject/addOrUpdateSubjectForm",params,
                WindowSize.addOrUpdateSubjectForm.getWidth(), WindowSize.addOrUpdateSubjectForm.getHeight());
    }

    public void updateSubjectButton(ActionEvent event) throws IOException
    {
        Przedmioty toUpdate = tableSubjects.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            chooseSubjectAlert(true);
        }
        else {
            Map params = new HashMap<String, String>();
            params.put("subject", toUpdate);
            params.put("mode", "update");
            Main.setRoot("administratorActions/subject/addOrUpdateSubjectForm",params,
                    WindowSize.addOrUpdateSubjectForm.getWidth(), WindowSize.addOrUpdateSubjectForm.getHeight());
        }
    }

    public void deleteSubjectButton(ActionEvent event) throws IOException
    {
        Przedmioty toDelete = tableSubjects.getSelectionModel().getSelectedItem();
        if(toDelete==null)
        {
           chooseSubjectAlert(false);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie przedmiotu");
            alert.setHeaderText("Czy na pewno chcesz usunąć ten przedmiot?");
            alert.setContentText(toDelete.getNazwa() +
                    "\nSpowoduje to też usunięcie wszystkich ocen, nieobecności i rozkładów z tym przedmiotem.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                deleteSchedule(toDelete);
                deleteGrades(toDelete);
                deleteAbsence(toDelete);

                (new Subject()).delete(toDelete);
                displayTableSubjects();
            }
        }
    }

    private void deleteSchedule(Przedmioty toDelete)
    {
        List<Rozklady> classScheduleList = (new ScheduleModel()).findBySubject(toDelete);
        if(!(classScheduleList.isEmpty())) {
            for (Rozklady r: classScheduleList) {
                (new ScheduleModel()).delete(r);
            }
        }
    }

    private void deleteGrades(Przedmioty toDelete)
    {
        List<Oceny> gradesList = (new Grade()).findBySubject(toDelete);
        if(!(gradesList.isEmpty())) {
            for (Oceny o: gradesList) {
                (new Grade()).delete(o);
            }
        }
    }

    private void deleteAbsence(Przedmioty toDelete)
    {
        List<Nieobecnosci> absenceList = (new AbsenceModel()).findBySubject(toDelete);
        if(!(absenceList.isEmpty())) {
            for (Nieobecnosci n: absenceList) {
                (new AbsenceModel()).delete(n);
            }
        }
    }

    private void chooseSubjectAlert(boolean update)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(update)
        {
            alert.setTitle("Modyfikacja przedmiotu");
            alert.setContentText("Wybierz przedmiot do modyfikacji.");
        }
        else
        {
            alert.setTitle("Usuwanie przedmiotu");
            alert.setContentText("Wybierz przedmiot do usunięcia.");
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm",
                WindowSize.adminMenuForm.getWidth(), WindowSize.adminMenuForm.getHeight());
    }
}
