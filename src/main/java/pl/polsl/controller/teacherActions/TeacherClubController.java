package pl.polsl.controller.teacherActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uwagi;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherClubController implements ParametrizedController {
    Integer loggedTeacherId;

    ObservableList<Kolanaukowe> clubsList;
    ObservableList<Uczniowie> studentList;

    @FXML
    private ComboBox comboboxClubs;
    @FXML
    private TableColumn<Uczniowie, String> columnName;
    @FXML
    private TableColumn<Uczniowie, String> columnClass;
    @FXML
    private TableColumn<Uczniowie, String> columnSurname;
    @FXML
    private TableView<Uczniowie> table;



    @Override
    public void receiveArguments(Map params) {
        loggedTeacherId = (Integer) params.get("id");

        Nauczyciele n = (new Teacher()).getTeacherById(loggedTeacherId);
        clubsList = FXCollections.observableArrayList((new ClubModel()).findByTeacher(n));

        if(clubsList.isEmpty()) {
            ObservableList<String> emp = null;
            emp.add("Brak kół zainteresowań");
            comboboxClubs.setItems(emp);
        } else {
            for(Kolanaukowe knl : clubsList) {
                comboboxClubs.getItems().add(knl.getOpis());
            }
            comboboxClubs.getSelectionModel().select(0);
            setParticipants(0);
        }
    }

    public void clickButtonBack(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("id", loggedTeacherId);
        Main.setRoot("menu/teacherMenuForm", params);
    }

    public void clickButtonAdd(ActionEvent event) throws IOException {
        Map params = new HashMap<String, String>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherAddNewClubForm", params);
    }

    public void clickButtonAssign(ActionEvent event) throws IOException {
        Map params = new HashMap<String, String>();
        // TODO: get selected club id
        Integer clubId = 1;
        params.put("id", loggedTeacherId);
        params.put("clubId", clubId);
        Main.setRoot("teacherActions/teacherAssignStudentToClubForm", params);
    }

    public void changeComboboxClubs(ActionEvent event) throws IOException {
        studentList.clear();
        setParticipants(comboboxClubs.getSelectionModel().getSelectedIndex());
    }

    private void setParticipants(int index) {

        Integer clubId = clubsList.get(index).getID();

        studentList = FXCollections.observableArrayList((new Student()).getStudentInClub(clubId));

   //     System.out.println(clubId + " " + studentList.get(0).getImie());

        columnClass.setCellValueFactory(CellData -> new SimpleStringProperty((new SchoolClass()).getClassById(CellData.getValue().getIdKlasy()).getNumer()));
        columnName.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie()));
        columnSurname.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getNazwisko()));

        table.setItems(studentList);
    }



}
