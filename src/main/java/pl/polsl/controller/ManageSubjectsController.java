package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;
import pl.polsl.model.Subject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageSubjectsController implements ParametrizedController {

    @FXML
    private TableView<Przedmioty> tableSubjects;
    @FXML
    private TableColumn<Przedmioty, String> nameC;

    @Override
    public void passArguments(Map params) {    }

    @FXML
    public void initialize()
    {
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
        Main.setRoot("addOrUpdateSubjectForm",params);
    }

    public void updateSubjectButton(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();

        params.put("subject", tableSubjects.getSelectionModel().getSelectedItem());
        params.put("mode","update");
        Main.setRoot("addOrUpdateSubjectForm",params);
    }

    //DO ZMIANY!!!!!!!!!!!!!!!
    public void deleteSubjectButton(ActionEvent event) throws IOException
    {
        System.out.println("Usuwanie ucznia");
        Main.setRoot("deleteUserConfirmation");

    }
    public void cancelButton(ActionEvent event) throws IOException
    {
        Main.setRoot("menu/adminMenuForm");

    }
}
