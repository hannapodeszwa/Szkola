package pl.polsl.controller.studentActions;


import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class studentScheduleController implements ParametrizedController {

    public Button buttonBack;
    public TableView<ScheduleTable> table;
    public TableColumn<ScheduleTable, Integer> columnNum;
    public TableColumn<ScheduleTable, String> columnHours;
    public TableColumn<ScheduleTable, Rozklady> columnMon;
    public TableColumn<ScheduleTable, Rozklady> columnTue;
    public TableColumn<ScheduleTable, Rozklady> columnWen;
    public TableColumn<ScheduleTable, Rozklady> columnThu;
    public TableColumn<ScheduleTable, Rozklady> columnFri;
    public ComboBox comboboxChildren;
    private Integer id;
    private StudentMenuController.md mode;
    private Integer id_child;
    private ObservableList<Uczniowie> children;
    private ObservableList<GodzinyLekcji> hour;
    private ObservableList<ScheduleTable> schedule;
    public ComboBox comboBoxSubject;
    public ComboBox comboBoxTeacher;
    public ComboBox comboBoxClassroom;

    @FXML
    public void initialize() {
        table.getSelectionModel().setCellSelectionEnabled(true);
    }


    public Sale decorate(Sale classroom){
        Sale decoratedClassroom = new Sale() {
            @Override
            public String toString() {
                return this.getNazwa() + " (liczba miejsc: " + this.getLiczbaMiejsc() + (this.getCzyJestRzutnik() ? ", wyposażona w rzutnik)" : ")");
            }
        };
        decoratedClassroom.setNazwa(classroom.getNazwa());
        decoratedClassroom.setID(classroom.getID());
        decoratedClassroom.setCzyJestRzutnik(classroom.getCzyJestRzutnik());
        decoratedClassroom.setLiczbaMiejsc(classroom.getLiczbaMiejsc());
        comboBoxClassroom.getItems().add(decoratedClassroom);
        return decoratedClassroom;
    }


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


    public Przedmioty decorate(Przedmioty subject){
        Przedmioty decoratedSubject = new Przedmioty(){
            @Override
            public String toString() {
                return this.getNazwa();
            }
        };
        decoratedSubject.setID(subject.getID());
        decoratedSubject.setNazwa(subject.getNazwa());
        return decoratedSubject;
    }






    public void addAdminListeners() {

        table.getSelectionModel().getSelectedCells().addListener((ListChangeListener.Change<? extends TablePosition> change) -> {

            Rozklady selected = null;

            if (change.getList().size() > 0) {
                TablePosition<?,?> selectedCell = change.getList().get(0);
                selected = (Rozklady) selectedCell.getTableColumn().getCellObservableValue(selectedCell.getRow()).getValue();
            }

            if (selected != null) {
                comboBoxSubject.setDisable(false);
                comboBoxTeacher.setDisable(false);
                comboBoxClassroom.setDisable(false);
                comboBoxSubject.getSelectionModel().select(decorate((new Subject()).getSubjectById(selected.getIdPrzedmiotu())));
                comboBoxTeacher.getSelectionModel().select(decorate((new Teacher()).getTeacherById(selected.getIdNauczyciela())));
                comboBoxClassroom.getSelectionModel().select(decorate((new Classroom()).getClassroomById(selected.getIdKlasy())));
            }
            else {
                comboBoxSubject.setDisable(true);
                comboBoxTeacher.setDisable(true);
                comboBoxClassroom.setDisable(true);
            }
        });
    }

    public void fillAdminControls() {


        List<Sale> classrooms = (new Classroom()).getAllClassrooms();

        for (Sale classroom : classrooms) {
            comboBoxClassroom.getItems().add(decorate(classroom));
        }

        List<Przedmioty> subjects = (new Subject()).displaySubjects();
        for (Przedmioty subject : subjects) {
            comboBoxSubject.getItems().add(decorate(subject));
        }

        List<Nauczyciele> teachers = (new Teacher()).getAllTeachers();
        for (Nauczyciele tea : teachers) {
            comboBoxTeacher.getItems().add(decorate(tea));
        }
    }

    void setTable(Integer idKlasy) {
        schedule = (new ScheduleTable()).getSchedule(idKlasy,hour);

        columnNum.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        columnMon.setCellValueFactory(new PropertyValueFactory<>("mon"));
        columnTue.setCellValueFactory(new PropertyValueFactory<>("tue"));
        columnWen.setCellValueFactory(new PropertyValueFactory<>("wen"));
        columnThu.setCellValueFactory(new PropertyValueFactory<>("thu"));
        columnFri.setCellValueFactory(new PropertyValueFactory<>("fri"));


        table.setItems(schedule);

    }


    @Override
    public void receiveArguments(Map params) {
        mode = StudentMenuController.md.valueOf((String) params.get("mode"));

        if (mode == StudentMenuController.md.Admin) {
            hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
            Klasy firstClass = (Klasy) (new SchoolClass()).displayClass().get(0);
            setTable(firstClass.getID());
            comboboxChildren.setVisible(false);
            comboBoxClassroom.setVisible(true);
            comboBoxSubject.setVisible(true);
            comboBoxTeacher.setVisible(true);

            comboBoxSubject.setDisable(true);
            comboBoxTeacher.setDisable(true);
            comboBoxClassroom.setDisable(true);

            fillAdminControls();
            addAdminListeners();
        }
        else if (mode == StudentMenuController.md.Parent) {
            children = FXCollections.observableArrayList((new Student()).getParentsChildren(id));
            id = (Integer) params.get("id");

            if (!children.isEmpty()) {
                for (Uczniowie act : children) {
                    comboboxChildren.getItems().add(act.getImie() + " " + act.getNazwisko());
                }
                comboboxChildren.getSelectionModel().select(0);
                id_child = children.get(0).getID();
                hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
                setTable((new Student()).getStudentById(id_child).getIdKlasy());
            }
        }
        else {
            id = (Integer) params.get("id");
            comboboxChildren.setVisible(false);
            id_child = id;
            hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
            setTable((new Student()).getStudentById(id_child).getIdKlasy());
        }

    }


    public void clickButtonBack() throws IOException {
        if (mode == StudentMenuController.md.Admin)
            Main.setRoot("menu/adminMenuForm", WindowSize.adminMenuForm);
        else {
            Map params = new HashMap<String, String>();
            params.put("mode", mode.toString());
            params.put("id", id);
            Main.setRoot("menu/studentMenuForm", params);
        }
    }

    public void changeComboboxChildren(ActionEvent actionEvent) {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        schedule.clear();
        setTable((new Student()).getStudentById(id_child).getIdKlasy());
    }


}
