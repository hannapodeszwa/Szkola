package pl.polsl.controller.studentActions;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.*;
import pl.polsl.model.ClubModel;
import pl.polsl.model.CompetitionModel;
import pl.polsl.model.Teacher;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentCompetitionController implements ParametrizedController {
    public Label competitionDescription;
    public Label competitionName;
    public ComboBox<Konkursy> comboBoxCompetitions;
    public ComboBox<Nauczyciele> comboBoxTeachers;
    public TableView<Konkursy> competitionTable;
    public TableColumn<Konkursy, String> competitionColumn;
    public TableColumn<Konkursy, String> competitionNameColumn;
    public Button buttonApply;

    private Integer id;
    private StudentMenuController.md mode;


    public Nauczyciele decorate(Nauczyciele tea){
        Nauczyciele decoratedTea = new Nauczyciele(){
            @Override
            public String toString() {
                return this.getImie() + " " + this.getNazwisko();
            }
        };
        decoratedTea.setImie(tea.getImie());
        decoratedTea.setDrugieImie(tea.getDrugieImie());
        decoratedTea.setNazwisko(tea.getNazwisko());
        decoratedTea.setEmail(tea.getEmail());
        decoratedTea.setNrKontaktowy(tea.getNrKontaktowy());
        decoratedTea.setID(tea.getID());
        return decoratedTea;
    }

    public Konkursy decorate(Konkursy comp){
        Konkursy decoratedComp = new Konkursy(){
            @Override
            public String toString() {
                return this.getNazwa();
            }
        };
        decoratedComp.setID(comp.getID());
        decoratedComp.setNazwa(comp.getNazwa());
        decoratedComp.setOpis(comp.getOpis());
        decoratedComp.setDataOdbyciaSie(comp.getDataOdbyciaSie());
        return decoratedComp;
    }

    @Override
    public void receiveArguments(Map params) {
        mode = StudentMenuController.md.valueOf((String) params.get("mode"));
        id = (Integer) params.get("id");
        List l = (new ClubModel()).findByStudentId(id);
        competitionTable.getItems().addAll((new CompetitionModel()).findByStudentId(id));
    }

    public void initialize() {
        buttonApply.setDisable(true);
        competitionNameColumn.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        competitionColumn.setCellValueFactory(new PropertyValueFactory<>("opis"));
        for (Konkursy comp : (new CompetitionModel()).findAll())
            comboBoxCompetitions.getItems().add(decorate(comp));
        for (Nauczyciele tea : (new Teacher()).getAllTeachers())
            comboBoxTeachers.getItems().add(decorate(tea));
        comboBoxCompetitions.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            competitionName.setText(newSelection.getNazwa());
            competitionDescription.setText((newSelection.getOpis() != null ? newSelection.getOpis() : "") + (newSelection.getDataOdbyciaSie() != null ? "\nTermin konkursu: " + newSelection.getDataOdbyciaSie().toLocalDate().toString() : ""));
            if (comboBoxTeachers.getSelectionModel().getSelectedItem() != null)
                buttonApply.setDisable(false);
        });
        comboBoxTeachers.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (comboBoxCompetitions.getSelectionModel().getSelectedItem() != null)
                buttonApply.setDisable(false);
        });
    }

    public void clickButtonBack(ActionEvent actionEvent) throws IOException {
        Map params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        if (mode == StudentMenuController.md.Student)
            Main.setRoot("menu/studentMenuForm", params, WindowSize.studenMenuForm);
        else
            Main.setRoot("menu/studentMenuForm", params,WindowSize.parentMenuForm);
    }


    public void clickButtonLeave() {
        Konkursy competitions = competitionTable.getSelectionModel().getSelectedItem();
        if (competitions != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zrezygnować z udziału w konkursie \"" + competitions.getOpis() + "\"?", ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Rezygnacja z konkursu");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                Udzialwkonkursie u = (new CompetitionModel()).findByBoth(id,competitionTable.getSelectionModel().getSelectedItem().getID());
                (new ClubModel()).delete(u);
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Zrezygnowano z udziału z konkursu.", ButtonType.OK);
                done.setHeaderText("");
                done.setTitle("Rezygnacja z konkursu");
                done.showAndWait();
                refreshTable();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nie wybrano żadnego konkursu z tabeli.", ButtonType.OK);
            alert.setHeaderText("");
            alert.setTitle("Rezygnacja z konkursu");
            alert.showAndWait();
        }
    }

    public void clickButtonApply() {
        Integer competitionId = comboBoxCompetitions.getSelectionModel().getSelectedItem().getID();


        if (competitionTable.getItems().stream().anyMatch(c -> c.getID() == competitionId)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Już bierzesz udział w tym konkursie.", ButtonType.OK);
            alert.setHeaderText("");
            alert.setTitle("Zapis na konkurs");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zapisać się na konkurs \"" + comboBoxCompetitions.getSelectionModel().getSelectedItem().getOpis() + "\"?", ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Zapis na konkurs");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK){
                Udzialwkonkursie u = new Udzialwkonkursie();
                u.setIdKonkursu(comboBoxCompetitions.getSelectionModel().getSelectedItem().getID());
                u.setIdNauczyciela(comboBoxTeachers.getSelectionModel().getSelectedItem().getID());
                u.setIdUcznia(id);
                (new CompetitionModel()).persist(u);
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Zapisano się na konkurs.", ButtonType.OK);
                done.setHeaderText("");
                done.setTitle("Zapis na konkurs");
                done.showAndWait();
                refreshTable();
            }
        }
    }

    private void refreshTable() {
        Integer index = competitionTable.getSelectionModel().getSelectedIndex();
        competitionTable.getItems().clear();
        competitionTable.getItems().addAll((new CompetitionModel()).findByStudentId(id));
        competitionTable.getSelectionModel().select(index);
        competitionTable.getFocusModel().focus(index);
    }
}
