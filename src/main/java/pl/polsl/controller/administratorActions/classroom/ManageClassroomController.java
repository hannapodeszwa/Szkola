package pl.polsl.controller.administratorActions.classroom;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

public class ManageClassroomController {
    @FXML
    private TableView<Sale> tableClassrooms;
    @FXML
    private TableColumn<Sale, String> nameC;
    @FXML
    private Label size;
    @FXML
    private Label projector;


    @FXML
    public void initialize()
    {
        displayTableClassrooms();
        changeLabels();
    }

    private void displayTableClassrooms()
    {
        tableClassrooms.getItems().clear();
        Classroom c = new Classroom();
        List l=c.getAllClassrooms();
        nameC.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        for (Object s: l) {
            tableClassrooms.getItems().add((Sale) s);
        }
    }

    private void changeLabels()
    {
        ObservableList<Sale> selectedClassroom = tableClassrooms.getSelectionModel().getSelectedItems();
        selectedClassroom.addListener(new ListChangeListener<Sale>() {
            @Override
            public void onChanged(Change<? extends Sale> change) {
                Integer selectedSize = selectedClassroom.get(0).getLiczbaMiejsc();
                Boolean selectedProjector = selectedClassroom.get(0).getCzyJestRzutnik();

                if (selectedSize != null) {
                    size.setText(selectedSize.toString());
                }
                else
                    size.setText("");
                if(selectedProjector) {
                    projector.setText("Tak");
                }
                else {
                    projector.setText("Nie");
                }
            }
        });
    }

    public void addClassroomButton(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("mode","add");
        Main.setRoot("administratorActions/classroom/addOrUpdateClassroomForm",params,
                WindowSize.addOrUpdateClassroomForm);
    }

    public void updateClassroomButton(ActionEvent event) throws IOException
    {
        Sale toUpdate = tableClassrooms.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            chooseClassroomAlert(true);
        }
        else {
            Map params = new HashMap<String, String>();
            params.put("classroom", toUpdate);
            params.put("mode", "update");
            Main.setRoot("administratorActions/classroom/addOrUpdateClassroomForm",params,
                    WindowSize.addOrUpdateClassroomForm);
        }
    }

    public void deleteClassroomButton(ActionEvent event) throws IOException
    {
        Sale toDelete = tableClassrooms.getSelectionModel().getSelectedItem();
        if(toDelete==null)
        {
            chooseClassroomAlert(false);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie sali");
            alert.setHeaderText("Czy na pewno chcesz usunąć tą salę?");
            alert.setContentText(toDelete.getNazwa());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                deleteSchedule(toDelete);

                (new Classroom()).delete(toDelete);
                displayTableClassrooms();
            }
        }
    }

    private void deleteSchedule(Sale toDelete)
    {
        List<Rozklady> classroomScheduleList = (new ScheduleModel()).findByClassroom(toDelete);
        if(!(classroomScheduleList.isEmpty())) {
            for (Rozklady r: classroomScheduleList) {
                r.setIdSali(null);
                (new ScheduleModel()).update(r);
            }
        }
    }

    private void chooseClassroomAlert(boolean update)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(update)
        {
            alert.setTitle("Modyfikacja sali");
            alert.setContentText("Wybierz salę do modyfikacji.");
        }
        else
        {
            alert.setTitle("Usuwanie sali");
            alert.setContentText("Wybierz salę do usunięcia.");
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm",
                WindowSize.adminMenuForm);
    }
}
