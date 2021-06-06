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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentsController implements ParametrizedController {
    @FXML
    private TableView<Uczniowie> tableStudents;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;
    @FXML
    private TableColumn<Uczniowie, String> classC;

    private Integer xdd = 5;

    public void testtest(){
        xdd = 10;
    }



    @Override
    public void passArguments(Map<String,String> params) {
        params.put("imie","Adam");
        params.put("nazwisko","Mickiewicz");
        params.put("klasa","3C");
        params.put("tryb","modyfikowanie");
        params.put("tryb","dodawanie");
    }


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
        Map params = new HashMap<String, String>();
        params.put("imie","Adam");
        params.put("nazwisko","Mickiewicz");
        params.put("klasa","3C");
        params.put("tryb","dodawanie");
        Main.setRoot("addOrUpdateStudentForm",params);
    }

    public void updateStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Logowanie ucznia");

        Map params = new HashMap<String, String>();
        params.put("imie","Adam");
        params.put("nazwisko","Mickiewicz");
        params.put("klasa","3C");
        params.put("tryb","modyfikowanie");
        //AddOrUpdateStudentsController
        Main.setRoot("addOrUpdateStudentForm",params);
    }
    //wyswietlanie wszystkich studentow
    public void deleteStudentsButton(ActionEvent event) throws IOException
    {
        System.out.println("Usuwanie ucznia");
        Main.setRoot("deleteUserConfirmation");

    }
}
