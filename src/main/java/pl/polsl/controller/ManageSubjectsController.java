package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.Przedmioty;
import pl.polsl.model.Subject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageSubjectsController {

    @FXML
    private TableView<Przedmioty> tableSubjects;
    @FXML
    private TableColumn<Przedmioty, String> nameC;


    @FXML
    public void initialize()
    {
       displayTableSubjects();
    }

    public void displayTableSubjects()
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
        Main.setRoot("addOrUpdateSubjectForm",params, 400, 280);
    }

    public void updateSubjectButton(ActionEvent event) throws IOException
    {
        Przedmioty toUpdate = tableSubjects.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modyfikacja przedmiotu");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz przedmiot do modyfikacji.");
            alert.showAndWait();
        }
        else {
            Map params = new HashMap<String, String>();
            params.put("subject", toUpdate);
            params.put("mode", "update");
            Main.setRoot("addOrUpdateSubjectForm",params, 400, 280);
        }
    }

    public void deleteSubjectButton(ActionEvent event) throws IOException
    {
        Przedmioty toDelete = tableSubjects.getSelectionModel().getSelectedItem();
        if(toDelete==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuwanie przedmiotu");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz przedmiot do usunięcia.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie przedmiotu");
            alert.setHeaderText("Czy na pewno chcesz usunąć ten przedmiot?");
            alert.setContentText(toDelete.getNazwa());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                (new Subject()).delete(toDelete);
                displayTableSubjects();
            }
        }
    }
    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm");
    }
}
