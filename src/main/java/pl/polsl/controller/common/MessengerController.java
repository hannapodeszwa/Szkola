package pl.polsl.controller.common;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Wiadomosci;
import pl.polsl.model.MessageModel;
import pl.polsl.model.Teacher;


import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class MessengerController implements ParametrizedController {

    private final String teacherRole = "nauczyciel";
    private final String studentRole = "uczen";
    private final String parentRole = "rodzic";

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
    private List<Wiadomosci> sentList;
    private Boolean firstTabSelected;

    @FXML
    private TableColumn<Map, String> senderRColumn;

    @FXML
    private TableColumn<Map, String> topicRColumn;

    @FXML
    private TableColumn<Map, Date> dateRColumn;

    @FXML
    private TableColumn<Map, String> receiverSColumn;

    @FXML
    private TableColumn<Map, String> topicSColumn;

    @FXML
    private TableColumn<Map, Date> dateSColumn;

    @FXML
    private TabPane messagesTabPane;

    @FXML
    private TableView<Map<String, Object>> receivedTable;

    @FXML
    private TableView<Map<String, Object>> sentTable;


    @FXML
    public void initialize() {
        firstTabSelected = true;
        teacherModel = new Teacher();
        messageModel = new MessageModel();
        receivedTable.setRowFactory(table -> {
            TableRow<Map<String, Object>> row = new TableRow<>();
            row.setOnMouseClicked(event->{
                if(event.getClickCount() == 2 && !row.isEmpty()){
                    Map<String, Object> item = row.getItem();

                }
            });
            return row;
        });

        sentTable.setRowFactory(table -> {
            TableRow<Map<String, Object>> row = new TableRow<>();
            row.setOnMouseClicked(event->{
                if(event.getClickCount() == 2 && !row.isEmpty()){
                    Map<String, Object> item = row.getItem();

                }
            });
            return row;
        });
    }

    @Override
    public void receiveArguments(Map params) {
        senderRColumn.setCellValueFactory(new MapValueFactory<>("senderRColumn"));
        topicRColumn.setCellValueFactory(new MapValueFactory<>("topicRColumn"));
        dateRColumn.setCellValueFactory(new MapValueFactory<>("dateRColumn"));
        receiverSColumn.setCellValueFactory(new MapValueFactory<>("receiverSColumn"));
        topicSColumn.setCellValueFactory(new MapValueFactory<>("topicSColumn"));
        dateSColumn.setCellValueFactory(new MapValueFactory<>("dateSColumn"));
        messagesTabPane.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTab, newTab) -> {
                    firstTabSelected = newTab.getId().equals("receivedTab");
                    if (firstTabSelected) displayReceivedMessages();
                    else displaySentMessages();
                });
        previousLocation = (String) params.get("previousLocation");
        role = (String) params.get("role");
        id = (Integer) params.get("id");
        switch (role) {
            case studentRole:
                receivedList = messageModel.getReceivedMessagesByTypeAndId(id, messageTypes.teacherStudent.value);
                sentList = messageModel.getSentMessagesByTypeAndId(id, messageTypes.studentTeacher.value);
                break;
            case parentRole:
                receivedList = messageModel.getReceivedMessagesByTypeAndId(id, messageTypes.teacherParent.value);
                sentList = messageModel.getSentMessagesByTypeAndId(id, messageTypes.parentTeacher.value);
                break;
            case teacherRole:
                List<Wiadomosci> tmpReceivedList = messageModel.getReceivedMessagesByTypeAndId(id, messageTypes.studentTeacher.value);
                receivedList = messageModel.getReceivedMessagesByTypeAndId(id, messageTypes.parentTeacher.value);
                receivedList.addAll(tmpReceivedList);

                List<Wiadomosci> tmpSentList = messageModel.getSentMessagesByTypeAndId(id, messageTypes.teacherStudent.value);
                sentList = messageModel.getSentMessagesByTypeAndId(id, messageTypes.teacherParent.value);
                sentList.addAll(tmpSentList);
        }
        displayReceivedMessages();
    }

    public void backButtonAction() throws IOException {
        Main.setRoot(previousLocation);
    }

    public void newMessageButtonAction() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", previousLocation);
        parameters.put("role", role);
        parameters.put("id", id);
        Main.setRoot("common/messageForm", parameters, 800.0, 450.0);
    }

    public void refreshMessagesButtonAction() {
        if (firstTabSelected)
            displayReceivedMessages();
        else
            displaySentMessages();
    }

    private void displayReceivedMessages() {
        receivedTable.getItems().clear();
        ObservableList<Map<String, Object>> items =
                FXCollections.observableArrayList();
        String fullName;

        if (!receivedList.isEmpty()) {
            switch (role) {
                case studentRole:
                case parentRole: {
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
                case teacherRole: {
                    break;
                }
            }
        }
    }

    void displaySentMessages() {
        sentTable.getItems().clear();
        ObservableList<Map<String, Object>> items =
                FXCollections.observableArrayList();
        String fullName;
        if (!sentList.isEmpty()) {
            switch (role) {
                case parentRole:
                case studentRole: {
                    for (Wiadomosci message : sentList) {
                        Nauczyciele nauczyciele = teacherModel.getTeacherById(message.getOdbiorca());
                        fullName = nauczyciele.getImie() + " " + nauczyciele.getNazwisko();
                        Map<String, Object> item = new HashMap<>();
                        item.put("receiverSColumn", fullName);
                        item.put("topicSColumn", message.getTemat());
                        item.put("dateSColumn", message.getData());
                        items.add(item);
                    }
                    sentTable.getItems().addAll(items);
                    break;
                }
                case teacherRole: {
                    break;
                }
            }
        }
    }
}
