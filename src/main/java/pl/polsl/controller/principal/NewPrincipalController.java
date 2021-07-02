package pl.polsl.controller.principal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.UserModel;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import java.io.IOException;


public class NewPrincipalController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;

    private Uzytkownicy principal;

    @FXML
    public void initialize()
    {
         principal = (new UserModel()).getPrincipal();
         login.setText(principal.getLogin());
         password.setText(principal.getHaslo());
    }

    public void pressChange()
    {
        Uzytkownicy u = new Uzytkownicy();

        setNewValues(u,0);
        deleteUser();
        (new UserModel()).persist(u);
        alert();
    }

    private void setNewValues(Uzytkownicy u, Integer id)
    {
        u.setLogin(login.getText());
        u.setHaslo(password.getText());
        u.setDostep(Roles.PRINCIPAL);
        u.setID(id);
    }
    private void alert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Zmiana dyrektora");
        alert.setContentText("Dyrektor został zmieniony.");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    private void deleteUser()
    {
        if(principal !=null)
        {
            (new UserModel()).delete(principal);
        }
    }

    public void cancelButton() throws IOException
    {
            Main.setRoot("menu/adminMenuForm", WindowSize.adminMenuForm);
    }
}
