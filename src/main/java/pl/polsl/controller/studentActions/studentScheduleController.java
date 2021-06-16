package pl.polsl.controller.studentActions;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class studentScheduleController implements ParametrizedController {

    public Button buttonBack;
    public TableView table;
    public TableColumn columnNum;
    public TableColumn columnHours;
    public TableColumn columnTue;
    public TableColumn columnMon;
    public TableColumn columnWen;
    public TableColumn columnThu;
    public TableColumn columnFri;
    public ComboBox comboboxChildren;
    private Integer id;
    private StudentMenuController.md mode;
    private Integer id_child;
    private ObservableList<Uczniowie> children;

    void setTable(){



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
                setTable();
            }
        }
        else {
            comboboxChildren.setVisible(false);
            id_child = id;
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
    }
}
