package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Klasy_;
import pl.polsl.entities.Przedmioty;
import pl.polsl.model.Class1;
import pl.polsl.model.Subject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageClassController {
    @FXML
    private TableView<Klasy> tableClass;
    @FXML
    private TableColumn<Klasy, String> nameC;

    @FXML
    public void initialize()
    {
        Class1 c= new Class1();
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
        Main.setRoot("addOrUpdateClassForm",params);
    }
    public void updateClassButton(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();

        params.put("class", tableClass.getSelectionModel().getSelectedItem());
        params.put("mode","update");
        Main.setRoot("addOrUpdateClassForm",params);
    }
    //DO ZMIANY!!!!!!!!!!!!!!!
    public void deleteClassButton(ActionEvent event) throws IOException
    {
        Main.setRoot("deleteUserConfirmation");

    }
    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm");

    }
}
