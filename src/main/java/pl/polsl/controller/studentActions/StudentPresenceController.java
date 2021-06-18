package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Present;
import pl.polsl.model.Presentv2;
import pl.polsl.model.Student;
import pl.polsl.model.Subject;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentPresenceController implements ParametrizedController {

    @FXML
    public TableView<Presentv2> table;
    public TableColumn<Presentv2, Date> columnDate;
    public TableColumn<Presentv2, Integer> columnHour;
    public TableColumn<Presentv2, CheckBox> columnCheck;
    public TableColumn<Presentv2, String> columnSubject;
    public Button buttonBack;
    public ComboBox<String> comboboxChildren;
    public AnchorPane window;

    private StudentMenuController.md mode;
    private Integer id;
    private Integer id_child;
    private List<Nieobecnosci> list;
    private final ObservableList<Presentv2> data = FXCollections.observableArrayList();
    private ObservableList<Uczniowie> children;


    private void setTable() {
        list = (new Present()).displayPresent(id_child);

        for (Nieobecnosci a : list) {
            data.add(new Presentv2(a));
        }

        columnDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        columnHour.setCellValueFactory(new PropertyValueFactory<>("godzina"));//TODO poprawić na numer, nie id
        columnCheck.setCellValueFactory(new PropertyValueFactory<>("czyUsp"));
        columnSubject.setCellValueFactory(CellData -> {
            Integer idPrzedmiotu = CellData.getValue().getPrzedmiotyId();
            String nazwaPrzedmiotu = (new Subject()).getSubjectName(idPrzedmiotu);
            return new ReadOnlyStringWrapper(nazwaPrzedmiotu);
        });

        table.setItems(data);


    }

    private void saveData() {

        for (Presentv2 a : data) {
            if (a.Usp.isSelected() == (a.czyUsprawiedliwiona == 0)) {
                for (Nieobecnosci find : list) {
                    if (a.ID.equals(find.ID)) {
                        if (a.Usp.isSelected()) {
                            find.setCzyUsprawiedliwiona(1);
                        } else {
                            find.setCzyUsprawiedliwiona(0);
                        }
                        (new Present()).update(find);
                        break;
                    }
                }
            }
        }
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

        if (mode == StudentMenuController.md.Student) {
            for (Presentv2 a : data)
                a.Usp.setDisable(true);
        }
    }


    public void clickButtonBack() throws IOException {
        Map params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm", params);

        if (mode == StudentMenuController.md.Parent)
            saveData();

    }


    public void changeComboboxChildren() {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        saveData();
        list.clear();
        data.clear();
        setTable();
    }
}
