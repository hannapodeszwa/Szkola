package pl.polsl.controller.studentActions;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Udzialwkole;
import pl.polsl.model.*;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentClubsForm implements ParametrizedController {
    public Label clubDescription;
    public Label clubTeacher;
    public ComboBox<Kolanaukowe> comboBoxClubs;
    public TableView<Kolanaukowe> clubsTable;
    public TableColumn<Kolanaukowe, String> clubsColumn;
    public Button buttonApply;

    private Integer id;
    private StudentMenuController.md mode;

    @Override
    public void receiveArguments(Map params) {
        mode = StudentMenuController.md.valueOf((String) params.get("mode"));
        id = (Integer) params.get("id");
        List l = (new ClubModel()).findByStudentId(id);
        clubsTable.getItems().addAll((new ClubModel()).findByStudentId(id));
    }

    public void clickButtonBack(ActionEvent actionEvent) throws IOException {
        Map params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm", params);
    }

    public void clickButtonApply() {
        Integer clubId = comboBoxClubs.getSelectionModel().getSelectedItem().getID();

        //Student is already participating in selected club
        if (clubsTable.getItems().stream().anyMatch(c -> c.getID() == clubId)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Jesteś już zapisany do tego koła naukowego.", ButtonType.OK);
            alert.setHeaderText("");
            alert.setTitle("Zapis do koła");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chce zapisać się do koła naukowego \"" + comboBoxClubs.getSelectionModel().getSelectedItem().getOpis() + "\"?", ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Zapis do koła");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK){
                Udzialwkole u = new Udzialwkole();
                u.setDataDolaczenia(new Date(System.currentTimeMillis()));
                u.setIdKola(clubId);
                u.setIdUcznia(id);
                (new ClubModel()).persist(u);
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Zapisałeś się do koła.", ButtonType.OK);
                done.setHeaderText("");
                done.setTitle("Zapis do koła");
                done.showAndWait();
                refreshTable();
            }
        }
    }

    private void refreshTable() {
        Integer index = clubsTable.getSelectionModel().getSelectedIndex();
        clubsTable.getItems().clear();
        clubsTable.getItems().addAll((new ClubModel()).findByStudentId(id));
        clubsTable.getSelectionModel().select(index);
        clubsTable.getFocusModel().focus(index);
    }

    public void initialize() {
        buttonApply.setDisable(true);
        clubsColumn.setCellValueFactory(new PropertyValueFactory<>("opis"));
        comboBoxClubs.getItems().addAll((new ClubModel()).findAll());
        comboBoxClubs.getSelectionModel().selectedItemProperty().addListener ((observable, oldSelection, newSelection) -> {
            Nauczyciele teacher = (new Teacher()).getTeacherById(newSelection.getIdNauczyciela());
            clubTeacher.setText(teacher.getImie() + " " + teacher.getNazwisko() + "\n" + teacher.getEmail());
            clubDescription.setText(newSelection.getOpis());
            buttonApply.setDisable(false);
        });
    }
}
