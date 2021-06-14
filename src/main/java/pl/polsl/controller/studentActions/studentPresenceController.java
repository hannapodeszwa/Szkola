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
import pl.polsl.model.SchoolClass;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class studentPresenceController implements ParametrizedController {

    @FXML
    public TableView<Nieobecnosci> table;
    public TableColumn<Nieobecnosci, Date> columnDate;
    public TableColumn<Nieobecnosci, Integer> columnHour;
    public TableColumn<Nieobecnosci, CheckBox> columnCheck;
    public TableColumn<Nieobecnosci, String> columnSubject;
    public Button buttonBack;

    private StudentMenuController.md mode;
    private Integer id;
    private Map params;
    private List<Nieobecnosci> list;
    private ObservableList<Nieobecnosci> data = FXCollections.observableArrayList();


    @Override
    public void receiveArguments(Map params) {
        mode = StudentMenuController.md.valueOf((String)params.get("mode"));
        id = (Integer) params.get("id");

    }


    public void initialize() {

        table.setEditable(true);
        Present s = new Present();
        list = s.displayPresent();
        data = FXCollections.observableArrayList((list));

        columnDate.setCellValueFactory(new PropertyValueFactory<Nieobecnosci, Date>("data"));
        columnHour.setCellValueFactory(new PropertyValueFactory<Nieobecnosci, Integer>("godzina"));
        columnHour.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnCheck.setCellValueFactory(new PropertyValueFactory<Nieobecnosci, CheckBox>("czyUsprawiedliwiona"));




        columnSubject.setCellValueFactory(CellData -> {
            Integer idPrzedmiotu = CellData.getValue().getPrzedmiotyId();
            String nazwaPrzedmiotu = (new Present()).getSubjectName(idPrzedmiotu);
            return new ReadOnlyStringWrapper(nazwaPrzedmiotu);
        });

        table.setItems(data);


    }

    public void clickButtonBack(ActionEvent event) throws IOException
    {
        params = new HashMap<String, String>();
        params.put("mode", mode.toString());
        params.put("id", id);
        Main.setRoot("menu/studentMenuForm",params);
        Integer pos = 0;
        for (Nieobecnosci a: table.getItems()){
            Integer ida = a.getID();

            data.set(pos,a);
            pos++;
        }
        Integer b =0 ;
    }

    public void onEditChange(TableColumn.CellEditEvent<Nieobecnosci, Integer> nieobecnosciDateCellEditEvent) {
        Nieobecnosci nieb = table.getSelectionModel().getSelectedItem();
        nieb.setGodzina(nieobecnosciDateCellEditEvent.getNewValue());

    }

    public void CheckCancel(TableColumn.CellEditEvent<Nieobecnosci, CheckBox> nieobecnosciCheckBoxCellEditEvent) {
        Integer a = 2;
    }

    public void CheckCommit(TableColumn.CellEditEvent<Nieobecnosci, CheckBox> nieobecnosciCheckBoxCellEditEvent) {
        Integer a = 2;
    }

    public void CheckStart(TableColumn.CellEditEvent<Nieobecnosci, CheckBox> nieobecnosciCheckBoxCellEditEvent) {
        Integer a = 2;
    }

    // public void clickColumnCheck (){
   //     if(event.getClickCount() == 2){
   //         buttonBack.setText("2");
   //     }
   // }


}
