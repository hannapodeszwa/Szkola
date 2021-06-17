package pl.polsl.controller.common;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Wiadomosci;
import pl.polsl.model.MessageModel;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewMessageController implements ParametrizedController {

    private String previousLocation;
    private String role;
    private Integer id;
    private String login;
    private String topic, correspondent, type;
    private Date date;
    private MessageModel messageModel;

    @FXML
    private TextField topicTextField;

    @FXML
    private TextField senderTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextArea messageTextArea;

    @FXML
    public void initialize() {

    }

    @Override
    public void receiveArguments(Map params) {
        messageModel = new MessageModel();
        previousLocation = (String) params.get("previousLocation");
        role = (String) params.get("role");
        id = (Integer) params.get("id");
        login = (String) params.get("login");
        topic = (String) params.get("topic");
        date = (Date) params.get("date");
        correspondent = (String) params.get("correspondent");
        type = (String) params.get("type");

        displayMessage();
    }

    public void backButtonAction() throws IOException {
        returnFromMessageViewer();
    }

    public void respondButtonAction() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", previousLocation);
        parameters.put("role", role);
        parameters.put("id", id);
        parameters.put("login", login);
        parameters.put("correspondent", correspondent);
        parameters.put("topic", "RE: " + topic);
        Main.setRoot("common/messageForm", parameters, 800.0, 450.0);
    }

    private void displayMessage() {
        topicTextField.setText(topic);
        senderTextField.setText(correspondent);
        dateTextField.setText("Wysłano " + date.toString());
        String correspondentLogin = getLoginFromCorrespondentsName();
        if(type.equals("received"))
            messageTextArea.setText(messageModel.getMessageByReceiverSenderAndDat(login, correspondentLogin, date));
        else
            messageTextArea.setText(messageModel.getMessageByReceiverSenderAndDat(correspondentLogin, login, date));
    }

    private void returnFromMessageViewer() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", previousLocation);
        parameters.put("role", role);
        parameters.put("id", id);
        parameters.put("login", login);
        Main.setRoot("common/messengerForm", parameters, 800.0, 450.0);
    }

    private String getLoginFromCorrespondentsName(){
        String[] correspondentNickname = correspondent.split(" ");
        correspondentNickname[2] = correspondentNickname[2].replace("[", "");
        return correspondentNickname[2].replace("]", "");
    }
}
