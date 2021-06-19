package pl.polsl.controller.teacherActions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Udzialwkole;
import pl.polsl.model.ClubModel;
import pl.polsl.model.SchoolClass;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TeacherAssignStudentToClubController implements ParametrizedController {
    Integer clubId;
    Integer loggedTeacherId;

    ObservableList<Klasy> classList;
    ObservableList<Uczniowie> studentsList;

    @FXML
    private ComboBox comboboxClass;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private TableColumn<Uczniowie, String> columnName;
    @FXML
    private TableColumn<Uczniowie, String> columnSurname;



    @Override
    public void receiveArguments(Map params) {
        loggedTeacherId = (Integer) params.get("id");
        clubId = (Integer) params.get("clubId");

        classList = FXCollections.observableList((new SchoolClass()).displayClass());
        if(!classList.isEmpty()) {
            for (Klasy cl : classList) {
                comboboxClass.getItems().add(cl.getNumer());
            }
            comboboxClass.getSelectionModel().select(0);
            Klasy k = classList.get(0);
            setStudents((k));
        }

    }

    public void clickButtonBack(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherClubForm", params);
    }

    public void clickButtonAdd(ActionEvent event) throws IOException {
        Integer uid = table.getSelectionModel().getSelectedItem().getID();
        Udzialwkole parclub = new Udzialwkole();
        parclub.setIdUcznia(uid);
        parclub.setDataDolaczenia(new Date(System.currentTimeMillis()));
        parclub.setIdKola(clubId);
        (new ClubModel()).persist(parclub);

    }

    public void changeComboboxClass(ActionEvent event) throws  IOException {
        studentsList.clear();
        Integer classID = comboboxClass.getSelectionModel().getSelectedIndex();
        Klasy k = classList.get(classID);
        setStudents(k);
    }

    private void setStudents(Klasy k){
        studentsList = FXCollections.observableList((new SchoolClass()).getStudentsByClass(k));

        columnName.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie()));
        columnSurname.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getNazwisko()));


        table.setItems(studentsList);
    }
}
