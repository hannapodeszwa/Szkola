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
import pl.polsl.entities.*;
import pl.polsl.model.*;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherCompetitionController implements ParametrizedController {
    Integer loggedTeacherId;

    ObservableList<Konkursy> competitionsList;
    ObservableList<Uczniowie> studentList;

    @FXML
    private ComboBox comboboxCompetitions;
    @FXML
    private TableColumn<Uczniowie, String> columnStudent;
    @FXML
    private TableColumn<Uczniowie, String> columnClass;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private Label infoLabel;
    @FXML
    private  Label descLabel;
    @FXML
    private Label dateLabel;

    private void refreshCompetitionList(){
        comboboxCompetitions.getItems().clear();
        competitionsList = FXCollections.observableArrayList(((new CompetitionModel()).findAll()));

        if(competitionsList.isEmpty()) {
            ObservableList<String> emp = null;
            emp.add("Brak konkursów");
            comboboxCompetitions.setItems(emp);
            dateLabel.setText("");
            descLabel.setText("");
        } else {

            for(Konkursy knk : competitionsList) {
                comboboxCompetitions.getItems().add(knk.getNazwa());
            }
            comboboxCompetitions.getSelectionModel().select(0);
            refreshDesc(0);
            setParticipants(0);
        }

    }
    @Override
    public void receiveArguments(Map params) {
        loggedTeacherId = (Integer) params.get("id");


        competitionsList = FXCollections.observableArrayList(((new CompetitionModel()).findAll()));

        refreshCompetitionList();

        infoLabel.setText("");


    }

    private void refreshDesc (Integer index){
        dateLabel.setText(competitionsList.get(index).getDataOdbyciaSie().toString());
        descLabel.setText(competitionsList.get(index).getOpis());
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
        Main.setRoot("teacherActions/teacherAddNewCompetitionForm", params);
    }

    public void clickButtonAssign(ActionEvent event) throws IOException {
        Map params = new HashMap<String, String>();
        Integer tmpId = comboboxCompetitions.getSelectionModel().getSelectedIndex();
        Konkursy k =  competitionsList.get(tmpId);
        Integer competitionID = k.getID();
        params.put("id", loggedTeacherId);
        params.put("competitionId", competitionID);
        Main.setRoot("teacherActions/teacherAssignStudentToCompetitionForm", params);
    }

    public void clickButtonDelete() {
        if(studentList.isEmpty()){
            Integer tmpId = comboboxCompetitions.getSelectionModel().getSelectedIndex();
            Konkursy k =  competitionsList.get(tmpId);
            (new CompetitionModel()).delete(k);
            refreshDesc(0);
            competitionsList.clear();
            refreshCompetitionList();
        } else {
            infoLabel.setText("Usuń wszystkich\nuczestników przed\nusunięciem konkursu!");
        }
    }

    public void clickButtonUnassign() {
     /*   Oceny o = table.getSelectionModel().getSelectedItem();
        (new Grade()).delete(o); */
        Integer tmpId = comboboxCompetitions.getSelectionModel().getSelectedIndex();
        Konkursy k =  competitionsList.get(tmpId);
        List<Udzialwkonkursie> participantsList = (new CompetitionModel()).findByCompetition(k);

        Integer uid = table.getSelectionModel().getSelectedItem().getID();


        Udzialwkonkursie tmp = participantsList.stream().filter(p -> p.getIdUcznia().equals(uid)).findAny().orElse(null);
        if(tmp != null) {
            
            (new CompetitionModel()).delete(tmp);
        }
        studentList.clear();
        refreshCompetitionList();
        setParticipants(tmpId);
    }

    public void changeComboboxCompetitions(ActionEvent event) throws IOException {
        studentList.clear();
        Integer index = comboboxCompetitions.getSelectionModel().getSelectedIndex();
        refreshDesc(index);
        setParticipants(index);
    }

    private void setParticipants(int index) {

        Integer competitionId = competitionsList.get(index).getID();

        studentList = FXCollections.observableArrayList((new Student()).getStudentInCompetition(competitionId));

        columnClass.setCellValueFactory(CellData -> new SimpleStringProperty((new SchoolClass()).getClassById(CellData.getValue().getIdKlasy()).getNumer()));
        columnStudent.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie() + " " + CellData.getValue().getNazwisko()));

        table.setItems(studentList);
    }



}
