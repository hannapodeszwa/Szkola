package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.*;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.util.List;

public class ManageStudentsController {
    @FXML
    private TableView<Uczniowie> tableStudents;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;
    @FXML
    private TableColumn<Uczniowie, String> classC;

    @FXML
    public void initialize()
    {
        Student s= new Student();
        List l=s.displayStudents();


       nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        classC.setCellValueFactory(new PropertyValueFactory<>("klass"));

        for (Object u: l) {
            tableStudents.getItems().add((Uczniowie)u);
        }
    }




    public void addStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Logowanie ucznia");
        Main.setRoot("addOrUpdateStudentForm");
    }

    public void updateStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Logowanie ucznia");
        Main.setRoot("addOrUpdateStudentForm");
    }
    //wyswietlanie wszystkich studentow
    public void deleteStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Usuwanie ucznia");
        Main.setRoot("deleteUserConfirmationForm");

    }


}
