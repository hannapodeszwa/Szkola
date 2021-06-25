package pl.polsl.controller.teacherActions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

public class TeacherCompetitionController implements ParametrizedController {

    @FXML
    private TextField achievementField;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUnassign;
    @FXML
    private Button buttonAssign;
    @FXML
    private ComboBox<String> comboboxCompetitions;
    @FXML
    private TableColumn<Uczniowie, String> columnStudent;
    @FXML
    private TableColumn<Uczniowie, String> columnClass;
    @FXML
    private TableColumn<Uczniowie, String> columnAchievement;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private Label infoLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label dateLabel;

    private Integer loggedTeacherId;
    private ObservableList<Konkursy> competitionsList;
    private ObservableList<Uczniowie> studentList;
    private ObservableList<Udzialwkonkursie> participationList;

    private void refreshCompetitionList(Integer numberCompetition){
        comboboxCompetitions.getItems().clear();
        competitionsList = FXCollections.observableArrayList(((new CompetitionModel()).findAll()));

        if(competitionsList.isEmpty()) {
            dateLabel.setText("");
            descLabel.setText("");
            buttonAssign.setDisable(true);
            buttonDelete.setDisable(true);
        } else {

            for(Konkursy knk : competitionsList) {
                comboboxCompetitions.getItems().add(knk.getNazwa());
            }
            table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) deleteGrade);
            comboboxCompetitions.getSelectionModel().select(numberCompetition);
            refreshDesc(numberCompetition);
            setParticipants(numberCompetition);
        }
        buttonAssign.setDisable(false);
        buttonDelete.setDisable(false);

    }
    @Override
    public void receiveArguments(Map<String, Object> params) {
        loggedTeacherId = (Integer) params.get("id");
        Integer numberCompetition = (Integer) params.get("numberCompetition");
        if(numberCompetition == null)
            numberCompetition = 0;
        competitionsList = FXCollections.observableArrayList(((new CompetitionModel()).findAll()));
        refreshCompetitionList(numberCompetition);
        infoLabel.setText("");

    }

    private final ListChangeListener<? extends TablePosition> deleteGrade = (
            javafx.collections.ListChangeListener.Change<? extends TablePosition> change) -> {
        Uczniowie tym = table.getSelectionModel().getSelectedItem();
        if (tym == null){
            buttonUnassign.setDisable(true);
            achievementField.setText("");
        }
        else{
            buttonUnassign.setDisable(false);
            for(Udzialwkonkursie act : participationList) {
                if (act.getIdUcznia().equals(tym.getID())) {
                    achievementField.setText(act.getOsiagniecie());
                    break;
                }
            }
        }
    };

    private void refreshDesc (Integer index){
        dateLabel.setText(competitionsList.get(index).getDataOdbyciaSie().toString());
        descLabel.setText(competitionsList.get(index).getOpis());
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
        Main.setRoot("teacherActions/teacherAddNewCompetitionForm", params, WindowSize.teacherAddNewCompetitionForm);
    }

    public void clickButtonAssign() throws IOException {
        Map<String, Object> params = new HashMap<>();
        int tmpId = comboboxCompetitions.getSelectionModel().getSelectedIndex();
        Konkursy k =  competitionsList.get(tmpId);
        Integer competitionID = k.getID();
        params.put("id", loggedTeacherId);
        params.put("competitionId", competitionID);
        params.put("numberCompetition",comboboxCompetitions.getSelectionModel().getSelectedIndex());
        Main.setRoot("teacherActions/teacherAssignStudentToCompetitionForm", params, WindowSize.TeacherAssignStudentToCompetitionForm);
    }

    public void clickButtonAchievement() throws IOException {
        Integer studentID = studentList.get(table.getSelectionModel().getSelectedIndex()).getID();
        Integer competitionIndex = comboboxCompetitions.getSelectionModel().getSelectedIndex();
        Integer competitionID = competitionsList.get(competitionIndex).getID();
        Udzialwkonkursie p = new CompetitionModel().findByBoth(studentID, competitionID);
        p.setOsiagniecie(achievementField.getText());
        (new CompetitionModel()).update(p);
      //  setParticipants(competitionIndex);
        changeComboboxCompetitions();
     }

    public void clickButtonDelete() {
        if(studentList.isEmpty()){
            int tmpId = comboboxCompetitions.getSelectionModel().getSelectedIndex();
            Konkursy k =  competitionsList.get(tmpId);
            (new CompetitionModel()).delete(k);
            refreshDesc(0);
            competitionsList.clear();
            refreshCompetitionList(0);
        } else {
            infoLabel.setText("Usuń wszystkich uczestników przed usunięciem konkursu!");
        }
    }

    public void clickButtonUnassign() {

        int tmpId = comboboxCompetitions.getSelectionModel().getSelectedIndex();
        Konkursy k =  competitionsList.get(tmpId);
        List<Udzialwkonkursie> participantsList = (new CompetitionModel()).findByCompetition(k);

        Integer uid = table.getSelectionModel().getSelectedItem().getID();


        participantsList.stream().filter(p -> p.getIdUcznia().equals(uid)).findAny().ifPresent(tmp -> (new CompetitionModel()).delete(tmp));
        studentList.clear();
        refreshCompetitionList(0);
        setParticipants(tmpId);
    }

    public void changeComboboxCompetitions() {
        if(comboboxCompetitions.getSelectionModel().getSelectedIndex()!=-1) {
            studentList.clear();
            int index = comboboxCompetitions.getSelectionModel().getSelectedIndex();
            refreshDesc(index);
            setParticipants(index);
        }
    }

    private void setParticipants(int index) {

        Integer competitionId = competitionsList.get(index).getID();

        studentList = FXCollections.observableArrayList((new Student()).getStudentInCompetition(competitionId));
        participationList = FXCollections.observableArrayList((new CompetitionModel().findByCompetitionId(competitionId)));

        columnClass.setCellValueFactory(CellData -> new SimpleStringProperty((new SchoolClass()).getClassById(CellData.getValue().getIdKlasy()).getNumer()));
        columnStudent.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie() + " " + CellData.getValue().getNazwisko()));
        columnAchievement.setCellValueFactory(CellData -> new SimpleStringProperty((new CompetitionModel()).findByBoth(CellData.getValue().getID(), competitionId).getOsiagniecie()));


        table.setItems(studentList);
    }



}
