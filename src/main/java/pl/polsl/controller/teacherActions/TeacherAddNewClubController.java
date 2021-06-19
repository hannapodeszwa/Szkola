package pl.polsl.controller.teacherActions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.model.ClubModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherAddNewClubController implements ParametrizedController {
    Integer loggedTeacherId;

    @FXML
    private TextField nameTextField;

    @Override
    public void receiveArguments(Map params) {
        loggedTeacherId = (Integer) params.get("id");
    }

    public void clickButtonBack(ActionEvent event) throws IOException
    {
        Map params = new HashMap<String, String>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherClubForm", params);
    }

    public void clickButtonAdd(ActionEvent event) throws IOException {
        if(nameTextField.getText().isEmpty()){
            // TODO: add label, display error text
        } else {
            // TODO: add duplicate name check, display error text
            Kolanaukowe kl = new Kolanaukowe();
            kl.setOpis(nameTextField.getText());
            kl.setIdNauczyciela(loggedTeacherId);
            (new ClubModel()).persist(kl);
            // TODO: add label, display success text if OK
        }
    }

}
