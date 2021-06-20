
package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Oceny;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Grade;
import pl.polsl.model.Student;
import pl.polsl.model.Subject;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StudentGradesController implements ParametrizedController {

    @FXML
    private TableView<Oceny> table;
    @FXML
    private TableColumn<Oceny, String> columnSubject;
    @FXML
    private TableColumn<Oceny, Integer> columnGrade;
    @FXML
    private TableColumn<Oceny, Date> columnDate;
    @FXML
    private TableColumn<Oceny, String> columnDescription;
    @FXML
    private TableColumn<Oceny, Integer> columnWeight;
    @FXML
    private ComboBox<String> comboboxChildren;

    private Integer id;
    private String mode;
    private Integer id_child;
    private ObservableList<Uczniowie> children;
    private ObservableList<Oceny> list;

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String) params.get("mode");
        id = (Integer) params.get("id");

        if (mode.equals(Roles.PARENT)) {
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

    void setTable(){

        list = FXCollections.observableArrayList((new Grade()).getGradeByStudent(id_child));

        columnGrade.setCellValueFactory(new PropertyValueFactory<>("ocena"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<>("waga"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("opis"));
        columnSubject.setCellValueFactory(CellData -> {
            Integer idPrzedmiotu = CellData.getValue().getIdPrzedmiotu();
            String nazwaPrzedmiotu = (new Subject()).getSubjectName(idPrzedmiotu);
            return new ReadOnlyStringWrapper(nazwaPrzedmiotu);
        });

        table.setItems(list);
    }

    public void changeComboboxChildren() {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        list.clear();
        setTable();
    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        if (mode.equals(Roles.PARENT))
            Main.setRoot("menu/studentMenuForm", params, WindowSize.studenMenuForm);
        else
            Main.setRoot("menu/studentMenuForm", params,WindowSize.parentMenuForm);
    }
}