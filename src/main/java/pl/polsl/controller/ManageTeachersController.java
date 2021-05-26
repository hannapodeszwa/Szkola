package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.polsl.Main;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.List;

public class ManageTeachersController {
    private final Stage thisStage;
    @FXML
    private TableView<Nauczyciele> tableTeachers;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;

    public ManageTeachersController() {
        thisStage = new Stage();
    }


    @FXML
    public void initialize()
    {
        Teacher t = new Teacher();
        List l=t.displayTeachers();


        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        for (Object n: l) {
            tableTeachers.getItems().add((Nauczyciele) n);
        }
    }

    public void addTeacherButton(ActionEvent event) throws IOException
    {
       /* try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addOrUpdateTeacherForm.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("testowe okno");

        } catch (IOException e) {
            e.printStackTrace();
        }*/
       Main.setRoot("addOrUpdateTeacherForm");
    }

    public void updateTeacherButton(ActionEvent event) throws IOException
    {
       Main.setRoot("addOrUpdateTeacherForm");
    }

    public void deleteTeacherButton(ActionEvent event) throws IOException
    {
        System.out.println("Usuwanie nauczyciela");
        Main.setRoot("deleteUserConfirmationForm");
    }
}
