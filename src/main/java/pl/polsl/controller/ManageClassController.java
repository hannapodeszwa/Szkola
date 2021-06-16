package pl.polsl.controller;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pl.polsl.Main;
import pl.polsl.Window2;
import pl.polsl.WindowSize;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rozklady;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.ScheduleModel;
import pl.polsl.model.SchoolClass;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageClassController extends Window2 {
    double width = 600; //DO ZMIANY
    double height = 600;
    @FXML
    private AnchorPane window;
    @FXML
    private TableView<Klasy> tableClass;
    @FXML
    private TableColumn<Klasy, String> nameC;
    @FXML
    private Label tutor;
    @FXML
    private Label leader;


    @FXML
    public void initialize() {
        displayTableClass();
        changeLabels();
    }

    private void displayTableClass() {
        tableClass.getItems().clear();
        SchoolClass c = new SchoolClass();
        List l = c.displayClass();
        nameC.setCellValueFactory(new PropertyValueFactory<>("numer"));

        for (Object k : l) {
            tableClass.getItems().add((Klasy) k);
        }
    }

    private void changeLabels()
    {
        ObservableList<Klasy> selectedClass = tableClass.getSelectionModel().getSelectedItems();
        selectedClass.addListener(new ListChangeListener<Klasy>() {
            @Override
            public void onChanged(Change<? extends Klasy> change) {
                Integer selectedtutor = selectedClass.get(0).getIdWychowawcy();
                Integer selectedleader = selectedClass.get(0).getIdPrzewodniczacego();

                if (selectedtutor != null) {
                    Nauczyciele tutorClass = (new Teacher()).getTeacherById(selectedtutor);
                    tutor.setText(tutorClass.getImie() + " " + tutorClass.getNazwisko());
                }
                else
                    tutor.setText("");
                if (selectedleader != null) {
                    Uczniowie leaderClass = (new Student()).getStudentById(selectedleader);
                    leader.setText(leaderClass.getImie() + " " + leaderClass.getNazwisko());
                }
                else
                    leader.setText("");
            }
        });
    }

    public void addClassButton(ActionEvent event) throws IOException {
        Map params = new HashMap<String, String>();

        params.put("mode", "add");
        Main.setRoot("administratorActions/class/addOrUpdateClassForm", params,
                addOrUpdateClassFormWidth, addOrUpdateClassFormHeight);
    }

    public void updateClassButton(ActionEvent event) throws IOException {
        Klasy toUpdate = tableClass.getSelectionModel().getSelectedItem();
        if (toUpdate == null) {
            chooseClassAlert(true);
        } else {
            Map params = new HashMap<String, String>();
            params.put("class", tableClass.getSelectionModel().getSelectedItem());
            params.put("mode", "update");
            Main.setRoot("administratorActions/class/addOrUpdateClassForm", params,
                    addOrUpdateClassFormWidth, addOrUpdateClassFormHeight);
        }
    }

    public void deleteClassButton(ActionEvent event) throws IOException {
        Klasy toDelete = tableClass.getSelectionModel().getSelectedItem();
        if (toDelete == null) {
           chooseClassAlert(false);
        } else {
            List<Klasy> classStudents = (new SchoolClass()).getStudentsByClass(toDelete);
            if (!(classStudents.isEmpty())) {
               classWithStudentsAlert();
            } else {
                List<Rozklady> classScheduleList = (new ScheduleModel()).findByClass(toDelete);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usuwanie klasy");
                alert.setHeaderText("Czy na pewno chcesz usunąć tą klasę?");
                alert.setContentText(toDelete.getNumer() +
                        "\nSpowoduje to też usunięcie wszystkich rozkładów z tą klasą.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    //delete schedule with class
                    if(!(classScheduleList.isEmpty())) {
                        for (Rozklady r: classScheduleList) {
                            (new ScheduleModel()).delete(r);
                        }
                    }

                    (new SchoolClass()).delete(toDelete);
                    displayTableClass();
                }
            }

        }
    }

    private void classWithStudentsAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Usuwanie klasy");
        alert.setHeaderText("Nie możesz usunąć klasy, która ma przypisanych uczniów!!!");
        alert.setContentText("Przenieś uczniów do innej klasy lub ich usuń.");
        alert.showAndWait();
    }

    private void chooseClassAlert(boolean update)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(update) {
            alert.setTitle("Modyfikacja klasy");
            alert.setContentText("Wybierz klasę do modyfikacji.");
        }
        else {
            alert.setTitle("Usuwanie klasy");
            alert.setContentText("Wybierz klasę do usunięcia.");
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void cancelButton(ActionEvent event) throws IOException {
        Main.setRoot("menu/adminMenuForm",
                WindowSize.adminMenuForm.getWidth(), WindowSize.adminMenuForm.getHeight());
    }
}
