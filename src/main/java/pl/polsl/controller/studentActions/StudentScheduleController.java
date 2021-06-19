package pl.polsl.controller.studentActions;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentScheduleController implements ParametrizedController {

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
    public ComboBox<Klasy> comboboxClasses;
    public Label warnings;
    private Integer id;
    private StudentMenuController.md mode;
    private Integer id_child;
    private ObservableList<Uczniowie> children;
    private ObservableList<GodzinyLekcji> hour;
    private ObservableList<ScheduleTable> schedule;
    public ComboBox<Przedmioty> comboBoxSubject;
    public ComboBox<Nauczyciele> comboBoxTeacher;
    public ComboBox<Sale> comboBoxClassroom;

    private Rozklady selected = null;
    private TablePosition<?,?> selectedCell;
    private Integer idKlasy;


    private void checkProblems() {
        String problems = "";
        //If the classroom is too small to fit all students in
        if (comboBoxClassroom.getSelectionModel().getSelectedItem() != null &&
                comboBoxClassroom.getSelectionModel().getSelectedItem().getLiczbaMiejsc() <
                (new SchoolClass()).getStudentsByClass(comboboxClasses.getSelectionModel().getSelectedItem()).size()) {
            problems += "Wybrana sala jest za mała\n";
        }

        //If the classroom has another lesson at the same time
        if (comboBoxClassroom.getSelectionModel().getSelectedItem() != null &&
                (new ScheduleModel()).findByClassroom(comboBoxClassroom.getSelectionModel().getSelectedItem()).stream()
                        .anyMatch(s ->
                                s.getGodzina().equals(selected.getGodzina())
                                        && s.getDzien().equals(selected.getDzien())
                                        && !s.getIdKlasy().equals(selected.getIdKlasy())
                        )) {
            problems += "Wybrana sala jest już zajęta\n";
        }

        //If the teacher has another lesson at the same time
        if (comboBoxTeacher.getSelectionModel().getSelectedItem() != null &&
                (new ScheduleModel()).findByTeacher(comboBoxTeacher.getSelectionModel().getSelectedItem()).stream()
                        .anyMatch(r ->
                                r.getGodzina().equals(selected.getGodzina())
                                        && r.getDzien().equals(selected.getDzien())
                                        && !r.getIdKlasy().equals(selected.getIdKlasy())
                        )) {
            problems += "Wybrany nauczyciel prowadzi w tym czasie inne zajęcia\n";
        }
        warnings.setText(problems);
    }

    public void prepareNewSchedule() {
        String weekDay = selectedCell.getTableColumn().getText();
        switch (weekDay){
            case "Poniedziałek":
                selected.setDzien("pon");
                break;
            case "Wtorek":
                selected.setDzien("wto");
                break;
            case "Środa":
                selected.setDzien("sro");
                break;
            case "Czwartek":
                selected.setDzien("czw");
                break;
            case "Piątek":
                selected.setDzien("pia");
                break;
        }
        selected.setGodzina(selectedCell.getRow() + 1);
        selected.setIdKlasy(idKlasy);
    }

    @FXML
    public void initialize() {
        table.getSelectionModel().setCellSelectionEnabled(true);
    }

    public void adminInitialize() {
        comboBoxSubject.getSelectionModel().selectedItemProperty().addListener ((observable, oldSelection, newSelection) -> {
            if (selectedCell.getColumn() >= 2) {
                if (newSelection == null) {
                    if (selected != null) {
                        (new ScheduleModel()).delete(selected);
                        refreshTable();
                        comboBoxTeacher.getSelectionModel().select(null);
                        comboBoxTeacher.setDisable(true);
                        comboBoxClassroom.getSelectionModel().select(null);
                        comboBoxClassroom.setDisable(true);
                    }
                }
                else {
                    if (newSelection != null && selected == null) {
                        selected = new Rozklady();
                        selected.setIdPrzedmiotu(newSelection.getID());
                        prepareNewSchedule();
                        (new ScheduleModel()).persist(selected);
                        refreshTable();
                        comboBoxTeacher.setDisable(false);
                        comboBoxClassroom.setDisable(false);
                    }
                    else if (!newSelection.getID().equals(selected.getIdPrzedmiotu())) {
                        selected.setIdPrzedmiotu(newSelection.getID());
                        (new ScheduleModel()).update(selected);
                        refreshTable();
                        comboBoxTeacher.setDisable(false);
                        comboBoxClassroom.setDisable(false);
                    }
                }
            }
            checkProblems();
        });

        comboBoxTeacher.getSelectionModel().selectedItemProperty().addListener ((observable, oldSelection, newSelection) -> {
            if (selected != null) {
                if (newSelection == null) {
                    if (selected.getIdNauczyciela() != null) {
                        selected.setIdNauczyciela(null);
                        (new ScheduleModel()).update(selected);
                        refreshTable();
                    }
                }
                else if (!newSelection.getID().equals(selected.getIdNauczyciela())) {
                    selected.setIdNauczyciela(newSelection.getID());
                    (new ScheduleModel()).update(selected);
                    refreshTable();
                }
            }
            checkProblems();
        });

        comboBoxClassroom.getSelectionModel().selectedItemProperty().addListener ((observable, oldSelection, newSelection) -> {
            if (selected != null) {
                if (newSelection == null) {
                    if (selected.getIdSali() != null) {
                        selected.setIdSali(null);
                        (new ScheduleModel()).update(selected);
                        refreshTable();
                    }
                }
                else if (!newSelection.getID().equals(selected.getIdSali())) {
                    selected.setIdSali(newSelection.getID());
                    (new ScheduleModel()).update(selected);
                    refreshTable();
                }
            }
            checkProblems();
        });


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


    public Klasy decorate(Klasy classes){
        Klasy decoratedClass = new Klasy(){
            @Override
            public String toString() {
                return this.getNumer();
            }
        };
        decoratedClass.setID(classes.getID());
        decoratedClass.setIdPrzewodniczacego(classes.getIdPrzewodniczacego());
        decoratedClass.setIdWychowawcy(classes.getIdWychowawcy());
        decoratedClass.setNumer(classes.getNumer());
        return decoratedClass;
    }


    private ListChangeListener<? extends TablePosition> cellSelectListener = (ListChangeListener.Change<? extends TablePosition> change) -> {
        selected = null;

        if (change.getList().size() > 0) {
            selectedCell = change.getList().get(0);
            Object val = selectedCell.getTableColumn().getCellObservableValue(selectedCell.getRow()).getValue();
            if (val != null && val.getClass() == Rozklady.class)
                selected = (Rozklady) val;
        }

        if (selected != null) {
            comboBoxSubject.setDisable(false);
            comboBoxTeacher.setDisable(false);
            comboBoxClassroom.setDisable(false);

            Przedmioty selectedSubject = decorate((new Subject()).getSubjectById(selected.getIdPrzedmiotu()));
            comboBoxSubject.getSelectionModel().select(selectedSubject);

            if (selected.getIdNauczyciela() != null) {
                Nauczyciele selectedTeacher = decorate((new Teacher()).getTeacherById(selected.getIdNauczyciela()));
                comboBoxTeacher.getSelectionModel().select(selectedTeacher);
            }
            else {
                comboBoxTeacher.getSelectionModel().select(null);
            }
            if (selected.getIdSali() != null) {
                Sale selectedClassroom = decorate((new Classroom()).getClassroomById(selected.getIdSali()));
                comboBoxClassroom.getSelectionModel().select(selectedClassroom);
            }
            else {
                comboBoxClassroom.getSelectionModel().select(null);
            }
        }
        else {
            comboBoxSubject.getSelectionModel().select(null);
            comboBoxSubject.setDisable(false);
            comboBoxTeacher.getSelectionModel().select(null);
            comboBoxTeacher.setDisable(true);
            comboBoxClassroom.getSelectionModel().select(null);
            comboBoxClassroom.setDisable(true);
        }
        checkProblems();
    };



    public void addAdminListeners() {
        table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) cellSelectListener);
    }

    public void fillAdminControls() {

        comboBoxClassroom.getItems().add(null);
        comboBoxSubject.getItems().add(null);
        comboBoxTeacher.getItems().add(null);

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

        List<Klasy> classes = (new SchoolClass()).displayClass();
        for (Klasy c : classes) {
            comboboxClasses.getItems().add(decorate(c));
        }
        comboboxClasses.getSelectionModel().selectFirst();
    }

    void setTable() {
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

    void refreshTable() {
        Integer row = -1;
        Integer column = -1;
        if (selectedCell != null) {
            row = selectedCell.getRow();
            column = selectedCell.getColumn();
        }
        Integer finalRow = row;
        Integer finalColumn = column;
        //Platform.runLater(() -> {
            System.out.println("Refreshing table");
            table.getSelectionModel().getSelectedCells().removeListener((ListChangeListener<? super TablePosition>) cellSelectListener);
            table.getItems().clear();
            schedule = (new ScheduleTable()).getSchedule(idKlasy,hour);
            table.setItems(schedule);

            if (finalRow != -1) {
                table.getSelectionModel().clearAndSelect(finalRow, table.getVisibleLeafColumn(finalColumn));
                table.getFocusModel().focus(finalRow, table.getVisibleLeafColumn(finalColumn));
            }

            table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) cellSelectListener);
        //});
    }


    @Override
    public void receiveArguments(Map params) {
        mode = StudentMenuController.md.valueOf((String) params.get("mode"));

        if (mode == StudentMenuController.md.Admin) {
            adminInitialize();
            hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
            Klasy firstClass = (Klasy) (new SchoolClass()).displayClass().get(0);
            idKlasy = firstClass.getID();
            setTable();
            comboboxChildren.setVisible(false);
            comboBoxClassroom.setVisible(true);
            comboBoxSubject.setVisible(true);
            comboBoxTeacher.setVisible(true);
            comboboxClasses.setVisible(true);

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
                idKlasy = (new Student()).getStudentById(id_child).getIdKlasy();
                setTable();
            }
        }
        else {
            id = (Integer) params.get("id");
            comboboxChildren.setVisible(false);
            id_child = id;
            hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
            idKlasy = (new Student()).getStudentById(id_child).getIdKlasy();
            setTable();
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
        idKlasy = (new Student()).getStudentById(id_child).getIdKlasy();
        setTable();
    }

    public void changeComboboxClasses(ActionEvent actionEvent) {
        idKlasy = comboboxClasses.getSelectionModel().getSelectedItem().getID();
        //comboBoxSubject.getSelectionModel().select(null);
        comboBoxSubject.setDisable(true);
        //comboBoxTeacher.getSelectionModel().select(null);
        comboBoxTeacher.setDisable(true);
        //comboBoxClassroom.getSelectionModel().select(null);
        comboBoxClassroom.setDisable(true);
        refreshTable();
    }
}