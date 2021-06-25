package pl.polsl.controller.teacherActions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.*;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherClubController implements ParametrizedController {

    Integer loggedTeacherId;

    ObservableList<Kolanaukowe> clubsList;
    ObservableList<Uczniowie> studentList;

    @FXML
    private ComboBox<String> comboboxClubs;
    @FXML
    private TableColumn<Uczniowie, String> columnStudent;
    @FXML
    private TableColumn<Uczniowie, String> columnClass;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private Label infoLabel;
    @FXML
    private Button buttonUnassign;
    @FXML
    private Button buttonDelete;

    private void refreshClubList(){
        if(clubsList.isEmpty()) {
            ObservableList<String> emp = FXCollections.observableArrayList();
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
    public void receiveArguments(Map<String, Object> params) {
        loggedTeacherId = (Integer) params.get("id");

        Nauczyciele n = (new Teacher()).getTeacherById(loggedTeacherId);
        clubsList = FXCollections.observableArrayList((new ClubModel()).findByTeacher(n));

        refreshClubList();

        infoLabel.setText("");
    }

    public void clickButtonBack() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        Main.setRoot("menu/teacherMenuForm", params, WindowSize.teacherMenuForm);
    }

    public void clickButtonAdd() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherAddNewClubForm", params);
    }

    public void clickButtonAssign() throws IOException {
        Map<String, Object> params = new HashMap<>();
        int tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
        Kolanaukowe k =  clubsList.get(tmpId);
        Integer clubId = k.getID();
        params.put("id", loggedTeacherId);
        params.put("clubId", clubId);
        Main.setRoot("teacherActions/teacherAssignStudentToClubForm", params);
    }

    public void clickButtonDelete() {
        if(studentList.isEmpty()){
            int tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
            Kolanaukowe k =  clubsList.get(tmpId);
            (new ClubModel()).delete(k);
            refreshClubList();
        } else {
            infoLabel.setText("Usuń wszystkich\nuczestników przed\nusunięciem koła naukowego!");
        }
    }

    public void clickButtonUnassign() {
        int tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
        Kolanaukowe k =  clubsList.get(tmpId);
        List<Udzialwkole> participantsList = (new ClubParticipationModel()).findByClub(k);

        Integer uid = table.getSelectionModel().getSelectedItem().getID();


        participantsList.stream().filter(p -> p.getIdUcznia().equals(uid)).findAny().ifPresent(tmp -> (new ClubParticipationModel()).delete(tmp));
        studentList.clear();
        refreshClubList();
        setParticipants(tmpId);
    }

    public void changeComboboxClubs() {
        studentList.clear();
        setParticipants(comboboxClubs.getSelectionModel().getSelectedIndex());
    }

    private void setParticipants(int index) {

        Integer clubId = clubsList.get(index).getID();

        studentList = FXCollections.observableArrayList((new Student()).getStudentInClub(clubId));

        columnClass.setCellValueFactory(CellData -> new SimpleStringProperty((new SchoolClass()).getClassById(CellData.getValue().getIdKlasy()).getNumer()));
        columnStudent.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie() + " " + CellData.getValue().getNazwisko()));

        table.setItems(studentList);
    }



}
