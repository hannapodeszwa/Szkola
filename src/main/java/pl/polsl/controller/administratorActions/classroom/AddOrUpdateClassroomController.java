package pl.polsl.controller.administratorActions.classroom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.administratorActions.subject.AddOrUpdateSubjectController;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Sale;
import pl.polsl.model.Classroom;
import pl.polsl.model.Subject;

import java.io.IOException;
import java.util.Map;

public class AddOrUpdateClassroomController implements ParametrizedController {
    public TextField name;
    public TextField size;
    public RadioButton yes;
    public RadioButton no;
    public ToggleGroup group;
    public Label title;
    public Button confirm;

    private Sale toUpdate;
    public enum md {Add, Update}
    private md mode = md.Update;
    private boolean selectedYes;

    @FXML
    public void initialize()
    {
        ToggleGroup group = new ToggleGroup();
        yes.setToggleGroup(group);
        no.setToggleGroup(group);
        name.textProperty().addListener(TextListener);
        disableButton();

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    if(group.getSelectedToggle().equals(yes))
                    {
                        no.setSelected(false);
                        selectedYes = true;
                    }
                    else
                    {
                        yes.setSelected(false);
                        selectedYes = false;
                    }
                }
            }
        });

        size.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    size.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void disableButton()
    {
        if (name.getText().isEmpty())
            confirm.setDisable(true);
        else
            confirm.setDisable(false);
    }
    private ChangeListener TextListener = (observable, oldValue, newValue) -> {
        disableButton();
    };

    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add") {
            mode = md.Add;
            title.setText("Dodawanie sali:");
        }
        else {
            mode = md.Update;
            toUpdate = (Sale) params.get("classroom");
            title.setText("Modyfikacja sali:");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getNazwa());
            size.setText(toUpdate.getLiczbaMiejsc().toString());
            selectedYes=toUpdate.getCzyJestRzutnik();
            if(toUpdate.getCzyJestRzutnik())
                yes.setSelected(true);
        }
    }

    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        if (mode == md.Add) {
            Sale s = new Sale();
            setNewValues(s);

            (new Classroom()).persist(s);
        } else if (mode == md.Update) {
            setNewValues(toUpdate);
            (new Classroom()).update(toUpdate);
        }
        Main.setRoot("administratorActions/classroom/manageClassroomsForm",
                WindowSize.manageClassroomsForm);
    }

    private void setNewValues(Sale s)
    {
        s.setNazwa( (name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));
        s.setLiczbaMiejsc(null);
        if(!(size.getText().isEmpty()))
        s.setLiczbaMiejsc(Integer.parseInt(size.getText()));
        s.setCzyJestRzutnik(selectedYes);
    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        Main.setRoot("administratorActions/classroom/manageClassroomsForm",
                WindowSize.manageClassroomsForm);
    }
}
