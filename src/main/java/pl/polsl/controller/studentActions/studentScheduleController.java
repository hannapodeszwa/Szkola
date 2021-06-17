package pl.polsl.controller.studentActions;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
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

    void setTable(){
        schedule = (new ScheduleTable()).getSchedule((new Student()).getStudentById(id_child).getIdKlasy(),hour);

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
        id = (Integer) params.get("id");

        if (mode == StudentMenuController.md.Parent) {
            children = FXCollections.observableArrayList((new Student()).getParentsChildren(id));

            if (!children.isEmpty()) {
                for (Uczniowie act : children) {
                    comboboxChildren.getItems().add(act.getImie() + " " + act.getNazwisko());
                }
                comboboxChildren.getSelectionModel().select(0);
                id_child = children.get(0).getID();
                hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
                setTable();
            }
        }
        else {
            comboboxChildren.setVisible(false);
            id_child = id;
            hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
            setTable();
        }


    }


    public void clickButtonBack() throws IOException {
        Map params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm", params);
    }

    public void changeComboboxChildren(ActionEvent actionEvent) {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        schedule.clear();
        setTable();
    }


}
