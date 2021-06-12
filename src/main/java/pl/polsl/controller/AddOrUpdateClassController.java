package pl.polsl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.polsl.Main;
import pl.polsl.Window;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.SchoolClass;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddOrUpdateClassController extends Window implements ParametrizedController{
    double width = 450;
    double height = 300;

    @FXML
    private AnchorPane window;
    public TextField name;
    public Label title;
    public ComboBox<String> tutor;
    public ComboBox<String> leader;

    private Klasy toUpdate;
    public enum md {Add, Update}
    private md mode = md.Update;

    List <Nauczyciele> teachers;
    List <Uczniowie> students;

    @FXML
    public void initialize()
    {
        displayStudents();
        displayTeachers();
    }

    private void displayStudents()
    {
        leader.getItems().add(null);
        Student student = new Student();
        students = student.displayStudents();
        for (Uczniowie u : students) {
            leader.getItems().add(u.getImie() + " " + u.getNazwisko());
        }
    }
    private void displayTeachers()
    {
        tutor.getItems().add(null);
        Teacher teacher = new Teacher();
        teachers = teacher.displayTeachers();
        for (Nauczyciele n : teachers) {
            tutor.getItems().add(n.getImie() + " " + n.getNazwisko());
        }
    }

    @Override
    public void receiveArguments(Map params) {
        if (params.get("mode") == "add")
        {
            mode = md.Add;
            title.setText("Dodawanie klasy:");
        }
        else {
            mode = md.Update;
            toUpdate = (Klasy) params.get("class");
            title.setText("Modyfikacja klasy:");
            if(toUpdate.getIdWychowawcy() != null) {
                Nauczyciele selectedTutor = (new Teacher()).getTeacherById(toUpdate.getIdWychowawcy());
                tutor.getSelectionModel().select(selectedTutor.getImie() + " " + selectedTutor.getNazwisko());
            }
            if(toUpdate.getIdPrzewodniczacego() != null) {
                Uczniowie selectedLeader = (new Student()).getStudentById(toUpdate.getIdPrzewodniczacego());
                leader.getSelectionModel().select(selectedLeader.getImie() + " " + selectedLeader.getNazwisko());
            }
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getNumer());
        }
    }

    public void confirmChangesButton(ActionEvent event) throws IOException
    {
        if (mode == md.Add) {
            Klasy k = new Klasy();
            setNewValues(k);
            (new SchoolClass()).persist(k);
        } else if (mode == md.Update) {
            setNewValues(toUpdate);
            (new SchoolClass()).update(toUpdate);
        }
        Main.setRoot("administratorActions/class/manageClassForm", manageClassFormWidth, manageClassFormHeight);
    }
    private void setNewValues(Klasy k)
    {
        k.setNumer(name.getText());
        Nauczyciele selectedTutor = teachers.get(tutor.getSelectionModel().getSelectedIndex()-1);
        k.setIdWychowawcy(selectedTutor.getID());

        Uczniowie selectedLeader = students.get(leader.getSelectionModel().getSelectedIndex()-1);
        k.setIdPrzewodniczacego(selectedLeader.getID());
    }

    public void discardChangesButton(ActionEvent event) throws IOException {
        Main.setRoot("administratorActions/class/manageClassForm", manageClassFormWidth, manageClassFormHeight);
    }



}
