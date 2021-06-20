package pl.polsl.controller.teacherActions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Udzialwkole;
import pl.polsl.model.*;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherCompetitionController implements ParametrizedController {
    Integer loggedTeacherId;

    ObservableList<Kolanaukowe> clubsList;
    ObservableList<Uczniowie> studentList;

    @FXML
    private ComboBox comboboxClubs;
    @FXML
    private TableColumn<Uczniowie, String> columnStudent;
    @FXML
    private TableColumn<Uczniowie, String> columnClass;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private Label infoLabel;

    private void refreshClubList(){
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
    @Override
    public void receiveArguments(Map params) {
        loggedTeacherId = (Integer) params.get("id");

        Nauczyciele n = (new Teacher()).getTeacherById(loggedTeacherId);
        clubsList = FXCollections.observableArrayList((new ClubModel()).findByTeacher(n));

        refreshClubList();

        infoLabel.setText("");
    }

    public void clickButtonBack(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("id", loggedTeacherId);
        Main.setRoot("menu/teacherMenuForm", params, WindowSize.teacherMenuForm);
    }

    public void clickButtonAdd(ActionEvent event) throws IOException {
        Map params = new HashMap<String, String>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherAddNewClubForm", params);
    }

    public void clickButtonAssign(ActionEvent event) throws IOException {
        Map params = new HashMap<String, String>();
        Integer tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
        Kolanaukowe k =  clubsList.get(tmpId);
        Integer clubId = k.getID();
        params.put("id", loggedTeacherId);
        params.put("clubId", clubId);
        Main.setRoot("teacherActions/teacherAssignStudentToClubForm", params);
    }

    public void clickButtonDelete() {
        if(studentList.isEmpty()){
            Integer tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
            Kolanaukowe k =  clubsList.get(tmpId);
            (new ClubModel()).delete(k);
            refreshClubList();
        } else {
            infoLabel.setText("Usuń wszystkich\nuczestników przed\nusunięciem koła naukowego!");
        }
    }

    public void clickButtonUnassign() {
     /*   Oceny o = table.getSelectionModel().getSelectedItem();
        (new Grade()).delete(o); */
        Integer tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
        Kolanaukowe k =  clubsList.get(tmpId);
        List<Udzialwkole> participantsList = (new ClubParticipationModel()).findByClub(k);

        Integer uid = table.getSelectionModel().getSelectedItem().getID();


        Udzialwkole tmp = participantsList.stream().filter(p -> p.getIdUcznia().equals(uid)).findAny().orElse(null);
        if(tmp != null) {
            
            (new ClubParticipationModel()).delete(tmp);
        }
        studentList.clear();
        refreshClubList();
        setParticipants(tmpId);
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
        columnStudent.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie() + " " + CellData.getValue().getNazwisko()));

        table.setItems(studentList);
    }



}
