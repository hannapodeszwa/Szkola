package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uwagi;
import pl.polsl.model.*;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentNoteController implements ParametrizedController {

    @FXML
    private TableView<Uwagi> table;
    @FXML
    private TableColumn<Uwagi, String> columnTeacher;
    @FXML
    private TableColumn<Uwagi, String>  columnDesc;
    @FXML
    private ComboBox<String>  comboboxChildren;

    private StudentMenuController.md mode;
    private Integer id;
    private Integer id_child;
    private ObservableList<Uczniowie> children;

    @Override
    public void receiveArguments(Map<String, Object> params) {
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

    public Integer getWitdh(String text){
        return (int)(new Text(text)).getBoundsInLocal().getWidth();
    }

    public String wrapString(String wraping,Integer wid){
        Integer width = wid-10;
        if(getWitdh(wraping) < width) {
            return wraping;
        }
        StringBuilder result = new StringBuilder();
        String[] words = wraping.split(" ");
        List<Integer> sizewords = new ArrayList<>();

        for(String word : words){
            sizewords.add(getWitdh(word));
        }
        Integer act = 0;
        int i = 0;
        for(Integer size : sizewords) {
            if(size + act < width){
                result.append(words[i]).append(" ");
                act +=size+3;
            }
            else if (size + act >= width) {
                result.append("\n").append(words[i]).append(" ");
                act=size+3;
            }
            else {
                result.append("\n").append(words[i]).append("\n");
                act = 0;
            }
            i++;
        }


        return result.toString();
    }

    private void setTable(){
        ObservableList<Uwagi>  list = FXCollections.observableArrayList(new NoteModel().getStudentNote(id_child));

        columnDesc.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getTresc();
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnDesc.getWidth()));
        });

        columnTeacher.setCellValueFactory(CellData -> {
            Integer idTeacher = CellData.getValue().getIdNauczyciela();
            Nauczyciele act = (new Teacher()).getTeacherById(idTeacher);
            if(act != null)
            return new ReadOnlyStringWrapper(act.getImie() + " " + act.getNazwisko());
            else
                return new ReadOnlyStringWrapper("");
        });

        table.setItems(list);
    }

    public void changeComboboxChildren() {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        setTable();
    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode.toString());
        params.put("id", id);
        if (mode == StudentMenuController.md.Student)
            Main.setRoot("menu/studentMenuForm", params, WindowSize.studenMenuForm);
        else
            Main.setRoot("menu/studentMenuForm", params,WindowSize.parentMenuForm);
    }
}
