package pl.polsl.controller.teacherActions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.GodzinyLekcji;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherScheduleController implements ParametrizedController {

    @FXML
    public TableView table;
    public TableColumn columnNum;
    public TableColumn columnHours;
    public TableColumn columnMon;
    public TableColumn columnTue;
    public TableColumn columnWen;
    public TableColumn columnThu;
    public TableColumn columnFri;
    public Button buttonBack;

    Integer id;
    private ObservableList<GodzinyLekcji> hour;
    private ObservableList<ScheduleTableTeacher> schedule;

    @Override
    public void receiveArguments(Map params) {
        id = (Integer) params.get("id");
        hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
        setTable();
    }

    void setTable() {
        schedule = (new ScheduleTableTeacher()).getSchedule(id,hour);

        columnNum.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        columnMon.setCellValueFactory(new PropertyValueFactory<>("mon"));
        columnTue.setCellValueFactory(new PropertyValueFactory<>("tue"));
        columnWen.setCellValueFactory(new PropertyValueFactory<>("wen"));
        columnThu.setCellValueFactory(new PropertyValueFactory<>("thu"));
        columnFri.setCellValueFactory(new PropertyValueFactory<>("fri"));


        table.setItems(schedule);
    }

    public void clickButtonBack() throws IOException {
        Map params = new HashMap<String, String>();
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm", params);
    }


}
