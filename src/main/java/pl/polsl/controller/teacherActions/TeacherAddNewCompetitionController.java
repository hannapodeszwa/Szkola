package pl.polsl.controller.teacherActions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.entities.Konkursy;
import pl.polsl.model.ClubModel;
import pl.polsl.model.CompetitionModel;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherAddNewCompetitionController implements ParametrizedController {
    Integer loggedTeacherId;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label infoLabel;

    @FXML
    private DatePicker datePicker;

    @Override
    public void receiveArguments(Map<String, Object> params) {
        loggedTeacherId = (Integer) params.get("id");
        infoLabel.setText("");

    }

    public void clickButtonBack() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherCompetitionForm", params, WindowSize.teacherClubForm);
    }

    public void clickButtonAdd() throws IOException {
        infoLabel.setText("");
        String name = nameTextField.getText();
        String description = descriptionTextField.getText();

        if(name.isEmpty() || description.isEmpty()){
            infoLabel.setText("Nazwa i opis konkursu nie mogą być puste!");
        } else {
            ObservableList<Konkursy> competitionsList = FXCollections.observableList((new CompetitionModel()).findAll());
            if(competitionsList.stream().anyMatch(c -> c.getOpis().equals(name))){
                infoLabel.setText("Błąd! Konkurs " + name + " już istnieje!");
            } else {
                Konkursy kn = new Konkursy();
                kn.setOpis(description);
                kn.setNazwa(name);
                kn.setDataOdbyciaSie(Date.valueOf(datePicker.getValue()));

                (new CompetitionModel()).persist(kn);
                infoLabel.setText("Konkurs " + name + " utworzono pomyślnie");
            }
        }
    }

    private boolean checkIfExists(String name){
        Boolean ifExistsAlready = false;
        List<Kolanaukowe> clubList = (new ClubModel()).findAll();
        for(Kolanaukowe k : clubList){
            if(k.getOpis() == name){
                ifExistsAlready = true;
            }
        }
        return ifExistsAlready;
    }

}
