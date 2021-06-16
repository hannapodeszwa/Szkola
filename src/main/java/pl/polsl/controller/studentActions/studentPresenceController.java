package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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


public class studentPresenceController implements ParametrizedController {

    @FXML
    public TableView<Presentv2> table;
    public TableColumn<Presentv2, Date> columnDate;
    public TableColumn<Presentv2, Integer> columnHour;
    public TableColumn<Presentv2, CheckBox> columnCheck;
    public TableColumn<Presentv2, String> columnSubject;
    public Button buttonBack;
    public ComboBox comboboxChildren;
    public AnchorPane window;

    private StudentMenuController.md mode;
    private Integer id;
    private Integer id_child;
    private Map params;
    private List<Nieobecnosci> list;
    private ObservableList<Presentv2> data = FXCollections.observableArrayList();
    private ObservableList<Uczniowie> children;


    private void setTable() {
        list = (new Present()).displayPresent(id_child);

        for (Nieobecnosci a : list) {
            data.add(new Presentv2(a));
        }

        columnDate.setCellValueFactory(new PropertyValueFactory<Presentv2, Date>("data"));
        columnHour.setCellValueFactory(new PropertyValueFactory<Presentv2, Integer>("godzina"));
        columnCheck.setCellValueFactory(new PropertyValueFactory<Presentv2, CheckBox>("czyUsp"));


        columnSubject.setCellValueFactory(CellData -> {
            Integer idPrzedmiotu = CellData.getValue().getPrzedmiotyId();
            String nazwaPrzedmiotu = (new Subject()).getSubjectName(idPrzedmiotu);
            return new ReadOnlyStringWrapper(nazwaPrzedmiotu);
        });

        table.setItems(data);


    }

    private void saveData() {

        for (Presentv2 a : data) {
            if (a.Usp.isSelected() != (a.czyUsprawiedliwiona != 0)) {
                for (Nieobecnosci find : list) {
                    if (a.ID == find.ID) {
                        if (a.Usp.isSelected() == true) {
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
        } else {
            comboboxChildren.setVisible(false);

            id_child = id;
            setTable();
        }


        if (mode == StudentMenuController.md.Student) {
            for (Presentv2 a : data)
                a.Usp.setDisable(true);
        }
    }


    public void clickButtonBack(ActionEvent event) throws IOException {
        params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm", params);

        if (mode == StudentMenuController.md.Parent)
            saveData();

    }



    public void changeComboboxChildren(ActionEvent actionEvent) {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        saveData();
        list.clear();
        data.clear();
        setTable();
    }
}
