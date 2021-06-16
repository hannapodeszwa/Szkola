package pl.polsl.controller.administratorActions.parent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.WindowSize;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.*;
import pl.polsl.model.ParentModel;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageParentsController implements ManageDataBase {
    @FXML
    private TableView<Rodzice> tableParents;
    @FXML
    private TableColumn<Rodzice, String> nameC;
    @FXML
    private TableColumn<Rodzice, String> surnameC;


    @FXML
    public void initialize()
    {
        displayTableParents();
    }

    public void displayTableParents()
    {
        tableParents.getItems().clear();
        ParentModel p = new ParentModel();
        List l=p.getAllParents();

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        for (Object r: l) {
            tableParents.getItems().add((Rodzice) r);
        }
    }

    public void addTeacherButton(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("mode","add");
        Main.setRoot("administratorActions/parent/addOrUpdateParentForm",params,
                WindowSize.addOrUpdateParentForm.getWidth(),  WindowSize.addOrUpdateParentForm.getHeight());
    }

    public void updateTeacherButton(ActionEvent event) throws IOException
    {
        Rodzice toUpdate = tableParents.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            chooseParentAlert(true);
        }
        else {
            Map params = new HashMap<String, String>();
            params.put("parent", tableParents.getSelectionModel().getSelectedItem());
            params.put("mode", "update");
            Main.setRoot("administratorActions/parent/addOrUpdateParentForm", params,
                    WindowSize.addOrUpdateParentForm.getWidth(),  WindowSize.addOrUpdateParentForm.getHeight());
        }
    }

    public void deleteTeacherButton(ActionEvent event) throws IOException
    {
        Rodzice toDelete = tableParents.getSelectionModel().getSelectedItem();

        if(toDelete==null)
        {
           chooseParentAlert(false);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie rodzica");
            String message = "Czy na pewno chcesz usunąć tego rodzica?";
            alert.setHeaderText(message);

            alert.setContentText(toDelete.getImie() + " " + toDelete.getNazwisko());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                //delete user
                Uzytkownicy userToDelete = (new UserModel()).getUserByIdAndRole(toDelete.getID(), "rodzic");
                if(userToDelete !=null)
                {
                    (new UserModel()).delete(userToDelete);
                }


                (new ParentModel()).delete(toDelete);
                displayTableParents();
            }
        }
    }

    private void chooseParentAlert(boolean update)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(update) {
            alert.setTitle("Modyfikacja rodzica");
            alert.setContentText("Wybierz rodzica do modyfikacji.");
        }
        else {
            alert.setTitle("Usuwanie rodzica");
            alert.setContentText("Wybierz rodzica do usunięcia.");
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
