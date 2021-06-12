package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.polsl.Main;
import pl.polsl.Window;
import pl.polsl.entities.Klasy;
import pl.polsl.model.SchoolClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageClassController extends Window  {
    double width = 600; //DO ZMIANY
    double height = 600;
    @FXML
    private AnchorPane window;
    @FXML
    private TableView<Klasy> tableClass;
    @FXML
    private TableColumn<Klasy, String> nameC;


    @FXML
    public void initialize()
    {
        displayTableClass();
    }

    public void displayTableClass()
    {
        tableClass.getItems().clear();
        SchoolClass c= new SchoolClass();
        List l=c.displayClass();
        nameC.setCellValueFactory(new PropertyValueFactory<>("numer"));

        for (Object k: l) {
            tableClass.getItems().add((Klasy) k);
        }
    }

    public void addClassButton(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();

        params.put("mode","add");
        Main.setRoot("administratorActions/class/addOrUpdateClassForm",params,
                addOrUpdateClassFormWidth, addOrUpdateClassFormHeight);
    }
    public void updateClassButton(ActionEvent event) throws IOException
    {
        Klasy toUpdate = tableClass.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modyfikacja klasy");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz klase do modyfikacji.");
            alert.showAndWait();
        }
        else {
            Map params = new HashMap<String, String>();
            params.put("class", tableClass.getSelectionModel().getSelectedItem());
            params.put("mode","update");
            Main.setRoot("administratorActions/class/addOrUpdateClassForm",params,
                    addOrUpdateClassFormWidth, addOrUpdateClassFormHeight);
        }
    }

    public void deleteClassButton(ActionEvent event) throws IOException
    {
        Klasy toDelete = tableClass.getSelectionModel().getSelectedItem();
        if(toDelete==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuwanie klasy");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz klase do usunięcia.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie klasy");
            alert.setHeaderText("Czy na pewno chcesz usunąć tą klase?");
            alert.setContentText(toDelete.getNumer());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                (new SchoolClass()).delete(toDelete);
                displayTableClass();
            }
        }

    }
    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm");
    }


}
