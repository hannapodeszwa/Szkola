package pl.polsl.controller.teacherActions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Udzialwkole;
import pl.polsl.model.ClubModel;
import pl.polsl.model.ClubParticipationModel;
import pl.polsl.model.SchoolClass;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherAssignStudentToCompetitionController implements ParametrizedController {
    Integer clubId;
    Integer loggedTeacherId;

    ObservableList<Klasy> classList;
    ObservableList<Uczniowie> studentsList;
    List<Udzialwkole> studentAdded;

    @FXML
    private ComboBox<String> comboboxClass;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private TableColumn<Uczniowie, String> columnName;
    @FXML
    private TableColumn<Uczniowie, String> columnSurname;
    @FXML
    private Label infoLabel;
    @FXML
    private Button buttonAdd;



    private final ListChangeListener<? extends TablePosition> tableListener = (
            ListChangeListener.Change<? extends TablePosition> change) -> {
        if(table.getSelectionModel().getSelectedIndex()==-1) {
            buttonAdd.setDisable(true);
            infoLabel.setText("");
        }
        else if(studentAdded.stream().anyMatch(c -> c.getIdUcznia().equals(table.getSelectionModel().getSelectedItem().getID()))) {
            buttonAdd.setDisable(true);
            infoLabel.setText("Ten uczeń jest już\nprzypisany do tego koła!");
        }
        else {
            buttonAdd.setDisable(false);
            infoLabel.setText("");
        }
    };

    @Override
    public void receiveArguments(Map<String, Object> params) {
        loggedTeacherId = (Integer) params.get("id");
        clubId = (Integer) params.get("clubId");

        classList = FXCollections.observableList((new SchoolClass()).displayClass());
        if (!classList.isEmpty()) {
            for (Klasy cl : classList) {
                comboboxClass.getItems().add(cl.getNumer());
            }
            comboboxClass.getSelectionModel().select(0);
            Klasy k = classList.get(0);
            setStudents((k));
        }
        infoLabel.setText("");
        buttonAdd.setDisable(true);
        table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) tableListener);

        studentAdded = (new ClubParticipationModel()).findByClub(clubId);

    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherClubForm", params, WindowSize.teacherClubForm);
    }



    public void clickButtonAdd() throws IOException {
        infoLabel.setText("");

        Integer uid = table.getSelectionModel().getSelectedItem().getID();
        Udzialwkole parclub = new Udzialwkole();
        parclub.setIdUcznia(uid);
        parclub.setDataDolaczenia(new Date(System.currentTimeMillis()));
        parclub.setIdKola(clubId);
        (new ClubModel()).persist(parclub);
        infoLabel.setText("Sukces! Uczeń został\nprzypisany do koła!");
    }





    public void changeComboboxClass() {
        studentsList.clear();
        Klasy k = classList.get(comboboxClass.getSelectionModel().getSelectedIndex());
        setStudents(k);
    }

    private void setStudents(Klasy k){
        studentsList = FXCollections.observableList((new SchoolClass()).getStudentsByClass(k));

        columnName.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie()));
        columnSurname.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getNazwisko()));


        table.setItems(studentsList);
    }
}
