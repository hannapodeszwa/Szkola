package pl.polsl.controller.common;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Wiadomosci;
import pl.polsl.model.MessageModel;
import pl.polsl.model.Teacher;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Types:
0 - from student to teacher
1 - from teacher to student
2 - from parent to teacher
3 - from teacher to parent
 */

public class MessageController implements ParametrizedController {

    private enum messageTypes {
        studentTeacher(0),
        teacherStudent(1),
        parentTeacher(2),
        teacherParent(3);
        private final Integer value;

        messageTypes(Integer value) {
            this.value = value;
        }
    }

    private Teacher teacherModel;
    private String previousLocation;
    private String role;
    private Integer id;
    private MessageModel messageModel;
    private List<Wiadomosci> receivedList;
    private List sentList;
    private Boolean firstTabSelected;

    @FXML
    private TableColumn<Map, String> senderRColumn;

    @FXML
    private TableColumn<Map, String> topicRColumn;

    @FXML
    private TableColumn<Map, Date> dateRColumn;

    @FXML
    private TabPane messagesTabPane;

    @FXML
    private TableView<Map<String, Object>> receivedTable;


    @FXML
    public void initialize() {
        teacherModel = new Teacher();
        messageModel = new MessageModel();
        senderRColumn.setCellValueFactory(new MapValueFactory<>("senderRColumn"));
        topicRColumn.setCellValueFactory(new MapValueFactory<>("topicRColumn"));
        dateRColumn.setCellValueFactory(new MapValueFactory<>("dateRColumn"));
        messagesTabPane.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTab, newTab) -> {
                    firstTabSelected = newTab.getId().equals("receivedTab");
                }
        );
    }

    @Override
    public void receiveArguments(Map params) {
        previousLocation = (String) params.get("previousLocation");
        role = (String) params.get("role");
        id = (Integer) params.get("id");
        switch (role) {
            case "uczen":
                receivedList = messageModel.getReceivedMessagesByTypeAndId(id, messageTypes.teacherStudent.value);
                sentList = messageModel.getSentMessagesByTypeAndId(id, messageTypes.studentTeacher.value);
                break;
            case "rodzic":
                receivedList = messageModel.getReceivedMessagesByTypeAndId(id, messageTypes.teacherParent.value);
                sentList = messageModel.getSentMessagesByTypeAndId(id, messageTypes.parentTeacher.value);
                break;
        }
    }

    public void backButtonAction() throws IOException {
        Main.setRoot(previousLocation);
    }

    public void newMessageButtonAction() {

    }

    public void showMessagesButtonAction() {
        if(firstTabSelected)
            displayReceivedMessages();
        else
            displaySentMessages();
    }

    private void displayReceivedMessages() {
        ObservableList<Map<String, Object>> items =
                FXCollections.observableArrayList();
        String fullName;

        if (!receivedList.isEmpty()) {
            switch (role) {
                case "uczen":
                case "rodzic": {
                    for (Wiadomosci message : receivedList) {
                        Nauczyciele nauczyciele = teacherModel.getTeacherById(message.getNadawca());
                        fullName = nauczyciele.getImie() + " " + nauczyciele.getNazwisko();
                        Map<String, Object> item = new HashMap<>();
                        item.put("senderRColumn", fullName);
                        item.put("topicRColumn", message.getTemat());
                        item.put("dateRColumn", message.getData());
                        items.add(item);
                    }
                    receivedTable.getItems().addAll(items);
                    break;
                }
                case "nauczyciel": {
                    /*for (Wiadomosci message : receivedList) {
                        Nauczyciele nauczyciele = teacherModel.getTeacherById(message.getNadawca());
                        fullName = nauczyciele.getImie() + " " + nauczyciele.getNazwisko();
                        Map<String, Object> item = new HashMap<>();
                        item.put("senderRColumn", fullName);
                        item.put("topicRColumn", message.getTemat());
                        item.put("dateRColumn", message.getData());
                        items.add(item);
                    }
                    receivedTable.getItems().addAll(items);
                    break;*/
                }
            }
        }
    }

    void displaySentMessages(){
        ObservableList<Map<String, Object>> items =
                FXCollections.observableArrayList();
        String fullName;
    }
}
