package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.Oceny;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class studentGradesController implements ParametrizedController {
    public TableView<Oceny> table;
    public TableColumn<Oceny, String> columnSubject;
    public TableColumn<Oceny, Integer> columnGrade;
    public TableColumn<Oceny, Date> columnDate;
    public TableColumn<Oceny, String> columnDescription;
    public TableColumn<Oceny, Integer> columnWeight;
    public Button buttonBack;
    public ComboBox<String> comboboxChildren;

    private Integer id;
    private StudentMenuController.md mode;
    private Integer id_child;
    private ObservableList<Uczniowie> children;
    private ObservableList<Oceny> list;

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


    public void clickButtonBack(ActionEvent actionEvent) throws IOException {
        Map params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm", params);
    }

    public void changeComboboxChildren(ActionEvent actionEvent) {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        list.clear();
        setTable();
    }
}
