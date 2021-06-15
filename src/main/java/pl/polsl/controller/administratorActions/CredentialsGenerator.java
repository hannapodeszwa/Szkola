package pl.polsl.controller.administratorActions;
import javafx.application.Platform;
import pl.polsl.model.UserModel;
import pl.polsl.model.email.MailSenderModel;

import java.util.Locale;
import java.util.Random;

public interface CredentialsGenerator {
    default String generateLogin(String imie, String nazwisko) {
        String generatedLogin = (imie.length() >= 4 ? imie.substring(0,4) : imie) + (nazwisko.length() >= 3 ? nazwisko.substring(0,3) : nazwisko);
        generatedLogin = generatedLogin.toLowerCase();
        Random rand = new Random();
        while (true) {
        Integer num = rand.nextInt(900) + 100;
        if ((new UserModel()).getUserByLogin(generatedLogin + num) == null)
            return generatedLogin + num;
        }
    }
    default String generatePassword() {
        String generatedLogin = "";
        String letters = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            Integer index = rand.nextInt(letters.length());
            generatedLogin += letters.charAt(index);
        }
        return generatedLogin;
    }
    default void sendCredentialsByEmail(String login, String password, String emailAddress) {
        new Thread(() -> {
            MailSenderModel mailSenderModel = new MailSenderModel();
            mailSenderModel.setTopic("Twoje konto do aplikacji Szkoła jest już gotowe!");
            mailSenderModel.setMessageText("Twój login: " + login + "\nTwoje hasło: " + password);
            mailSenderModel.sendMail(emailAddress);
        }).start();
    }
}
