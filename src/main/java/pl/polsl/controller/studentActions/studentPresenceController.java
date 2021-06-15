package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Present;
import pl.polsl.model.Presentv2;
import pl.polsl.model.SchoolClass;

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

    private StudentMenuController.md mode;
    private Integer id;
    private Map params;
    private List<Nieobecnosci> list;
    private ObservableList<Presentv2> data = FXCollections.observableArrayList();


    @Override
    public void receiveArguments(Map params) {
        mode = StudentMenuController.md.valueOf((String) params.get("mode"));
        id = (Integer) params.get("id");

    }


    public void initialize() {

        table.setEditable(true);
        Present s = new Present();
        list = s.displayPresent();

        for (Nieobecnosci a : list) {
            data.add(new Presentv2(a));
        }

        columnDate.setCellValueFactory(new PropertyValueFactory<Presentv2, Date>("data"));
        columnHour.setCellValueFactory(new PropertyValueFactory<Presentv2, Integer>("godzina"));
        columnCheck.setCellValueFactory(new PropertyValueFactory<Presentv2, CheckBox>("czyUsp"));


        columnSubject.setCellValueFactory(CellData -> {
            Integer idPrzedmiotu = CellData.getValue().getPrzedmiotyId();
            String nazwaPrzedmiotu = (new Present()).getSubjectName(idPrzedmiotu);
            return new ReadOnlyStringWrapper(nazwaPrzedmiotu);
        });

        table.setItems(data);


    }

    public void clickButtonBack(ActionEvent event) throws IOException {
        params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm", params);


        Integer index = 0;
        for (Presentv2 a : data) {

            if (a.Usp.isSelected() != (a.czyUsprawiedliwiona != 0)) {
                Nieobecnosci find = list.get(index);
                if (a.Usp.isSelected() == true) {
                    find.setCzyUsprawiedliwiona(1);
                } else {
                    find.setCzyUsprawiedliwiona(0);
                }
                (new Present()).update(find);
            }
            index++;
        }

    }

    public void onEditChange(TableColumn.CellEditEvent<Nieobecnosci, Integer> nieobecnosciDateCellEditEvent) {
        Nieobecnosci nieb = table.getSelectionModel().getSelectedItem();
        nieb.setGodzina(nieobecnosciDateCellEditEvent.getNewValue());

    }


}
